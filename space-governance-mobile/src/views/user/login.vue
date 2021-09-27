<template>
  <div>
    <van-notice-bar left-icon="el-icon-s-opportunity" :text="loadingText" />
  </div>
</template>

<script>
import store from "@/store";
import router from "@/router";
import { getCode } from "@/utils/dingtalk/login";
// eslint-disable-next-line no-unused-vars
import { dingtalkLogin, weihongLogin } from "@/views/user/api";
// eslint-disable-next-line no-unused-vars
import http from "@/scripts/http";

export default {
  name: "login",
  data() {
    return {
      code: "",
      corp_id: "dingdcb29247cb699e0fa1320dcb25e91351",
      agent_id: "1303764901",
      app_key: "dinguyfzku4kv0zfevhh",
      app_secret:
        "xM3021o5fm8325P1q3jpyPWcMshOVjM4hP4i7nwgy62y0_9Gtenz1TCnHOplRutA",
      loadingText: "",
    };
  },
  mounted() {
    this.dingAuth();
  },
  watch: {
    code: {
      handler(newVal) {
        if (newVal != "") {
          this.dingLogin();
        }
      },
    },
  },
  methods: {
    dingAuth() {
      this.loadingText = "钉钉登录中...";
      getCode((code) => {
        this.code = code;
        // console.log(code);
        // this.loadingText = "钉钉code：" + code;
        if (typeof code !== "string") {
          this.loadingText = "请在正确的钉钉环境中打开应用...";
        }
      });
    },
    dingLogin() {
      // http
      // .get("/sys/thirdLogin/dingtalkLogin", { code: code })
      debugger;
      if (this.code !== "") {
        // alert("code: " + this.code);
        dingtalkLogin({
          code: this.code.toString(),
        })
          .then((res) => {
            // alert(JSON.stringify(res));
            if (res.data.success) {
              // alert("res: " + this.code);
              const result = res.data.result;
              console.log(result);
              store.dispatch("Login", result).then(() => {
                router
                  .push({
                    name: "Home",
                  })
                  .catch((e) => {
                    Promise.reject(e);
                  });
              });
            }
          })
          .catch(function (error) {
            console.log(error);
            // handle error
            // alert("error: " + JSON.stringify(error));
          });
      }
    },
  },
};
</script>

<style scoped></style>
