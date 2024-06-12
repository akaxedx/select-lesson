<script lang="ts" setup>
import {reactive} from 'vue'
import {LoginReq, Service} from "../../generated";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";

const loginForm = reactive({
  username: '',
  password: '',
  authority: 'STUDENT'
} as LoginReq)
const router = useRouter();
const onSubmit = async () =>{
  const res = await Service.login(loginForm)
  if (res.code === 200) {
    ElMessage({
      message: '登录成功',
      type: 'success',
    })
    localStorage.setItem("token", res.data?.token as string)
    localStorage.setItem("name", res.data?.username as string)
    console.log(res.data?.username)
    if (loginForm.authority == 'STUDENT') {
      await router.push('/student/');
    }
    if (loginForm.authority == 'TEACHER') {
      await router.push('/teacher/');
    }
    if (loginForm.authority == 'MANAGER') {
      await router.push('/manager/');
    }
  } else {
    ElMessage.error(res.msg);
  }
}

const register = () =>{
  router.push("/user/register");
}

</script>

<template>
  <el-form :model="loginForm" label-width="auto" style="max-width: 600px">
    <el-form-item label="用户名" >
      <el-input v-model="loginForm.username" placeholder="用户名"/>
    </el-form-item>
    <el-form-item label="密码">
      <el-input type="password" v-model="loginForm.password" placeholder="密码"/>
    </el-form-item>
    <el-form-item label="身份">
      <el-select v-model="loginForm.authority" placeholder="选择身份">
        <el-option label="学生" value="STUDENT"/>
        <el-option label="老师" value="TEACHER"/>
        <el-option label="管理员" value="MANAGER"/>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">登录</el-button>
      <el-button @click="register">注册</el-button>
    </el-form-item>
  </el-form>

</template>

<style scoped>

</style>