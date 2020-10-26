package cn.geminiplanet.ccms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`message`")
@ApiModel(value="Message对象", description="")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "mId", type = IdType.AUTO)
    private Integer mId;

    @TableId(value = "tId")
    private Integer tId;

    @TableId(value = "sId")
    private String sId;

    @NotBlank(message = "消息内容不能为空")
    private String content;

    @NotBlank(message = "消息发生时间不能为空")
    private String time;
}
