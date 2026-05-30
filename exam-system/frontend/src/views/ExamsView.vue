<template>
  <div class="page-wrap">
    <el-card>
      <div class="card-title">考试管理</div>
      <div class="toolbar">
        <el-input v-model="form.name" placeholder="考试名称" style="width: 200px" />
        <el-select v-model="form.courseId" placeholder="课程" style="width: 120px">
          <el-option v-for="c in courses" :key="c.id" :label="c.name" :value="c.id" />
        </el-select>
        <el-select v-model="form.paperId" placeholder="试卷" style="width: 160px">
          <el-option v-for="p in papers" :key="p.id" :label="`${p.name}(#${p.id})`" :value="p.id" />
        </el-select>
        <el-date-picker v-model="form.timeRange" type="datetimerange" range-separator="到" start-placeholder="开始时间" end-placeholder="结束时间" value-format="YYYY-MM-DDTHH:mm:ss" />
        <el-button type="primary" @click="add">发布考试</el-button>
      </div>
      <el-table :data="exams" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="考试名称" />
        <el-table-column prop="courseId" label="课程ID" width="100" />
        <el-table-column prop="paperId" label="试卷ID" width="100" />
        <el-table-column prop="startTime" label="开始时间" width="180" />
        <el-table-column prop="endTime" label="结束时间" width="180" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createExam, getCourses, getExams, getPapers } from '../api'

const exams = ref([])
const courses = ref([])
const papers = ref([])
const form = reactive({ name: '', courseId: null, paperId: null, timeRange: [] })

async function load() {
  exams.value = await getExams()
  courses.value = await getCourses()
  papers.value = await getPapers()
}

async function add() {
  if (!form.name || !form.courseId || !form.paperId || form.timeRange.length !== 2) {
    ElMessage.warning('请填写完整考试信息')
    return
  }
  await createExam({
    name: form.name,
    courseId: form.courseId,
    paperId: form.paperId,
    startTime: form.timeRange[0],
    endTime: form.timeRange[1],
  })
  ElMessage.success('发布成功')
  form.name = ''
  form.timeRange = []
  await load()
}

onMounted(load)
</script>
