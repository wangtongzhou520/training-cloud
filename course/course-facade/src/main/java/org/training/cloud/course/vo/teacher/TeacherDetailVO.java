package org.training.cloud.course.vo.teacher;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema(description = "讲师详细信息返回")
public class TeacherDetailVO extends TeacherVO {

    // 继承TeacherVO的所有字段
    // 未来可以在这里添加额外的详细信息，如课程列表等

}
