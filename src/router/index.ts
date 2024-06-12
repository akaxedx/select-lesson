import {createRouter, createWebHistory, onBeforeRouteLeave, RouteRecordRaw, useRouter} from 'vue-router'
import {routes} from "@/router/routes";
import {Service} from "../../generated";

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})
export default router
