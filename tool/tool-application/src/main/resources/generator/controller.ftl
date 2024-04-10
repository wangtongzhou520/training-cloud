package ${basePackage}.${table.moduleName}.controller.admin.${table.businessName}



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.utils.collection.CollectionExtUtils;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;

import org.training.cloud.system.convert.user.UserConvert;

import ${basePackage}.${table.moduleName}.dto.${table.businessName}.*;


import org.training.cloud.system.service.dept.DeptService;
import org.training.cloud.system.service.user.UserService;
import org.training.cloud.system.vo.user.UserVO;

import javax.validation.Valid;
import java.util.List;
