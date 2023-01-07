package com.springcloud.study.system.vo.dept;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 部门层级树
 *
 * @author wangtongzhou
 * @since 2020-08-11 13:32
 */
@Data
@Accessors(chain = true)
public class DeptTreeVO extends QueryDeptVO {

    /**
     * 下一层级列表
     */
    private List<DeptTreeVO> deptTreeBOList;
}
