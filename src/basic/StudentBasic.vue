<script lang="ts" setup>
import {useRoute, useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import {ConfirmReq, GetLessonReq, Service} from "../../generated";

const router = useRouter()
const username = localStorage.getItem('name')
const token = localStorage.getItem('token')
const lessonSearch = () => {
  router.push('/student/lessonList')
}
const selectLesson = () => {
  router.push('/student/select')
}
const isStudent = ref(true)
const login = () => {
  router.push('/user/login')
}
const selectTeacher = () => {
  router.push('/student/teacherList')
}
const confirmReq = reactive({
  token: ""
} as ConfirmReq)
onMounted(async () => {
  confirmReq.token = token as string
  const res = await Service.confirm(confirmReq)
  if (res.data == false) {
    isStudent.value = false
  }
})
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header>你好, {{username}}
        <el-button type="primary" @click="lessonSearch" v-if="isStudent">查看课程表</el-button>
        <el-button type="primary" @click="selectLesson" v-if="isStudent">选课</el-button>
        <el-button type="primary" @click="selectTeacher">选导师</el-button>
        <el-button type="primary" @click="login">切换登录</el-button>
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </div>

</template>

<style scoped>
/* 定义颜色主题 */
:root {
  --header-background-color: #007bff; /* 蓝色背景 */
  --header-text-color: #ffffff; /* 白色文字 */
  --button-active-background: #0056b3; /* 按钮点击时的背景色 */
}

/* 通用样式重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.common-layout {
  font-family: 'Arial', sans-serif; /* 设置默认字体 */
}

/* 头部样式 */
.el-header {
  height: 60px; /* 头部高度 */
  line-height: 60px; /* 使文本垂直居中 */
  background-color: var(--header-background-color);
  color: var(--header-text-color);
  padding: 0 20px; /* 头部内边距 */
  display: flex;
  align-items: center;
  justify-content: space-between;
}

/* 用户名样式 */
.el-header > div:first-child {
  display: flex;
  align-items: center;
}

/* 导航栏中的按钮样式 */
.el-button {
  margin-left: 8px; /* 按钮之间的间距 */
  padding: 8px 20px; /* 按钮内边距 */
  border-radius: 5px; /* 按钮圆角 */
  background-color: var(--header-background-color);
  border-color: var(--header-background-color);
  color: var(--header-text-color);
  transition: background-color 0.3s;
}

.el-button:hover,
.el-button:focus {
  background-color: var(--button-active-background);
}

/* 主体内容样式 */
.el-main {
  padding: 20px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .el-header {
    flex-wrap: wrap;
    padding: 0 10px;
  }

  .el-button {
    margin: 5px;
    padding: 8px 15px;
  }
}
</style>