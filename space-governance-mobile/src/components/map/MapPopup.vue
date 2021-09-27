<template>
  <div class="map-popup-container">
    <div
      class="attribute-wrapper"
      v-for="(item, index) in layerDataList"
      :key="index"
    >
      <div class="attribute-title">{{ "图层：" + item.layerTitle }}</div>
      <div
        class="attribute-key-value"
        v-for="(value, key, aIndex) in item.layerAttributes"
        :key="aIndex"
      >
        <div class="attribute-key">{{ key + "：" }}</div>
        <div class="attribute-value">{{ value }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MapPopup",
  props: {
    layerDataList: {
      type: Array,
      default: null,
    },
  },
  data() {
    return {
      attributeList: [
        {
          layerTitle: "layerTitle1",
          layerAttributes: {
            a1: "a1",
            a2: "a2",
            a3: "a3",
            a4: "a4",
          },
        },
        {
          layerTitle: "layerTitle2",
          layerAttributes: {
            a1: "a11",
            a2: "a22",
            a3: "a33",
            a4: "a44",
          },
        },
      ],
    };
  },
};
</script>
<style scoped>
.map-popup-container {
  display: flex;
  overflow-x: hidden;
  overflow-y: auto;
  flex-direction: column;
  justify-content: flex-start;
  max-height: 2000px;
  width: 700px !important;
  border-radius: 8px;
  background: linear-gradient(#feffff, #deebff);
}

.attribute-wrapper {
  padding: 20px 30px;
}
.attribute-title {
  margin-bottom: 5px;
  color: black;
  font-size: 20px;
}
.attribute-key-value {
  display: flex;
  align-content: flex-start;
  flex-direction: row;
  flex-wrap: nowrap;
  justify-content: flex-start;
  margin-top: 25px;
}
.attribute-key {
  max-width: 300px;
  color: #5b5b5b;
  font-size: 16px;
  line-height: 20px;
}
.attribute-value {
  align-self: center;
  max-width: 350px;
  color: #5b5b5b;
  vertical-align: center;
  font-size: 15px;
  line-height: 20px;
}
</style>

<style>
.map-popup-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  max-height: 700px;
  width: 400px !important;
  border-radius: 8px;
  background: linear-gradient(#feffff, #deebff);
}

.map-popup-container .d2cgl-popup-tip {
  position: absolute;
  top: 100%;
  right: 50%;
}

/* 美化地图通用popup */
.d2cgl-popup {
  z-index: 9999;
  max-width: unset !important;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(86, 131, 200, 0.4);
  opacity: 0.95;
}
.d2cgl-popup-content {
  padding: 0;
  background: transparent;
  /*min-width: 300px;*/
}

.d2cgl-popup-anchor-bottom .d2cgl-popup-tip {
  border-top-color: #deebff !important;
}

.d2cgl-popup-close-button {
  font-size: 16px;
  /*color: #fff;*/
}
</style>
