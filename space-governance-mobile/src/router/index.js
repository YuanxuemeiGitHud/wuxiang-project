import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import { Loading } from "element-ui";
import util from "@/utils/util";
import { USER_INFO } from "@/store/mutation-types";

Vue.use(VueRouter);

const routes = [
  {
    path: "/home",
    name: "Home",
    component: Home,
    meta: {
      title: "一张图",
    },
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("@/views/user/login"),
    meta: {
      title: "登录",
    },
  },
  {
    path: "/search",
    name: "Search",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Search.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: "",
  routes,
});

// router守卫
router.beforeEach((to, from, next) => {
  Loading.service({ text: "加载中..." });
  util.title(to.meta.title);
  const { name } = to;
  const whiteList = ["Login"];
  const isInWhiteList = util.oneOf(name, whiteList);
  const userInfo = Vue.ls.get(USER_INFO);
  if (!userInfo && !isInWhiteList) {
    // 判断是否已经登录且页面不在白名单
    next({
      name: "Login",
    });
  } else if (userInfo && name === "Login") {
    // 判断是否已经登录且前往的是登录页
    util.title();
    next({
      name: "Home",
    });
  } else {
    next();
  }
});

// eslint-disable-next-line no-unused-vars
router.afterEach((to) => {
  Loading.service({ text: "加载中..." }).close();
  // util.openNewPage(router.app, to.name, to.params, to.query)
  window.scrollTo(0, 0);
});

export default router;
