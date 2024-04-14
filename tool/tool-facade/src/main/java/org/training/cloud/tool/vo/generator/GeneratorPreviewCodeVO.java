package org.training.cloud.tool.vo.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 生成代码
 *
 * @author wangtongzhou
 * @since 2024-04-14 10:27
 */
@Data
public class GeneratorPreviewCodeVO implements Serializable {

    @Schema(description = "文件路径")
    private String filePath;

    @Schema(description = "代码")
    private String code;

}
