package org.training.cloud.tool.service.file;


import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.tool.convert.file.FileConvert;
import org.training.cloud.tool.dao.file.FileMapper;
import org.training.cloud.tool.dto.file.AddFileDTO;
import org.training.cloud.tool.dto.file.FileDTO;
import org.training.cloud.tool.entity.file.File;

import javax.annotation.Resource;
import java.util.Objects;

import static org.training.cloud.tool.constant.FileExceptionEnumConstants.FILE_NOT_EXISTS;



/**
 * 文件管理
 *
 * @author wangtongzhou
 * @since 2024-04-21 17:37
 */

@Service
public class FileServiceImpl implements FileService{
    @Resource
    private FileMapper fileMapper;

    @Override
    public void addFile(AddFileDTO addFileDTO) {
        File file = FileConvert.INSTANCE.convert(addFileDTO);
        fileMapper.insert(file);
    }




    @Override
    public PageResponse<File> pageFile(FileDTO fileDTO) {
        return fileMapper.selectPage(fileDTO);
    }


    @Override
    public void delFile(Long id) {
        checkExistById(id);
        fileMapper.deleteById(id);
    }


    @Override
    public File getFileById(Long id) {
        return fileMapper.selectById(id);
    }



    private void checkExistById(Long id) {
        File file = fileMapper.selectById(id);
        if (Objects.isNull(file)) {
            throw new BusinessException(FILE_NOT_EXISTS);
        }
    }

}
