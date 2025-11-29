package org.training.cloud.course.constant;

import org.training.cloud.common.core.constant.ExceptionCode;


public interface CourseExceptionEnumConstants {

    /**
     * 分类相关异常
     */
    ExceptionCode CATEGORY_NOT_EXISTS = new ExceptionCode(103001001, "课程分类不存在");
    ExceptionCode CATEGORY_HAS_COURSES = new ExceptionCode(103001002, "分类下存在课程，无法删除");
    ExceptionCode CATEGORY_HAS_SUB_CATEGORY = new ExceptionCode(103001003, "分类下存在子分类，无法删除");
    ExceptionCode CATEGORY_PARENT_NOT_EXISTS = new ExceptionCode(103001004, "父级分类不存在");

    /**
     * 章节相关异常
     */
    ExceptionCode CHAPTER_NOT_EXISTS = new ExceptionCode(103002001, "课程章节不存在");
    ExceptionCode CHAPTER_HAS_LESSONS = new ExceptionCode(103002002, "章节下存在课时，无法删除");
    ExceptionCode CHAPTER_COURSE_NOT_EXISTS = new ExceptionCode(103002003, "所属课程不存在");

    /**
     * 课时相关异常
     */
    ExceptionCode LESSON_NOT_EXISTS = new ExceptionCode(103003001, "课程内容不存在");
    ExceptionCode LESSON_CHAPTER_NOT_EXISTS = new ExceptionCode(103003002, "所属章节不存在");

    /**
     * 课程相关异常
     */
    ExceptionCode COURSE_NOT_EXISTS = new ExceptionCode(103004001, "课程不存在");
    ExceptionCode COURSE_HAS_CHAPTERS = new ExceptionCode(103004002, "课程下存在章节，无法删除");
    ExceptionCode COURSE_CATEGORY_NOT_EXISTS = new ExceptionCode(103004003, "课程分类不存在");
    ExceptionCode COURSE_ALREADY_PUBLISHED = new ExceptionCode(103004004, "课程已发布，无法删除");
    ExceptionCode COURSE_NOT_PUBLISHED = new ExceptionCode(103004005, "课程未发布");

}