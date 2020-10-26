package cn.geminiplanet.ccms.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="", description="(课题ID,课题名称,小组id,成绩)")
public class ScoreModel {
    private Integer subId;

    private String topic;

    private Integer gId;

    private Integer score;

    public ScoreModel(Integer subId, String topic, Integer gId, Integer score) {
        this.subId = subId;
        this.topic = topic;
        this.gId = gId;
        this.score = score;
    }
}
