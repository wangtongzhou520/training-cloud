import request from '@/utils/request'


/**
 * 分页
 */
export const page${table.className}List = (data) => {
  return request({
    url: '/${table.moduleName}/${symbolCaseClassName}/page',
    method: 'POST',
    data
  })
}

/**
 * 新增
 */
export const add${table.className} = (data) => {
  return request({
    url: '/${table.moduleName}/${symbolCaseClassName}/add',
    method: 'POST',
    data
  })
}

/**
 * 修改
 */
export const modify${table.className} = (data) => {
  return request({
    url: '/${table.moduleName}/${symbolCaseClassName}/modify',
    method: 'PUT',
    data
  })
}

/**
 * 删除角色
 */
export const delete${table.className} = (id) => {
  return request({
    url: `/${table.moduleName}/${symbolCaseClassName}/delete/id`,
    method: 'DELETE'
  })
}