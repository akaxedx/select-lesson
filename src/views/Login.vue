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
  <div class="centered-form">
    <h1 class="title">欢迎使用考研辅导选课系统</h1>
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
    <div class="forgot-password">
      忘记密码请联系管理员
    </div>
  </div>
</template>

<style scoped>
.centered-form {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  margin: 0;
  padding: 20px;
  box-sizing: border-box;
  background-color: #f5f5f5;
  font-family: 'Arial', sans-serif;
  flex-direction: column;
}

.title {
  text-align: center;
  color: #333; /* 深灰色标题 */
  margin-bottom: 40px; /* 标题与表单之间的间距 */
}

.el-form {
  max-width: 600px;
  width: 100%;
  padding: 40px;
  background: white;
  border-radius: 10px; /* 圆角边框 */
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1); /* 轻微的阴影效果 */
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

.forgot-password {
  color: #409eff;
  font-size: 1em;
  text-align: center;
  margin-top: 15px;
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