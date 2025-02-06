package org.training.cloud.course.service.course;


import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.convert.course.ChapterConvert;
import org.training.cloud.course.dao.course.ChapterMapper;
import org.training.cloud.course.dto.course.AddChapterDTO;
import org.training.cloud.course.dto.course.ChapterDTO;
import org.training.cloud.course.dto.course.ModifyChapterDTO;
import org.training.cloud.course.entity.course.Chapter;

import javax.annotation.Resource;
import java.util.Objects;

import static org.training.cloud.course.constant.CourseExceptionEnumConstants.CHAPTER_NOT_EXISTS;


@Service
public class ChapterServiceImpl implements ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    @Override
    public void addChapter(AddChapterDTO addChapterDTO) {
        Chapter chapter = ChapterConvert.INSTANCE.convert(addChapterDTO);
        chapterMapper.insert(chapter);
    }


    @Override
    public void modifyChapter(ModifyChapterDTO modifyChapterDTO) {
        checkExistById(modifyChapterDTO.getId());
        Chapter chapter = ChapterConvert.INSTANCE.convert(modifyChapterDTO);
        chapterMapper.updateById(chapter);
    }


    @Override
    public PageResponse<Chapter> pageChapter(ChapterDTO chapterDTO) {
        return chapterMapper.selectPage(chapterDTO);
    }


    @Override
    public void delChapter(Long id) {
        checkExistById(id);
        chapterMapper.deleteById(id);
    }


    @Override
    public Chapter getChapterById(Long id) {
        return chapterMapper.selectById(id);
    }


    private void checkExistById(Long id) {
        Chapter chapter = chapterMapper.selectById(id);
        if (Objects.isNull(chapter)) {
            throw new BusinessException(CHAPTER_NOT_EXISTS);
        }
    }
}