package org.training.cloud.tool.service.generator;

import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.tool.dto.db.DatabaseTableDTO;
import org.training.cloud.tool.dto.generator.AddGeneratorDTO;
import org.training.cloud.tool.dto.generator.ModifyGeneratorDTO;
import org.training.cloud.tool.dto.generator.table.GeneratorTableDTO;
import org.training.cloud.tool.vo.db.DatabaseTableVO;
import org.training.cloud.tool.vo.generator.GeneratorVO;
import org.training.cloud.tool.vo.generator.table.GeneratorTableVO;

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


    /**
     * 修改代码生成器列表
     *
     * @param modifyGeneratorDTO
     */
    void modifyGenerator(ModifyGeneratorDTO modifyGeneratorDTO);

    /**
     * 查询生成表单
     *
     * @param databaseTableDTO
     * @return
     */
    List<DatabaseTableVO> queryGeneratorTableList(DatabaseTableDTO databaseTableDTO);


    /**
     * 查询表单生成详情
     *
     * @param tableId
     * @return
     */
    GeneratorVO queryGeneratorDetail(Long tableId);


    /**
     * 分页查询
     *
     *
     * @param generatorTableDTO
     * @return
     */
    PageResponse<GeneratorTableVO> pageGeneratorTable(GeneratorTableDTO generatorTableDTO);


    /**
     * 同步表单
     *
     * @param tableId
     */
    void syncGeneratorDB(Long tableId);


    /**
     * 删掉代码生成
     *
     * @param tableId
     */
    void delGenerator(Long tableId);


}
