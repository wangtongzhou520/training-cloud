package org.training.cloud.tool.service.generator;

import org.training.cloud.tool.dto.generator.AddGeneratorDTO;

import java.util.List;

/**
 * 代码生成器
 *
 * @author wangtongzhou
 * @since 2024-02-27 20:51
 */
public interface GeneratorService {


    /**
     * 创建代码生成器列表
     *
     * @param userId
     * @param addGeneratorDTO
     * @return
     */
    List<Long> addGeneratorList(Long userId, AddGeneratorDTO addGeneratorDTO);

}
