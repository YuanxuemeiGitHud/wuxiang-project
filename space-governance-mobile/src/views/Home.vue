`
<template>
  <div>
    <!-- 属性弹框 -->
    <MapPopup
      ref="mapPopup"
      :popup="mapPopup"
      :layer-data-list="layerData"
      v-show="showMapPopup"
    >
    </MapPopup>
    <!-- logo -->
    <!--        <van-image-->
    <!--          class="user-poster"-->
    <!--          :src="require('../assets/img/image_logo.png')"-->
    <!--        />-->
    <search-module :layerList="checkedLayerList"></search-module>
    <!-- 搜索 -->
    <!-- <van-search class="kc-search" v-model="value" placeholder="搜索" /> -->
    <!-- 内容主体 -->
    <div class="kc-home-body">
      <van-notice-bar
        left-icon="volume-o"
        color="#464646"
        :text="noticeText"
        v-if="noticeText"
      />
      <!--      <van-image :height="'100%'" :src="require('../assets/ditu.jpg')" />-->
      <!--      地图-->
      <MainMap @mapLoad="mapLoad" @addDraw="addDraw"></MainMap>
      <div class="kc-nav-left">
        <van-button type="default" class="kc-nav-left-btn" @click="logout">
          <van-icon class="iconfont icon-wode" size="22" />
          <span class="kc-nav-left-span">注销</span>
        </van-button>
        <van-button
          type="default"
          class="kc-nav-left-btn"
          @click="spatialSearch"
        >
          <van-icon class="iconfont icon-i" size="20" />
          <span class="kc-nav-left-span">{{ searchText }}</span>
        </van-button>
        <van-button type="default" class="kc-nav-left-btn">
          <van-icon class="iconfont icon-zhuizongdingwei" size="18" />
          <span class="kc-nav-left-span">定位</span>
        </van-button>
      </div>
      <div class="kc-nav-right">
        <van-button type="default" class="kc-nav-right-btn" @click="openTC">
          <van-icon class="iconfont icon-tuceng" size="18" />
          <span class="kc-nav-right-span">图层</span>
        </van-button>
        <div class="kc-divider van-hairline--top"></div>
        <van-button type="default" class="kc-nav-right-btn">
          <van-icon class="iconfont icon-gongju" size="14" />
          <span class="kc-nav-right-span">工具</span>
        </van-button>
      </div>
    </div>
    <!-- 右侧图层 -->
    <van-popup
      v-model="showPopup"
      position="right"
      :style="{ width: '60%', height: '100%' }"
    >
      <van-collapse v-model="activeCollapse" class="kc-collapse">
        <van-collapse-item name="0" disabled>
          <template #right-icon>
            <div></div>
          </template>
          <template #title>
            <div class="kc-collapse-disabled">地图类型</div>
          </template>
          <div class="kc-collapse-content">
            <div
              class="kc-collapse-content-img"
              :class="{ 'active-img': activeMap }"
              @click="changeMap('0')"
            >
              <img src="../assets/tuceng1.jpg" alt="" class="" />
              <div class="img-overlay"></div>
            </div>
            <div
              class="kc-collapse-content-img"
              :class="{ 'active-img': !activeMap }"
              @click="changeMap('1')"
            >
              <img src="../assets/tuceng2.jpg" alt="" class="" />
              <div class="img-overlay"></div>
            </div>
          </div>
        </van-collapse-item>
        <div v-for="(item, index) in this.layerTree" :key="index">
          <van-collapse-item name="1" class="no-padding">
            <template #title>
              <div class="collapse-title">
                <!--              <div class="collapse-title-check">-->
                <!--                <van-checkbox-->
                <!--                  v-model="allChecked"-->
                <!--                  @click.stop.native="allChecked = true"-->
                <!--                ></van-checkbox>-->
                <!--              </div>-->
                <div class="collapse-title-text">{{ item.title }}</div>
              </div>
            </template>
            <van-checkbox-group
              v-model="checkedLayerList"
              ref="checkboxGroupMyLayer"
              @change="handleLayerCheck"
            >
              <van-row
                gutter="10"
                class="kc-row"
                v-for="child in item.children"
                :key="child.id"
              >
                <van-col span="30">
                  <van-checkbox
                    :ref="child.id"
                    v-model="child.defaultShow"
                    :name="child.id"
                    @change="handleLayerCheck"
                  >
                    {{ child.title }}
                  </van-checkbox>
                </van-col>
                <!--                <van-col span="12">-->
                <!--                  <van-slider-->
                <!--                    v-model="child.sliderValue"-->
                <!--                    bar-height="14px"-->
                <!--                    active-color="#6d94ee"-->
                <!--                    inactive-color="#c0c0c0"-->
                <!--                    class="slider-inser-box"-->
                <!--                  >-->
                <!--                    <template #button>-->
                <!--                      <div class="custom-button">-->
                <!--                        透明度{{ child.sliderValue }}%-->
                <!--                      </div>-->
                <!--                    </template>-->
                <!--                  </van-slider>-->
                <!--                </van-col>-->
              </van-row>
            </van-checkbox-group>
          </van-collapse-item>
        </div>
      </van-collapse>
    </van-popup>
  </div>
</template>

<script>
import D2c from "d2c";
import Vue from "vue";
import store from "@/store";
import { ACCESS_TOKEN } from "@/store/mutation-types";
import SearchModule from "../components/SearchModule.vue";
import MainMap from "../components/map/MainMap";
import MapPopup from "@/components/map/MapPopup";
import http from "../scripts/http";
import { qsStringify } from "../scripts/utils";

export default {
  name: "Home",
  components: { "search-module": SearchModule, MainMap, MapPopup },
  provide() {
    return {
      mapLoad: () => this.map,
    };
  },
  data() {
    return {
      noticeText: "",
      searchText: "开启查询",
      value: "",
      showPopup: false, // 右侧抽屉
      activeCollapse: ["0", "1", "2"], // 折叠板->默认全打开
      activeMap: true, // 地图类型切换
      myResultCheckbox: [], // 我的图层->选中值
      allChecked: false, // 我的图层->全选
      myLayerData: [
        {
          id: 0,
          layer: "我的标绘",
          eName: "wdbh",
          sliderValue: 30,
        },
        {
          id: 1,
          layer: "我的轨迹",
          eName: "wdgj",
          sliderValue: 30,
        },
      ],
      layerCheckbox: [], // 图层管理->选中值
      allCheckedLayer: false, // 图层管理->全选
      layerManageData: [
        {
          id: 0,
          layer: "总规划用地类",
          eName: "zghydl",
          sliderValue: 30,
        },
        {
          id: 1,
          layer: "城乡规划",
          eName: "cxgh",
          sliderValue: 30,
        },
        {
          id: 2,
          layer: "如果图层名称超过三行则用省略号表示",
          eName: "test",
          sliderValue: 30,
        },
      ],
      map: "",
      draw: "",
      layerTree: "",
      checkedLayerList: [],
      layerObjList: [],
      mapPopup: null,
      showMapPopup: false,
      isSpatialSearch: false,
      layerData: [],
    };
  },
  mounted() {
    this.initLayerData();
    this.initPopup();
  },
  watch: {
    activeMap: {
      handler(newVal) {
        if (newVal) {
          console.log("切换并更新图1");
        } else {
          console.log("切换并更新图2");
        }
      },
    },
    isSpatialSearch: {
      handler(newVal) {
        if (!newVal) {
          if (this.mapPopup) {
            this.mapPopup.remove();
          }
        }
      },
    },
    allChecked: {
      handler(newVal) {
        if (newVal) {
          this.$refs.checkboxGroupMyLayer.toggleAll(true);
        } else {
          this.$refs.checkboxGroupMyLayer.toggleAll(false);
        }
      },
    },
    allCheckedLayer: {
      handler(newVal) {
        if (newVal) {
          this.$refs.checkboxGroupLayerM.toggleAll(true);
        } else {
          this.$refs.checkboxGroupLayerM.toggleAll(false);
        }
      },
    },
    checkedLayerList: {
      // eslint-disable-next-line no-unused-vars
      handler(newVal, oldVal) {
        const that = this;
        if (newVal) {
          newVal.forEach((nv) => {
            if (!oldVal.includes(nv)) {
              const find = that.layerObjList.find((layer) => layer.id == nv);
              if (find) {
                this.addLayer(find);
              }
            }
          });
        }
        if (oldVal) {
          oldVal.forEach((ov) => {
            if (!newVal.includes(ov)) {
              const find = this.layerObjList.find((layer) => layer.id == ov);
              if (find) {
                this.removeLayer(find);
              }
            }
          });
        }
      },
    },
  },
  methods: {
    logout() {
      store.dispatch("Logout").then(() => {
        Vue.ls.remove(ACCESS_TOKEN);
        window.location.reload();
      });
    },
    openTC() {
      this.showPopup = true;
    },
    changeMap(type) {
      if (type == "0") {
        this.activeMap = true;
        // 切换矢量底图
        const id = "vectorStyle";
        const url = window.BASE_MAP_ITEMS.vectorStyle.url;
        if (!this.map.getLayer(id)) {
          this.map.addLayer(
            {
              id: id,
              type: "raster",
              source: {
                type: "raster",
                tiles: [url],
                tileSize: 256,
              },
              paint: {},
            },
            "rasterStyleSymbol"
          );
        } else {
          this.map.setLayoutProperty(id, "visibility", "visible");
        }
        const removeId = "rasterStyle";
        if (this.map.getLayer(removeId)) {
          this.map.removeLayer(removeId);
          this.map.removeSource(removeId);
        }
      } else {
        this.activeMap = false;
        // 切换栅格底图
        const id = "rasterStyle";
        const url = window.BASE_MAP_ITEMS.rasterStyle.url;
        if (!this.map.getLayer(id)) {
          this.map.addLayer(
            {
              id: id,
              type: "raster",
              source: {
                type: "raster",
                tiles: [url],
                tileSize: 256,
              },
              paint: {},
            },
            "rasterStyleSymbol"
          );
        } else {
          this.map.setLayoutProperty(id, "visibility", "visible");
        }
        const removeId = "vectorStyle";
        if (this.map.getLayer(removeId)) {
          this.map.removeLayer(removeId);
          this.map.removeSource(removeId);
        }
      }
    },
    mapLoad(e) {
      this.map = e;
    },
    addDraw(e) {
      this.draw = e;
    },

    // 图层数据初始化
    initLayerData() {
      const params = {
        module: "1430014074449600514",
        pid: "0",
      };
      const paramsSerializer = (params) => {
        return qsStringify(params, { arrayFormat: "comma" });
      };
      const config = {
        params,
        paramsSerializer, // 在拦截器中已配置该默认值，在业务中通常不需要重写
      };
      http
        .get("/cockpit/jiangnan/jnAdaptiveLayer/getListByModule", config)
        .then((res) => {
          const dataList = res.data.result;
          this.initLayerOtherAttr(dataList);
          // this.changeTitleToLabel(dataList);
          this.layerTree = dataList;
        });
    },
    initLayerOtherAttr(listObj) {
      this.layerObjList.push(...listObj);
      listObj.forEach((obj) => {
        if (obj.defaultShow === "1") {
          this.addLayer(obj);
          this.checkedLayerList.push(obj.id);
          obj.isChecked = true;
        } else {
          obj.isChecked = false;
        }
        obj.sliderValue = 0;
        if (obj.children.length > 0) {
          this.initLayerOtherAttr(obj.children);
        }
      });
    },
    handleLayerCheck(checked) {
      console.log(checked);
    },

    // 图层显隐相关方法
    addLayer(layerObj) {
      if (layerObj.layerType === "0") {
        return;
      }
      const id = layerObj.id;
      const url = layerObj.url;
      const longitude = layerObj.longitude;
      const latitude = layerObj.latitude;
      const zoom = layerObj.zoom;
      if (layerObj.layerType === "3") {
        this.addVectorLayer(id, url);
      } else if (layerObj.layerType === "2") {
        this.addRasterLayer(id, url);
      }
      if (zoom != null && zoom !== "") {
        this.map.flyTo({
          center: [longitude, latitude],
          zoom: zoom,
          pitch: 0,
        });
      }
    },
    removeLayer(layerObj) {
      if (layerObj.layerType === "0") {
        return;
      }
      if (layerObj.layerType === "3") {
        this.removeVectorLayer(layerObj);
      } else if (layerObj.layerType === "2") {
        this.removeRasterLayer(layerObj);
      }
    },
    addVectorLayer(id, url) {
      if (this.map != null && this.map !== "") {
        if (!this.map.getStyle(id)) {
          this.map.addStyle(id, url);
        } else {
          this.map.updateStyleLayout(id, "visibility", "visible");
        }
      }
    },
    addRasterLayer(id, url) {
      if (!this.map.getLayer(id)) {
        this.map.addLayer({
          id: id,
          type: "raster",
          source: {
            type: "raster",
            tiles: [url],
            tileSize: 256,
          },
          paint: {},
        });
      } else {
        this.map.setLayoutProperty(id, "visibility", "visible");
      }
    },
    removeVectorLayer(layerObj) {
      const id = layerObj.id;
      if (this.map != null && this.map !== "") {
        if (this.map.getStyle(id)) {
          // this.map.updateStyleLayout(id, 'visibility', 'none')
          this.map.removeStyle(id);
        }
      }
    },
    removeRasterLayer(layerObj) {
      const id = layerObj.id;
      if (this.map != null && this.map !== "") {
        if (this.map.getLayer(id)) {
          // this.map.setLayoutProperty(id, 'visibility', 'none')
          this.map.removeLayer(id);
          this.map.removeSource(id);
        }
      }
    },

    // 点击查询
    initPopup() {
      if (!this.mapPopup) {
        this.mapPopup = new D2c.Popup({
          closeButton: false,
          closeOnClick: false,
          className: "map-popup-container",
        }).setDOMContent(this.$refs.mapPopup.$el);
      }
    },
    spatialSearch() {
      this.isSpatialSearch = !this.isSpatialSearch;
      if (this.isSpatialSearch) {
        this.noticeText = "点击图层查看属性";
        this.searchText = "关闭查询";
        this.map.on("click", (e) => this.spatialSearchFunc(this, e));
      } else {
        this.noticeText = "";
        this.searchText = "开启查询";
        this.map.off("click", (e) => this.spatialSearchFunc(this, e));
      }
    },
    spatialSearchFunc(that, e) {
      if (!that.isSpatialSearch) {
        if (that.mapPopup) {
          that.mapPopup.remove();
        }
        return;
      }
      const result = that.map.queryRenderedFeatures(e.point);
      if (!result.length > 0) {
        if (that.mapPopup) {
          that.mapPopup.remove();
        }
        return;
      }
      const layerDataList = [];
      result.forEach((layerData) => {
        const layerTitle = layerData.layer.id;
        const layerProperties = layerData.properties;
        layerDataList.push({
          layerTitle: layerTitle,
          layerAttributes: layerProperties,
        });
      });
      that.layerData = layerDataList;
      that.map.flyTo({
        center: e.lngLat,
        zoom: 15,
      });
      that.showMapPopup = true;
      that.mapPopup.setLngLat(e.lngLat).addTo(that.map);
    },
  },
};
</script>

<style lang="less" scoped>
.user {
  &-poster {
    width: 330px;
    height: 36px;
    display: block;
    left: 50%;
    margin-left: -165px;
    padding: 24px 0;
  }
}
.kc-divider {
  width: 100%;
  height: 1px;
  background: #fff;
}
.img-overlay {
  width: 187px;
  height: 134px;
  background: #01000070;
  display: none;
}
.kc-home-body {
  width: 100vw;
  top: 36px;
  //height: calc(100vh - 226px);
  height: calc(100vh - 190px);
  //height: 100vh;
  background: #fff;
  position: relative;
  .van-notice-bar {
    background: rgba(255, 255, 255, 0.7);
    height: 60px;
    font-size: 28px;
    position: absolute;
    left: 0;
    right: 0;
    top: 10px;
    z-index: 99;
    /deep/ .van-notice-bar__left-icon {
      color: #1b5bf8;
    }
  }
  .kc-nav-left {
    position: absolute;
    bottom: 30px;
    left: 30px;
    z-index: 99;
    display: flex;
    flex-direction: column;
    &-btn {
      width: 120px;
      height: 120px;
      padding: 0;
      line-height: 1.1;
      border-color: #ccc;
      margin-bottom: 15px;
    }
    &-btn:last-child {
      margin-bottom: 0;
    }
    &-span {
      display: block;
      width: 100%;
      text-align: center;
      font-size: 20px;
    }
  }
  .kc-nav-right {
    position: absolute;
    bottom: 30px;
    right: 30px;
    z-index: 99;
    display: flex;
    flex-direction: column;
    &-btn {
      width: 120px;
      height: 120px;
      padding: 0;
      line-height: 1.1;
      border-color: #ccc;
      border-bottom-width: 0px;
    }
    &-btn:last-child {
      border-bottom-width: 1px;
      border-top-width: 0px;
    }
    &-span {
      display: block;
      margin: 0;
      padding: 0;
      font-size: 20px;
    }
  }
}
.kc-collapse {
  &::after {
    display: table;
    clear: both;
  }
  .van-collapse-item {
    margin-bottom: 1px;
  }
  /deep/ .van-cell--clickable {
    background-color: #e6efff;
    padding: 15px 45px;
    .van-checkbox__icon {
      background-color: #fff;
      border-radius: 50%;
    }
  }
  &-disabled {
    text-align: left;
    color: #010000;
  }
  &-content {
    display: flex;
    align-items: center;
    justify-content: space-around;
    &-img {
      position: relative;
      width: 187px;
      height: 134px;
      img {
        width: 187px;
        height: 134px;
        display: block;
      }
      .img-overlay {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        top: 0;
        width: 187px;
        height: 134px;
        z-index: 1;
        background: url("../assets/img/icon_xuanzhong.png") left center
          no-repeat;
        background-size: 100% 100%;
      }
    }
    .active-img .img-overlay {
      z-index: 9;
      display: block;
    }
  }
  .collapse-title {
    display: flex;
    &-check {
      margin-right: 10px;
    }
    &-text {
      margin-left: 10px;
      color: #010000;
    }
  }
}
.no-padding /deep/ .van-collapse-item__content {
  // padding: 15px;
  .van-cell--clickable {
    background-color: #fff;
    align-items: center;
  }
}
.no-padding /deep/.van-cell__right-icon {
  color: #001047;
}
.kc-checkbox-box {
  display: flex;
  align-items: center;
  &-title {
    display: flex;
    height: 100px;
    line-height: 100px;
    &-p {
      margin: 0;
    }
  }
}
.kc-row {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  padding: 20px 0;
  /deep/ .van-checkbox__label {
    margin-left: 6px;
    margin-top: 3px;
    font-size: 20px;
    line-height: 48px;
    color: #000;
    text-align: left;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
    overflow: hidden;
  }
  &:last-child {
    margin-bottom: 0;
  }
}
.no-border {
  border: 0;
}
.kc-row-border-b {
  // border-bottom: 1px solid #d1d1d1;
  position: relative;
  &::after {
    position: absolute;
    box-sizing: border-box;
    content: " ";
    pointer-events: none;
    top: -50%;
    right: -50%;
    bottom: -50%;
    left: -50%;
    border: 0 solid #d1d1d1;
    transform: scale(0.5);
    border-bottom-width: 1px;
    display: block;
  }
}
.slider-inser-box {
  /deep/ .van-slider__button-wrapper,
  /deep/ .van-slider__button-wrapper-right {
    top: 0;
    left: 6px;
    right: 0;
    transform: none;
    .custom-button {
      width: 200px;
      font-size: 30px;
      line-height: 1.2;
      color: #fff;
    }
  }
}
</style>
`
