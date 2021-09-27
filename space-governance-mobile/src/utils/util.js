const util = {};

util.title = function (title) {
  title = title || "加载中...";
  window.document.title = title;
};

util.millsToTime = function (mills) {
  if (!mills) {
    return "";
  }
  const s = mills / 1000;
  if (s < 60) {
    return `${s.toFixed(0)} 秒`;
  }
  const m = s / 60;
  if (m < 60) {
    return `${m.toFixed(0)} 分钟`;
  }
  const h = m / 60;
  if (h < 24) {
    return `${h.toFixed(0)} 小时`;
  }
  const d = h / 24;
  if (d < 30) {
    return `${d.toFixed(0)} 天`;
  }
  const month = d / 30;
  if (month < 12) {
    return `${month.toFixed(0)} 个月`;
  }
  const year = month / 12;
  return `${year.toFixed(0)} 年`;
};

util.inOf = function (arr, targetArr) {
  let res = true;
  arr.forEach((item) => {
    if (targetArr.indexOf(item) < 0) {
      res = false;
    }
  });
  return res;
};

util.oneOf = function (ele, targetArr) {
  if (targetArr.indexOf(ele) >= 0) {
    return true;
  }
  return false;
};

util.getRouterObjByName = function (routers, name) {
  if (!name || !routers || !routers.length) {
    return null;
  }
  let routerObj = null;
  for (const item of routers) {
    if (item.name == name) {
      return item;
    }
    routerObj = util.getRouterObjByName(item.children, name);
    if (routerObj) {
      return routerObj;
    }
  }
  return null;
};

util.handleTitle = function (vm, item) {
  if (typeof item.title === "object") {
    return vm.$t(item.title.i18n);
  }
  return item.title;
};

util.setCurrentPath = function (vm, name) {
  let title = "";
  let isOtherRouter = false;
  vm.$store.state.app.routers.forEach((item) => {
    if (item.children.length == 1) {
      if (item.children[0].name == name) {
        title = util.handleTitle(vm, item);
        if (item.name == "otherRouter") {
          isOtherRouter = true;
        }
      }
    } else {
      item.children.forEach((child) => {
        if (child.name == name) {
          title = util.handleTitle(vm, child);
          if (item.name == "otherRouter") {
            isOtherRouter = true;
          }
        }
      });
    }
  });
  let currentPathArr = [];
  if (name == "home_index") {
    currentPathArr = [
      {
        title: util.handleTitle(
          vm,
          util.getRouterObjByName(vm.$store.state.app.routers, "home_index")
        ),
        path: "",
        name: "home_index",
      },
    ];
  } else if (
    (name.indexOf("_index") >= 0 || isOtherRouter) &&
    name !== "home_index"
  ) {
    currentPathArr = [
      {
        title: util.handleTitle(
          vm,
          util.getRouterObjByName(vm.$store.state.app.routers, "home_index")
        ),
        path: "/home",
        name: "home_index",
      },
      {
        title,
        path: "",
        name,
      },
    ];
  } else {
    const currentPathObj = vm.$store.state.app.routers.filter((item) => {
      if (item.children.length <= 1) {
        return item.children[0].name == name;
      }
      let i = 0;
      const childArr = item.children;
      const len = childArr.length;
      while (i < len) {
        if (childArr[i].name == name) {
          return true;
        }
        i++;
      }
      return false;
    })[0];
    if (currentPathObj.children.length <= 1 && currentPathObj.name == "home") {
      currentPathArr = [
        {
          title: "首页",
          path: "",
          name: "home_index",
        },
      ];
    } else if (
      currentPathObj.children.length <= 1 &&
      currentPathObj.name !== "home"
    ) {
      currentPathArr = [
        {
          title: "首页",
          path: "/home",
          name: "home_index",
        },
        {
          title: currentPathObj.title,
          path: "",
          name,
        },
      ];
    } else {
      const childObj = currentPathObj.children.filter((child) => {
        return child.name == name;
      })[0];
      currentPathArr = [
        {
          title: "首页",
          path: "/home",
          name: "home_index",
        },
        {
          title: currentPathObj.title,
          path: "",
          name: currentPathObj.name,
        },
        {
          title: childObj.title,
          path: `${currentPathObj.path}/${childObj.path}`,
          name,
        },
      ];
    }
  }
  vm.$store.commit("setCurrentPath", currentPathArr);

  return currentPathArr;
};

util.openNewPage = function (vm, name, argu, query) {
  if (!vm.$store) {
    return;
  }
  const { pageOpenedList } = vm.$store.state.app;
  const openedPageLen = pageOpenedList.length;
  let i = 0;
  let tagHasOpened = false;
  while (i < openedPageLen) {
    if (name == pageOpenedList[i].name) {
      // 页面已经打开
      vm.$store.commit("pageOpenedList", {
        index: i,
        argu,
        query,
      });
      tagHasOpened = true;
      break;
    }
    i++;
  }
  if (!tagHasOpened) {
    let tag = vm.$store.state.app.tagsList.filter((item) => {
      if (item.children) {
        return name == item.children[0].name;
      }
      return name == item.name;
    });
    tag = tag[0];
    if (tag) {
      tag = tag.children ? tag.children[0] : tag;
      if (argu) {
        tag.argu = argu;
      }
      if (query) {
        tag.query = query;
      }
      vm.$store.commit("increateTag", tag);
    }
  }
  vm.$store.commit("setCurrentPageName", name);
};

util.toDefaultPage = function (routers, name, route, next) {
  const len = routers.length;
  let i = 0;
  let notHandle = true;
  while (i < len) {
    if (
      routers[i].name == name &&
      routers[i].children &&
      routers[i].redirect == undefined
    ) {
      route.replace({
        name: routers[i].children[0].name,
      });
      notHandle = false;
      next();
      break;
    }
    i++;
  }
  if (notHandle) {
    next();
  }
};

// 将Csv文件解析为二维数组
export const getArrayFromFile = (file) => {
  const nameSplit = file.name.split(".");
  const format = nameSplit[nameSplit.length - 1];
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsText(file); // 以文本格式读取
    let arr = [];
    reader.onload = function (evt) {
      const data = evt.target.result; // 读到的数据
      const pasteData = data.trim();
      arr = pasteData
        .split(/[\n\u0085\u2028\u2029]|\r\n?/g)
        .map((row) => {
          return row.split("\t");
        })
        .map((item) => {
          return item[0].split(",");
        });
      if (format == "csv") resolve(arr);
      else reject(new Error("[Format Error]:不是Csv文件"));
    };
  });
};

// 将二维数组转为表格数据
export const getTableDataFromArray = (array) => {
  let columns = [];
  let tableData = [];
  if (array.length > 1) {
    const titles = array.shift();
    columns = titles.map((item) => {
      return {
        title: item,
        key: item,
      };
    });
    tableData = array.map((item) => {
      const res = {};
      item.forEach((col, i) => {
        res[titles[i]] = col;
      });
      return res;
    });
  }
  return {
    columns,
    tableData,
  };
};

export default util
