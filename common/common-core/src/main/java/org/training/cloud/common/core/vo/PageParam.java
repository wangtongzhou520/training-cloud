package org.training.cloud.common.core.vo;

import java.io.Serializable;

/**
 * 分页参数
 *
 * @author wangtongzhou
 * @since 2020-06-01 20:44
 */
public class PageParam implements Serializable {

    /**
     * 升序
     */
    public static final String ORDER_ASC = "asc";
    /**
     * 降序
     */
    public static final String ORDER_DESC = "desc";

    /**
     * 页码
     */
    private Integer pageNo;

    /**
     * 条数
     */
    private Integer pageSize;

    /**
     * 偏移量
     */
    private Integer offset;

    /**
     * 字段 多个字段,分割
     */
    private String field;

    /**
     * 顺序
     */
    private String order;


    public Integer getPageNo() {
        return pageNo;
    }

    public PageParam setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public PageParam setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Integer getOffset() {
        return (pageNo - 1) * pageSize;
    }

    public PageParam setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public String getField() {
        return field;
    }

    public PageParam setField(String field) {
        this.field = field;
        return this;
    }

    public String getOrder() {
        return order;
    }

    public PageParam setOrder(String order) {
        this.order = order;
        return this;
    }
}
