package org.training.cloud.course.service.material;


import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.material.AddCourseMaterialDTO;
import org.training.cloud.course.dto.material.CourseMaterialDTO;
import org.training.cloud.course.dto.material.ModifyCourseMaterialDTO;
import org.training.cloud.course.vo.material.CourseMaterialVO;

import java.util.List;


public interface CourseMaterialService {

    /**
     * 添加课程资料
     *
     * @param addCourseMaterialDTO 资料信息
     */
    void addCourseMaterial(AddCourseMaterialDTO addCourseMaterialDTO);

    /**
     * 修改课程资料
     *
     * @param modifyCourseMaterialDTO 资料信息
     */
    void modifyCourseMaterial(ModifyCourseMaterialDTO modifyCourseMaterialDTO);

    /**
     * 分页查询课程资料
     *
     * @param courseMaterialDTO 查询条件
     * @return 分页结果
     */
    PageResponse<CourseMaterialVO> pageInfo(CourseMaterialDTO courseMaterialDTO);

    /**
     * 查询课程资料列表
     *
     * @param courseId 课程ID
     * @param lessonId 课时ID（可为空）
     * @return 资料列表
     */
    List<CourseMaterialVO> listByCourseAndLesson(Long courseId, Long lessonId);

    /**
     * 查询课程资料详情
     *
     * @param id 资料ID
     * @return 资料详情
     */
    CourseMaterialVO getCourseMaterialDetail(Long id);

    /**
     * 删除课程资料
     *
     * @param id 资料ID
     */
    void delCourseMaterial(Long id);

    /**
     * 增加下载次数
     *
     * @param id 资料ID
     */
    void increaseDownloadCount(Long id);

}
