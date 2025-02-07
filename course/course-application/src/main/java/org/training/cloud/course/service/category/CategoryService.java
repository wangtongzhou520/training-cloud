package org.training.cloud.course.service.category;


import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.category.AddCategoryDTO;
import org.training.cloud.course.dto.category.ModifyCategoryDTO;
import org.training.cloud.course.dto.category.CategoryDTO;
import org.training.cloud.course.entity.category.Category;


/**
 * 课程分类 Service 接口
 *
 * @author
 */
public interface CategoryService {



    /**
     * 创建课程分类
     *
     * @param addCategoryDTO
     */
    void addCategory(AddCategoryDTO addCategoryDTO);



    /**
     * 修改课程分类
     *
     * @param modifyCategoryDTO
     */
    void modifyCategory(ModifyCategoryDTO modifyCategoryDTO);


    /**
     * 分页课程分类
     *
     * @param categoryDTO
     */
    PageResponse<Category> pageCategory(CategoryDTO categoryDTO);


    /**
     * 删除课程分类
     *
     * @param id
     */
    void delCategory(Long id);




    /**
     * 查询单个课程分类
     *
     * @param id
     * @return
     */
    Category getCategoryById(Long id);


}