package org.training.cloud.course.convert.course;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.course.AddChapterDTO;
import org.training.cloud.course.dto.course.ModifyChapterDTO;
import org.training.cloud.course.entity.course.Chapter;
import org.training.cloud.course.vo.course.ChapterVO;


@Mapper
public interface ChapterConvert {


    ChapterConvert INSTANCE = Mappers.getMapper(ChapterConvert.class);


    Chapter convert(AddChapterDTO addChapterDTO);

    PageResponse<ChapterVO> convert(PageResponse<Chapter> chapterPageResponse);


    Chapter convert(ModifyChapterDTO modifyChapterDTO);


    ChapterVO convert(Chapter chapter);





}