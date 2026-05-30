<template>
  <div class="page-wrap">
    <el-card>
      <div class="card-title">题库管理</div>
      <div class="toolbar">
        <el-select v-model="form.courseId" placeholder="课程" style="width: 120px">
          <el-option v-for="c in courses" :key="c.id" :label="c.name" :value="c.id" />
        </el-select>
        <el-select v-model="form.type" placeholder="类型" style="width: 120px">
          <el-option label="单选" value="SINGLE" />
          <el-option label="多选" value="MULTIPLE" />
          <el-option label="判断" value="JUDGE" />
        </el-select>
        <el-input v-model="form.title" placeholder="题干" style="width: 320px" />
        <el-input v-model="form.answer" placeholder="答案(多选示例 A,B)" style="width: 220px" />
        <el-button type="primary" @click="addQuestion">新增题目</el-button>
      </div>
      <el-table :data="questions" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="courseId" label="课程ID" width="90" />
        <el-table-column prop="type" label="类型" width="100" />
        <el-table-column prop="title" label="题干" />
        <el-table-column prop="answer" label="答案" width="140" />
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createQuestion, deleteQuestion, getCourses, getQuestions } from '../api'

const courses = ref([])
const questions = ref([])

const form = reactive({
  courseId: null,
  type: 'SINGLE',
  title: '',
  answer: '',
})

async function load() {
  courses.value = await getCourses()
  questions.value = await getQuestions()
}

async function addQuestion() {
  if (!form.courseId || !form.title || !form.answer) {
    ElMessage.warning('请填写课程、题干、答案')
    return
  }
  const payload = {
    courseId: form.courseId,
    title: form.title,
    type: form.type,
    answer: form.answer,
    analysis: '',
    options: [],
  }
  await createQuestion(payload)
  ElMessage.success('新增成功')
  form.title = ''
  form.answer = ''
  await load()
}

async function remove(id) {
  await deleteQuestion(id)
  ElMessage.success('删除成功')
  await load()
}

onMounted(load)
</script>
