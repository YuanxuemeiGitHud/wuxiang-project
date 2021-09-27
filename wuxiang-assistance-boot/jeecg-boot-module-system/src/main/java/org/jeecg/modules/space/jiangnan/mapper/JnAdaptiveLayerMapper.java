package org.jeecg.modules.space.jiangnan.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.space.jiangnan.entity.JnAdaptiveLayer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 江南大屏图层
 * @Author: jeecg-boot
 * @Date: 2021-08-25
 * @Version: V1.0
 */
public interface JnAdaptiveLayerMapper extends BaseMapper<JnAdaptiveLayer> {

    /**
     * 编辑节点状态
     *
     * @param id
     * @param status
     */
    void updateTreeNodeStatus(@Param("id") String id, @Param("status") String status);

}
