package org.training.cloud.common.web.mybatis.extend;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * QueryWrapper扩展类 支持空判断
 *
 * @author wangtongzhou 
 * @since 2023-04-02 16:10
 */
public class QueryWrapperExtend<T> extends QueryWrapper<T> {
    public QueryWrapperExtend<T> likeIfPresent(String column, String val) {
        if (StringUtils.hasText(val)) {
            return (QueryWrapperExtend<T>) super.like(column, val);
        }
        return this;
    }

    public QueryWrapperExtend<T> inIfPresent(String column, Collection<?> values) {
        if (!CollectionUtils.isEmpty(values)) {
            return (QueryWrapperExtend<T>) super.in(column, values);
        }
        return this;
    }

    public QueryWrapperExtend<T> inIfPresent(String column, Object... values) {
        if (!ArrayUtils.isEmpty(values)) {
            return (QueryWrapperExtend<T>) super.in(column, values);
        }
        return this;
    }

    public QueryWrapperExtend<T> eqIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapperExtend<T>) super.eq(column, val);
        }
        return this;
    }

    public QueryWrapperExtend<T> neIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapperExtend<T>) super.ne(column, val);
        }
        return this;
    }

    public QueryWrapperExtend<T> gtIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapperExtend<T>) super.gt(column, val);
        }
        return this;
    }

    public QueryWrapperExtend<T> geIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapperExtend<T>) super.ge(column, val);
        }
        return this;
    }

    public QueryWrapperExtend<T> ltIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapperExtend<T>) super.lt(column, val);
        }
        return this;
    }

    public QueryWrapperExtend<T> leIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapperExtend<T>) super.le(column, val);
        }
        return this;
    }

    public QueryWrapperExtend<T> betweenIfPresent(String column, Object val1, Object val2) {
        if (val1 != null && val2 != null) {
            return (QueryWrapperExtend<T>) super.between(column, val1, val2);
        }
        if (val1 != null) {
            return (QueryWrapperExtend<T>) ge(column, val1);
        }
        if (val2 != null) {
            return (QueryWrapperExtend<T>) le(column, val2);
        }
        return this;
    }

    public QueryWrapperExtend<T> betweenIfPresent(String column, Object[] values) {
        if (values!= null && values.length != 0 && values[0] != null && values[1] != null) {
            return (QueryWrapperExtend<T>) super.between(column, values[0], values[1]);
        }
        if (values!= null && values.length != 0 && values[0] != null) {
            return (QueryWrapperExtend<T>) ge(column, values[0]);
        }
        if (values!= null && values.length != 0 && values[1] != null) {
            return (QueryWrapperExtend<T>) le(column, values[1]);
        }
        return this;
    }


    @Override
    public QueryWrapperExtend<T> eq(boolean condition, String column, Object val) {
        super.eq(condition, column, val);
        return this;
    }

    @Override
    public QueryWrapperExtend<T> eq(String column, Object val) {
        super.eq(column, val);
        return this;
    }

    @Override
    public QueryWrapperExtend<T> orderByDesc(String column) {
        super.orderByDesc(true, column);
        return this;
    }

    @Override
    public QueryWrapperExtend<T> last(String lastSql) {
        super.last(lastSql);
        return this;
    }

    @Override
    public QueryWrapperExtend<T> in(String column, Collection<?> coll) {
        super.in(column, coll);
        return this;
    }
}
