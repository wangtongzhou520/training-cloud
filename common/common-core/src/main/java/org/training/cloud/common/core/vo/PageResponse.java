package org.training.cloud.common.core.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息
 *
 * @author wangtongzhou
 * @since 2020-05-31 10:23
 */
public class PageResponse<T> implements Serializable {

    /**
     * 数据
     */
    private List<T> list;

    /**
     * 总量
     */
    private Long total;


    public List<T> getList() {
        return list;
    }

    public PageResponse<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    public Long getTotal() {
        return total;
    }

    public PageResponse<T> setTotal(Long total) {
        this.total = total;
        return this;
    }

    /**
     * 分页信息
     *
     * @param list  分页
     * @param total 总数
     * @param <T>
     * @return 分页信息
     */
    public static <T> PageResponse<T> pageResponse(List<T> list, Long total) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setList(list);
        pageResponse.setTotal(total);
        return pageResponse;
    }
}
