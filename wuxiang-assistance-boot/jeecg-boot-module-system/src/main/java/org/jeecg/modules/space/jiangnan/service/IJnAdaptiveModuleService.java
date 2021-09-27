package org.jeecg.modules.space.jiangnan.service;

import org.jeecg.modules.space.jiangnan.entity.JnAdaptiveModule;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.exception.JeecgBootException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

/**
 * @Description: 江南大屏模块
 * @Author: jeecg-boot
 * @Date: 2021-08-25
 * @Version: V1.0
 */
public interface IJnAdaptiveModuleService extends IService<JnAdaptiveModule> {

    /**
     * 根节点父ID的值
     */
    public static final String ROOT_PID_VALUE = "0";

    /**
     * 树节点有子节点状态值
     */
    public static final String HASCHILD = "1";

    /**
     * 树节点无子节点状态值
     */
    public static final String NOCHILD = "0";

    /**
     * 新增节点
     */
    void addJnAdaptiveModule(JnAdaptiveModule jnAdaptiveModule);

    /**
     * 修改节点
     */
    void updateJnAdaptiveModule(JnAdaptiveModule jnAdaptiveModule) throws JeecgBootException;

    /**
     * 删除节点
     */
    void deleteJnAdaptiveModule(String id) throws JeecgBootException;

    /**
     * 查询所有数据，无分页
     */
    List<JnAdaptiveModule> queryTreeListNoPage(QueryWrapper<JnAdaptiveModule> queryWrapper);

}
