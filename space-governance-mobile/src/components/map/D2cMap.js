/* eslint-disable */
import D2c from 'd2c'
import axios from 'axios'
import turf from 'turf'

const http = axios.create()
export default {
  name: 'd2c-map',
  props: {
    option: {
      default() {
        return {
          style: {
            version: 8,
            sources: {},
            layers: [],
          },
          center: [108.45814149748155, 22.777444343312645],
          zoom: 12,
        }
      },
    },
    container: {
      default: `map-${new Date().getTime()}`,
    },
    D2c: {
      default() {
        return D2c || window.d2c
      },
    },
    name: {
      default: '',
    },
    /* 默认d2c模式: 所有layer通过 d2c addStyle 加载 */
    mode: {
      default() {
        return 'd2c'
      },
    },
    width: {
      default: '100%',
    },
    height: {
      default: '100%',
    },
    preserve: {
      default: false,
    },
  },
  data() {
    return {
      map: null,
    }
  },
  mounted() {
    this.initMap(this.option)
    window.addEventListener('resize', this.resize)
  },
  methods: {
    initMap(option) {
      if (typeof option === 'string') {
        http(option).then(res => {
          this.initMap(res.data)
        })
      } else if (typeof option === 'object') {
        this.checkOption(option).then(validOption => {
          if (validOption) {
            const options = {
              localIdeographFontFamily:
                "'Arial', 'Noto Sans CJK SC', sans-serif",
              ...validOption,
              container: this.container,
            }
            if (this.preserve) {
              options.preserveDrawingBuffer = true
            }
            if (this.mode === 'd2c') {
              this.map = this.initByD2c(options)
            } else {
              this.map = this.initByDefault(options)
              this.map.$options = options
            }
            this.map.on('style.load', this.handleMapLoad)
            this.map.on('load', this.handleLoad)
            this.map.once('load', this.handleFirstLoad)
          }
        })
      }
    },
    initByD2c(options) {
      const { layers, sources } = options.style
      delete options.style.layers
      delete options.style.sources
      const map = new this.D2c.map({
        ...options,
        style: { ...options.style, layers: [], sources: {} },
      })
      map.once('load', () => {
        this.initLevel(map)
        map.addStyle(
          this.name || '底图',
          { layers, sources },
          {
            before: 'bottom',
          },
        )
        this.map.$options = {
          ...options,
          style: { ...options.style, layers, sources },
        }
      })
      return map
    },
    initLevel(map) {
      !map.hasLevel('top') && map.addLevel('top')
      !map.hasLevel('middle') && map.addLevel('middle', { before: 'top' })
      !map.hasLevel('bottom') && map.addLevel('bottom', { before: 'middle' })
    },
    initByDefault(options) {
      return new this.D2c.map(options)
    },
    async checkOption(data) {
      if (data && data.style) {
        data = { ...data, ...data.style }
      }
      const option = {
        container: data.container || 'map',
        center: data.center,
        bearing: data.bearing || 0,
        zoom: data.zoom || 12,
        maxzoom: data.maxzoom || 22,
        minzoom: data.minzoom || 0,
        style: {
          version: data.version || 8,
          glyphs: data.glyphs,
          sprite: data.sprite,
          sources: data.sources,
          layers: data.layers,
        },
      }
      if (!data.glyphs) delete option.style.glyphs
      if (!data.sprite) delete option.style.sprite
      // 判断是否需要初始化基础图层
      if (window.BASE_LAYER) {
        let baseLayerGeo = await http(window.BASE_LAYER)
        console.log(baseLayerGeo.data)
        if (baseLayerGeo.data) {
          option.bounds = turf.bbox(baseLayerGeo.data)
        }
      }
      return option
    },
    /**
     * @param {Object} -evt.style style
     * @param {Object} -evt.target map
     * @param {Object} -evt.type
     */
    handleMapLoad(evt) {
      this.$emit('style-load', evt)
    },
    handleLoad(evt) {
      this.$emit('load', evt)
    },
    handleFirstLoad(evt) {
      // evt.target.addControl(new D2c.ScaleControl(), 'bottom-left');
      this.$emit('first-load', evt)
    },
    resize() {
      this.map.resize()
    },
  },
  render(h) {
    return h('div', {
      attrs: {
        id: this.container,
      },
      style: {
        // left: 0,
        // top: 0,
        // position: 'absolute',
        width: this.width,
        height: this.height,
      },
      ref: 'map',
    })
  },
}
