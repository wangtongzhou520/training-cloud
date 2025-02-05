package org.training.cloud.course.dao.course;


import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.course.dto.course.ChapterDTO;
import org.training.cloud.course.entity.course.Chapter;




@Mapper
public interface ChapterMapper extends BaseMapperExtend<Chapter> {

    default PageResponse<Chapter> selectPage(ChapterDTO chapterDTO) {
        return selectPage(chapterDTO,new LambdaQueryWrapperExtend<Chapter>()
                .likeIfPresent(Chapter::getChapterName, chapterDTO.getChapterName())
                .eqIfPresent(Chapter::getCourseId, chapterDTO.getCourseId())
                .eqIfPresent(Chapter::getSort, chapterDTO.getSort())
                .eqIfPresent(Chapter::getDeleteState, chapterDTO.getDeleteState())
                .orderByDesc(Chapter::getId)
        );
    }

}