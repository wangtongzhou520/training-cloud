package org.training.cloud.course.service.course;


import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.course.AddChapterDTO;
import org.training.cloud.course.dto.course.ChapterDTO;
import org.training.cloud.course.dto.course.ModifyChapterDTO;
import org.training.cloud.course.entity.course.Chapter;


/**
 * 课程章节 Service 接口
 *
 * @author
 */
public interface ChapterService {



    /**
     * 创建课程章节
     *
     * @param addChapterDTO
     */
    void addChapter(AddChapterDTO addChapterDTO);



    /**
     * 修改课程章节
     *
     * @param modifyChapterDTO
     */
    void modifyChapter(ModifyChapterDTO modifyChapterDTO);


    /**
     * 分页课程章节
     *
     * @param chapterDTO
     */
    PageResponse<Chapter> pageChapter(ChapterDTO chapterDTO);


    /**
     * 删除课程章节
     *
     * @param id
     */
    void delChapter(Long id);




    /**
     * 查询单个课程章节
     *
     * @param id
     * @return
     */
    Chapter getChapterById(Long id);


}