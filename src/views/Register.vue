<script lang="ts" setup>
import {reactive} from 'vue'
import {LoginReq, RegisterReq, Service} from "../../generated";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";

const registerForm = reactive({
  username: '',
  password: '',
  confirmPassword: ''
} as RegisterReq)
const loginForm = reactive({
  username: '',
  password: '',
  authority: ''
} as LoginReq)
const router = useRouter();
const onSubmit = async () =>{
  if (registerForm.username && (registerForm.username.length < 5 || registerForm.username.length > 15)) {
    ElMessage({
      type: 'info',
      message: "用户名长度在5-15之间"
    })
    return
  }
  if (registerForm.password && (registerForm.password.length < 5 || registerForm.password.length > 15)) {
    ElMessage({
      type: 'info',
      message: "密码长度在5-15之间"
    })
    return
  }
  if (registerForm.confirmPassword != registerForm.password) {
    ElMessage({
      type: 'info',
      message: "两次密码不相等"
    })
    return
  }
  const res = await Service.register(registerForm)
  if (res.code === 200) {
    ElMessage({
      message: '注册成功',
      type: 'success',
    })
    loginForm.username = registerForm.username
    loginForm.password = registerForm.password
    loginForm.authority = 'STUDENT'
    const loginRes = await Service.login(loginForm)
    localStorage.setItem("token", loginRes.data?.token as string)
    localStorage.setItem("name", loginRes.data?.username as string)
    await router.push('/student');
  } else {
    ElMessage.error(res.msg);
  }
}

const login = () => {
  router.push("/user/login");
}

</script>

<template>
  <el-form :model="registerForm" label-width="auto" style="max-width: 600px">
    <el-form-item label="用户名" >
      <el-input v-model="registerForm.username" placeholder="用户名"/>
    </el-form-item>
    <el-form-item label="密码">
      <el-input type="password" v-model="registerForm.password" placeholder="密码"/>
    </el-form-item>
    <el-form-item label="确认密码">
      <el-input type="password" v-model="registerForm.confirmPassword" placeholder="确认密码"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">注册</el-button>
      <el-button @click="login">返回</el-button>
    </el-form-item>
  </el-form>

</template>

<style scoped>

</style>