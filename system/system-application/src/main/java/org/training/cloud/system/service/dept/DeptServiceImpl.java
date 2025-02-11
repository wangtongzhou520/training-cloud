package org.training.cloud.system.service.dept;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.utils.collection.CollectionExtUtils;
import org.training.cloud.system.convert.dept.DeptConvert;
import org.training.cloud.system.dao.dept.SysDeptMapper;
import org.training.cloud.system.dto.dept.AddDeptDTO;
import org.training.cloud.system.dto.dept.DeptDTO;
import org.training.cloud.system.dto.dept.ModifyDeptDTO;
import org.training.cloud.system.entity.dept.SysDept;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.service.user.UserService;
import org.training.cloud.common.core.utils.LevelUtil;
import org.training.cloud.system.vo.dept.DeptTreeVO;
import org.training.cloud.system.vo.dept.DeptVO;

import java.util.*;
import java.util.stream.Collectors;

import static org.training.cloud.system.constant.SystemExceptionEnumConstants.DEPT_NAME_EXISTS;
import static org.training.cloud.system.constant.SystemExceptionEnumConstants.DEPT_NOT_EXISTS;
import static com.google.common.collect.ArrayListMultimap.create;


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

    @Autowired
    private UserService userService;

    @Override
    public void addDept(AddDeptDTO addDeptDTO) {
        //判断同一层级下面是否包含相同的部门名称
        checkDeptNameExist(addDeptDTO.getParentId(), addDeptDTO.getName());
        //参数转换
        SysDept sysDept = DeptConvert.INSTANCE.convert(addDeptDTO);
        String level = LevelUtil.calculateLevel(
                queryLevelById(addDeptDTO.getParentId()),
                addDeptDTO.getParentId());
        sysDept.setLevel(level);
        sysDeptMapper.insert(sysDept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyDept(ModifyDeptDTO modifyDeptDTO) {
        //检查传入的部门id是否存在
        SysDept before = checkDeptExistById(modifyDeptDTO.getId());
//        //检查同一层级下面是否存在相同的部门
//        checkDeptNameExist(modifyDeptDTO.getParentId(), modifyDeptDTO.getName());
        //组合do
        SysDept after = DeptConvert.INSTANCE.convert(modifyDeptDTO);
        String level = LevelUtil.calculateLevel(
                queryLevelById(modifyDeptDTO.getParentId()),
                modifyDeptDTO.getParentId());
        after.setLevel(level);
        //更新部门信息
        updateWithChild(before, after);
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
            List<SysDept> sysDeptList = sysDeptMapper.selectByChildDeptByLevel(beforeLevel);
            if (CollectionUtils.isNotEmpty(sysDeptList)) {
                sysDeptList.stream().forEach(sysDept -> {
                    String level = sysDept.getLevel();
                    if (level.indexOf(beforeLevel) == 0) {
                        level = afterLevel + level.substring(beforeLevel.length());
                        sysDept.setLevel(level);
                    }
                });
                sysDeptMapper.insertBatch(sysDeptList);
            }
        }
        //更新自己
        sysDeptMapper.updateById(after);
    }


    @Override
    public void removeDeptById(Long id) {
        //查询部门是否存在
        checkDeptExistById(id);
        //检查该部门下面是否存在子部门
        if (sysDeptMapper.countByParentId(id) > 0) {
            throw new BusinessException("当前部门下面还存在子部门");
        }
        //检查该部门下是否还存在用户信息
        sysDeptMapper.deleteById(id);
    }

    @Override
    public SysDept getDeptId(Long id) {
        SysDept dept = sysDeptMapper.selectById(id);
        if (Objects.isNull(dept)) {
            throw new BusinessException(DEPT_NOT_EXISTS);
        }
        return dept;
    }

    @Override
    public List<SysDept> getDeptListByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.emptyList();
        }
        return sysDeptMapper.selectDeptListByIds(ids);
    }

    @Override
    public List<DeptVO> getAllDept(DeptDTO deptDTO) {
        List<SysDept> sysDeptList = sysDeptMapper.selectDeptList(deptDTO);
        if (CollectionUtils.isEmpty(sysDeptList)) {
            return null;
        }
        //查询负责人列表
        List<SysUser> sysUserList = userService.getUserByIds(CollectionExtUtils.convertSet(sysDeptList,
                SysDept::getManageId));
       return DeptConvert.INSTANCE.convert(sysDeptList,sysUserList);
    }

    /**
     * 转化部门树
     *
     * @param deptBoList 部门列表
     * @return 部门树列表
     */
    private List<DeptTreeVO> deptBoListConvertDeptTree(List<DeptTreeVO> deptBoList) {
        if (CollectionUtils.isEmpty(deptBoList)) {
            return Lists.newArrayList();
        }
        //level -> [dept1,dept2,..]
        Multimap<String, DeptTreeVO> deptTreeBoMultimap = create();
        List<DeptTreeVO> rootList = Lists.newArrayList();
        for (DeptTreeVO deptTreeVO : deptBoList) {
            deptTreeBoMultimap.put(deptTreeVO.getLevel(), deptTreeVO);
            //加入第一层
            if (LevelUtil.ROOT.equals(deptTreeVO.getLevel())) {
                rootList.add(deptTreeVO);
            }
        }
        //rootList排序
        rootList = rootList.stream()
                .sorted(Comparator.comparing(DeptTreeVO::getSeq))
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
    private void transformDeptTree(List<DeptTreeVO> deptTreeBoList,
                                   String level,
                                   Multimap<String, DeptTreeVO> multimap) {
        for (int i = 0; i < deptTreeBoList.size(); i++) {
            //取到部门信息
            DeptTreeVO deptTreeVo = deptTreeBoList.get(i);
            //获取下一层级level
            String nextLevel = LevelUtil.calculateLevel(level,
                    deptTreeVo.getParentId());
            //获取下一层级的信息
            List<DeptTreeVO> nextDeptTreeList = (List<DeptTreeVO>) multimap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(nextDeptTreeList)) {
                nextDeptTreeList = nextDeptTreeList
                        .stream()
                        .sorted(Comparator.comparing(DeptTreeVO::getSeq))
                        .collect(Collectors.toList());
                deptTreeVo.setDeptTreeBOList(nextDeptTreeList);
                transformDeptTree(nextDeptTreeList, nextLevel, multimap);
            }

        }
    }


    private SysDept checkDeptExistById(Long deptId) {
        SysDept dept = sysDeptMapper.selectById(deptId);
        if (Objects.isNull(dept)) {
            throw new BusinessException(DEPT_NOT_EXISTS);
        }
        return dept;
    }

    /**
     * 校验同一层级下面部门名称是否存在相同的
     *
     * @param parentId
     * @param name
     * @return
     */
    private void checkDeptNameExist(Long parentId, String name) {
        Long count = sysDeptMapper.countByNameAndParentId(parentId, name);
        if (count > 0) {
            throw new BusinessException(DEPT_NAME_EXISTS);
        }
    }

    /**
     * 根据父级部门的层级信息
     *
     * @param deptId 部门id
     * @return 层级信息
     */
    private String queryLevelById(Long deptId) {
        SysDept deptDO = sysDeptMapper.selectById(deptId);
        if (Objects.isNull(deptDO)) {
            return null;
        }
        return deptDO.getLevel();
    }
}
