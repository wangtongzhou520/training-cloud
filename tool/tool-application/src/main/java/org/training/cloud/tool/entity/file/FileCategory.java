package org.training.cloud.tool.entity.file;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

/**
 * @author wangtongzhou
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tool_file_category", autoResultMap = true)
@Accessors(chain = true)
public class FileCategory extends BaseDO {
    /**
     * 文件分类ID
     */
    @TableId
    private Long id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 父分类ID
     */
    private Long parentId;
    /**
     * 分类级别
     */
    private String level;
    /**
     * 排序序号
     */
    private Integer seq;
    /**
     * 备注
     */
    private String remark;

}







