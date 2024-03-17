package org.training.cloud.tool.service.db;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.training.cloud.tool.dto.db.DatabaseTableDTO;

import java.util.List;

/**
 * 数据库表
 *
 * @author wangtongzhou
 * @since 2024-02-26 21:12
 */
public interface DatabaseTableService {


    /**
     * 查询数据库表列表
     *
     * @param databaseTableDTO
     * @return
     */
    List<TableInfo> getTableList(DatabaseTableDTO databaseTableDTO);


    /**
     * 获取单个表
     *
     * @param databaseTableDTO
     * @return
     */
    TableInfo getTable(DatabaseTableDTO databaseTableDTO);


}
