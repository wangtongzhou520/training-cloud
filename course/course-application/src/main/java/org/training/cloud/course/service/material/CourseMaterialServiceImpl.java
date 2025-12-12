package org.training.cloud.course.service.material;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.course.convert.material.CourseMaterialConvert;
import org.training.cloud.course.dao.course.CourseMapper;
import org.training.cloud.course.dao.course.LessonMapper;
import org.training.cloud.course.dao.material.CourseMaterialMapper;
import org.training.cloud.course.dto.material.AddCourseMaterialDTO;
import org.training.cloud.course.dto.material.CourseMaterialDTO;
import org.training.cloud.course.dto.material.ModifyCourseMaterialDTO;
import org.training.cloud.course.entity.course.Course;
import org.training.cloud.course.entity.course.Lesson;
import org.training.cloud.course.entity.material.CourseMaterial;
import org.training.cloud.course.vo.material.CourseMaterialVO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static org.training.cloud.course.constant.CourseExceptionEnumConstants.*;


@Service
public class CourseMaterialServiceImpl implements CourseMaterialService {

    @Resource
    private CourseMaterialMapper courseMaterialMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private LessonMapper lessonMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCourseMaterial(AddCourseMaterialDTO addCourseMaterialDTO) {
        // 检查课程是否存在
        Course course = courseMapper.selectById(addCourseMaterialDTO.getCourseId());
        if (Objects.isNull(course)) {
            throw new BusinessException(COURSE_MATERIAL_COURSE_NOT_EXISTS);
        }

        // 如果关联了课时，检查课时是否存在且属于该课程
        if (Objects.nonNull(addCourseMaterialDTO.getLessonId())) {
            Lesson lesson = lessonMapper.selectById(addCourseMaterialDTO.getLessonId());
            if (Objects.isNull(lesson)) {
                throw new BusinessException(COURSE_MATERIAL_LESSON_NOT_EXISTS);
            }
            // 验证课时是否属于该课程（通过章节关联）
            // 这里简化处理，实际应该通过chapter_id验证
        }

        // 转换并初始化资料信息
        CourseMaterial courseMaterial = CourseMaterialConvert.INSTANCE.convert(addCourseMaterialDTO);
        courseMaterial.setDownloadCount(0);

        courseMaterialMapper.insert(courseMaterial);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyCourseMaterial(ModifyCourseMaterialDTO modifyCourseMaterialDTO) {
        checkExistById(modifyCourseMaterialDTO.getId());

        CourseMaterial courseMaterial = CourseMaterialConvert.INSTANCE.convert(modifyCourseMaterialDTO);
        courseMaterialMapper.updateById(courseMaterial);
    }

    @Override
    public PageResponse<CourseMaterialVO> pageInfo(CourseMaterialDTO courseMaterialDTO) {
        PageResponse<CourseMaterial> pageResponse = courseMaterialMapper.selectPage(courseMaterialDTO);
        return CourseMaterialConvert.INSTANCE.convert(pageResponse);
    }

    @Override
    public List<CourseMaterialVO> listByCourseAndLesson(Long courseId, Long lessonId) {
        LambdaQueryWrapperExtend<CourseMaterial> wrapper = new LambdaQueryWrapperExtend<>();
        wrapper.eq(CourseMaterial::getCourseId, courseId)
                .eq(CourseMaterial::getDeleteState, false);

        // 如果指定了lessonId，则查询该课时的资料；否则查询课程资料（lessonId为空）
        if (Objects.nonNull(lessonId)) {
            wrapper.eq(CourseMaterial::getLessonId, lessonId);
        } else {
            wrapper.isNull(CourseMaterial::getLessonId);
        }

        wrapper.orderByAsc(CourseMaterial::getSort);

        List<CourseMaterial> materials = courseMaterialMapper.selectList(wrapper);
        return CourseMaterialConvert.INSTANCE.convertList(materials);
    }

    @Override
    public CourseMaterialVO getCourseMaterialDetail(Long id) {
        CourseMaterial courseMaterial = getCourseMaterialById(id);
        return CourseMaterialConvert.INSTANCE.convert(courseMaterial);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delCourseMaterial(Long id) {
        checkExistById(id);
        courseMaterialMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseDownloadCount(Long id) {
        CourseMaterial courseMaterial = getCourseMaterialById(id);

        // 增加下载次数
        CourseMaterial updateMaterial = new CourseMaterial();
        updateMaterial.setId(id);
        updateMaterial.setDownloadCount(courseMaterial.getDownloadCount() + 1);

        courseMaterialMapper.updateById(updateMaterial);
    }

    /**
     * 根据ID获取资料信息
     *
     * @param id 资料ID
     * @return 资料信息
     */
    private CourseMaterial getCourseMaterialById(Long id) {
        CourseMaterial courseMaterial = courseMaterialMapper.selectById(id);
        if (Objects.isNull(courseMaterial)) {
            throw new BusinessException(COURSE_MATERIAL_NOT_EXISTS);
        }
        return courseMaterial;
    }

    /**
     * 校验资料是否存在
     *
     * @param id 资料ID
     */
    private void checkExistById(Long id) {
        CourseMaterial courseMaterial = courseMaterialMapper.selectById(id);
        if (Objects.isNull(courseMaterial)) {
            throw new BusinessException(COURSE_MATERIAL_NOT_EXISTS);
        }
    }

}
