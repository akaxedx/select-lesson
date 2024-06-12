<script lang="ts" setup>
import {onMounted, reactive, ref} from "vue";
import {GetAllLessonsReq, GetAllTeachersReq, RegisterStudentReq, SelectLessonReq, Service} from "../../../generated";
import {prop} from "vue-class-component";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";


const token = localStorage.getItem('token')
const total = ref(1)
const router = useRouter()
const tableData = reactive([
  {
    id:0,
    isChoose:false,
    teacherName: '',
  },
  {
    id:0,
    isChoose:false,
    teacherName: '',
  },
  {
    id:0,
    isChoose:false,
    teacherName: '',
  },
  {
    id:0,
    isChoose:false,
    teacherName: '',
  },
  {
    id:0,
    isChoose:false,
    teacherName: '',
  },
  {
    id:0,
    isChoose:false,
    teacherName: '',
  },
  {
    id:0,
    isChoose:false,
    teacherName: '',
  },
  {
    id:0,
    isChoose:false,
    teacherName: '',
  },
  {
    id:0,
    isChoose:false,
    teacherName: '',
  },
  {
    id:0,
    isChoose:false,
    teacherName: '',
  },
])
const getAllTeachersReq = reactive({
  token: '',
  pageNum:1,
} as GetAllTeachersReq)
const selectTeacherReq = reactive({
  token: '',
  teacherId: 0
} as RegisterStudentReq)
const isChoose = ref(false)
const handleClick1 = async (row: any) => {
  selectTeacherReq.teacherId = row.id
  selectTeacherReq.token = token as string
  const res = await Service.registerStudent(selectTeacherReq)
  if (res.code===200) {
    ElMessage.success("选则成功");
    isChoose.value = true
    window.location.reload()
  }
  if (res.code===400) {
    ElMessage.error(res.msg)
  }
}
const getALlTeachers = async (name: string,pageNum:number) =>{
  getAllTeachersReq.pageNum=pageNum
  getAllTeachersReq.token = token as string
  const res = await Service.getAllTeachers(getAllTeachersReq)
  if (res.code === 400) {
    ElMessage.error(res.msg)
    return
  }
  let i = 0
  res.data?.teacherDTOS?.forEach(teacher=>{
    tableData[i].id = teacher.teacher?.id as number
    tableData[i].isChoose = teacher.isChoose as boolean
    tableData[i].teacherName = teacher.username as string
    if (teacher.isChoose) {
      isChoose.value = true
    }
    i++
  })
  console.log(res)
  total.value = res.data?.pageTotal as number
}
onMounted(()=>{
  getALlTeachers("",1)
})
const small = ref(false)
const background = ref(false)
const disabled = ref(false)
const currentPage = ref(1)
</script>

<template>
  <el-table :data="tableData.filter(item=>item.id!==0)" style="width: 100%">
    <el-table-column prop="teacherName" label="老师名称" width="150" />
    <el-table-column label="老师id" prop="id" width="120" />
    <el-table-column label="操作" prop="isChoose" width="120">
      <template #default="scope">
          <el-popconfirm title="选择后将不能更改，是否确定？" @confirm="handleClick1(scope.row)">
            <template #reference>
              <el-button type="primary" size="small"  v-if="!isChoose">
                选择老师
              </el-button>
            </template>
          </el-popconfirm>
        <el-button type="success" disabled v-if="isChoose">已有导师</el-button>
      </template>
    </el-table-column>
  </el-table>
  <div class="demo-pagination-block">
    <el-pagination
        v-model:current-page="currentPage"
        :page-size="100"
        :small="small"
        :disabled="disabled"
        :background="background"
        layout="总数, prev, pager, next"
        :total=total
        @current-change="getALlTeachers('',currentPage)"
    />
  </div>
</template>

<style scoped>

</style>