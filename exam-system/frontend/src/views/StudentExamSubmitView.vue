<template>
  <div class="page-wrap">
    <el-card>
      <div class="card-title">在线答题（考试ID: {{ examId }}）</div>
      <el-alert title="MVP 输入格式：每行一题，格式 questionId=答案，例如 1=A；多选 2=A,B" type="info" show-icon />
      <el-input v-model="rawAnswers" type="textarea" :rows="10" style="margin-top: 12px" placeholder="1=A\n2=A,B\n3=TRUE" />
      <div style="margin-top: 12px">
        <el-button type="primary" @click="submit">提交试卷</el-button>
      </div>
      <div v-if="score !== null" style="margin-top: 12px; font-weight: 700">本次得分：{{ score }}</div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { submitExam } from '../api'

const route = useRoute()
const examId = Number(route.params.examId)
const rawAnswers = ref('')
const score = ref(null)

function parseAnswers(text) {
  const lines = text.split('\n').map((i) => i.trim()).filter(Boolean)
  return lines.map((line) => {
    const pair = line.split('=')
    return {
      questionId: Number(pair[0].trim()),
      answerContent: (pair[1] || '').trim(),
    }
  }).filter((item) => !Number.isNaN(item.questionId) && item.answerContent)
}

async function submit() {
  const answers = parseAnswers(rawAnswers.value)
  if (!answers.length) {
    ElMessage.warning('请至少输入一道题答案')
    return
  }
  const data = await submitExam(examId, { answers })
  score.value = data.totalScore
  ElMessage.success('提交成功')
}
</script>
