package org.jeecg.modules.space.jiangnan.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.space.jiangnan.entity.JnAdaptiveLayer;
import org.jeecg.modules.space.jiangnan.service.impl.JnAdaptiveLayerServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Import;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class JnAdaptiveLayerTreeVO {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 所属部门
     */
    private String sysOrgCode;
    /**
     * 父级节点
     */
    private String pid;
    /**
     * 是否有子节点
     */
    @Dict(dicCode = "yn")
    private String hasChild;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String title;
    /**
     * 图层类型
     */
    @Dict(dictTable = "jn_adaptive_layer_type", dicText = "type_title", dicCode = "type_code")
    private String layerType;
    /**
     * 图层服务路径
     */
    @ApiModelProperty(value = "图层服务路径")
    private String url;
    /**
     * 所属板块
     */
    @Dict(dictTable = "jn_adaptive_module", dicText = "title", dicCode = "id")
    private String module;
    /**
     * 图例路径
     */
    @ApiModelProperty(value = "图例路径")
    private String figureUrl;
    /**
     * 仰角
     */
    @ApiModelProperty(value = "仰角")
    private Double pitch;
    /**
     * 缩放级别
     */
    @ApiModelProperty(value = "缩放级别")
    private Double zoom;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private Double longitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private Double latitude;
    /**
     * 排序值
     */
    @ApiModelProperty(value = "排序值")
    private Double sortOrder;
    /**
     * 默认显示
     */
    @Dict(dicCode = "yn")
    @ApiModelProperty(value = "默认显示")
    private java.lang.String defaultShow;

    public List<JnAdaptiveLayerTreeVO> children;

    public JnAdaptiveLayerTreeVO(JnAdaptiveLayer jnAdaptiveLayer) {
        BeanUtils.copyProperties(jnAdaptiveLayer, this);
        this.initChildren();
    }

    public void initChildren() {
        JnAdaptiveLayerServiceImpl layerService = SpringContextUtils.getBean(JnAdaptiveLayerServiceImpl.class);
        List<JnAdaptiveLayer> layerList = layerService.getChildrenListByParentId(this.id);
        this.children = layerList.stream().map(JnAdaptiveLayerTreeVO::new).collect(Collectors.toList());
    }
}
