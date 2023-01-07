package com.springcloud.study.common.core.vo;

/**
 * 分页参数
 *
 * @author wangtongzhou
 * @since 2020-06-01 20:44
 */
public class PageParam {

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
}
