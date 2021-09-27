import http from "../../scripts/http";
// eslint-disable-next-line no-unused-vars
import axios from "axios";

export const dingtalkLogin = (params) => {
  return http.post("/sys/dingtalkLogin", params);
  // return axios.get("/jeecg-boot/sys/dingtalkLogin", params);
};

export const weihongLogin = (params) => {
  return http.post("/sys/weihongLogin", params);
};
