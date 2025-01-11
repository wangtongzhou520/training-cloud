package org.training.cloud.tool.service.file;


import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.dto.dept.DeptDTO;
import org.training.cloud.system.vo.dept.DeptVO;
import org.training.cloud.tool.dto.file.AddFileCategoryDTO;
import org.training.cloud.tool.dto.file.FileCategoryDTO;
import org.training.cloud.tool.dto.file.ModifyFileCategoryDTO;
import org.training.cloud.tool.entity.file.FileCategory;
import org.training.cloud.tool.vo.file.FileCategoryVO;

import java.util.List;


/**
 * 文件分类 Service 接口
 *
 * @author
 */
public interface FileCategoryService {



    /**
     * 创建文件分类
     *
     * @param addFileCategoryDTO
     */
    void addFileCategory(AddFileCategoryDTO addFileCategoryDTO);



    /**
     * 修改文件分类
     *
     * @param modifyFileCategoryDTO
     */
    void modifyFileCategory(ModifyFileCategoryDTO modifyFileCategoryDTO);


    /**
     * 分页文件分类
     *
     * @param fileCategoryDTO
     */
    PageResponse<FileCategory> pageFileCategory(FileCategoryDTO fileCategoryDTO);


    /**
     * 删除文件分类
     *
     * @param id
     */
    void delFileCategory(Long id);




    /**
     * 查询单个文件分类
     *
     * @param id
     * @return
     */
    FileCategory getFileCategoryById(Long id);




    /**
     * 分类列表
     *
     * @return
     */
    List<FileCategoryVO> getAllFileCategory(FileCategoryDTO fileCategoryDTO);


}