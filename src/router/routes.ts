import {RouteRecordRaw} from "vue-router";
import UserBasic from "@/basic/UserBasic.vue";
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
import StudentBasic from "@/basic/StudentBasic.vue";
import StudentView from "@/views/student/StudentView.vue";
import SelectLessonView from "@/views/student/SelectLessonView.vue";
import TeacherBasic from "@/basic/TeacherBasic.vue";
import SelectTeacherView from "@/views/student/SelectTeacherView.vue";
import ManagerBasic from "@/basic/ManagerBasic.vue";
import * as path from "path";
import buildingView from "@/views/manager/buildingView.vue";
import SetTeacherView from "@/views/manager/SetTeacherView.vue";

export const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'login',
        redirect: '/user/login'
    },
    {
        path: '/student',
        name: 'student_center',
        component: StudentBasic,
        children: [
            {
                path: '/student/teacherList',
                name: 'teacherSelect',
                component: SelectTeacherView
            },
            {
                path: '/student/lessonList',
                name: 'my_center',
                component: StudentView
            },
            {
                path: '/student/select',
                name: 'selectLesson',
                component: SelectLessonView
            }]
    },
    {
        path: '/teacher',
        name: '/teacher_center',
        component: TeacherBasic,
        children: [],
    },
    {
        path: '/manager',
        name: '/manager_center',
        component: ManagerBasic,
        children: [
            {
                path: '/manager/building',
                name: 'building',
                component: buildingView
            },
            {
                path: '/manager/set-teacher',
                name: 'set-mteacher',
                component: SetTeacherView
            }
        ],
    },
    {
        path: '/user',
        name: 'user',
        component: UserBasic,
        children: [{
            path: '/user/login',
            name: 'login',
            component: Login
        }, {
            path: '/user/register',
            name: 'register',
            component: Register
        }]
    }
]