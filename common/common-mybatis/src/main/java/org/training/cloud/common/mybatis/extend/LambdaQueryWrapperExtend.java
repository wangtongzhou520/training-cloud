package org.training.cloud.common.mybatis.extend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * LambdaQueryWrapper扩展类
 *
 * @author wangtongzhou 
 * @since 2023-04-02 16:09
 */
public class LambdaQueryWrapperExtend<T> extends LambdaQueryWrapper<T> {

    public LambdaQueryWrapperExtend<T> likeIfPresent(SFunction<T, ?> column, String val) {
        if (StringUtils.hasText(val)) {
            return (LambdaQueryWrapperExtend<T>) super.like(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperExtend<T> inIfPresent(SFunction<T, ?> column, Collection<?> values) {
        if (!CollectionUtils.isEmpty(values)) {
            return (LambdaQueryWrapperExtend<T>) super.in(column, values);
        }
        return this;
    }

    public LambdaQueryWrapperExtend<T> inIfPresent(SFunction<T, ?> column, Object... values) {
        if (!ArrayUtils.isEmpty(values)) {
            return (LambdaQueryWrapperExtend<T>) super.in(column, values);
        }
        return this;
    }

    public LambdaQueryWrapperExtend<T> eqIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapperExtend<T>) super.eq(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperExtend<T> neIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapperExtend<T>) super.ne(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperExtend<T> gtIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapperExtend<T>) super.gt(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperExtend<T> geIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapperExtend<T>) super.ge(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperExtend<T> ltIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapperExtend<T>) super.lt(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperExtend<T> leIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapperExtend<T>) super.le(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperExtend<T> betweenIfPresent(SFunction<T, ?> column, Object val1, Object val2) {
        if (val1 != null && val2 != null) {
            return (LambdaQueryWrapperExtend<T>) super.between(column, val1, val2);
        }
        if (val1 != null) {
            return (LambdaQueryWrapperExtend<T>) ge(column, val1);
        }
        if (val2 != null) {
            return (LambdaQueryWrapperExtend<T>) le(column, val2);
        }
        return this;
    }

    public LambdaQueryWrapperExtend<T> betweenIfPresent(SFunction<T, ?> column, Object[] values) {
        Object val1 = values[0];
        Object val2 = values[1];
        return betweenIfPresent(column, val1, val2);
    }


    @Override
    public LambdaQueryWrapperExtend<T> eq(boolean condition, SFunction<T, ?> column, Object val) {
        super.eq(condition, column, val);
        return this;
    }

    @Override
    public LambdaQueryWrapperExtend<T> eq(SFunction<T, ?> column, Object val) {
        super.eq(column, val);
        return this;
    }

    @Override
    public LambdaQueryWrapperExtend<T> orderByDesc(SFunction<T, ?> column) {
        super.orderByDesc(true, column);
        return this;
    }

    @Override
    public LambdaQueryWrapperExtend<T> last(String lastSql) {
        super.last(lastSql);
        return this;
    }

    @Override
    public LambdaQueryWrapperExtend<T> in(SFunction<T, ?> column, Collection<?> coll) {
        super.in(column, coll);
        return this;
    }
}
