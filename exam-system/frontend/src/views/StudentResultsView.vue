<template>
  <div class="page-wrap">
    <el-card>
      <div class="card-title">我的成绩</div>
      <el-table :data="results" border>
        <el-table-column prop="recordId" label="记录ID" width="100" />
        <el-table-column prop="examId" label="考试ID" width="100" />
        <el-table-column prop="examName" label="考试名称" />
        <el-table-column prop="totalScore" label="分数" width="100" />
        <el-table-column prop="submittedAt" label="提交时间" width="180" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getStudentResults } from '../api'

const results = ref([])

async function load() {
  results.value = await getStudentResults()
}

onMounted(load)
</script>
