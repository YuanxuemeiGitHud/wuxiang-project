module.exports = () => ({
  outputDir: "dist",
  // publicPath: process.env.NODE_ENV === "production" ? "/vant-demo/" : "/",
  publicPath: "",

  devServer: {
    disableHostCheck: true,
    /* 更详细的配置规则：https://webpack.docschina.org/configuration/dev-server/#devserver-proxy */
    proxy: {
      "/jeecg-boot": {
        target: "http://127.0.0.1:44109/", // 请求本地 需要xboot后端项目
        ws: true,
      },
    },
    host: "0.0.0.0", // 需要内网的其它机器也能访问时，将值改成 '0.0.0.0'
    port: 44443,
  },

  chainWebpack: (config) => {
    // 配置外部静态资源库
    const cdn = {
      css: ["libs/css/d2c.1.4.1.css"],
      js: [
        "libs/js/config.js",
        "libs/js/DrawOption.js",
        "libs/js/d2c.min.1.4.1.js",
        "libs/js/turf.min.js",
        "libs/js/d2c.helper-0.35.min.js",
      ],
    };
    config.externals(["d2c", "config", "turf", "DrawOption"]);
    config.plugin(`html`).tap((args) => {
      args[0].title = "空间治理";
      args[0].cdn = cdn;
      return args;
    });
  },
});
