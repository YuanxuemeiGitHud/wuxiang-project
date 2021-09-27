package org.jeecg.modules.fdesigner.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 设计器表单
 * @Author: jeecg-boot
 * @Date: 2021-08-20
 * @Version: V1.0
 */
@Data
@TableName("fd_form")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "fd_form对象", description = "设计器表单")
public class FdForm implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
    /**
     * 表单设计
     */
    @Excel(name = "表单设计", width = 15)
    @ApiModelProperty(value = "表单设计")
    private java.lang.String formSchema;
    /**
     * 表单类型
     */
    @Excel(name = "表单类型", width = 15, dicCode = "ol_form_biz_type")
    @Dict(dicCode = "ol_form_biz_type")
    @ApiModelProperty(value = "表单类型")
    private java.lang.String formType;
    /**
     * 表单名
     */
    @Excel(name = "表单名", width = 15)
    @ApiModelProperty(value = "表单名")
    private java.lang.String title;
    /**
     * 表单描述
     */
    @Excel(name = "表单描述", width = 15)
    @ApiModelProperty(value = "表单描述")
    private java.lang.String description;
}
