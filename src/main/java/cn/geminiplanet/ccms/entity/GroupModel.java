package cn.geminiplanet.ccms.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="", description="(课题ID,课题名称,小组id,完成度)")
public class GroupModel {
    private Integer subId;

    private String topic;

    private Integer gId;

    private Integer completed;

    public GroupModel(Integer subId, String topic, Integer gId, Integer completed) {
        this.subId = subId;
        this.topic = topic;
        this.gId = gId;
        this.completed = completed;
    }
}
