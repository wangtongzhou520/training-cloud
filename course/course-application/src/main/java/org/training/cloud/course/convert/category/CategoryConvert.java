package org.training.cloud.course.convert.category;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.category.AddCategoryDTO;
import org.training.cloud.course.dto.category.ModifyCategoryDTO;
import org.training.cloud.course.entity.category.Category;
import org.training.cloud.course.vo.category.CategoryVO;


@Mapper
public interface CategoryConvert {


    CategoryConvert INSTANCE = Mappers.getMapper(CategoryConvert.class);


    Category convert(AddCategoryDTO addCategoryDTO);

    PageResponse<CategoryVO> convert(PageResponse<Category> categoryPageResponse);


    Category convert(ModifyCategoryDTO modifyCategoryDTO);


    CategoryVO convert(Category category);





}