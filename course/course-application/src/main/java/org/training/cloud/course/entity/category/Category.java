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
     * 父菜单ID（0表示顶级分类）
     */
    private Long parentId;

    /**
     * 分类层级（1-一级，2-二级，3-三级）
     */
    private Integer categoryLevel;

    /**
     * 排序序号（数字越小越靠前）
     */
    private Integer sort;

    /**
     * 分类图标URL
     */
    private String categoryIcon;

    /**
     * 分类描述
     */
    private String categoryDescription;

    /**
     * 是否显示（0-隐藏，1-显示）
     */
    private Boolean isShow;

    /**
     * 课程数量（冗余字段，便于展示）
     */
    private Integer courseCount;

}







