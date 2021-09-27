<template>
  <div class="map-box">
    <D2cMap
      width="100%"
      height="100%"
      :container="'mainMap'"
      :option="option"
      @style-load="mapStyleLoad"
      @load="mapLoad"
      @first-load="initMap"
    />
    <slot />
  </div>
</template>

<script>
import D2cMap from "@/components/map/D2cMap";
// import axios from 'axios'

// const Axios = axios.create()
export default {
  components: {
    D2cMap,
  },
  props: {
    initLayer: {
      type: Array,
      default() {
        return [];
      },
    },
  },
  data() {
    return {
      // option: window.BASE_STYLE[5].url,
      baseMap: window.BASE_STYLE,
      option: window.BASE_OPTION,
      baseLayer: "base-layer",
      mainPoint: window.MAIN_POINT,
    };
  },
  methods: {
    mapStyleLoad(e) {
      this.map = e.target;
      // eslint-disable-next-line no-undef
      const draw = new d2c.draw({
        displayControlsDefault: false,
        // eslint-disable-next-line no-undef
        styles: DEFAULT_OPTION,
      });
      this.map.addControl(draw);
      this.$emit("addDraw", draw);
    },

    mapLoad(e) {
      this.map = e.target;
      this.addBaseLayers();
      // this.$emit('on-load', e)
    },

    initMap(e) {
      this.map = e.target;
      this.addBaseLayers();
      // this.$emit('on-init-load', e)
    },
    addBaseLayers() {
      if (this.map) {
        for (const baseMapKey in this.baseMap) {
          const item = this.baseMap[baseMapKey];
          const id = item.id;
          // 是否已经加载过改图层
          if (!this.map.getLayer(id)) {
            this.map.addLayer({
              id: id,
              type: "raster",
              source: {
                type: "raster",
                tiles: [item.url],
                tileSize: 256,
              },
              paint: {},
            });
          } else {
            this.map.setLayoutProperty(id, "visibility", "visible");
          }
        }
      }
      this.$emit("mapLoad", this.map);
    },
  },
};
</script>

<style lang="less">
.map-box {
  position: relative;
  width: 100%;
  height: 100%;
  transform: scale(1);
}
</style>
