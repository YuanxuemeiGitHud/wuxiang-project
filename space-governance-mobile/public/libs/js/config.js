// 地图底图地址
const BASE_STYLE = {
  rasterStyle: {
    id: "vectorStyle",
    name: "矢量",
    url: "http://t0.tianditu.com/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=f5622655d05ac580167f857b2536163d",
  },
  rasterStyleSymbol: {
    id: "rasterStyleSymbol",
    name: "底图注记",
    url: "http://t0.tianditu.com/DataServer?T=cva_w&x={x}&y={y}&l={z}&tk=f5622655d05ac580167f857b2536163d",
  },
};

const BASE_MAP_ITEMS = {
  rasterStyle: {
    name: "影像",
    url: "http://t0.tianditu.com/DataServer?T=img_w&x={x}&y={y}&l={z}&tk=f5622655d05ac580167f857b2536163d",
  },
  vectorStyle: {
    name: "矢量",
    url: "http://t0.tianditu.com/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=f5622655d05ac580167f857b2536163d",
  },
};

const BASE_OPTION = {
  container: "map", // dom容器
  style: {
    // 空底图
    version: 8,
    layers: [],
    sources: {},
    glyphs:
      "http://113.16.255.12:43901/oms/static/fonts/default_font/{fontstack}/{range}.pbf",
  },
  center: [108.45814149748155, 22.777444343312645], // 中心点
  zoom: 12, // 层级
};

const MAIN_POINT = {
  center: [108.41060684164341, 22.780830991144253],
  zoom: 11.508471331974892,
};

// 地图底图集
window.BASE_STYLE = BASE_STYLE;
window.BASE_OPTION = BASE_OPTION;
window.MAIN_POINT = MAIN_POINT;
window.BASE_MAP_ITEMS = BASE_MAP_ITEMS;
