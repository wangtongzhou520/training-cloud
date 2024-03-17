package org.training.cloud.tool.service.db;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.training.cloud.tool.dto.db.DatabaseTableDTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * table
 *
 * @author wangtongzhou
 * @since 2024-03-17 17:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseTableServiceImplTest {

    @Resource
    private DatabaseTableService databaseTableService;



    @Test
    public void test_getTable(){
        DatabaseTableDTO databaseTableDTO=new DatabaseTableDTO();
        databaseTableDTO.setDataSourceConfigId(1L);
        List<TableInfo> tableInfos= databaseTableService.getTableList(databaseTableDTO);
        Assert.assertTrue(CollectionUtils.isNotEmpty(tableInfos));
    }


}
