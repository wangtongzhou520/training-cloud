package org.training.cloud.common.mybatis.dao;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * 基础实体对象
 *
 * @author wangtongzhou
 * @since 2020-05-25 08:02
 */
public class BaseDO implements Serializable {

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

    /**
     * 创建者
     */
    private String createOperator;

    /**
     * 修改者
     */
    private String modifiedOperator;




    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public BaseDO setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public BaseDO setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
        return this;
    }

    public String getCreateOperator() {
        return createOperator;
    }

    public BaseDO setCreateOperator(String createOperator) {
        this.createOperator = createOperator;
        return this;
    }

    public String getModifiedOperator() {
        return modifiedOperator;
    }

    public BaseDO setModifiedOperator(String modifiedOperator) {
        this.modifiedOperator = modifiedOperator;
        return this;
    }

    @Override
    public String toString() {
        return (new ToStringBuilder(this)).toString();
    }

}
