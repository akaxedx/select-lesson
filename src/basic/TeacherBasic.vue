<script lang="ts" setup>
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import {BaseReq, GetLessonReq, GetMyLessonStudentReq, RegisterStudentReq, Service, SetLessonReq} from "../../generated";
import type {ComponentSize, FormInstance} from 'element-plus'
import {ElMessage} from "element-plus";
import { ElMessageBox } from 'element-plus'

const dialogVisible = ref(false)
const over = () =>{
  dialogVisible.value=false
  window.location.reload()
}
const handleClose = (done: () => void) => {
  ElMessageBox.confirm('Are you sure to close this dialog?')
      .then(() => {
        done()
      })
      .catch(() => {
        // catch error
      })
}
const router = useRouter()
const username = localStorage.getItem('name')
const token = localStorage.getItem('token')
const setStudent = () => {
  router.push('/teacher/setStudent')
}
const selectLesson = () => {
  router.push('/student/select')
}
const login = () => {
  router.push('/user/login')
}
const drawer1 = ref(false)
const drawer2 = ref(false)
const drawer3 = ref(false)
const formRef = ref<FormInstance>()

const numberValidateForm = reactive({
  num: '',
})


const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

const registerStudentReq = reactive({
  token: '',
  studentNum: 50,
} as RegisterStudentReq)

const registerStudent = async (num: number) => {
  registerStudentReq.token = token as string
  registerStudentReq.studentNum = num
  const res = await Service.registerStudent(registerStudentReq)
  if (res.code === 200) {
    ElMessage.success("招生成功")
  }
  if (res.code === 400) {
    ElMessage.error(res.msg)
  }
}
const formSize = ref<ComponentSize>('default')
const ruleForm = reactive({
  startDate: '',
  endDate: '',
  lessonName: '',
  roomId: '',
  roomList: [
    {
      roomName: '',
      roomId: '',
    }],
  time: {
    monday: [],
    tuesday: [],
    wednesday: [],
    thursday: [],
    saturday: [],
    friday: [],
    sunday: []
  },
  simpleTime: []

})

const baseReq = reactive({
  token: '',
} as BaseReq)
const getMyLessons = reactive({
  token: ''
} as GetLessonReq)
const tableData = reactive([
  {
    time: '8:00-8:45',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday: '',
    Friday: '',
    Saturday: '',
    Sunday: '',
  },
  {
    time: '8:50-9:35',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday: '',
    Friday: '',
    Saturday: '',
    Sunday: '',
  },
  {
    time: '10:00-10:45',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday: '',
    Friday: '',
    Saturday: '',
    Sunday: '',
  },
  {
    time: '10:50-11:35',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday: '',
    Friday: '',
    Saturday: '',
    Sunday: '',
  },
  {
    time: '14:30-15:15',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday: '',
    Friday: '',
    Saturday: '',
    Sunday: '',
  },
  {
    time: '15:20-16:05',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday: '',
    Friday: '',
    Saturday: '',
    Sunday: '',
  },
  {
    time: '16:30-17:15',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday: '',
    Friday: '',
    Saturday: '',
    Sunday: '',
  },
  {
    time: '19:00-19:45',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday: '',
    Friday: '',
    Saturday: '',
    Sunday: '',
  },
  {
    time: '19:50-20:35',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday: '',
    Friday: '',
    Saturday: '',
    Sunday: '',
  },
  {
    time: '20:45-21:30',
    Monday: '',
    Tuesday: '',
    Wednesday: '',
    Thursday: '',
    Friday: '',
    Saturday: '',
    Sunday: '',
  }
])
const lessonData = reactive([
  {
    lessonName: "",
    lessonId: 0,
    current: 0,
    pos: ""
  }
])
let studentData = reactive([
  {
    studentName:"",
    studentId:0
  }
])
const back = () => {
  drawer2.value = false
}

onMounted(async () => {
  baseReq.token = token as string
  const res = await Service.getAllOkRoom(baseReq)
  let isOne = true
  res.data?.forEach(room => {
    if (isOne) {
      console.log(room)
      ruleForm.roomId = room.id.toString()
      ruleForm.roomList[0] = {
        roomName: room.building + ':' + room.floor + '楼' + room.num + '号教室',
        roomId: room.id?.toString() as string,
      }
      console.log("6")
      isOne = false
    } else {
      ruleForm.roomList.push(
          {
            roomName: room.building + ':' + room.floor + '楼' + room.num + '号教室',
            roomId: room.id?.toString() as string,
          }
      )
    }
  })
  getMyLessons.token = token as string
  const res2 = await Service.getMyLessons(getMyLessons)
  if (res2.code === 400) {
    ElMessage.error(res2.msg)
    return
  }
  res2.data?.forEach(lesson => {
    lesson.usedTimeDTO?.monday?.forEach(time => {
      tableData[time - 1].Monday = lesson.lessonName as string
    })
    lesson.usedTimeDTO?.tuesday?.forEach(time => {
      tableData[time - 1].Tuesday = lesson.lessonName as string
    })
    lesson.usedTimeDTO?.wednesday?.forEach(time => {
      tableData[time - 1].Wednesday = lesson.lessonName as string
    })
    lesson.usedTimeDTO?.thursday?.forEach(time => {
      tableData[time - 1].Thursday = lesson.lessonName as string
    })
    lesson.usedTimeDTO?.friday?.forEach(time => {
      tableData[time - 1].Friday = lesson.lessonName as string
    })
    lesson.usedTimeDTO?.saturday?.forEach(time => {
      tableData[time - 1].Saturday = lesson.lessonName as string
    })
    lesson.usedTimeDTO?.sunday?.forEach(time => {
      tableData[time - 1].Sunday = lesson.lessonName as string
    })
  })
  const res3 = await Service.getTMyLessons(baseReq)
  res3.data?.lessonDTOS?.forEach(lesson => {
    const newLesson = {
      lessonName: "",
      lessonId: 0,
      current: 0,
      pos: ""
    }
    newLesson.lessonName = lesson.lesson?.lessonName
    newLesson.lessonId = lesson.lesson?.id
    newLesson.pos = lesson.lesson?.pos
    newLesson.current = lesson.lesson?.currentNum
    console.log("2")
    console.log(newLesson)
    lessonData.push(newLesson)
  })
})
const setLessonReq = reactive({
  token: '',
  startDate: '',
  endDate: '',
  lessonName: '',
  roomId: 0,
  thursday: [],
  monday: [],
  wednesday: [],
  sunday: [],
  friday: [],
  tuesday: [],
  saturday: []
} as SetLessonReq)
const onSubmit = async () => {
  ruleForm.simpleTime.forEach(item => {
    const day = Math.floor(Number(item) / 10)
    if (day === 1) {
      ruleForm.time.monday.push(Number(item) % (day * 10) + 1)
    }
    if (day === 2) {
      ruleForm.time.tuesday.push(Number(item) % (day * 10) + 1)
    }
    if (day === 3) {
      ruleForm.time.wednesday.push(Number(item) % (day * 10) + 1)
    }
    if (day === 4) {
      ruleForm.time.thursday.push(Number(item) % (day * 10) + 1)
    }
    if (day === 5) {
      ruleForm.time.friday.push(Number(item) % (day * 10) + 1)
    }
    if (day === 6) {
      ruleForm.time.saturday.push(Number(item) % (day * 10) + 1)
    }
    if (day === 7) {
      ruleForm.time.sunday.push(Number(item) % (day * 10) + 1)
    }
  })
  if (ruleForm.startDate === '' || ruleForm.endDate === '') {
    ElMessage.error("请输入起始日期")
    return
  }
  const start = new Date(ruleForm.startDate)
  const end = new Date(ruleForm.endDate)
  ruleForm.startDate = formatDate(start)
  ruleForm.endDate = formatDate(end)
  setLessonReq.token = token as string
  setLessonReq.endDate = ruleForm.endDate
  setLessonReq.startDate = ruleForm.startDate
  setLessonReq.lessonName = ruleForm.lessonName
  setLessonReq.friday = ruleForm.time.friday
  setLessonReq.roomId = Number(ruleForm.roomId)
  setLessonReq.monday = ruleForm.time.monday
  setLessonReq.tuesday = ruleForm.time.tuesday
  setLessonReq.wednesday = ruleForm.time.wednesday
  setLessonReq.thursday = ruleForm.time.thursday
  setLessonReq.saturday = ruleForm.time.saturday
  setLessonReq.sunday = ruleForm.time.sunday
  const res = await Service.register1(setLessonReq)
  if (res.code === 200) {
    ElMessage.success("成功创建");
    window.location.reload()
  }
  if (res.code === 400) {
    ruleForm.time.monday = []
    ruleForm.time.thursday = []
    ruleForm.time.wednesday = []
    ruleForm.time.thursday = []
    ruleForm.time.friday = []
    ruleForm.time.saturday = []
    ruleForm.time.sunday = []

    ElMessage.error(res.msg);
  }
}
const formatDate = (date: Date): string => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');

  // 构建新的日期时间字符串
  return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
}
const getMyLessonsStudentReq = reactive({
  token: "",
  lessonId: 0
} as GetMyLessonStudentReq)

const handleClick1 = async (row: any) => {
  dialogVisible.value=true
  getMyLessonsStudentReq.lessonId = row.lessonId
  getMyLessonsStudentReq.token = token
  const res = await Service.getMyLessonsStudents(getMyLessonsStudentReq)
  res.data?.forEach(student=>{
    const newStudent = {
      studentName:"",
      studentId:0
    }
    newStudent.studentId=student.studentId
    newStudent.studentName=student.name
    console.log("111")
    console.log(newStudent)
    studentData.push(newStudent)
  })
}
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header>你好, {{ username }}
        <el-button style="margin-left: 16px" type="primary" @click="drawer1 = true">
          设置招生
        </el-button>
        <el-drawer v-model="drawer1" :with-header="false" title="I am the title">
          <el-form
              ref="formRef"
              :model="numberValidateForm"
              class="demo-ruleForm"
              label-width="auto"
              style="max-width: 600px"
          >
            <el-form-item
                :rules="[
        { required: true, message: 'num is required' },
        { type: 'number', message: 'num must be a number' },
      ]"
                label="num"
                prop="num"
            >
              <el-input
                  v-model.number="numberValidateForm.num"
                  autocomplete="off"
                  type="text"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="registerStudent(Number(numberValidateForm.num))">Submit</el-button>
              <el-button @click="resetForm(formRef)">Reset</el-button>
            </el-form-item>
          </el-form>
        </el-drawer>

        <el-button style="margin-left: 16px" type="primary" @click="drawer2 = true">
          新建课程
        </el-button>
        <el-drawer v-model="drawer2" :with-header="false" title="I am the title">
          <el-form
              ref="ruleFormRef"
              :model="ruleForm"
              :size="formSize"
              class="demo-ruleForm"
              label-width="auto"
              status-icon
              style="max-width: 600px"
          >
            <el-form-item label="Activity name">
              <el-input v-model="ruleForm.lessonName"/>
            </el-form-item>
            <el-form-item label="教室" prop="roomId">
              <!-- 绑定 v-model 到 ruleForm 中的 selectedRoomId -->
              <el-select
                  v-model="ruleForm.roomId"
                  placeholder="教室"
              >
                <!-- 使用 :options 自动渲染选项 -->
                <el-option
                    v-for="item in ruleForm.roomList"
                    :key="item.roomId"
                    :label="item.roomName"
                    :value="item.roomId"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="起始时间">
              <el-col :span="11">
                <el-date-picker
                    v-model="ruleForm.startDate"
                    placeholder="Pick a date"
                    style="width: 100%"
                    type="datetime"
                />
              </el-col>
              <el-col :span="2" class="text-center">
                <span class="text-gray-500">-</span>
              </el-col>
              <el-col :span="11">
                <el-date-picker
                    v-model="ruleForm.endDate"
                    placeholder="Pick a date"
                    style="width: 100%"
                    type="datetime"
                />
              </el-col>
            </el-form-item>
            <el-form-item label="time">
              <el-checkbox-group v-model="ruleForm.simpleTime">
                <el-checkbox name="type" value="10">
                  周一第一节
                </el-checkbox>
                <el-checkbox name="type" value="11">
                  周一第二节
                </el-checkbox>
                <el-checkbox name="type" value="12">
                  周一第三节
                </el-checkbox>
                <el-checkbox name="type" value="13">
                  周一第四节
                </el-checkbox>
                <el-checkbox name="type" value="14">
                  周一第五节
                </el-checkbox>
                <el-checkbox name="type" value="15">
                  周一第六节
                </el-checkbox>
                <el-checkbox name="type" value="16">
                  周一第七节
                </el-checkbox>
                <el-checkbox name="type" value="17">
                  周一第八节
                </el-checkbox>
                <el-checkbox name="type" value="18">
                  周一第九节
                </el-checkbox>
                <el-checkbox name="type" value="19">
                  周一第十节
                </el-checkbox>
                <el-checkbox name="type" value="20">
                  周二第一节
                </el-checkbox>
                <el-checkbox name="type" value="21">
                  周二第二节
                </el-checkbox>
                <el-checkbox name="type" value="22">
                  周二第三节
                </el-checkbox>
                <el-checkbox name="type" value="23">
                  周二第四节
                </el-checkbox>
                <el-checkbox name="type" value="24">
                  周二第五节
                </el-checkbox>
                <el-checkbox name="type" value="25">
                  周二第六节
                </el-checkbox>
                <el-checkbox name="type" value="26">
                  周二第七节
                </el-checkbox>
                <el-checkbox name="type" value="27">
                  周二第八节
                </el-checkbox>
                <el-checkbox name="type" value="28">
                  周二第九节
                </el-checkbox>
                <el-checkbox name="type" value="29">
                  周二第十节
                </el-checkbox>
                <el-checkbox name="type" value="30">
                  周三第一节
                </el-checkbox>
                <el-checkbox name="type" value="31">
                  周三第二节
                </el-checkbox>
                <el-checkbox name="type" value="32">
                  周三第三节
                </el-checkbox>
                <el-checkbox name="type" value="33">
                  周三第四节
                </el-checkbox>
                <el-checkbox name="type" value="34">
                  周三第五节
                </el-checkbox>
                <el-checkbox name="type" value="35">
                  周三第六节
                </el-checkbox>
                <el-checkbox name="type" value="36">
                  周三第七节
                </el-checkbox>
                <el-checkbox name="type" value="37">
                  周三第八节
                </el-checkbox>
                <el-checkbox name="type" value="38">
                  周三第九节
                </el-checkbox>
                <el-checkbox name="type" value="39">
                  周三第十节
                </el-checkbox>
                <el-checkbox name="type" value="40">
                  周四第一节
                </el-checkbox>
                <el-checkbox name="type" value="41">
                  周四第二节
                </el-checkbox>
                <el-checkbox name="type" value="42">
                  周四第三节
                </el-checkbox>
                <el-checkbox name="type" value="43">
                  周四第四节
                </el-checkbox>
                <el-checkbox name="type" value="44">
                  周四第五节
                </el-checkbox>
                <el-checkbox name="type" value="45">
                  周四第六节
                </el-checkbox>
                <el-checkbox name="type" value="46">
                  周四第七节
                </el-checkbox>
                <el-checkbox name="type" value="47">
                  周四第八节
                </el-checkbox>
                <el-checkbox name="type" value="48">
                  周四第九节
                </el-checkbox>
                <el-checkbox name="type" value="49">
                  周四第十节
                </el-checkbox>
                <el-checkbox name="type" value="50">
                  周五第一节
                </el-checkbox>
                <el-checkbox name="type" value="51">
                  周五第二节
                </el-checkbox>
                <el-checkbox name="type" value="52">
                  周五第三节
                </el-checkbox>
                <el-checkbox name="type" value="53">
                  周五第四节
                </el-checkbox>
                <el-checkbox name="type" value="54">
                  周五第五节
                </el-checkbox>
                <el-checkbox name="type" value="55">
                  周五第六节
                </el-checkbox>
                <el-checkbox name="type" value="56">
                  周五第七节
                </el-checkbox>
                <el-checkbox name="type" value="57">
                  周五第八节
                </el-checkbox>
                <el-checkbox name="type" value="58">
                  周五第九节
                </el-checkbox>
                <el-checkbox name="type" value="59">
                  周五第十节
                </el-checkbox>
                <el-checkbox name="type" value="60">
                  周六第一节
                </el-checkbox>
                <el-checkbox name="type" value="61">
                  周六第二节
                </el-checkbox>
                <el-checkbox name="type" value="62">
                  周六第三节
                </el-checkbox>
                <el-checkbox name="type" value="63">
                  周六第四节
                </el-checkbox>
                <el-checkbox name="type" value="64">
                  周六第五节
                </el-checkbox>
                <el-checkbox name="type" value="65">
                  周六第六节
                </el-checkbox>
                <el-checkbox name="type" value="66">
                  周六第七节
                </el-checkbox>
                <el-checkbox name="type" value="67">
                  周六第八节
                </el-checkbox>
                <el-checkbox name="type" value="68">
                  周六第九节
                </el-checkbox>
                <el-checkbox name="type" value="69">
                  周六第十节
                </el-checkbox>
                <el-checkbox name="type" value="70">
                  周日第一节
                </el-checkbox>
                <el-checkbox name="type" value="71">
                  周日第二节
                </el-checkbox>
                <el-checkbox name="type" value="72">
                  周日第三节
                </el-checkbox>
                <el-checkbox name="type" value="73">
                  周日第四节
                </el-checkbox>
                <el-checkbox name="type" value="74">
                  周日第五节
                </el-checkbox>
                <el-checkbox name="type" value="75">
                  周日第六节
                </el-checkbox>
                <el-checkbox name="type" value="76">
                  周日第七节
                </el-checkbox>
                <el-checkbox name="type" value="77">
                  周日第八节
                </el-checkbox>
                <el-checkbox name="type" value="78">
                  周日第九节
                </el-checkbox>
                <el-checkbox name="type" value="79">
                  周日第十节
                </el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">Create</el-button>
              <el-button @click="back">Cancel</el-button>
            </el-form-item>
          </el-form>
        </el-drawer>
        <el-button style="margin-left: 16px" type="primary" @click="drawer3 = true">
          课程详情
        </el-button>
        <el-drawer v-model="drawer3" :with-header="false" size="80%" title="I am the title">
          <template #default>
            <el-table :data="lessonData.filter(item=>item.lessonId!==0)" style="width: 100%">
              <el-table-column label="课程名" prop="lessonName" width="180"/>
              <el-table-column label="课程id" prop="lessonId" width="180"/>
              <el-table-column label="课程人数" prop="current" width="180"/>
              <el-table-column label="学生详情" width="180">
                <template v-slot="scope">
                  <el-button plain @click="handleClick1(scope.row)">
                    查看详情
                  </el-button>

                  <el-dialog
                      v-model="dialogVisible"
                      title="Tips"
                      width="500"
                      :before-close="handleClose"
                  >
                    <el-table :data="studentData.filter(item=>item.studentId!==0)" style="width: 100%">
                      <el-table-column prop="studentName" label="学生姓名" width="180" />
                      <el-table-column prop="studentId" label="学生ID" width="180" />
                    </el-table>
                    <template #footer>
                      <div class="dialog-footer">
                        <el-button type="primary" @click="over">
                          确定
                        </el-button>
                      </div>
                    </template>
                  </el-dialog>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-drawer>
        <span>&nbsp;</span>
        <el-button type="primary" @click="login">切换登录</el-button>
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </div>
  <el-table :data="tableData" height="500" style="width: 100%">
    <el-table-column label="时间" prop="time" width="180"/>
    <el-table-column label="周一" prop="Monday" width="180"/>
    <el-table-column label="周二" prop="Tuesday" width="180"/>
    <el-table-column label="周三" prop="Wednesday" width="180"/>
    <el-table-column label="周四" prop="Thursday" width="180"/>
    <el-table-column label="周五" prop="Friday" width="180"/>
    <el-table-column label="周六" prop="Saturday" width="180"/>
    <el-table-column label="周日" prop="Sunday" width="180"/>
  </el-table>

</template>

<style scoped>

</style>