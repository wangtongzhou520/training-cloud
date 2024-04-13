package ${basePackage}.${table.moduleName}.service.${table.businessName};


import ${PageResponseClassName};
import ${basePackage}.${table.moduleName}.dto.${table.businessName}.Add${table.className}DTO;
import ${basePackage}.${table.moduleName}.dto.${table.businessName}.Modify${table.className}DTO;
import ${basePackage}.${table.moduleName}.dto.${table.businessName}.${table.className}DTO;
import ${basePackage}.${table.moduleName}.entity.${table.businessName}.${table.className};


/**
* ${table.classComment} Service 接口
*
* @author
*/
public interface ${table.className}Service {



   /**
    * 创建${table.classComment}
    *
    * @param add${table.className}DTO
    */
   void add${table.className}(Add${table.className}DTO add${table.className}DTO);



   /**
    * 修改${table.classComment}
    *
    * @param modify${table.className}DTO
    */
   void modify${table.className}(Modify${table.className}DTO modify${table.className}DTO);


   /**
    * 分页${table.classComment}
    *
    * @param ${firstLowerClassName}DTO
    */
   PageResponse<${table.className}> page${table.className}(${table.className}DTO ${firstLowerClassName}DTO);


   /**
    * 删除${table.classComment}
    *
    * @param id
    */
   void del${table.className}(Long id);




   /**
    * 查询单个${table.classComment}
    *
    * @param id
    * @return
    */
   ${table.className} get${table.className}ById(Long id);


}