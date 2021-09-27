package org.jeecg.modules.sysCustomFormJson.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author yxm
 * @since 2021-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_custom_form_json")
@ApiModel(value="SysCustomFormJson对象", description="")
public class SysCustomFormJson implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "表单设计器定义的表单名称(也是自动生成的数据库的名称后缀)")
    @TableField("form_name")
    private String formName;

    @ApiModelProperty(value = "表单设计器定义的json字符串")
    @TableField("form_json")
    private String formJson;

    @ApiModelProperty(value = "创建人")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "创建日期")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_by")
    private String updateBy;

    @ApiModelProperty(value = "更新日期")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "所属部门编码")
    @TableField("sys_org_code")
    private String sysOrgCode;


}

