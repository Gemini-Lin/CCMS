package cn.geminiplanet.ccms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`student`")
@ApiModel(value="Student对象", description="")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sId")
    @Length(min = 13, max = 13,message = "请输入正确的学号")
    private String sId;

    @Pattern(regexp = "^[a-zA-Z0-9_]{3,15}$",message = "密码要求：4-16位英文大小写、数字、下划线组成")
    private String password;

    @NotNull(message = "性别不能为空")
    private Character gender;

    @NotBlank(message = "联系方式不能为空")
    private String contact;

}
