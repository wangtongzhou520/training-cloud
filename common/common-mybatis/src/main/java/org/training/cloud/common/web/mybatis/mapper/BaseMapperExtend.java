package org.training.cloud.common.web.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.apache.ibatis.annotations.Param;
import org.training.cloud.common.web.core.vo.PageParam;
import org.training.cloud.common.web.core.vo.PageResponse;
import org.training.cloud.common.web.mybatis.util.MyBatisUtils;

import java.util.Collection;
import java.util.List;

/**
 * mybatis plus baseMapper 扩展类
 *
 * @author wangtongzhou
 * @since 2023-01-29 07:39
 */
public interface BaseMapperExtend<T> extends BaseMapper<T> {

    /**
     * 分页扩展
     *
     * @param pageParam
     * @param queryWrapper
     * @return
     */
    default PageResponse<T> selectPage(PageParam pageParam, @Param("ew") Wrapper<T> queryWrapper) {
        // MyBatis Plus 查询
        IPage<T> mpPage = MyBatisUtils.buildPage(pageParam);
        selectPage(mpPage, queryWrapper);
        // 转换返回
        return PageResponse.pageResponse(mpPage.getRecords(), mpPage.getTotal());
    }

    /**
     * 根据字段查询单个实体
     *
     * @param field
     * @param value
     * @return
     */
    default T selectOne(String field, Object value) {
        return selectOne(new QueryWrapper<T>().eq(field, value));
    }

    default T selectOne(String field1, Object value1, String field2, Object value2) {
        return selectOne(new QueryWrapper<T>().eq(field1, value1).eq(field2, value2));
    }

    default T selectOne(SFunction<T, ?> field1, Object value1, SFunction<T, ?> field2, Object value2) {
        return selectOne(new LambdaQueryWrapper<T>().eq(field1, value1).eq(field2, value2));
    }

    /**
     * 根据函数查询单个实体
     *
     * @param field
     * @param value
     * @return
     */
    default T selectOne(SFunction<T, ?> field, Object value) {
        return selectOne(new LambdaQueryWrapper<T>().eq(field, value));
    }

    /**
     * 根据字段查询条数
     *
     * @param field
     * @param value
     * @return
     */
    default Integer selectCount(String field, Object value) {
        return selectCount(new QueryWrapper<T>().eq(field, value));
    }

    /**
     * 根据函数查询单个实体
     *
     * @param field
     * @param value
     * @return
     */
    default Integer selectCount(SFunction<T, ?> field, Object value) {
        return selectCount(new LambdaQueryWrapper<T>().eq(field, value));
    }

    /**
     * 查询所有
     *
     * @return
     */
    default List<T> selectList() {
        return selectList(new QueryWrapper<>());
    }

    /**
     * 根据字段查询列表
     *
     * @param field
     * @param value
     * @return
     */
    default List<T> selectList(String field, Object value) {
        return selectList(new QueryWrapper<T>().eq(field, value));
    }

    /**
     * 根据函数查询列表
     *
     * @param field
     * @param value
     * @return
     */
    default List<T> selectList(SFunction<T, ?> field, Object value) {
        return selectList(new LambdaQueryWrapper<T>().eq(field, value));
    }

    /**
     * 根据字段查询多个值
     *
     * @param field
     * @param values
     * @return
     */
    default List<T> selectList(String field, Collection<?> values) {
        return selectList(new QueryWrapper<T>().in(field, values));
    }

    /**
     * 根据函数查询多个值
     *
     * @param field
     * @param values
     * @return
     */
    default List<T> selectList(SFunction<T, ?> field, Collection<?> values) {
        return selectList(new LambdaQueryWrapper<T>().in(field, values));
    }

}
