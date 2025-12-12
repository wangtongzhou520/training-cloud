package org.training.cloud.course.dto.course;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Schema(description = "课程内容修改")
public class ModifyLessonDTO implements Serializable {

    @Schema(description = "课时ID")
    private Long id;

    @Schema(description = "课时名称")
    @NotBlank(message = "课时名称不能为空")
    private String lessonName;

    @Schema(description = "章节ID")
    @NotNull(message = "章节ID不能为空")
    private Long chapterId;

    @Schema(description = "排序序号")
    @NotNull(message = "排序序号不能为空")
    private Integer sort;

    @Schema(description = "课时URL", example = "https://baidu.com")
    @NotBlank(message = "课时URL不能为空")
    private String lessonUrl;

    @Schema(description = "课程ID（冗余字段，便于查询）")
    private Long courseId;

    @Schema(description = "课时类型（VIDEO-视频，LIVE-直播，DOC-文档，AUDIO-音频）")
    private String lessonType;

    @Schema(description = "课时时长（单位：秒）")
    private Integer lessonDuration;

    @Schema(description = "是否免费试听（0-否，1-是）")
    private Boolean isFree;

    @Schema(description = "是否可预览（0-否，1-是）")
    private Boolean isPreview;

    @Schema(description = "视频清晰度（SD-标清，HD-高清，FHD-超清，4K-4K）")
    private String videoQuality;

    @Schema(description = "文件大小（单位：字节）")
    private Long fileSize;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    @NotNull(message = "删除状态（0：未删除，1：已删除）不能为空")
    private Boolean deleteState;

}
