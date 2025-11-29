package org.training.cloud.course.service.category;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.course.convert.category.CategoryConvert;
import org.training.cloud.course.dao.category.CategoryMapper;
import org.training.cloud.course.dao.course.CourseMapper;
import org.training.cloud.course.dto.category.AddCategoryDTO;
import org.training.cloud.course.dto.category.CategoryDTO;
import org.training.cloud.course.dto.category.ModifyCategoryDTO;
import org.training.cloud.course.entity.category.Category;
import org.training.cloud.course.entity.course.Course;
import org.training.cloud.course.vo.category.CategoryVO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static org.training.cloud.course.constant.CourseExceptionEnumConstants.*;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CourseMapper courseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCategory(AddCategoryDTO addCategoryDTO) {
        // 校验父级分类是否存在（如果parentId不为空且不为0）
        if (addCategoryDTO.getParentId() != null && addCategoryDTO.getParentId() != 0) {
            checkParentCategoryExists(addCategoryDTO.getParentId());
        }

        Category category = CategoryConvert.INSTANCE.convert(addCategoryDTO);
        categoryMapper.insert(category);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyCategory(ModifyCategoryDTO modifyCategoryDTO) {
        checkExistById(modifyCategoryDTO.getId());

        // 如果修改了父级分类，需要校验
        if (modifyCategoryDTO.getParentId() != null && modifyCategoryDTO.getParentId() != 0) {
            // 不能将自己设置为父级分类
            if (modifyCategoryDTO.getId().equals(modifyCategoryDTO.getParentId())) {
                throw new BusinessException(CATEGORY_PARENT_NOT_EXISTS);
            }
            checkParentCategoryExists(modifyCategoryDTO.getParentId());
        }

        Category category = CategoryConvert.INSTANCE.convert(modifyCategoryDTO);
        categoryMapper.updateById(category);
    }


    @Override
    public PageResponse<Category> pageCategory(CategoryDTO categoryDTO) {
        return categoryMapper.selectPage(categoryDTO);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delCategory(Long id) {
        checkExistById(id);

        // 检查是否存在子分类
        Long subCategoryCount = categoryMapper.selectCount(
                new LambdaQueryWrapperExtend<Category>()
                        .eq(Category::getParentId, id)
                        .eq(Category::getDeleteState, false)
        );
        if (subCategoryCount > 0) {
            throw new BusinessException(CATEGORY_HAS_SUB_CATEGORY);
        }

        // 检查分类下是否存在课程
        Long courseCount = courseMapper.selectCount(
                new LambdaQueryWrapperExtend<Course>()
                        .eq(Course::getCategoryId, id)
                        .eq(Course::getDeleteState, false)
        );
        if (courseCount > 0) {
            throw new BusinessException(CATEGORY_HAS_COURSES);
        }

        categoryMapper.deleteById(id);
    }


    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryMapper.selectById(id);
        if (Objects.isNull(category)) {
            throw new BusinessException(CATEGORY_NOT_EXISTS);
        }
        return category;
    }

    @Override
    public List<CategoryVO> categoryList(CategoryDTO categoryDTO) {
        List<Category> categoryList = categoryMapper.selectList();
        if (CollectionUtils.isEmpty(categoryList)) {
            return null;
        }
        return CategoryConvert.INSTANCE.convert(categoryList);
    }


    /**
     * 校验分类是否存在
     *
     * @param id 分类ID
     */
    private void checkExistById(Long id) {
        Category category = categoryMapper.selectById(id);
        if (Objects.isNull(category)) {
            throw new BusinessException(CATEGORY_NOT_EXISTS);
        }
    }

    /**
     * 校验父级分类是否存在
     *
     * @param parentId 父级分类ID
     */
    private void checkParentCategoryExists(Long parentId) {
        Category parentCategory = categoryMapper.selectById(parentId);
        if (Objects.isNull(parentCategory)) {
            throw new BusinessException(CATEGORY_PARENT_NOT_EXISTS);
        }
    }
}