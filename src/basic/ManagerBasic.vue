<script setup lang="ts">
import {useRouter} from "vue-router";

const router = useRouter()
const username = localStorage.getItem('name')
const token = localStorage.getItem('token')
const login = () => {
  router.push('/user/login')
}
const building = () => {
  router.push('/manager/building')
}
const teacher = () => {
  router.push('/manager/set-teacher')
}
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header>你好, {{username}}
        <el-button type="primary" @click="building">建楼</el-button>
        <el-button type="primary" @click="teacher">升级/生成老师</el-button>
        <el-button type="primary" @click="login">切换登录</el-button>
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </div>
</template>

<style>

</style>