<template>
    <div class="container">
        <el-card class="header">
            <el-form :inline="true" :model="queryParams">
              <#list columns as column>
               <#if column.queryField>
                <#if column.htmlType == "input">
                <el-form-item label="${column.columnComment}" prop="${column.javaField}">
                  <el-input
                    v-model="queryParams.${column.javaField}"
                    placeholder="请输入${column.columnComment}"
                    clearable
                    class="!w-240px"
                  >
                  </el-input>
                </el-form-item>
                </#if>
                <#if column.htmlType == "select" || column.htmlType == "radio">
                <el-form-item label="${column.columnComment}" prop="${column.javaField}">
                  <el-select
                    v-model="queryParams.${column.javaField}"
                    placeholder="请选择${column.columnComment}"
                    clearable
                    class="!w-240px"
                  >
                    <el-option label="请选择字典生成" value="" />
                  </el-select>
                </el-form-item>
                </#if>
                <#if column.htmlType == "datetime">
                    <#if column.queryConditionField != "BETWEEN">
                <el-form-item label="${column.columnComment}" prop="${column.javaField}">
                  <el-date-picker
                    v-model="queryParams.${column.javaField}"
                    value-format="YYYY-MM-DD"
                    type="date"
                    placeholder="选择${column.columnComment}"
                    clearable
                    class="!w-240px"
                  />
                </el-form-item>
                    <#else>
                <el-form-item label="${column.columnComment}" prop="${column.javaField}">
                  <el-date-picker
                    v-model="queryParams.${column.javaField}"
                    value-format="YYYY-MM-DD HH:mm:ss"
                    type="daterange"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
                    class="!w-240px"
                  />
                </el-form-item>
                    </if>
                </#if>
               </#if>
               </#if>
              </#list>
                <el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="handleQuery">查询</el-button>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="success" @click="handleCreate">新增</el-button>
                  </el-form-item>
                </el-form-item>
            </el-form>
        </el-card>
        <el-card>
          <el-table :data="tableData" v-loading="loading" border style="width: 100%">
              <el-table-column label="#" type="index" />
              <#list columns as column>
                  <#if column.javaType == "LocalDateTime">
                 <el-table-column
                   label="${column.columnComment}"
                   align="center"
                   prop="${column.javaField}"
                   :formatter="dateFormatter"
                   width="180px"
                 />
                      <#else>
                <el-table-column label="${column.columnComment}" align="center" prop="${column.javaField}" />
                  </#if>
              </#list>
              <el-table-column label="操作" align="center">
                <el-button
                  type="info"
                  size="small"
                  @click="handleModify(row)"
                  v-permission="['${permissionPrefix}:role-menu']"
                >编辑角色</el-button>
                <el-button
                  link
                  type="danger"
                  @click="handleDelete(row)"
                  v-hasPermi="['${permissionPrefix}:delete']"
                >删除</el-button>
              </el-table-column>
          </el-table>
          <el-pagination
            class="pagination"
            v-model:current-page="queryParams.pageNo"
            v-model:page-size="queryParams.pageSize"
            :page-sizes="[10, 20]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          >
          </el-pagination>
        </el-card>
        <${table.className}Dialog
          v-model="${lowerCameClassName}FormVisible"
          :selectRow="selectRow"
          @success="getListData()"
        />
    </div>
</template>


<script setup>
import { ref, reactive } from 'vue'
import ${table.className}Dialog from '../${table.moduleName}/${table.className}From.vue'
import { page${table.className}List, delete${table.className} } from '@/api/${table.moduleName}/${smallClassName}'

/**
 * 列表内容
 */
const tableData = ref([])
/**
 * 总数
 */
const total = ref(0)

/**
 * 选中的数据
 */
const selectRow = ref({})
/**
 * 查询参数
 */
const queryParams = reactive({
    <#list columns as column>
        <#if column.queryField>
            <#if column.queryConditionField != 'BETWEEN'>
    ${column.javaField}:undefined,
            </#if>
            <#if column.htmlType == "datetime" || column.queryConditionField == "BETWEEN">
    ${column.javaField}:[],
            </#if>
        </#if>
    </#list>
    pageNo: 1,
    pageSize: 10
})

/**
 * 获取数据
 */
const getListData = async () => {
    const result = await page${table.className}List(queryParams)
    tableData.value = result.list
    total.value = result.total
}

/**
 * 编辑
 */
const ${lowerCameClassName}FormVisible = ref(false)
const handleModify = (row) => {
    ${lowerCameClassName}FormVisible.value = true
    selectRow.value = row
}

/**
 * 新增
 */
const handleCreate = () => {
    ${lowerCameClassName}FormVisible.value = true
    selectRow.value = {}
}


// 分页相关
/**
 * size 改变触发
 */
const handleSizeChange = (currentSize) => {
    size.value = currentSize
    getListData()
}

/**
 * 页码改变触发
 */
const handleCurrentChange = (currentPage) => {
    page.value = currentPage
    getListData()
}


/**
 * 删除
 */
const handleDelete = (row) => {
    ElMessageBox.confirm('您确定删除吗', {
        type: 'warning'
    }).then(async () => {
        await delete${table.className}(row.id)
        ElMessage.success('删除成功')
        // 重新渲染数据
        getListData()
    })
}


/**
 * 查询
 */
const handleQuery = () => {
    getListData()
}


/**
 * 初始化
 */
onMounted(() => {
    getListData()
})


</script>

<#assign cssStyles>
.container {
  .header {
    margin-bottom: 22px;
    text-align: left;
  }
  :deep(.el-tag) {
    margin-right: 6px;
  }
  .pagination {
    margin-top: 20px;
    text-align: center;
    justify-content: center;
  }
}
</#assign>
<style lang="scss" scoped>
${cssStyles}
</style>