package cn.geminiplanet.ccms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("topicselect")
@ApiModel(value="TopicSelect对象", description="")
public class TopicSelect implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "tsId", type = IdType.AUTO)
    private Integer tsId;

    @TableId(value = "subId")
    private Integer subId;

    @TableId(value = "gId")
    private Integer gId;
}
