package cn.geminiplanet.ccms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("subject")
@ApiModel(value="Subject对象", description="")
public class Subject implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "subID", type = IdType.AUTO)
    private Integer subId;

    @NotBlank(message = "课题名称不能为空")
    private String topic;

    @NotBlank(message = "课题描述不能为空")
    private String description;

    @TableId(value = "tID")
    private Integer tId;
}
