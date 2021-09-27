package org.jeecg.modules.sysCustomFormJson.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.sysCustomFormJson.entity.SysCustomFormJson;
import org.jeecg.modules.sysCustomFormJson.mapper.DynamicTableMapper;
import org.jeecg.modules.sysCustomFormJson.mapper.SysCustomFormJsonMapper;
import org.jeecg.modules.sysCustomFormJson.service.ISysCustomFormJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yxm
 * @since 2021-09-22
 */
@Service
public class SysCustomFormJsonServiceImpl extends ServiceImpl<SysCustomFormJsonMapper, SysCustomFormJson> implements ISysCustomFormJsonService {
    @Autowired
    private DynamicTableMapper dynamicTableMapper;
    @Override
    public void createAutoTask(String tableName, List<String> tableFields) {
        dynamicTableMapper.createAutoTask(tableName,tableFields);
    }

    @Override
    public List<Map<String, Object>> queryTableByProcessId(String processId, String tableName) {
        return dynamicTableMapper.queryTableByProcessId(processId, tableName);
    }

    @Override
    public void addTableData(Map<String, Object> tableData) {
        dynamicTableMapper.addTableData(tableData);
    }

    @Override
    public boolean deleteDynamicTable(String tableName) {
        return dynamicTableMapper.deleteDynamicTable(tableName);
    }

    @Override
    public List<Map<String, Object>> getColumnsByTableName(String tableName) {
        return dynamicTableMapper.getColumnsByTableName(tableName);
    }

    @Override
    public List<Object> queryTableByTableName(String tableName) {
        return dynamicTableMapper.queryTableByTableName(tableName);
    }
}
