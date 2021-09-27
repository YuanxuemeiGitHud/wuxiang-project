<template>
  <div class="kc-search">
    <!-- 搜索 -->
    <van-search
      class="kc-search-input"
      v-model.trim="value"
      placeholder="搜索(静态页面输入“公司”展示效果)"
      @search="onSearch"
    >
      <template #right-icon>
        <div
          v-show="searchBtn"
          class="kc-search-input-right"
          @click="onSearch(value)"
        >
          搜索
        </div>
      </template>
      <template #left-icon>
        <van-icon name="search" v-show="!searchBtn" />
        <van-icon name="arrow-left" v-show="searchBtn" />
      </template>
    </van-search>
    <!-- 模糊搜索内容 -->
    <van-cell-group class="kc-search-group" v-show="searchResultBox">
      <div v-if="searchResultData.length > 0">
        <div v-for="item in searchResultData" :key="item.id">
          <van-cell is-link to="search">
            <!-- 使用 title 插槽来自定义标题 -->
            <template #title>
              <div class="kc-search-group-title">
                <van-icon
                  name="location-o"
                  class="kc-search-group-title-icon"
                />
                <span
                  class="kc-search-group-title-text"
                  v-html="item.name"
                ></span>
              </div>
            </template>
            <template #label>
              <div class="kc-search-group-label van-ellipsis">
                {{ item.label }}
              </div>
            </template>
            <template #right-icon>
              <div></div>
            </template>
          </van-cell>
        </div>
      </div>
      <div v-else class="no-data">没有找到“{{ value }}”数据~</div>
    </van-cell-group>
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import d2c from "d2c";
import search from "d2c";

export default {
  name: "SearchModule",
  props: {
    layerList: {
      type: Array,
      default: function () {
        return [];
      },
    },
  },
  inject: ["mapLoad"],
  data() {
    return {
      searchClient: null,
      value: "", // 搜索框 - 搜索值
      searchBtn: false, // 搜索框 - 右侧‘搜索’按钮
      searchResultData: [], // 搜索内容
      searchResultBox: false, // 搜索列表
      test: [
        {
          id: 0,
          label: "良庆区-五象大道666号",
          name: "广西移动五项数字信息<span style='color: #1c5bfb'>公司</span>",
        },
        {
          id: 1,
          label: "良庆区-五象大道18号",
          name: "中国-东盟信息港股份有限<span style='color: #1c5bfb'>公司</span>",
        },
        {
          id: 2,
          label: "良庆区-五象大道667号",
          name: "广西移动五项数字信息<span style='color: #1c5bfb'>公司</span>",
        },
        {
          id: 3,
          label: "良庆区-五象大道668号",
          name: "中国-东盟信息港股份有限<span style='color: #1c5bfb'>公司</span>",
        },
        {
          id: 4,
          label: "良庆区-五象大道669号",
          name: "广西移动五项数字信息<span style='color: #1c5bfb'>公司</span>",
        },
        {
          id: 5,
          label: "良庆区-五象大道700号",
          name: "中国-东盟信息港股份有限<span style='color: #1c5bfb'>公司</span>",
        },
      ],
    };
  },
  mounted() {
    this.initLtSearchClient();
  },
  watch: {
    value: {
      handler(newV) {
        if (newV != "") {
          this.searchBtn = true;
        } else {
          this.searchBtn = false;
          this.searchResultBox = false;
          this.searchResultData = [];
        }
      },
    },
  },
  methods: {
    onSearch(value) {
      debugger;
      if (value == "公司") {
        this.searchResultData = this.test;
      } else {
        const layerIndex = this.layerList.join(",");
        this.searchClient
          .searchFullText({
            index: layerIndex,
            query: value,
          })
          .then(function (resp) {
            console.log(resp);
            debugger;
          });

        this.searchResultData = [];
      }
      this.searchResultBox = true;
    },
    initLtSearchClient() {
      if (!this.searchClient) {
        this.searchClient = new search.Client();
        // this.searchClient = new D2c.search.Client({
        //   host: "113.16.255.12:43901",
        //   log: "trace",
        // });
      }
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="less">
.kc-search {
  &-input {
    padding: 20px 45px;
    .van-search__content {
      background-color: #e6edfa;
    }
    &-right {
      color: #1c5bfb;
    }
  }
  &-group {
    position: absolute;
    top: 231px;
    left: 30px;
    right: 30px;
    // bottom: 30px;
    z-index: 1000;
    max-height: calc(100vh - 256px);
    overflow: auto;
    .van-cell {
      text-align: left;
      border-bottom: 1px solid #e6edfa;
    }
    &-title {
      display: flex;
      align-items: center;
      &-icon {
        margin-bottom: 6px;
      }
      &-text {
        margin-left: 6px;
      }
    }
    &-label {
      margin-left: 45px;
    }
  }
  .no-data {
    color: #969799;
    padding: 30px 0;
    text-align: center;
    font-size: 36px;
  }
}
</style>
