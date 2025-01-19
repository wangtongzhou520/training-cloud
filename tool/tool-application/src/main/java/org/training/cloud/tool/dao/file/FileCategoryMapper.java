package org.training.cloud.tool.dao.file;


import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.tool.dto.file.FileCategoryDTO;
import org.training.cloud.tool.entity.file.FileCategory;

import java.util.List;


@Mapper
public interface FileCategoryMapper extends BaseMapperExtend<FileCategory> {

    default PageResponse<FileCategory> selectPage(FileCategoryDTO fileCategoryDTO) {
        return selectPage(fileCategoryDTO, new LambdaQueryWrapperExtend<FileCategory>()
                        .likeIfPresent(FileCategory::getName,
                                fileCategoryDTO.getName())
//                .eqIfPresent(FileCategory::getParentId, fileCategoryDTO.getParentId())
//                .eqIfPresent(FileCategory::getLevel, fileCategoryDTO.getLevel())
//                .eqIfPresent(FileCategory::getSeq, fileCategoryDTO.getSeq())
//                .eqIfPresent(FileCategory::getRemark, fileCategoryDTO.getRemark())
//                .eqIfPresent(FileCategory::getCreateOperator, fileCategoryDTO.getCreateOperator())
//                .eqIfPresent(FileCategory::getModifiedOperator, fileCategoryDTO.getModifiedOperator())
//                .eqIfPresent(FileCategory::getGmtCreate, fileCategoryDTO.getGmtCreate())
//                .eqIfPresent(FileCategory::getGmtModified, fileCategoryDTO.getGmtModified())
//                .eqIfPresent(FileCategory::getDeleteState, fileCategoryDTO.getDeleteState())
                        .orderByDesc(FileCategory::getId)
        );
    }


    /**
     * 根据父节点查询个数
     *
     * @param id
     * @return
     */
    default Long countByParentId(Long id) {
        return selectCount(new LambdaQueryWrapperExtend<FileCategory>()
                .eq(FileCategory::getParentId, id)
                .eq(FileCategory::getDeleteState, false)
        );
    }


    /**
     * 查询同一层级下有没有相同分类
     *
     * @param parentId
     * @param name
     * @return
     */
    default Long countByNameAndParentId(Long parentId, String name) {
        return selectCount(new LambdaQueryWrapperExtend<FileCategory>()
                .eq(FileCategory::getParentId, parentId)
                .eq(FileCategory::getName, name)
                .eq(FileCategory::getDeleteState, false));
    }


    /**
     * 根据层级查询子部门
     *
     * @param level
     * @return
     */
    default List<FileCategory> selectByChildDeptByLevel(String level) {
        return selectList(new LambdaQueryWrapperExtend<FileCategory>()
                .likeIfPresent(FileCategory::getLevel, level)
                .eq(FileCategory::getDeleteState, false)
        );
    }


    /**
     * 查询分类树
     *
     * @return
     */
    default List<FileCategory> selectFileCategoryList(FileCategoryDTO fileCategoryDTO) {
        return selectList(new LambdaQueryWrapperExtend<FileCategory>()
                .likeIfPresent(FileCategory::getName,
                        fileCategoryDTO.getName())
                .eqIfPresent(FileCategory::getId, fileCategoryDTO.getId())
                .eqIfPresent(FileCategory::getDeleteState, false)
        );
    }

}