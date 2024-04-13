package org.training.cloud.tool.service.generator;

import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 生成代码
 *
 * @author wangtongzhou
 * @since 2024-04-08 22:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneratorServiceImplTest {

    @Resource
    private GeneratorService generatorService;


    @Test
    public void test_previewGeneratorCode() {

        Map<String, String> generatorCodeMap =
                generatorService.previewGeneratorCode(1L);
        Assert.assertTrue(generatorCodeMap != null);

    }

}
