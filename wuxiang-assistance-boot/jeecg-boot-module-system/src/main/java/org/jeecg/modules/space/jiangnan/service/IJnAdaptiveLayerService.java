package org.jeecg.modules.space.jiangnan.service;

import org.jeecg.modules.space.jiangnan.entity.JnAdaptiveLayer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.exception.JeecgBootException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

/**
 * @Description: 江南大屏图层
 * @Author: jeecg-boot
 * @Date: 2021-08-25
 * @Version: V1.0
 */
public interface IJnAdaptiveLayerService extends IService<JnAdaptiveLayer> {

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
    void addJnAdaptiveLayer(JnAdaptiveLayer jnAdaptiveLayer);

    /**
     * 修改节点
     */
    void updateJnAdaptiveLayer(JnAdaptiveLayer jnAdaptiveLayer) throws JeecgBootException;

    /**
     * 删除节点
     */
    void deleteJnAdaptiveLayer(String id) throws JeecgBootException;

    /**
     * 查询所有数据，无分页
     */
    List<JnAdaptiveLayer> queryTreeListNoPage(QueryWrapper<JnAdaptiveLayer> queryWrapper);

    List<JnAdaptiveLayer> getChildrenListByParentId(String pId);

}
