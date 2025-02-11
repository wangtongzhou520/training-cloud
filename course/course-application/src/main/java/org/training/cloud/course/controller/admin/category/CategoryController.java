package org.training.cloud.course.controller.admin.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.convert.category.CategoryConvert;
import org.training.cloud.course.dto.category.AddCategoryDTO;
import org.training.cloud.course.dto.category.CategoryDTO;
import org.training.cloud.course.dto.category.ModifyCategoryDTO;
import org.training.cloud.course.entity.category.Category;
import org.training.cloud.course.service.category.CategoryService;
import org.training.cloud.course.vo.category.CategoryVO;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Tag(name = "管理后台课程分类")
@RestController
@RequestMapping("/course/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @PostMapping("/add")
    @Operation(summary = "添加课程分类信息")
    public CommonResponse<?> addCategory(@RequestBody @Valid AddCategoryDTO addCategoryDTO) {
        categoryService.addCategory(addCategoryDTO);
        return CommonResponse.ok();
    }


    @PutMapping("/update")
    @Operation(summary = "更新课程分类信息")
    public CommonResponse<?> updateCategory(@RequestBody @Valid ModifyCategoryDTO modifyCategoryDTO) {
        categoryService.modifyCategory(modifyCategoryDTO);
        return CommonResponse.ok();
    }


    @GetMapping("/page")
    @Operation(summary = "分页查询课程分类")
    public CommonResponse<PageResponse<CategoryVO>> pageCategory(@Valid CategoryDTO categoryDTO) {
        PageResponse<Category> categoryPageResponse = categoryService.pageCategory(categoryDTO);
        return CommonResponse.ok(CategoryConvert.INSTANCE.convert(categoryPageResponse));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取课程分类信息")
    public CommonResponse<?> getCategoryById(@PathVariable("id") Long id) {
        Category category = categoryService.getCategoryById(id);
        return CommonResponse.ok(CategoryConvert.INSTANCE.convert(category));
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除课程分类")
    public CommonResponse<?> delCategory(@PathVariable("id") Long id) {
        categoryService.delCategory(id);
        return CommonResponse.ok();
    }


    @PostMapping("/categoryList")
    @Operation(summary = "课程分类")
    public CommonResponse<List<CategoryVO>> categoryList(@RequestBody CategoryDTO categoryDTO) {
        return CommonResponse.ok(categoryService.categoryList(categoryDTO));
    }
}