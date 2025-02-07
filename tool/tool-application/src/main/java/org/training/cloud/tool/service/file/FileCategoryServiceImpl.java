package org.training.cloud.tool.service.file;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.utils.LevelUtil;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.tool.convert.file.FileCategoryConvert;
import org.training.cloud.tool.dao.file.FileCategoryMapper;
import org.training.cloud.tool.dto.file.AddFileCategoryDTO;
import org.training.cloud.tool.dto.file.FileCategoryDTO;
import org.training.cloud.tool.dto.file.ModifyFileCategoryDTO;
import org.training.cloud.tool.entity.file.FileCategory;
import org.training.cloud.tool.vo.file.FileCategoryVO;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.training.cloud.tool.constant.FileExceptionEnumConstants.FILE_CATEGORY_EXISTS;
import static org.training.cloud.tool.constant.FileExceptionEnumConstants.FILE_CATEGORY_NOT_EXISTS;


@Service
public class FileCategoryServiceImpl implements FileCategoryService {

    @Resource
    private FileCategoryMapper fileCategoryMapper;

    @Override
    public void addFileCategory(AddFileCategoryDTO addFileCategoryDTO) {
        //判断同一层级下面是否包含相同的分类信息
        checkDeptNameExist(addFileCategoryDTO.getParentId(),
                addFileCategoryDTO.getName());
        //参数转换
        FileCategory fileCategory = FileCategoryConvert.INSTANCE.convert(addFileCategoryDTO);
        String level = LevelUtil.calculateLevel(
                queryLevelById(addFileCategoryDTO.getParentId()),
                addFileCategoryDTO.getParentId());
        fileCategory.setLevel(level);
        fileCategoryMapper.insert(fileCategory);
    }


    @Override
    public void modifyFileCategory(ModifyFileCategoryDTO modifyFileCategoryDTO) {
        //检查传入的部门id是否存在
        FileCategory before = checkFileCategoryExistById(modifyFileCategoryDTO.getId());
//        //检查同一层级下面是否存在相同的部门
//        checkDeptNameExist(modifyDeptDTO.getParentId(), modifyDeptDTO.getName());
        //组合do
        FileCategory after = FileCategoryConvert.INSTANCE.convert(modifyFileCategoryDTO);
        String level = LevelUtil.calculateLevel(
                queryLevelById(modifyFileCategoryDTO.getParentId()),
                modifyFileCategoryDTO.getParentId());
        after.setLevel(level);
        //更新部门信息
        updateWithChild(before, after);
    }


    private void updateWithChild(FileCategory before, FileCategory after) {
        //判断下级信息是否需要更新
        String beforeLevel = before.getLevel();
        String afterLevel = after.getLevel();
        if (!beforeLevel.equals(afterLevel)) {
            //查询部门登记查询下级信息
            List<FileCategory> sysDeptList =
                    fileCategoryMapper.selectByChildDeptByLevel(beforeLevel);
            if (CollectionUtils.isNotEmpty(sysDeptList)) {
                sysDeptList.stream().forEach(sysDept -> {
                    String level = sysDept.getLevel();
                    if (level.indexOf(beforeLevel) == 0) {
                        level = afterLevel + level.substring(beforeLevel.length());
                        sysDept.setLevel(level);
                    }
                });
                fileCategoryMapper.insertBatch(sysDeptList);
            }
        }
        //更新自己
        fileCategoryMapper.updateById(after);
    }


    private FileCategory checkFileCategoryExistById(Long deptId) {
        FileCategory fileCategory = fileCategoryMapper.selectById(deptId);
        if (Objects.isNull(fileCategory)) {
            throw new BusinessException(FILE_CATEGORY_NOT_EXISTS);
        }
        return fileCategory;
    }


    private void checkDeptNameExist(Long parentId, String name) {
        Long count = fileCategoryMapper.countByNameAndParentId(parentId, name);
        if (count > 0) {
            throw new BusinessException(FILE_CATEGORY_EXISTS);
        }
    }


    private String queryLevelById(Long fileCategoryId) {
        FileCategory fileCategory = fileCategoryMapper.selectById(fileCategoryId);
        if (Objects.isNull(fileCategory)) {
            return null;
        }
        return fileCategory.getLevel();
    }


    @Override
    public PageResponse<FileCategory> pageFileCategory(FileCategoryDTO fileCategoryDTO) {
        return fileCategoryMapper.selectPage(fileCategoryDTO);
    }


    @Override
    public void delFileCategory(Long id) {
        checkExistById(id);
        //判断是否存在子节点
        if (fileCategoryMapper.countByParentId(id) > 0) {
            throw new BusinessException("当前部门下面还存在子部门");
        }
        //删除
        fileCategoryMapper.deleteById(id);
    }


    @Override
    public FileCategory getFileCategoryById(Long id) {
        FileCategory fileCategory = fileCategoryMapper.selectById(id);
        if (Objects.isNull(fileCategory)) {
            throw new BusinessException(FILE_CATEGORY_NOT_EXISTS);
        }
        return fileCategory;
    }

    @Override
    public List<FileCategory> getFileCategoryByIds(Collection<Long> ids) {
        return fileCategoryMapper.selectBatchIds(ids);
    }

    @Override
    public List<FileCategoryVO> getAllFileCategory(FileCategoryDTO fileCategoryDTO) {
        List<FileCategory> sysDeptList = fileCategoryMapper.selectFileCategoryList(fileCategoryDTO);
        if (CollectionUtils.isEmpty(sysDeptList)) {
            return null;
        }
        return FileCategoryConvert.INSTANCE.convert(sysDeptList);
    }


    private void checkExistById(Long id) {
        FileCategory fileCategory = fileCategoryMapper.selectById(id);
        if (Objects.isNull(fileCategory)) {
            throw new BusinessException(FILE_CATEGORY_NOT_EXISTS);
        }
    }
}