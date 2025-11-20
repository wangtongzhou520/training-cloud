package org.training.cloud.course.service.category;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.convert.category.CategoryConvert;
import org.training.cloud.course.dao.category.CategoryMapper;
import org.training.cloud.course.dto.category.AddCategoryDTO;
import org.training.cloud.course.dto.category.CategoryDTO;
import org.training.cloud.course.dto.category.ModifyCategoryDTO;
import org.training.cloud.course.entity.category.Category;
import org.training.cloud.course.vo.category.CategoryVO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static org.training.cloud.course.constant.CourseExceptionEnumConstants.CATEGORY_NOT_EXISTS;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public void addCategory(AddCategoryDTO addCategoryDTO) {
        Category category = CategoryConvert.INSTANCE.convert(addCategoryDTO);
        categoryMapper.insert(category);
    }


    @Override
    public void modifyCategory(ModifyCategoryDTO modifyCategoryDTO) {
        checkExistById(modifyCategoryDTO.getId());
        Category category = CategoryConvert.INSTANCE.convert(modifyCategoryDTO);
        categoryMapper.updateById(category);
    }


    @Override
    public PageResponse<Category> pageCategory(CategoryDTO categoryDTO) {
        return categoryMapper.selectPage(categoryDTO);
    }


    @Override
    public void delCategory(Long id) {
        checkExistById(id);
        categoryMapper.deleteById(id);
    }


    @Override
    public Category getCategoryById(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public List<CategoryVO> categoryList(CategoryDTO categoryDTO) {
        List<Category> categoryList = categoryMapper.selectList();
        if (CollectionUtils.isEmpty(categoryList)) {
            return null;
        }
        return CategoryConvert.INSTANCE.convert(categoryList);
    }


    private void checkExistById(Long id) {
        Category category = categoryMapper.selectById(id);
        if (Objects.isNull(category)) {
            throw new BusinessException(CATEGORY_NOT_EXISTS);
        }
    }
}