<template>
  <div id="app">
      <router-view />
  </div>
</template>
<script lang="ts" setup>
import {useRoute, useRouter} from "vue-router";
import {ConfirmReq, Service} from "../generated";
import {onMounted, reactive} from "vue";

const route = useRoute()
const router = useRouter()
const confirmReq = reactive({
  token: ""
} as ConfirmReq)
const  doInit = async () => {
  if (!route.path.startsWith('/user/')) {
    const item = localStorage.getItem("token");
    if (item == null) {
      await router.push("/user/login");
      return
    }
    confirmReq.token=item
    const res = await Service.confirm(confirmReq);
    if (res.code == null){
      await router.push("/user/login");
      return
    }
  }
  if (route.path === '/') {
    await router.push("/user/login");
  }
};
onMounted(() => {
  doInit();
})


</script>
<style>
html,
body,
#app {
  width: 100%;
  height: 100%;
}
</style>
