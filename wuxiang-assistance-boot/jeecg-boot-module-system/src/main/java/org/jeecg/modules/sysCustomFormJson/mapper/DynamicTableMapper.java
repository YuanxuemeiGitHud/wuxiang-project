package org.jeecg.modules.sysCustomFormJson.mapper;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yxm
 * @since 2021-09-22
 */
public interface DynamicTableMapper<deleteDynamicTable> {
    void createAutoTask(@Param("tableName")String tableName, @Param("tableFields")List<String> tableFields);

    void addTableData(Map<String, Object> tableData);

    List<Map<String, Object>> queryTableByProcessId(@Param("processId")String processId, @Param("tableName")String tableName);

    boolean deleteDynamicTable(String tableName);

    List<Map<String, Object>> getColumnsByTableName(String tableName);

    List<Object> queryTableByTableName(String tableName);
}
