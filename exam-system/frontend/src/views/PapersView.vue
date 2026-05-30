<template>
  <div class="page-wrap">
    <el-card>
      <div class="card-title">试卷管理</div>
      <div class="toolbar">
        <el-input v-model="form.name" placeholder="试卷名" style="width: 220px" />
        <el-select v-model="form.courseId" placeholder="课程" style="width: 120px">
          <el-option v-for="c in courses" :key="c.id" :label="c.name" :value="c.id" />
        </el-select>
        <el-input v-model="form.questionIds" placeholder="题目ID列表，如 1,2,3" style="width: 240px" />
        <el-input-number v-model="form.score" :min="1" :max="100" />
        <el-button type="primary" @click="add">创建试卷</el-button>
      </div>
      <el-table :data="papers" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="试卷名" />
        <el-table-column prop="courseId" label="课程ID" width="100" />
        <el-table-column label="题目数量" width="120">
          <template #default="scope">{{ scope.row.questions?.length || 0 }}</template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createPaper, getCourses, getPapers } from '../api'

const papers = ref([])
const courses = ref([])
const form = reactive({ name: '', courseId: null, questionIds: '', score: 5 })

async function load() {
  papers.value = await getPapers()
  courses.value = await getCourses()
}

async function add() {
  if (!form.name || !form.courseId || !form.questionIds) {
    ElMessage.warning('请填写试卷名、课程、题目ID')
    return
  }
  const ids = form.questionIds.split(',').map((i) => Number(i.trim())).filter((v) => !Number.isNaN(v))
  const questions = ids.map((id, index) => ({ questionId: id, score: form.score, sortOrder: index + 1 }))
  await createPaper({ name: form.name, courseId: form.courseId, description: '', questions })
  ElMessage.success('创建成功')
  form.name = ''
  form.questionIds = ''
  await load()
}

onMounted(load)
</script>
