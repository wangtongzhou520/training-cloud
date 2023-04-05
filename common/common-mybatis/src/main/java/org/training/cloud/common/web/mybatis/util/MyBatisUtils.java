package org.training.cloud.common.web.mybatis.util;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.training.cloud.common.web.core.vo.PageParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * mybatis 工具类
 *
 * @author wangtongzhou
 * @since 2023-01-30 07:12
 */
public class MyBatisUtils {

    public static <T> Page<T> buildPage(PageParam pageParam) {
        Page<T> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
        if (StringUtils.isNotBlank(pageParam.getField())) {
            List<String> orderFields = Arrays.asList(pageParam.getField().split(","));
            page.addOrder(orderFields.stream().map(sortingField ->
                    PageParam.ORDER_ASC.equals(pageParam.getOrder()) ?
                            OrderItem.asc(sortingField) :
                            OrderItem.desc(sortingField)
            ).collect(Collectors.toList()));
        }
        return page;
    }
}
