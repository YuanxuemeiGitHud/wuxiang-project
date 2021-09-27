package org.jeecg.modules.sysCustomFormJson.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.sysCustomFormJson.entity.SysCustomFormJson;
import org.jeecg.modules.sysCustomFormJson.service.ISysCustomFormJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yxm
 * @since 2021-09-22
 */
@RestController
@RequestMapping("/sys-custom-form-json")
@Api(tags = "表单设计器接口")
public class SysCustomFormJsonController {
    /**
     * 添加
     *
     * @param sysCustomFormJsonObject
     * @return
     */
    @Autowired
    private ISysCustomFormJsonService sysCustomFormJsonService;

    @PostMapping(value = "/add")
    @AutoLog(value = "设计器表单-添加表单")
    @ApiOperation(value = "设计器表单-添加表单", notes = "设计器表单-添加表单")
    public Result<?> add(@RequestBody JSONObject sysCustomFormJsonObject) {
        //获取json数据中的tableName
        /**
         * json结构：
         * {
         *     "config": {
         *            "tableName": "XXXX",       <--------需要获取
         *         "layout": "horizontal",
         *     },
         *        "list": [{},{}]
         * }
         */
        Map<String, String> config = (Map) sysCustomFormJsonObject.get("config");

        //给tableName添加前缀，标识为自定义
        String tableName = "dynamic_" + sysCustomFormJsonObject.get("tableName");
        SysCustomFormJson sysCustomFormJson = new SysCustomFormJson();
        sysCustomFormJson.setFormName(tableName);
        //将table的数据存入数据库
        sysCustomFormJson.setFormJson(sysCustomFormJsonObject.toString());
        sysCustomFormJsonService.save(sysCustomFormJson);

        //解析json中的控件信息，并存入tableFilelds
        List<String> tableFields = new ArrayList<>();

        List joArray = (List) sysCustomFormJsonObject.get("list");

        for (int i = 0; i < joArray.size(); i++) {
            Map<String, String> map = (Map) joArray.get(i);
          String field = map.get("type");
//            String field = map.get("key");

            tableFields.add(field);
        }
        sysCustomFormJsonService.createAutoTask(tableName, tableFields);

        return Result.OK("添加成功！");
    }

    /**
     * 添加动态table的字段数据
     *
     * @param jsonObject
     * @return
     */
    @AutoLog(value = "设计器表单-添加动态table的字段数据")
    @ApiOperation(value = "设计器表单-添加动态table的字段数据", notes = "设计器表单-添加动态table的字段数据")
    @PostMapping(value = "/addTableData")
    public Result<?> addTableData(@RequestBody JSONObject jsonObject) {
        // 将tableName存入tableData中
        String tableName = "" + jsonObject.get("tableName");
        Map<String, Object> tableData = new HashMap<>();
        tableData.put("tableName", tableName);

        // 获取table字段数据
        Map tableFiledMap = (Map) jsonObject.get("tableFiled");
        // 自动生成32位UUID存入表的字段映射中，确保insert
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        tableFiledMap.put("id", uuid);

        // 将processId存入表的字段映射中
        String processId = (String) jsonObject.get("processId");
        tableFiledMap.put("processId", processId);
        // 再将table的成员Map存入tableData中
        tableData.put("tableFiledMap", tableFiledMap);

        sysCustomFormJsonService.addTableData(tableData);
        return Result.OK("添加成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "设计器表单-通过id查询")
    @ApiOperation(value = "设计器表单-通过id查询", notes = "设计器表单-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam String id) {
        SysCustomFormJson sysCustomFormJson = sysCustomFormJsonService.getById(id);
        return Result.OK(sysCustomFormJson);
    }

    /**
     * 通过processId和tableName查询动态生成的表单数据
     *
     * @param processId
     * @param tableName
     * @return
     */
    @AutoLog(value = "设计器表单-通过processId和tableName查询动态生成的表单数据")
    @ApiOperation(value = "设计器表单-通过processId和tableName查询动态生成的表单数据", notes = "设计器表单-通过processId和tableName查询动态生成的表单数据")
    @GetMapping(value = "/queryTableDataByIdAndTableName")
    public Result<?> queryById(@RequestParam(name = "processId", required = true) String processId,
                               @RequestParam(name = "tableName", required = true) String tableName) {
//        Map<String, Object> map =new HashMap<String, Object>();
//        map.put("content",sysCustomFormJsonService.queryTableByProcessId(processId, tableName));
          List<Map<String, Object>> map = sysCustomFormJsonService.queryTableByProcessId(processId, tableName);
//        Map<String, Object> map = sysCustomFormJsonService.queryTableByProcessId(processId, tableName);

//        如果只取出一条数据才返回获取成功，否则返回500
        if (map.size() == 1) {
//            for (String key : map.keySet()) {
//                Object value = map.get(key);
            return Result.OK(map.get(0));
//            }
        }
        return Result.error(500, "获取失败，因为存在多条相同记录。");
    }
 /**
     * 通过tableName查询动态生成的表单数据
     *
     * @param tableName
     * @return
     */
    @AutoLog(value = "设计器表单-通过tableName查询动态生成的表单数据")
    @ApiOperation(value = "设计器表单-通过tableName查询动态生成的表单数据", notes = "设计器表单-tableName查询动态生成的表单数据")
    @GetMapping(value = "/queryTableDataByTableName")
    public Result<?> queryByTableName(@RequestParam String tableName) {
          List<Object> list = sysCustomFormJsonService.queryTableByTableName(tableName);
          return Result.OK(list);
    }

    @AutoLog("查询所有的设计表单")
    @ApiOperation(value = "查询所有设计表单")
    @GetMapping(value = "/queryAllForms")
    public Result<?> queryAllForms() {
        List<SysCustomFormJson> list = sysCustomFormJsonService.list();
        return Result.OK(list);
    }

    @AutoLog("根据id删除表单")
    @ApiOperation(value = "根据id删除表单")
    @DeleteMapping(value = "/delFormById")
    public Result<?> delFormById(@RequestParam String id) {
        SysCustomFormJson sysCustomFormJson = sysCustomFormJsonService.getById(id);
        boolean i = sysCustomFormJsonService.removeById(id);

        //删除表单信息并删除该动态表
        sysCustomFormJsonService.removeById(id);
        sysCustomFormJsonService.deleteDynamicTable(sysCustomFormJson.getFormName());
        return Result.OK("删除成功！");
    }

    @AutoLog("根据表名获取数据表结构")
    @ApiOperation(value = "根据表名获取数据表结构")
    @GetMapping(value = "/getColumnsByTableName")
    public Result<?> getColumnsByTableName(@RequestParam String tableName) {
        List<Map<String, Object>> list = sysCustomFormJsonService.getColumnsByTableName(tableName);
        Iterator<Map<String, Object>> iterator = list.iterator();
        Map<String, String> map1 = new HashMap<>();
        while (iterator.hasNext()) {
            Map<String, Object> map = iterator.next();
            for(String key : map.keySet()){
                if("COLUMN_NAME".equals(key)){
                    Object value = map.get(key);
                    map1.put(value.toString(),"");
                    System.out.println(key+":"+value);
                }
            }
        }
        return Result.OK(map1);
    }
}


