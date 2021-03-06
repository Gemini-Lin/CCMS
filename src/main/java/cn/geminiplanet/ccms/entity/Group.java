package cn.geminiplanet.ccms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import sun.net.www.content.image.gif;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`group`")
@ApiModel(value="Group对象", description="")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "gId", type = IdType.AUTO)
    @NotNull(message = "请输入正确的组号")
    private Integer gId;

    @NotBlank(message = "小组至少要有 3 个成员")
    private String sId_1;

    @NotBlank(message = "小组至少要有 3 个成员")
    private String sId_2;

    @NotBlank(message = "小组至少要有 3 个成员")
    private String sId_3;

    private String sId_4;

    private String sId_5;

    private String sId_6;
}
