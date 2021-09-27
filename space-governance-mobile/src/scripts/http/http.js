/* eslint-disable */
/**
 * 这里的配置项及拦截器通常和业务相关
 * 请求拦截器的执行顺序：最后注册--->最先注册
 * 响应拦截器的执行顺序：最先注册--->最后注册--->then
 * 根据顺序做好数据及状态的传递
 */
import Vue from "vue";
import _ from "lodash";
import createAxios from "./createAxios";
import router from "@/router";
// import { Message } from "element-ui";
import { ACCESS_TOKEN } from "@/store/mutation-types";

/**
 * 发送前拦截
 * @param {Parameters<createAxios>[0]} config
 */
const requestHandle = (config) => {
  const token = Vue.ls.get(ACCESS_TOKEN);
  if (token) {
    config.headers["X-Access-Token"] = token;
  }
  // config.headers["Content-Type"] = "application/json";
  // alert("req config：" + JSON.stringify(config));
  console.log(config)
  return config;
};

/* 发送失败拦截 */
const requestErrHandle = (err) => {
  // alert("req err：" + err);
  throw err;
};

/**
 * 响应成功拦截
 * @param {import('axios').AxiosResponse} res
 */
const responseHandle = (res) => {
  // alert("res + " + JSON.stringify(res));
  // eslint-disable-next-line no-unused-vars
  const { code, msg } = res.data || {};
  // 200 类成功
  if (
    code === 200 || // TODO: 结合具体项目
    /^(arraybuffer|blob|stream)$/.test(_.get(res.request, "responseType"))
  ) {
    return res;
  }
  // 200 类失败
  else {
    // let message = `${msg || '系统错误'}`
    // if (code) {
    //   message = `${code} :: ${message}`
    // }
    // if (!res.config.exNoErrorMassage) {
    //   Message.error(message)
    // }
    // const err = new Error(message)
    // err['exRes'] = res
    // throw err
    // eslint-disable-next-line no-unused-vars
    const message = res.data.message;
    // Message.error(message);
  }
};

/* 响应失败拦截 */
const responseErrHandle = (error) => {
  console.log(error)
  // alert("error + " + JSON.stringify(error));
  // 非 200 类失败 (有响应 & 响应体解析后是 json 对象)
  // if (err.response && _.isPlainObject(err.response.data)) {
  //   if (!_.get(err.config, 'exNoErrorMassage')) {
  //     const code = _.get(err.response.data, 'code')
  //     let message = _.get(err.response.data, 'msg') || '系统错误'
  //     if (code) {
  //       message = `${code} :: ${message}`
  //     }
  //     Message.error(message)
  //   }
  // }
  // throw err

  if (error.response) {
    const token = Vue.ls.get(ACCESS_TOKEN);
    switch (error.response.status) {
      case 403:
        // Message.error({
        //   message: "拒绝访问",
        //   duration: 1000,
        // });
        break;
      case 500:
        //notification.error({ message: '系统提示', description:'Token失效，请重新登录!',duration: 4})
        // if (data.message.includes('Token失效')) {
        // Message.error({
        //   message: "拒绝访问",
        //   duration: 1000,
        // });
        // eslint-disable-next-line no-unused-vars
        router.push("login").then((r) => {
          Vue.ls.remove(ACCESS_TOKEN);
        });
        // }
        break;
      case 404:
        // Message.error({
        //   message: "很抱歉，资源未找到!",
        //   duration: 1000,
        // });
        break;
      case 504:
        // Message.error({
        //   message: "网络超时",
        //   duration: 1000,
        // });
        break;
      case 401:
        // Message.error({
        //   message: "未授权，请重新登录",
        //   duration: 1000,
        // });
        if (token) {
          // eslint-disable-next-line no-unused-vars
          router.push("login").then((r) => {
            Vue.ls.remove(ACCESS_TOKEN);
          });
        }
        break;
      default:
        // Message.error({
        //   message: "data.message",
        //   duration: 1000,
        // });
        break;
    }
  }
  return Promise.reject(error);
};

export const http = createAxios(
  {
    baseURL: "/jeecg-boot",
    // process.env.VUE_APP_ENV === "stage"
    //   ? localStorage.SpaceGovernance_baseurl_api ||
    //     process.env.VUE_APP_BASEURL_API // stage 环境客户端侧允许自定义接口前缀，方便调试（特别是后端开发）
    //   : process.env.VUE_APP_BASEURL_API,
  },
  (instance) => {
    instance.interceptors.request.use(requestHandle, requestErrHandle);
    instance.interceptors.response.use(responseHandle, responseErrHandle);
  }
);

export default http;
