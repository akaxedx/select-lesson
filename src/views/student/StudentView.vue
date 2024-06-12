<script lang="ts" setup>
import {onMounted, reactive} from "vue";
import {GetLessonReq, Service} from "../../../generated";
import {ElMessage} from "element-plus";

const tableData = reactive([
  {
    time:'8:00-8:45',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday:'',
    Friday:'',
    Saturday:'',
    Sunday:'',
  },
  {
    time:'8:50-9:35',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday:'',
    Friday:'',
    Saturday:'',
    Sunday:'',
  },
  {
    time:'10:00-10:45',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday:'',
    Friday:'',
    Saturday:'',
    Sunday:'',
  },
  {
    time:'10:50-11:35',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday:'',
    Friday:'',
    Saturday:'',
    Sunday:'',
  },
  {
    time:'14:30-15:15',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday:'',
    Friday:'',
    Saturday:'',
    Sunday:'',
  },
  {
    time:'15:20-16:05',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday:'',
    Friday:'',
    Saturday:'',
    Sunday:'',
  },
  {
    time:'16:30-17:15',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday:'',
    Friday:'',
    Saturday:'',
    Sunday:'',
  },
  {
    time:'19:00-19:45',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday:'',
    Friday:'',
    Saturday:'',
    Sunday:'',
  },
  {
    time:'19:50-20:35',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday:'',
    Friday:'',
    Saturday:'',
    Sunday:'',
  },
  {
    time:'20:45-21:30',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday:'',
    Friday:'',
    Saturday:'',
    Sunday:'',
  }
])
const token = localStorage.getItem('token')
const getMyLessons = reactive({
  token:''
} as GetLessonReq)
onMounted(async () => {
  getMyLessons.token = token as string
  const res = await Service.getMyLessons(getMyLessons)
  if (res.code===400) {
    ElMessage.error(res.msg)
    return
  }
  res.data?.forEach(lesson =>{
    lesson.usedTimeDTO?.monday?.forEach(time=>{
      tableData[time-1].Monday = lesson.lessonName as string
    })
    lesson.usedTimeDTO?.tuesday?.forEach(time=>{
      tableData[time-1].Tuesday = lesson.lessonName as string
    })
    lesson.usedTimeDTO?.wednesday?.forEach(time=>{
      tableData[time-1].Wednesday = lesson.lessonName as string
    })
    lesson.usedTimeDTO?.thursday?.forEach(time=>{
      tableData[time-1].Thursday = lesson.lessonName as string
    })
    lesson.usedTimeDTO?.friday?.forEach(time=>{
      tableData[time-1].Friday = lesson.lessonName as string
    })
    lesson.usedTimeDTO?.saturday?.forEach(time=>{
      tableData[time-1].Saturday = lesson.lessonName as string
    })
    lesson.usedTimeDTO?.sunday?.forEach(time=>{
      tableData[time-1].Sunday = lesson.lessonName as string
    })
  })
  console.log(res.data)
})
</script>

<template>
  <el-table :data="tableData" height="500" style="width: 100%">
    <el-table-column prop="time" label="时间" width="180" />
    <el-table-column prop="Monday" label="周一" width="180" />
    <el-table-column prop="Tuesday" label="周二" width="180" />
    <el-table-column prop="Wednesday" label="周三" width="180" />
    <el-table-column prop="Thursday" label="周四" width="180" />
    <el-table-column prop="Friday" label="周五" width="180" />
    <el-table-column prop="Saturday" label="周六" width="180" />
    <el-table-column prop="Sunday" label="周日" width="180" />
  </el-table>

</template>

<style>
.is-selected {
  color: #1989fa;
}
</style>