package org.training.cloud.course.vo.course;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema(description = "课程章节详情返回信息（包含课时列表）")
public class ChapterDetailVO extends ChapterVO {

    @Schema(description = "课时列表")
    private List<LessonVO> lessons;

}
