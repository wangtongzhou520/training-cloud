package com.springcloud.study.system.service.dept;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.springcloud.study.common.core.exception.BusinessException;
import com.springcloud.study.common.core.util.ParamValidatorUtil;
import com.springcloud.study.system.bo.dept.DeptTreeBO;
import com.springcloud.study.system.convert.dept.SysDeptConvert;
import com.springcloud.study.system.dao.dept.SysDeptMapper;
import com.springcloud.study.system.dto.dept.SaveDeptDTO;
import com.springcloud.study.system.dto.dept.UpdateDeptDTO;
import com.springcloud.study.system.entity.dept.SysDeptDO;
import com.springcloud.study.system.util.LevelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.collect.ArrayListMultimap.create;

/**
 * 部门服务
 *
 * @author wangtongzhou
 * @since 2020-06-15 20:28
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public void saveDept(SaveDeptDTO saveDeptDTO) {
        //判断同一层级下面是否包含相同的部门名称
        if (checkExist(saveDeptDTO.getParentId(), saveDeptDTO.getName())) {
            throw new BusinessException("同一层级下存在相同的部门名称");
        }
        //参数转换
        SysDeptDO sysDeptDO = SysDeptConvert.INSTANCE.convert(saveDeptDTO);
        String level = LevelUtil.calculateLevel(
                queryLevelById(saveDeptDTO.getParentId()),
                saveDeptDTO.getParentId());
        sysDeptDO.setLevel(level)
                .setCreateOperator("")
                .setModifiedOperator("")
                .setModifiedOperatorIp("")
                .setGmtCreate(new Date())
                .setGmtModified(new Date());
        sysDeptMapper.insert(sysDeptDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDept(UpdateDeptDTO updateDeptDTO) {
        //检查传入的部门id是否存在
        SysDeptDO before =
                sysDeptMapper.selectById(updateDeptDTO.getId());
        Preconditions.checkNotNull(before, "待更新的部门信息不存在");
        //检查同一层级下面是否存在相同的部门
        if (checkExist(updateDeptDTO.getParentId(), updateDeptDTO.getName())) {
            throw new BusinessException("同一层级下存在相同的部门名称");
        }
        //组合do
        SysDeptDO after = SysDeptConvert.INSTANCE.convert(updateDeptDTO);
        String level = LevelUtil.calculateLevel(
                queryLevelById(updateDeptDTO.getParentId()),
                updateDeptDTO.getParentId());
        after.setLevel(level)
                .setCreateOperator("")
                .setModifiedOperator("")
                .setModifiedOperatorIp("")
                .setGmtCreate(new Date())
                .setGmtModified(new Date());
        //更新部门信息
        updateWithChild(before, after);
    }

    /**
     * 更新自己和自己下级信息
     *
     * @param before before
     * @param after  after
     */
    private void updateWithChild(SysDeptDO before, SysDeptDO after) {
        //判断下级部门信息是否需要更新
        String beforeLevel = before.getLevel();
        String afterLevel = after.getLevel();
        if (!beforeLevel.equals(afterLevel)) {
            //查询部门登记查询下级信息
            List<SysDeptDO> sysDeptDoList =
                    sysDeptMapper.queryChildDeptByLevel(beforeLevel);
            if (CollectionUtils.isNotEmpty(sysDeptDoList)) {
                sysDeptDoList.stream().forEach(sysDeptDO -> {
                    String level = sysDeptDO.getLevel();
                    if (level.indexOf(beforeLevel) == 0) {
                        level = afterLevel + level.substring(beforeLevel.length());
                        sysDeptDO.setLevel(level);
                    }
                });
                sysDeptMapper.batchUpdateLevel(sysDeptDoList);
            }
        }
        //更新自己
        sysDeptMapper.updateById(after);
    }


    @Override
    public List<DeptTreeBO> deptTrees() {
        List<SysDeptDO> allDeptList = sysDeptMapper.queryAllDept();
        //转化实体 do转bo
        List<DeptTreeBO> deptBoList =
                SysDeptConvert.INSTANCE.convert(allDeptList);
        //递归生成树
        return deptBoListConvertDeptTree(deptBoList);
    }

    @Override
    public void delDeptById(Integer id) {
        //查询部门是否存在
        SysDeptDO deptDO = sysDeptMapper.selectById(id);
        Preconditions.checkNotNull(deptDO, "当前部门不存在,无法删除");
        //检查该部门下面是否存在子部门
        if (sysDeptMapper.countByParentId(id) > 0) {
            throw new BusinessException("当前部门下面还存在子部门");
        }
        //检查该部门下是否还存在用户信息
        //TODO 待用户信息完善时候在补充
        sysDeptMapper.deleteById(id);
    }

    /**
     * 转化部门树
     *
     * @param deptBoList 部门列表
     * @return 部门树列表
     */
    private List<DeptTreeBO> deptBoListConvertDeptTree(List<DeptTreeBO> deptBoList) {
        if (CollectionUtils.isEmpty(deptBoList)) {
            return Lists.newArrayList();
        }
        //level -> [dept1,dept2,..]
        Multimap<String, DeptTreeBO> deptTreeBoMultimap = create();
        List<DeptTreeBO> rootList = Lists.newArrayList();
        for (DeptTreeBO deptTreeBO : deptBoList) {
            deptTreeBoMultimap.put(deptTreeBO.getLevel(), deptTreeBO);
            //加入第一层
            if (LevelUtil.ROOT.equals(deptTreeBO.getLevel())) {
                rootList.add(deptTreeBO);
            }
        }
        //rootList排序
        rootList = rootList.stream()
                .sorted(Comparator.comparing(DeptTreeBO::getSeq))
                .collect(Collectors.toList());
        //递归排序
        transformDeptTree(rootList, LevelUtil.ROOT, deptTreeBoMultimap);
        return rootList;
    }

    /**
     * 递归获取树的信息
     *
     * @param deptTreeBoList 树的列表
     * @param level          层级
     * @param multimap       层级和
     */
    private void transformDeptTree(List<DeptTreeBO> deptTreeBoList,
                                   String level,
                                   Multimap<String, DeptTreeBO> multimap) {
        for (int i = 0; i < deptTreeBoList.size(); i++) {
            //取到部门信息
            DeptTreeBO deptTreeBo = deptTreeBoList.get(i);
            //获取下一层级level
            String nextLevel = LevelUtil.calculateLevel(level,
                    deptTreeBo.getParentId());
            //获取下一层级的信息
            List<DeptTreeBO> nextDeptTreeList = (List<DeptTreeBO>) multimap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(nextDeptTreeList)) {
                nextDeptTreeList = nextDeptTreeList
                        .stream()
                        .sorted(Comparator.comparing(DeptTreeBO::getSeq))
                        .collect(Collectors.toList());
                deptTreeBo.setDeptTreeBOList(nextDeptTreeList);
                transformDeptTree(nextDeptTreeList, nextLevel, multimap);
            }

        }
    }


    /**
     * 校验同一层级下面部门名称是否存在相同的
     *
     * @param parentId 父级部门id
     * @param name     部门名称
     * @return true or false
     */
    private boolean checkExist(Integer parentId, String name) {
        int count = sysDeptMapper.countByNameAndParentId(parentId, name);
        return count > 0;
    }

    /**
     * 根据父级部门的层级信息
     *
     * @param deptId 部门id
     * @return 层级信息
     */
    private String queryLevelById(Integer deptId) {
        SysDeptDO deptDO = sysDeptMapper.selectById(deptId);
        if (Objects.isNull(deptDO)) {
            return null;
        }
        return deptDO.getLevel();
    }
}
