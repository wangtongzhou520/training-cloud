package org.training.cloud.tool.entity.file;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;


/**
 * 文件内容
 *
 * @author wangtongzhou
 * @since 2024-04-21 17:02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tool_file", autoResultMap = true)
@Accessors(chain = true)
public class File extends BaseDO {
    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 分类ID
     */
    private Long categoryId;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件路径
     */
    private String path;
    /**
     * url
     */
    private String url;
    /**
     * 文件后缀
     */
    private String suffix;
    /**
     * 文件类型,1文件0图片2视频
     */
    private Integer type;
    /**
     * 文件大小
     */
    private Integer size;
    /**
     * 备注
     */
    private String remark;

}








