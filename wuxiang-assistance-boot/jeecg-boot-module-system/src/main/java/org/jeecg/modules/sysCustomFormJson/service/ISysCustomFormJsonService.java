package org.jeecg.modules.sysCustomFormJson.service;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.sysCustomFormJson.entity.SysCustomFormJson;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxm
 * @since 2021-09-22
 */
public interface ISysCustomFormJsonService extends IService<SysCustomFormJson> {

    /** @Param tableName tableFields
    * */
    //根据tableName,控件field自动创建数据库表
    void createAutoTask(String tableName, List<String> tableFields);

    //根据tableName，processId查询数据
    List<Map<String, Object>> queryTableByProcessId(String processId, String tableName);

    /**
     * 动态添加自定义表单填入的数据。
     * @param tableData Map<String, Object>
     */
    void addTableData(Map<String, Object> tableData);

    boolean deleteDynamicTable(String tableName);

    List<Map<String, Object>> getColumnsByTableName(String tableName);

    List<Object> queryTableByTableName(String tableName);
}
