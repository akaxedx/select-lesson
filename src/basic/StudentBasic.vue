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

</style>