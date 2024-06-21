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
          已是导师
        </el-button>
        <el-button type="primary" size="small" @click="handleClick1(scope.row)" v-if="scope.row.authority=='STUDENT'">
          升级为老师
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>
/* 表格整体样式 */
.el-table {
  -webkit-font-smoothing: antialiased; /* 平滑字体渲染 */
  th, td {
    padding: 8px 0; /* 单元格内边距 */
    text-align: center; /* 文本居中对齐 */
  }
  th {
    background-color: #f5f5f5; /* 表头背景色 */
    font-weight: 500; /* 表头字重 */
  }
  td {
    border-bottom: 1px solid #ebeef5; /* 单元格下边框 */
  }
}

/* 操作按钮样式 */
.el-button {
  margin: 5px 0; /* 按钮与单元格边缘的间距 */
  border-radius: 3px; /* 按钮圆角 */
}

/* 禁用按钮样式 */
.el-button.is-disabled {
  opacity: 0.5; /* 降低透明度表示禁用状态 */
  cursor: not-allowed; /* 禁用时鼠标指针样式 */
}

/* 响应式设计 */
@media (max-width: 768px) {
  .el-table th, .el-table td {
    padding: 4px 0; /* 减少内边距以适应小屏幕 */
  }
}
</style>