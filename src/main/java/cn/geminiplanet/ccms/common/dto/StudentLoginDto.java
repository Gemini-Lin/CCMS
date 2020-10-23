package cn.geminiplanet.ccms.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class StudentLoginDto implements Serializable {

    // validation JSR303校验
    @NotBlank(message = "学号不能为空")
    private String sId;

    @NotBlank(message = "密码不能为空")
    private String password;
}

