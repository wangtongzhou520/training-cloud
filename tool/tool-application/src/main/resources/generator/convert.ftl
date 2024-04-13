package ${basePackage}.${table.moduleName}.convert.${table.businessName};


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ${PageResponseClassName};
import ${basePackage}.${table.moduleName}.dto.${table.businessName}.Add${table.className}DTO;
import ${basePackage}.${table.moduleName}.dto.${table.businessName}.Modify${table.className}DTO;
import ${basePackage}.${table.moduleName}.entity.${table.businessName}.${table.className};
import ${basePackage}.${table.moduleName}.vo.${table.businessName}.${table.className}tVO;


@Mapper
public interface ${table.className}Convert {


    ${table.className}Convert INSTANCE = Mappers.getMapper(${table.className}Convert.class);


    ${table.className} convert(Add${table.className}DTO add${table.className}DTO);

    PageResponse<${table.className}VO> convert(PageResponse<${table
    .className}> ${firstLowerClassName}PageResponse);


    ${table.className} convert(Modify${table.className}DTO modify${table.className}DTO);


    ${table.className}VO convert(${table.className} ${firstLowerClassName});





}