package org.training.cloud.course.constant;

import org.training.cloud.common.core.constant.ExceptionCode;


public interface CourseExceptionEnumConstants {


    ExceptionCode CATEGORY_NOT_EXISTS = new ExceptionCode(103001001,"课程分类不存在");


    ExceptionCode CHAPTER_NOT_EXISTS = new ExceptionCode(103002001,"课程章节不存在");


    ExceptionCode LESSON_NOT_EXISTS = new ExceptionCode(103003001,"课程内容不存在");



}