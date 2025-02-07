package org.training.cloud.tool.service.file;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.utils.collection.CollectionExtUtils;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.tool.convert.file.FileConvert;
import org.training.cloud.tool.dao.file.FileMapper;
import org.training.cloud.tool.dto.file.AddFileDTO;
import org.training.cloud.tool.dto.file.FileDTO;
import org.training.cloud.tool.dto.file.ModifyFileDTO;
import org.training.cloud.tool.entity.file.File;
import org.training.cloud.tool.entity.file.FileCategory;
import org.training.cloud.tool.vo.file.FileVO;
import org.training.cloud.tool.vo.generator.table.GeneratorTableVO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.training.cloud.tool.constant.FileExceptionEnumConstants.FILE_NOT_EXISTS;


/**
 * 文件管理
 *
 * @author wangtongzhou
 * @since 2024-04-21 17:37
 */

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private FileMapper fileMapper;

    @Resource
    private FileCategoryService fileCategoryService;

    @Override
    public void addFile(AddFileDTO addFileDTO) {
        File file = FileConvert.INSTANCE.convert(addFileDTO);
        fileMapper.insert(file);
    }

    @Override
    public void modifyFile(ModifyFileDTO modifyFileDTO) {
        checkExistById(modifyFileDTO.getId());
        File file = FileConvert.INSTANCE.convert(modifyFileDTO);
        fileMapper.updateById(file);
    }


    @Override
    public PageResponse<FileVO> pageFile(FileDTO fileDTO) {
        PageResponse<File> filePageResponse = fileMapper.selectPage(fileDTO);
        PageResponse<FileVO> result = FileConvert.INSTANCE.convert(filePageResponse);
        if (CollectionUtils.isNotEmpty(result.getList())) {
            Set<Long> categoryIds = CollectionExtUtils.convertSet(result.getList(),
                    FileVO::getCategoryId);
            List<FileCategory> fileCategories =
                    fileCategoryService.getFileCategoryByIds(categoryIds);
            Map<Long, String> fileCategoryMap = fileCategories.stream()
                    .collect(Collectors.toMap(FileCategory::getId, FileCategory::getName));
            result.getList().forEach(x -> {
                String fileCategoryName = fileCategoryMap.get(x.getCategoryId());
                if (StringUtils.isNotBlank(fileCategoryName)){
                    x.setCategoryName(fileCategoryName);
                }
            });
        }
        return result;
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
