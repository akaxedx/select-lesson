<script lang="ts" setup>

import {reactive} from "vue";
import {BuildBuildingReq, Service} from "../../../generated";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";

const token = localStorage.getItem("token") as string
const form = reactive({
  token: "",
  building: "",
  floor: 0,
  num: 0
} as BuildBuildingReq)
const router = useRouter()
const onSubmit = async () => {
  if (form.building === '') {
    ElMessage.error("请输入楼名")
  }
  if ( !Number.isInteger(Number(form.floor)) ) {
    ElMessage.error("请输入数字")
    return
  }
  if ( !Number.isInteger(Number(form.num)) ) {
    ElMessage.error("请输入数字")
    return
  }
  form.token = token
  const res = await Service.buildBuilding(form)
  if (res.code === 200) {
    ElMessage.success(res.msg)
  }
  if (res.code === 400) {
    ElMessage.error(res.msg)
  }
}
const push = ()=>{
  router.push('/manager/')
}
</script>

<template>
  <el-form :model="form" label-width="auto" style="max-width: 600px">
    <el-form-item label="楼名">
      <el-input v-model="form.building"/>
    </el-form-item>
    <el-form-item label="楼层数">
      <el-input v-model="form.floor"/>
    </el-form-item>
    <el-form-item label="每层教室数">
      <el-input v-model="form.num"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">Create</el-button>
      <el-button @click="push">Cancel</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped>

</style>