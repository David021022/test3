<template>
  <div class="page-wrap">
    <el-card>
      <div class="card-title">教师成绩查看</div>
      <div class="toolbar">
        <el-input-number v-model="examId" :min="1" />
        <el-button type="primary" @click="load">查询考试成绩</el-button>
      </div>
      <el-table :data="results" border>
        <el-table-column prop="recordId" label="记录ID" width="100" />
        <el-table-column prop="studentId" label="学生ID" width="100" />
        <el-table-column prop="studentUsername" label="学生账号" />
        <el-table-column prop="totalScore" label="分数" width="100" />
        <el-table-column prop="submittedAt" label="提交时间" width="180" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { getTeacherExamResults } from '../api'

const examId = ref(1)
const results = ref([])

async function load() {
  results.value = await getTeacherExamResults(examId.value)
}
</script>
