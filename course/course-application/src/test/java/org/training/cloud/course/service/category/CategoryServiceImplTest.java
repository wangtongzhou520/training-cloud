package org.training.cloud.course.service.category;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.course.dao.category.CategoryMapper;
import org.training.cloud.course.dao.course.CourseMapper;
import org.training.cloud.course.dto.category.AddCategoryDTO;
import org.training.cloud.course.dto.category.ModifyCategoryDTO;
import org.training.cloud.course.entity.category.Category;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.training.cloud.course.constant.CourseExceptionEnumConstants.*;

/**
 * 分类服务单元测试
 *
 * @author wangtongzhou
 * @since 2025-01-29
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private CourseMapper courseMapper;

    private Category testCategory;

    @Before
    public void setUp() {
        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setCategoryName("测试分类");
        testCategory.setParentId(0L);
        testCategory.setDeleteState(false);
    }

    /**
     * 测试新增分类 - 成功场景
     */
    @Test
    public void testAddCategory_Success() {
        // Given
        AddCategoryDTO addCategoryDTO = new AddCategoryDTO();
        addCategoryDTO.setName("新分类");
        addCategoryDTO.setParentId(0L);
        addCategoryDTO.setSort(1);

        when(categoryMapper.insert(any(Category.class))).thenReturn(1);

        // When
        categoryService.addCategory(addCategoryDTO);

        // Then
        verify(categoryMapper, times(1)).insert(any(Category.class));
    }

    /**
     * 测试新增分类 - 父级分类不存在
     */
    @Test(expected = BusinessException.class)
    public void testAddCategory_ParentNotExists() {
        // Given
        AddCategoryDTO addCategoryDTO = new AddCategoryDTO();
        addCategoryDTO.setName("新分类");
        addCategoryDTO.setParentId(999L);
        addCategoryDTO.setSort(1);

        when(categoryMapper.selectById(999L)).thenReturn(null);

        // When
        categoryService.addCategory(addCategoryDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试修改分类 - 成功场景
     */
    @Test
    public void testModifyCategory_Success() {
        // Given
        ModifyCategoryDTO modifyCategoryDTO = new ModifyCategoryDTO();
        modifyCategoryDTO.setId(1L);
        modifyCategoryDTO.setName("修改后的分类");

        when(categoryMapper.selectById(1L)).thenReturn(testCategory);
        when(categoryMapper.updateById(any(Category.class))).thenReturn(1);

        // When
        categoryService.modifyCategory(modifyCategoryDTO);

        // Then
        verify(categoryMapper, times(1)).updateById(any(Category.class));
    }

    /**
     * 测试修改分类 - 分类不存在
     */
    @Test(expected = BusinessException.class)
    public void testModifyCategory_NotExists() {
        // Given
        ModifyCategoryDTO modifyCategoryDTO = new ModifyCategoryDTO();
        modifyCategoryDTO.setId(999L);
        modifyCategoryDTO.setName("修改后的分类");

        when(categoryMapper.selectById(999L)).thenReturn(null);

        // When
        categoryService.modifyCategory(modifyCategoryDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试修改分类 - 不能将自己设置为父级分类
     */
    @Test(expected = BusinessException.class)
    public void testModifyCategory_SelfAsParent() {
        // Given
        ModifyCategoryDTO modifyCategoryDTO = new ModifyCategoryDTO();
        modifyCategoryDTO.setId(1L);
        modifyCategoryDTO.setParentId(1L); // 设置自己为父级

        when(categoryMapper.selectById(1L)).thenReturn(testCategory);

        // When
        categoryService.modifyCategory(modifyCategoryDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试删除分类 - 成功场景
     */
    @Test
    public void testDelCategory_Success() {
        // Given
        when(categoryMapper.selectById(1L)).thenReturn(testCategory);
        when(categoryMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(0L);
        when(courseMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(0L);
        when(categoryMapper.deleteById(1L)).thenReturn(1);

        // When
        categoryService.delCategory(1L);

        // Then
        verify(categoryMapper, times(1)).deleteById(1L);
    }

    /**
     * 测试删除分类 - 存在子分类
     */
    @Test(expected = BusinessException.class)
    public void testDelCategory_HasSubCategory() {
        // Given
        when(categoryMapper.selectById(1L)).thenReturn(testCategory);
        when(categoryMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(1L); // 有子分类

        // When
        categoryService.delCategory(1L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试删除分类 - 存在关联课程
     */
    @Test(expected = BusinessException.class)
    public void testDelCategory_HasCourses() {
        // Given
        when(categoryMapper.selectById(1L)).thenReturn(testCategory);
        when(categoryMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(0L); // 无子分类
        when(courseMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(1L); // 有课程

        // When
        categoryService.delCategory(1L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试根据ID查询分类 - 成功场景
     */
    @Test
    public void testGetCategoryById_Success() {
        // Given
        when(categoryMapper.selectById(1L)).thenReturn(testCategory);

        // When
        Category result = categoryService.getCategoryById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId().longValue());
        assertEquals("测试分类", result.getCategoryName());
    }

    /**
     * 测试根据ID查询分类 - 分类不存在
     */
    @Test(expected = BusinessException.class)
    public void testGetCategoryById_NotExists() {
        // Given
        when(categoryMapper.selectById(999L)).thenReturn(null);

        // When
        categoryService.getCategoryById(999L);

        // Then - 期望抛出BusinessException
    }
}
