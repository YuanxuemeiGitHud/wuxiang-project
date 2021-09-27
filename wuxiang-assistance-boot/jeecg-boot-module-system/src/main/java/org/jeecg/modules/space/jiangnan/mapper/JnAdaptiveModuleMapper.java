package org.jeecg.modules.space.jiangnan.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.space.jiangnan.entity.JnAdaptiveModule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 江南大屏模块
 * @Author: jeecg-boot
 * @Date: 2021-08-25
 * @Version: V1.0
 */
public interface JnAdaptiveModuleMapper extends BaseMapper<JnAdaptiveModule> {

    /**
     * 编辑节点状态
     *
     * @param id
     * @param status
     */
    void updateTreeNodeStatus(@Param("id") String id, @Param("status") String status);

}
