import { axios } from '@/utils/request'
/*添加设计表单*/
export function addForm(parameter) {
  return axios({
    headers: { "Content-Type": "application/json;charset=UTF-8" },
    url: '/sys-custom-form-json/add',
    method: 'post',
    data: parameter
  })
}
/*获取所有设计表单*/
export function getAllForms() {
  return axios({
    url: '/sys-custom-form-json/queryAllForms',
    method: 'get',
  })
}
/*根据表单id查询表单*/
export function getFormById(parameter) {
  return axios({
    url: '/sys-custom-form-json/queryById',
    method: 'get',
    params: parameter
  })
}
/*根据表单删除表单*/
export function delFormById(parameter) {
  return axios({
    url: '/sys-custom-form-json/delFormById',
    method: 'delete',
    params: parameter
  })
}
  /*根据表名动态获取表结构*/
  export function getColumnsByTableName(parameter) {
    return axios({
      url: '/sys-custom-form-json/getColumnsByTableName',
      method: 'get',
      params: parameter
    })
  }
  /*根据表名添加数据*/
  export function addTableData(parameter) {
    return axios({
      url: '/sys-custom-form-json/addTableData',
      method: 'post',
      data: parameter
    })
  }
  /*根据表名添加数据*/
  export function getTableByTableName(parameter) {
    return axios({
      url: '/sys-custom-form-json/queryTableDataByTableName',
      method: 'get',
      params: parameter
    })
  }


