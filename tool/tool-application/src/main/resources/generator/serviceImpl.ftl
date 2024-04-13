package ${basePackage}.${table.moduleName}.service.${table.businessName};


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ${basePackage}.common.core.exception.BusinessException;
import ${PageResponseClassName};
import ${basePackage}.${table.moduleName}.convert.${table.businessName}.${table.className}Convert;
import ${basePackage}.${table.moduleName}.dao.${table.businessName}.${table.className}Mapper;
import ${basePackage}.${table.moduleName}.dto.${table.businessName}.Add${table.className}DTO;
import ${basePackage}.${table.moduleName}.dto.${table.businessName}.Modify${table.className}DTO;
import ${basePackage}.${table.moduleName}.dto.${table.businessName}.${table.className}DTO;
import ${basePackage}.${table.moduleName}.entity.${table.businessName}.${table.className};

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Objects;

import static ${basePackage}.${table.moduleName}.constant.${table.moduleName}ExceptionEnumConstants.*;


@Service
public class ${table.className}ServiceImpl implements ${table.className}Service {

     @Resource
     private ${table.className}Mapper ${firstLowerClassName}Mapper;

     @Override
     public void add${table.className}(Add${table.className}DTO add${table.className}DTO) {
       ${table.className} ${firstLowerClassName} = ${table.className}Convert.INSTANCE.convert(add${table.className}DTO);
       ${firstLowerClassName}Mapper.insert(${firstLowerClassName});
     }


     @Override
     public void modify${table.className}(Modify${table.className}DTOmodify${table.className}DTO) {
       checkExistById(modify${table.className}.getId());
       ${table.className} ${firstLowerClassName} = ${table.className}Convert.INSTANCE.convert(modify${table.className}DTO);
       ${firstLowerClassName}Mapper.updateById(${firstLowerClassName});
     }


    @Override
    public PageResponse<${table.className}> page${table.className}(${table.className}DTO ${firstLowerClassName}DTO) {
      return ${firstLowerClassName}Mapper.selectPage(${firstLowerClassName}DTO);
    }


    @Override
    public void del${table.className}Id(Long id) {
      checkExistById(id);
      ${firstLowerClassName}Mapper.deleteById(id);
    }


    @Override
    public ${table.className} query${table.className}(Long id) {
      return ${firstLowerClassName}Mapper.selectById(id);
    }
}