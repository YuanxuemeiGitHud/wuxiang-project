import Vue from "vue";
import {
  ACCESS_TOKEN,
  USER_NAME,
  USER_INFO,
  UI_CACHE_DB_DICT_DATA,
  CACHE_INCLUDED_ROUTES,
} from "@/store/mutation-types";

const user = {
  state: {
    token: "",
    username: "",
    realname: "",
    tenantid: "",
    welcome: "",
    avatar: "",
    permissionList: [],
    info: {},
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
    SET_NAME: (state, { username, realname, welcome }) => {
      state.username = username;
      state.realname = realname;
      state.welcome = welcome;
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar;
    },
    SET_PERMISSIONLIST: (state, permissionList) => {
      state.permissionList = permissionList;
    },
    SET_INFO: (state, info) => {
      state.info = info;
    },
    SET_TENANT: (state, id) => {
      state.tenantid = id;
    },
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      Vue.ls.set(ACCESS_TOKEN, userInfo.token, 7 * 24 * 60 * 60 * 1000);
      Vue.ls.set(USER_NAME, userInfo.username, 7 * 24 * 60 * 60 * 1000);
      Vue.ls.set(USER_INFO, userInfo, 7 * 24 * 60 * 60 * 1000);
      Vue.ls.set(
        UI_CACHE_DB_DICT_DATA,
        userInfo.sysAllDictItems,
        7 * 24 * 60 * 60 * 1000
      );
      commit("SET_TOKEN", userInfo.token);
      commit("SET_INFO", userInfo);
      commit("SET_NAME", {
        username: userInfo.username,
        realname: userInfo.realname,
      });
      commit("SET_AVATAR", userInfo.avatar);
    },
    Logout({ commit }) {
      commit("SET_TOKEN", "");
      commit("SET_PERMISSIONLIST", []);
      Vue.ls.remove(ACCESS_TOKEN);
      Vue.ls.remove(USER_INFO);
      Vue.ls.remove(USER_NAME);
      Vue.ls.remove(UI_CACHE_DB_DICT_DATA);
      Vue.ls.remove(CACHE_INCLUDED_ROUTES);
    },
  },
};

export default user;
