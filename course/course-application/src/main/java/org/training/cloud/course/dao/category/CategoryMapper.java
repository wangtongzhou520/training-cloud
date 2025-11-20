package org.training.cloud.course.dao.category;


import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.course.dto.category.CategoryDTO;
import org.training.cloud.course.entity.category.Category;
import org.training.cloud.system.dto.dept.DeptDTO;

import java.util.List;


@Mapper
public interface CategoryMapper extends BaseMapperExtend<Category> {

    default PageResponse<Category> selectPage(CategoryDTO categoryDTO) {
        return selectPage(categoryDTO, new LambdaQueryWrapperExtend<Category>()
                .likeIfPresent(Category::getCategoryName, categoryDTO.getName())
                .eqIfPresent(Category::getId, categoryDTO.getId())
                .eqIfPresent(Category::getParentId, categoryDTO.getParentId())
                .eqIfPresent(Category::getSort, categoryDTO.getSort())
                .orderByDesc(Category::getId)
        );
    }


    /**
     * 查询部门树
     *
     * @return
     */
    default List<Category> selectCategoryList(CategoryDTO categoryDTO) {
        return selectList(new LambdaQueryWrapperExtend<Category>()
                .likeIfPresent(Category::getCategoryName, categoryDTO.getName())
                .eqIfPresent(Category::getDeleteState, false)
        );
    }

}