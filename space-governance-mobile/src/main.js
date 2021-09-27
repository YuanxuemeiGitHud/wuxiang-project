import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "../src/assets/style/icon.css";
import Storage from "vue-ls";
// 按需加载 -- S
import { Image as VanImage } from "vant";
import {
  Icon,
  Search,
  NoticeBar,
  Button,
  Grid,
  GridItem,
  Popup,
  Collapse,
  CollapseItem,
  Checkbox,
  CheckboxGroup,
  Cell,
  CellGroup,
  Slider,
  Col,
  Row,
} from "vant";
Vue.use(Col);
Vue.use(Row);
Vue.use(Icon);
Vue.use(VanImage);
Vue.use(Search);
Vue.use(NoticeBar);
Vue.use(Button);
Vue.use(Grid);
Vue.use(GridItem);
Vue.use(Popup);
Vue.use(Collapse);
Vue.use(CollapseItem);
Vue.use(Checkbox);
Vue.use(CheckboxGroup);
Vue.use(Cell);
Vue.use(CellGroup);
Vue.use(Slider);
// 按需加载 -- E
const storageOptions = {
  namespace: "pro__", // key prefix
  name: "ls", // name variable Vue.[ls] or this.[$ls],
  storage: "local", // storage name session, local, memory
};
Vue.use(Storage, storageOptions);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
