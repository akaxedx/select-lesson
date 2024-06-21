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
  <div class="centered-form">
    <h1 class="title">欢迎使用考研辅导选课系统</h1>
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
  </div>
</template>

<style scoped>
.centered-form {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 20px;
  box-sizing: border-box;
  font-family: 'Arial', sans-serif;
  flex-direction: column;
}

.title {
  color: #333; /* 深灰色标题 */
  margin-bottom: 30px; /* 标题与表单之间的间距 */
  text-align: center;
}

.el-form {
  background-color: white;
  padding: 40px;
  border-radius: 10px; /* 圆角边框 */
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1); /* 轻微的阴影效果 */
  max-width: 600px;
  width: 100%;
}

.el-form-item {
  margin-bottom: 20px; /* 表单项之间的间距 */
}

.el-form-item__label {
  color: #666; /* 标签文字颜色 */
  font-weight: 500; /* 中等字重 */
}

.el-form-item__content {
  display: flex;
  flex-direction: column;
}

.el-input__inner {
  border-radius: 4px; /* 输入框圆角 */
  border: 1px solid #dcdfe6; /* 输入框边框颜色 */
  padding: 10px; /* 输入框内边距 */
}

.el-input__inner:focus {
  border-color: #409eff; /* 输入框聚焦时的边框颜色 */
}

.el-button {
  padding: 10px 20px;
  margin-right: 10px; /* 按钮之间的间距 */
  border-radius: 4px; /* 按钮圆角 */
  font-weight: 500; /* 按钮文字字重 */
  transition: background-color 0.3s, border-color 0.3s; /* 按钮颜色变化过渡效果 */
}

.el-button--primary {
  background-color: #409eff;
  border-color: #409eff;
  color: white;
}

.el-button--default {
  background-color: #fff;
  color: #409eff;
  border-color: #dcdfe6;
}

.el-button:hover {
  filter: brightness(0.9); /* 鼠标悬浮时按钮变暗 */
}

/* 响应式设计 */
@media (max-width: 768px) {
  .centered-form {
    padding: 10px;
  }

  .el-form {
    padding: 20px;
  }
}
</style>