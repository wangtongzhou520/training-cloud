package org.training.cloud.system.service.dept;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.system.convert.dept.SysDeptConvert;
import org.training.cloud.system.dao.dept.SysDeptMapper;
import org.training.cloud.system.dto.dept.SaveDeptDTO;
import org.training.cloud.system.dto.dept.UpdateDeptDTO;
import org.training.cloud.system.entity.dept.SysDept;
import org.training.cloud.system.util.LevelUtil;
import org.training.cloud.system.vo.dept.DeptTreeVO;

import java.util.List;
import java.util.Objects;

/**
 * 部门服务
 *
 * @author wangtongzhou
 * @since 2020-06-15 20:28
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public void saveDept(SaveDeptDTO saveDeptDTO) {
        //判断同一层级下面是否包含相同的部门名称
        if (checkExist(saveDeptDTO.getParentId(), saveDeptDTO.getName())) {
            throw new BusinessException("同一层级下存在相同的部门名称");
        }
        //参数转换
        SysDept sysDept = SysDeptConvert.INSTANCE.convert(saveDeptDTO);
        String level = LevelUtil.calculateLevel(
                queryLevelById(saveDeptDTO.getParentId()),
                saveDeptDTO.getParentId());
        sysDept.setLevel(level)
                .setCreateOperator("")
                .setModifiedOperator("");
        sysDeptMapper.insert(sysDept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDept(UpdateDeptDTO updateDeptDTO) {
//        //检查传入的部门id是否存在
//        SysDept before =
//                sysDeptMapper.selectById(updateDeptDTO.getId());
//        Preconditions.checkNotNull(before, "待更新的部门信息不存在");
//        //检查同一层级下面是否存在相同的部门
//        if (checkExist(updateDeptDTO.getParentId(), updateDeptDTO.getName())) {
//            throw new BusinessException("同一层级下存在相同的部门名称");
//        }
//        //组合do
//        SysDept after = SysDeptConvert.INSTANCE.convert(updateDeptDTO);
//        String level = LevelUtil.calculateLevel(
//                queryLevelById(updateDeptDTO.getParentId()),
//                updateDeptDTO.getParentId());
//        after.setLevel(level)
//                .setCreateOperator("")
//                .setModifiedOperator("")
//                .setGmtCreate(new Date())
//                .setGmtModified(new Date());
//        //更新部门信息
//        updateWithChild(before, after);
    }

    /**
     * 更新自己和自己下级信息
     *
     * @param before before
     * @param after  after
     */
    private void updateWithChild(SysDept before, SysDept after) {
        //判断下级部门信息是否需要更新
        String beforeLevel = before.getLevel();
        String afterLevel = after.getLevel();
        if (!beforeLevel.equals(afterLevel)) {
            //查询部门登记查询下级信息
            List<SysDept> sysDeptList =
                    sysDeptMapper.queryChildDeptByLevel(beforeLevel);
            if (CollectionUtils.isNotEmpty(sysDeptList)) {
                sysDeptList.stream().forEach(sysDept -> {
                    String level = sysDept.getLevel();
                    if (level.indexOf(beforeLevel) == 0) {
                        level = afterLevel + level.substring(beforeLevel.length());
                        sysDept.setLevel(level);
                    }
                });
                sysDeptMapper.batchUpdateLevel(sysDeptList);
            }
        }
        //更新自己
        sysDeptMapper.updateById(after);
    }


    @Override
    public List<DeptTreeVO> deptTrees() {
        List<SysDept> allDeptList = sysDeptMapper.queryAllDept();
        //转化实体 do转bo
        List<DeptTreeVO> deptBoList =
                SysDeptConvert.INSTANCE.convert(allDeptList);
        //递归生成树
        return deptBoListConvertDeptTree(deptBoList);
    }

    @Override
    public void delDeptById(Integer id) {
//        //查询部门是否存在
//        SysDept deptDO = sysDeptMapper.selectById(id);
//        Preconditions.checkNotNull(deptDO, "当前部门不存在,无法删除");
//        //检查该部门下面是否存在子部门
//        if (sysDeptMapper.countByParentId(id) > 0) {
//            throw new BusinessException("当前部门下面还存在子部门");
//        }
//        //检查该部门下是否还存在用户信息
//        //TODO 待用户信息完善时候在补充
//        sysDeptMapper.deleteById(id);
    }

    /**
     * 转化部门树
     *
     * @param deptBoList 部门列表
     * @return 部门树列表
     */
    private List<DeptTreeVO> deptBoListConvertDeptTree(List<DeptTreeVO> deptBoList) {
//        if (CollectionUtils.isEmpty(deptBoList)) {
//            return Lists.newArrayList();
//        }
//        //level -> [dept1,dept2,..]
//        Multimap<String, DeptTreeVO> deptTreeBoMultimap = create();
//        List<DeptTreeVO> rootList = Lists.newArrayList();
//        for (DeptTreeVO deptTreeVO : deptBoList) {
//            deptTreeBoMultimap.put(deptTreeVO.getLevel(), deptTreeVO);
//            //加入第一层
//            if (LevelUtil.ROOT.equals(deptTreeVO.getLevel())) {
//                rootList.add(deptTreeVO);
//            }
//        }
//        //rootList排序
//        rootList = rootList.stream()
//                .sorted(Comparator.comparing(DeptTreeVO::getSeq))
//                .collect(Collectors.toList());
//        //递归排序
//        transformDeptTree(rootList, LevelUtil.ROOT, deptTreeBoMultimap);
//        return rootList;
        return null;
    }

    /**
     * 递归获取树的信息
     *
     * @param deptTreeBoList 树的列表
     * @param level          层级
     * @param multimap       层级和
     */
//    private void transformDeptTree(List<DeptTreeVO> deptTreeBoList,
//                                   String level,
//                                   Multimap<String, DeptTreeVO> multimap) {
//        for (int i = 0; i < deptTreeBoList.size(); i++) {
//            //取到部门信息
//            DeptTreeVO deptTreeVo = deptTreeBoList.get(i);
//            //获取下一层级level
//            String nextLevel = LevelUtil.calculateLevel(level,
//                    deptTreeVo.getParentId());
//            //获取下一层级的信息
//            List<DeptTreeVO> nextDeptTreeList = (List<DeptTreeVO>) multimap.get(nextLevel);
//            if (CollectionUtils.isNotEmpty(nextDeptTreeList)) {
//                nextDeptTreeList = nextDeptTreeList
//                        .stream()
//                        .sorted(Comparator.comparing(DeptTreeVO::getSeq))
//                        .collect(Collectors.toList());
//                deptTreeVo.setDeptTreeBOList(nextDeptTreeList);
//                transformDeptTree(nextDeptTreeList, nextLevel, multimap);
//            }
//
//        }
//    }


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
        SysDept deptDO = sysDeptMapper.selectById(deptId);
        if (Objects.isNull(deptDO)) {
            return null;
        }
        return deptDO.getLevel();
    }
}
