package org.training.cloud.tool.service.db;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.springframework.stereotype.Service;
import org.training.cloud.tool.dto.db.DatabaseTableDTO;

import java.util.List;

/**
 * 数据库表
 *
 * @author wangtongzhou
 * @since 2024-02-26 21:13
 */
@Service
public class DatabaseTableServiceImpl implements DatabaseTableService {
    @Override
    public List<TableInfo> getTableList(DatabaseTableDTO databaseTableDTO) {
        return null;
    }

    @Override
    public TableInfo getTable(DatabaseTableDTO databaseTableDTO) {
        return null;
    }
}
