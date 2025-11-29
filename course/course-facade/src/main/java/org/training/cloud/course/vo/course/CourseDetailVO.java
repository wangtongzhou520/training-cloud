package org.training.cloud.course.vo.course;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema(description = "课程详情返回信息（包含章节和课时）")
public class CourseDetailVO extends CourseVO {

    @Schema(description = "章节列表（包含课时）")
    private List<ChapterDetailVO> chapters;

}
