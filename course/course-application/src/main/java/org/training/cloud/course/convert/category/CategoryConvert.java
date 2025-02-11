package org.training.cloud.course.convert.category;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.category.AddCategoryDTO;
import org.training.cloud.course.dto.category.ModifyCategoryDTO;
import org.training.cloud.course.entity.category.Category;
import org.training.cloud.course.vo.category.CategoryVO;

import java.util.List;


@Mapper
public interface CategoryConvert {


    CategoryConvert INSTANCE = Mappers.getMapper(CategoryConvert.class);

    @Mapping(source = "name", target = "categoryName")
    Category convert(AddCategoryDTO addCategoryDTO);

    PageResponse<CategoryVO> convert(PageResponse<Category> categoryPageResponse);

    @Mapping(source = "name", target = "categoryName")
    Category convert(ModifyCategoryDTO modifyCategoryDTO);

    @Mapping(source = "categoryName", target = "name")
    CategoryVO convert(Category category);


    List<CategoryVO> convert(List<Category> categoryList);





}