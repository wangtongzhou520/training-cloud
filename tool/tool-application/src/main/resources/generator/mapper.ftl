package ${basePackage}.${table.moduleName}.dao.${table.businessName};


import org.apache.ibatis.annotations.Mapper;
import ${PageResponseClassName};
import ${LambdaQueryWrapperExtend};
import ${BaseMapperExtend};
import ${basePackage}.${table.moduleName}.dto.${table.businessName}.${table.className}DTO;
import ${basePackage}.${table.moduleName}.entity.${table.businessName}.${table.className};


<#macro listCondition>
<#list columns as column>
    <#if column.queryField>
        <#assign JavaField = column.javaField?cap_first>
        <#if column.queryConditionField == "=">
            .eqIfPresent(${table.className}DO::get${JavaField}, reqVO.get${JavaField}())
        </#if>
        <#if column.queryConditionField == "!=">
            .neIfPresent(${table.className}DO::get${JavaField}, reqVO.get${JavaField}())
        </#if>
        <#if column.queryConditionField == ">">
            .gtIfPresent(${table.className}DO::get${JavaField}, reqVO.get${JavaField}())
        </#if>
        <#if column.queryConditionField == ">=">
            .geIfPresent(${table.className}DO::get${JavaField}, reqVO.get${JavaField}())
        </#if>
        <#if column.queryConditionField == "<">
            .ltIfPresent(${table.className}DO::get${JavaField}, reqVO.get${JavaField}())
        </#if>
        <#if column.queryConditionField == "<=">
            .leIfPresent(${table.className}DO::get${JavaField}, reqVO.get${JavaField}())
        </#if>
        <#if column.queryConditionField == "LIKE">
            .likeIfPresent(${table.className}DO::get${JavaField}, reqVO.get${JavaField}())
        </#if>
        <#if column.queryConditionField == "BETWEEN">
        .betweenIfPresent(${table.className}DO::get${JavaField}, reqVO.get${JavaField}())
        </#if>
    </#if>
</#list>
</#macro>


@Mapper
public interface ${table.className}Mapper extends BaseMapperExtend<${table.className}> {

    default PageResponse<${table.className}> selectPage(${table.className}DTO
   ${firstLowerClassName}DTO) {
       return selectPage(${firstLowerClassName}DTO, new
          LambdaQueryWrapperExtend<${table.className}>().<@listCondition/>
          .orderByDesc(${table.className}::getId)
       );
    }

}