package cn.geminiplanet.ccms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("teacher")
@ApiModel(value="Teacher对象", description="")
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "tId", type = IdType.AUTO)
    private Integer tId;


    @Pattern(regexp = "^[a-zA-Z0-9_]{3,15}$",message = "账号要求：4-16位英文大小写、数字、下划线组成")
    private String account;

    @Pattern(regexp = "^[a-zA-Z0-9_]{3,15}$",message = "密码要求：4-16位英文大小写、数字、下划线组成")
    private String password;

    @NotBlank(message = "电话不能为空")
    private String phone;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "职位不能为空")
    @Pattern(regexp = "教授|副教授|讲师",message = "职位只能为 教授、副教授、讲师")
    private String job;

    @NotBlank(message = "性别不能为空")
    private Character gender;

    @NotBlank(message = "个人介绍不能为空：包括研究方向、科研情况、教学情况、获奖情况等")
    private String description;



}
