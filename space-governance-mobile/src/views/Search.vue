<template>
  <div>
    <van-image
      class="user-poster"
      :src="require('../assets/img/image_logo.png')"
    />
    <search-module></search-module>
    <!-- <van-search class="kc-search" v-model="value" placeholder="搜索" /> -->
    <div class="kc-home-body">
      <van-notice-bar
        left-icon="volume-o"
        color="#464646"
        text="在代码编写过程中，测试的bug数是衡量代码质量的唯一标准。"
      />
      <van-image :height="'100%'" :src="require('../assets/ditu.jpg')" />
      <div class="position-poin">
        <img src="../assets/img/icon_dingwei_03.png" width="20px" alt="" />
        <p class="van-multi-ellipsis--l2">中国-东盟信息港股份有限公司</p>
      </div>
      <div class="kc-nav-left">
        <van-button type="default" class="kc-nav-left-btn">
          <van-icon class="iconfont icon-zhuizongdingwei" size="21" />
          <span class="kc-nav-left-span">定位</span>
        </van-button>
        <van-button type="default" class="kc-nav-left-btn">
          <van-icon class="iconfont icon-i" size="18" />
          <span class="kc-nav-left-span">查询</span>
        </van-button>
        <van-button type="default" class="kc-nav-left-btn">
          <van-icon class="iconfont icon-tuceng" size="22" />
          <span class="kc-nav-left-span" style="margin-top: 0">图层</span>
        </van-button>
        <van-button type="default" class="kc-nav-left-btn">
          <van-icon class="iconfont icon-ceju" size="18" />
          <span class="kc-nav-left-span">测距</span>
        </van-button>
        <van-button type="default" class="kc-nav-left-btn">
          <van-icon class="iconfont icon-shitucemianji" size="16" />
          <span class="kc-nav-left-span" style="margin-top: 2px">测面</span>
        </van-button>
        <van-button type="default" class="kc-nav-left-btn">
          <van-icon class="iconfont icon-qingchu" size="20" />
          <span class="kc-nav-left-span">清除</span>
        </van-button>
      </div>
      <div class="kc-nav-bottom">
        <div class="kc-nav-bottom-head">
          <h5>返回查询界面</h5>
          <van-button
            icon="cross"
            size="small"
            class="no-border"
            @click="toHome"
          />
        </div>
        <div class="kc-nav-bottom-body">
          <van-cell center label="良庆区-五象大道888号">
            <template #title>
              <h5 class="kc-nav-bottom-body-title">
                中国-东盟信息港股份有限公司
              </h5>
            </template>
            <template #right-icon>
              <van-button
                type="default"
                class="kc-nav-bottom-body-btn no-border"
              >
                <template #icon>
                  <img src="../assets/img/icon_daohang.png" alt="" />
                </template>
              </van-button>
            </template>
          </van-cell>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SearchModule from "../components/SearchModule.vue";
export default {
  name: "Search",
  components: { "search-module": SearchModule },
  data() {
    return {
      value: "",
    };
  },
  watch: {},
  methods: {
    toHome() {
      this.$router.push("/");
    },
  },
};
</script>

<style lang="less" scoped>
.user {
  &-poster {
    width: 330px;
    height: 36px;
    display: block;
    left: 50%;
    margin-left: -165px;
    padding: 24px 0;
  }
}
.kc-divider {
  width: 100%;
  height: 1px;
  background: #fff;
}
.img-overlay {
  width: 187px;
  height: 134px;
  background: #01000070;
  display: none;
}
.no-border {
  border: 0;
}
.kc-home-body {
  width: 100vw;
  height: calc(100vh - 226px);
  background: #fff;
  position: relative;
  .van-notice-bar {
    background: rgba(255, 255, 255, 0.7);
    height: 60px;
    font-size: 28px;
    position: absolute;
    left: 0;
    right: 0;
    top: 10px;
    z-index: 99;
    /deep/ .van-notice-bar__left-icon {
      color: #1b5bf8;
    }
  }
  .kc-nav-left {
    position: fixed;
    bottom: 350px;
    left: 30px;
    z-index: 99;
    display: flex;
    &-btn {
      width: 120px;
      height: 120px;
      padding: 0;
      line-height: 1.1;
      border-color: #ccc;
      margin-right: 20px;
    }
    &-span {
      display: block;
      margin-top: 3px;
      width: 100%;
      line-height: 30px;
      text-align: center;
      font-size: 30px;
    }
  }
  .kc-nav-bottom {
    position: fixed;
    bottom: 0;
    left: 30px;
    right: 30px;
    background-color: #fff;
    border-radius: 9px 9px 0 0;
    padding: 0 30px 30px 30px;
    &-head {
      height: 108px;
      display: flex;
      align-items: center;
      justify-content: center;
      position: relative;
      border-bottom: 1px solid #d1d1d1;
      h5 {
        margin: 0;
      }
      .van-button {
        position: absolute;
        right: 0;
      }
    }
    &-body {
      height: 180px;
      .van-cell {
        padding: 30px 0 30px 30px;
      }
      .van-cell__title {
        text-align: left;
      }
      .van-cell__label {
        color: #5c5c5c;
      }
      &-title {
        margin: 0;
        color: #185cf8;
        font-size: 39px;
      }
      &-btn img {
        width: 60px;
        display: block;
      }
    }
  }
}
.position-poin {
  position: absolute;
  left: 50%;
  top: 45%;
  width: 300px;
  margin: -150px;
  img {
    display: block;
    margin: 0 auto;
  }
  p {
    margin: 0;
    padding: 15px;
    color: #000;
    font-size: 36px;
    line-height: 1.2;
    background-color: #ffffff90;
  }
}
</style>
