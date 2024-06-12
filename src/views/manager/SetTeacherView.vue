<script setup lang="ts">
import {onMounted, reactive} from "vue";
import {EditTeacherReq, GetAllTeachersReq, Service} from "../../../generated";
import {ElMessage} from "element-plus";

const tableData = reactive([
  {
    username:'',
    id:0,
    authority:'',
    teacherIdentity:''
  },{
    username:'',
    id:0,
    authority:'',
    teacherIdentity:''
  },{
    username:'',
    id:0,
    authority:'',
    teacherIdentity:''
  },{
    username:'',
    id:0,
    authority:'',
    teacherIdentity:''
  },{
    username:'',
    id:0,
    authority:'',
    teacherIdentity:''
  },{
    username:'',
    id:0,
    authority:'',
    teacherIdentity:''
  },{
    username:'',
    id:0,
    authority:'',
    teacherIdentity:''
  },{
    username:'',
    id:0,
    authority:'',
    teacherIdentity:''
  },{
    username:'',
    id:0,
    authority:'',
    teacherIdentity:''
  },{
    username:'',
    id:0,
    authority:'',
    teacherIdentity:''
  }])
const token = localStorage.getItem("token")
const getAllUsers = reactive({
  token:''
}as GetAllTeachersReq)
const editTeacherReq = reactive({
  token:'',
  identity:''
}as EditTeacherReq)
const getAllTeacher = async () => {
  getAllUsers.token = token as string
  getAllUsers.pageNum = 1
  const res = await Service.getAllUsers(getAllUsers)
  let i = 0;
  res.data?.users?.forEach(user => {
    if (user.teacherIdentity != null) {
      tableData[i].teacherIdentity = user.teacherIdentity
    }
    tableData[i].id = user.user?.id as number
    tableData[i].authority = user.user?.authority as string
    tableData[i].username = user.user?.username as string
    console.log(tableData[i])
    console.log(user)
    console.log("-------------------------")
    i++
  })
}
onMounted(()=>{
  getAllTeacher()
})
const handleClick1 = async (row: any) => {
  editTeacherReq.identity = 'common_teacher'
  editTeacherReq.token=token as string
  editTeacherReq.userId=row.id
  const res = await Service.editTeacher(editTeacherReq)
  if (res.code===200) {
    ElMessage.success("升级成功");
    row.authority = "TEACHER"
    row.teacherIdentity = res.data?.identity
  }
  if (res.code===400) {
    ElMessage.error(res.msg)
  }
}
</script>

<template>
  <el-table :data="tableData.filter(item=>item.id!==0)" style="width: 100%">
    <el-table-column prop="username" label="用户名" width="150" />
    <el-table-column label="用户id" prop="id" width="120" />
    <el-table-column label="用户身份" prop="authority" width="120" />
    <el-table-column label="操作" prop="teacherIdentity" width="120">
      <template #default="scope">
        <el-button type="primary" size="small" @click="handleClick1(scope.row)" v-if="scope.row.teacherIdentity=='common_teacher'">
          升级为导师
        </el-button>
        <el-button disabled size="small" v-else-if="scope.row.teacherIdentity=='class_teacher'">
          已是导师，无法操作
        </el-button>
        <el-button type="primary" size="small" @click="handleClick1(scope.row)" v-if="scope.row.authority=='STUDENT'">
          升级为老师
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>

</style>