package org.training.cloud.course.entity.category;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "course_category", autoResultMap = true)
@Accessors(chain = true)
public class Category extends BaseDO {
    /**
     * 分类ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 排序序号
     */
    private Integer sort;

}







