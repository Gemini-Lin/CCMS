package cn.geminiplanet.ccms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`topic_select`")
@ApiModel(value="TopicSelect对象", description="")
public class Topicselect implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "tsId", type = IdType.AUTO)
    private Integer tsId;

    @TableId(value = "subId")
    private Integer subId;

    @TableId(value = "gId")
    private Integer gId;

    @NotBlank(message = "完成度，如80就表示完成了80%")
    private Integer completed;

    @NotBlank(message = "请及时上传实验报告")
    private String filePath;
}
