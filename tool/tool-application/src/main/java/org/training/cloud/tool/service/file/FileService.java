package org.training.cloud.tool.service.file;


import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.tool.dto.file.AddFileDTO;
import org.training.cloud.tool.dto.file.FileDTO;
import org.training.cloud.tool.dto.file.ModifyFileDTO;
import org.training.cloud.tool.entity.file.File;

/**
 * 文件管理
 *
 * @author wangtongzhou
 * @since 2024-04-21 17:36
 */
public interface FileService {

    /**
     * 创建文件
     *
     * @param addFileDTO
     */
    void addFile(AddFileDTO addFileDTO);


    /**
     * 修改文件管理
     *
     * @param modifyFileDTO
     */
    void modifyFile(ModifyFileDTO modifyFileDTO);



    /**
     * 分页文件
     *
     * @param fileDTO
     */
    PageResponse<File> pageFile(FileDTO fileDTO);


    /**
     * 删除文件
     *
     * @param id
     */
    void delFile(Long id);




    /**
     * 查询单个文件
     *
     * @param id
     * @return
     */
    File getFileById(Long id);
}
