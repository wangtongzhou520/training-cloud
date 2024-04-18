<template>
  <el-dialog ref="formRef" :model-value="modelValue" :title="title" @close="closed" @open="open">
    <el-form :model="formData" label-width="80px">
      <#list columns as column>
          <#if column.addField || column.modifyField>
             <#if column.htmlType == "input" && !column.primaryKey>
      <el-form-item label="${column.columnComment}" prop="${column.javaField}">
        <el-input v-model="formData.${column.javaField}" placeholder="请输入${column.columnComment}" />
      </el-form-item>
             </#if>
             <#if column.htmlType == "select">
      <el-form-item label="${column.columnComment}" prop="${column.javaField}">
        <el-select v-model="formData.${column.javaField}" placeholder="请选择${column.columnComment}">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
             </#if>
             <#if column.htmlType == "checkbox">
      <el-form-item label="${column.columnComment}" prop="${column.javaField}">
        <el-checkbox-group v-model="formData.${column.javaField}">
          <el-checkbox>请选择字典生成</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
             </#if>
             <#if column.htmlType == "radio">
      <el-form-item label="${column.columnComment}" prop="${column.javaField}">
        <el-checkbox-group v-model="formData.${column.javaField}">
          <el-radio label="1">请选择字典生成</el-radio>
        </el-checkbox-group>
      </el-form-item>
             </#if>
             <#if column.htmlType == "datetime">
      <el-form-item label="${column.columnComment}" prop="${column.javaField}">
        <el-date-picker
          v-model="formData.${column.javaField}"
          type="date"
          value-format="x"
          placeholder="选择${column.columnComment}"
        />
      </el-form-item>
             </#if>
             <#if column.htmlType == "textarea">
      <el-form-item label="${column.columnComment}" prop="${column.javaField}">
        <el-input v-model="formData.${column.javaField}" type="textarea" placeholder="请输入${column.columnComment}" />
      </el-form-item>
             </#if>
          </#if>
      </#list>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="closed">关闭</el-button>
        <el-button type="primary" @click="onConfirm">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup>
import { defineProps, defineEmits } from 'vue'
import { add${table.className}, modify${table.className} } from '@/api/${smallClassName}/${smallClassName}'
import { ElMessage } from 'element-plus'

/**
 * 标题
 */
const title = ref('新增${table.tableComment}')

/**
 * 表单内容
 */
const formData = reactive({
  <#list columns as column>
  <#if column.addField || column.modifyField>
  ${column.javaField}:undefined,
  </#if>
  </#list>
})
/**
 * 父传子的参数
 */
const props = defineProps({
  modelValue: {
    type: Boolean,
    required: true
  },
  selectRow: {
    type: Object,
    required: true
  }
})

const emits = defineEmits(['update:modelValue', 'success'])

/**
 * 打开回调
 */
const open = async () => {
  //初始化表单
  <#list columns as column>
  <#if column.addField || column.modifyField>
  formData.${column.javaField}=props.selectRow.${column.javaField}
  </#if>
  </#list>
  title.value = formData.id !== undefined ? '编辑${table.tableComment}' : '新增${table.tableComment}'
}

/**
 * 关闭
 */
const closed = () => {
  emits('update:modelValue', false)
}

/**
 * 确定
 */
const onConfirm = async () => {
  if (formData.id !== undefined) {
    await modify${table.className}(formData)
    ElMessage.success('新增成功')
  } else {
    await add${table.className}(formData)
    ElMessage.success('编辑成功')
  }
  emits('update:modelValue', false)
  emits('success')
}


</script>

<style lang="scss" scoped></style>
