package org.jeecg.modules.space.jiangnan.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.UnsupportedEncodingException;

/**
 * @Description: 江南大屏图层
 * @Author: jeecg-boot
 * @Date: 2021-08-25
 * @Version: V1.0
 */
@Data
@TableName("jn_adaptive_layer")
@ApiModel(value = "jn_adaptive_layer对象", description = "江南大屏图层")
public class JnAdaptiveLayer implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
    /**
     * 父级节点
     */
    @Excel(name = "父级节点", width = 15)
    @ApiModelProperty(value = "父级节点")
    private String pid;
    /**
     * 是否有子节点
     */
    @Excel(name = "是否有子节点", width = 15, dicCode = "yn")
    @Dict(dicCode = "yn")
    @ApiModelProperty(value = "是否有子节点")
    private String hasChild;
    /**
     * 名称
     */
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String title;
    /**
     * 图层类型
     */
    @Excel(name = "图层类型", width = 15, dictTable = "jn_adaptive_layer_type", dicText = "type_title", dicCode = "type_code")
    @Dict(dictTable = "jn_adaptive_layer_type", dicText = "type_title", dicCode = "type_code")
    @ApiModelProperty(value = "图层类型")
    private String layerType;
    /**
     * 图层服务路径
     */
    @Excel(name = "图层服务路径", width = 15)
    @ApiModelProperty(value = "图层服务路径")
    private String url;
    /**
     * 所属板块
     */
    @Excel(name = "所属板块", width = 15, dictTable = "jn_adaptive_module", dicText = "title", dicCode = "id")
    @Dict(dictTable = "jn_adaptive_module", dicText = "title", dicCode = "id")
    @ApiModelProperty(value = "所属板块")
    private String module;
    /**
     * 图例路径
     */
    @Excel(name = "图例路径", width = 15)
    @ApiModelProperty(value = "图例路径")
    private String figureUrl;
    /**
     * 仰角
     */
    @Excel(name = "仰角", width = 15)
    @ApiModelProperty(value = "仰角")
    private Double pitch;
    /**
     * 缩放级别
     */
    @Excel(name = "缩放级别", width = 15)
    @ApiModelProperty(value = "缩放级别")
    private Double zoom;
    /**
     * 经度
     */
    @Excel(name = "经度", width = 15)
    @ApiModelProperty(value = "经度")
    private Double longitude;
    /**
     * 纬度
     */
    @Excel(name = "纬度", width = 15)
    @ApiModelProperty(value = "纬度")
    private Double latitude;
    /**
     * 排序值
     */
    @Excel(name = "排序值", width = 15)
    @ApiModelProperty(value = "排序值")
    private Double sortOrder;
    /**
     * 默认显示
     */
    @Excel(name = "默认显示", width = 15, dicCode = "yn")
    @Dict(dicCode = "yn")
    @ApiModelProperty(value = "默认显示")
    private java.lang.String defaultShow;
}
