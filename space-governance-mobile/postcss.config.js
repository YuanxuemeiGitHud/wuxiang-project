module.exports = {
  plugins: {
    autoprefixer: {}, // 用来给不同的浏览器自动添加相应前缀，如-webkit-，-moz-等等
    "postcss-px-to-viewport": {
      viewportWidth: 1080, // UI设计稿的宽度
      exclude: [/node_modules/], // 设置忽略文件，用正则做目录名匹配
    },
  },
};
