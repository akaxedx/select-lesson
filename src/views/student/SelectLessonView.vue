<script lang="ts" setup>
import {onMounted, reactive, ref} from "vue";
import {GetAllLessonsReq, SelectLessonReq, Service} from "../../../generated";
import {prop} from "vue-class-component";
import {ElMessage} from "element-plus";
import { ElDialog } from 'element-plus';


const token = localStorage.getItem('token')
const total = ref(1)
const tableData = reactive([
  {
    lessonName:'',
    id:0,
    pos:'',
    currentNum:0,
    maxNum:0,
    startDate:'',
    endDate:'',
    classTime:'',
    isChoose:false,
    teacherName: '',
  },
  {
    lessonName:'',
    id:0,
    pos:'',
    currentNum:0,
    maxNum:0,
    startDate:'',
    endDate:'',
    classTime:'',
    isChoose:false,
    teacherName: '',
  },
  {
    lessonName:'',
    id:0,
    pos:'',
    currentNum:0,
    maxNum:0,
    startDate:'',
    endDate:'',
    classTime:'',
    isChoose:false,
    teacherName: '',
  },
  {
    lessonName:'',
    id:0,
    pos:'',
    currentNum:0,
    maxNum:0,
    startDate:'',
    endDate:'',
    classTime:'',
    isChoose:false,
    teacherName: '',
  },
  {
    lessonName:'',
    id:0,
    pos:'',
    currentNum:0,
    maxNum:0,
    startDate:'',
    endDate:'',
    classTime:'',
    isChoose:false,
    teacherName: '',
  },
  {
    lessonName:'',
    id:0,
    pos:'',
    currentNum:0,
    maxNum:0,
    startDate:'',
    endDate:'',
    classTime:'',
    isChoose:false,
    teacherName: '',
  },
  {
    lessonName:'',
    id:0,
    pos:'',
    currentNum:0,
    maxNum:0,
    startDate:'',
    endDate:'',
    classTime:'',
    isChoose:false,
    teacherName: '',
  },
  {
    lessonName:'',
    id:0,
    pos:'',
    currentNum:0,
    maxNum:0,
    startDate:'',
    endDate:'',
    classTime:'',
    isChoose:false,
    teacherName: '',
  },
  {
    lessonName:'',
    id:0,
    pos:'',
    currentNum:0,
    maxNum:0,
    startDate:'',
    endDate:'',
    classTime:'',
    isChoose:false,
    teacherName: '',
  },
  {
    lessonName:'',
    id:0,
    pos:'',
    currentNum:0,
    maxNum:0,
    startDate:'',
    endDate:'',
    classTime:'',
    isChoose:false,
    teacherName: '',
  },
])
const getAllLessonsReq = reactive({
  token: '',
  lessonName: '',
  pageNum: 1,
} as GetAllLessonsReq)
const selectLesson = reactive({
  token: '',
  lessonId:0,
} as SelectLessonReq)
const handleClick1 = async (row: any) => {
  selectLesson.lessonId = row.id
  selectLesson.token = token as string
  const res = await Service.selectLesson(selectLesson)
  if (res.code===200) {
    ElMessage.success("选课成功");
    row.currentNum++
    row.isChoose = true
  }
  if (res.code===400) {
    ElMessage.error(res.msg)
  }
}
const handleClick2 = async (row: any) => {
  selectLesson.lessonId = row.id
  selectLesson.token = token as string
  const res = await Service.goAwayLesson(selectLesson)
  if (res.code === 200) {
    ElMessage.success("退课成功");
    row.currentNum--
    row.isChoose = false
  }
  if (res.code===400) {
    ElMessage.error(res.msg)
  }
}

const getAllLesson = async (name: string,pageNum:number) =>{
  getAllLessonsReq.lessonName = name
  getAllLessonsReq.pageNum=pageNum
  getAllLessonsReq.token = token as string
  const res = await Service.getAllLessons(getAllLessonsReq)
  if (res.code === 400) {
    ElMessage.error(res.msg)
    return
  }

  let i = 0
  res.data?.lessonDTOS?.forEach(lesson=>{
    tableData[i].lessonName = lesson.lesson?.lessonName as string
    tableData[i].id = lesson.lesson?.id as number
    tableData[i].pos = lesson.lesson?.pos as string
    tableData[i].currentNum = lesson.lesson?.currentNum as number
    tableData[i].maxNum = lesson.lesson?.maxNum as number
    tableData[i].startDate = (lesson.lesson?.startDate as string).split("T")[0]
    tableData[i].endDate = (lesson.lesson?.endDate as string).split("T")[0]
    tableData[i].classTime = lesson.lesson?.time as string
    tableData[i].isChoose = lesson.isChoose as boolean
    tableData[i].teacherName = lesson.username as string
    i++
  })


  total.value = res.data?.pageTotal as number
}
onMounted(()=>{
  getAllLesson("",1)
})
const small = ref(false)
const background = ref(false)
const disabled = ref(false)
const currentPage = ref(1)
const dialogVisible = ref(false);
const selectedClassTime = ref('');
const showClassTimeDialog = (time: string) => {
  selectedClassTime.value = time;
  dialogVisible.value = true;
};

</script>

<template>
  <el-table :data="tableData.filter(item=>item.id!==0)" style="width: 100%">
    <el-table-column prop="lessonName" label="课程名称" width="150" />
    <el-table-column label="课程id" prop="id" width="120" />
    <el-table-column label="上课地点" prop="pos" width="120" />
    <el-table-column label="当前人数" prop="currentNum" width="120" />
    <el-table-column label="人数上限" prop="maxNum" width="120" />
    <el-table-column label="开始时间" prop="startDate" width="120" />
    <el-table-column label="结束时间" prop="endDate" width="120" />
    <el-table-column label="上课时间" prop="classTime" width="180">
      <template #default="scope">
        <el-button type="text" size="small" @click="showClassTimeDialog(scope.row.classTime)">
          查看时间
        </el-button>
      </template>
    </el-table-column>
    <el-table-column label="任课老师" prop="teacherName" width="120" />
    <el-table-column label="操作" prop="isChoose" width="120">
      <template #default="scope">
        <el-button type="primary" size="small" @click="handleClick1(scope.row)" v-if="!scope.row.isChoose">
          选择课程
        </el-button>
        <el-button type="warning" size="small" @click="handleClick2(scope.row)" v-else>
          退掉课程
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="dialogVisible" title="上课时间详情">
    上课时间为: {{ selectedClassTime }}
  </el-dialog>
  <div class="demo-pagination-block">
    <el-pagination
        v-model:current-page="currentPage"
        :page-size="100"
        :small="small"
        :disabled="disabled"
        :background="background"
        layout="总数, prev, pager, next"
        :total=total
        @current-change="getAllLesson('',currentPage)"
    />
  </div>
 </template>

<style scoped>

</style>