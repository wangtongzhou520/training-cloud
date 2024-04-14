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
                    <#if column.listOperationCondition != "BETWEEN">
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

          </el-table>
        </el-card>
    </div>
</template>


<script setup>

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