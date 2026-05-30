<template>
  <div class="page-wrap">
    <el-card>
      <div class="card-title">我的考试</div>
      <el-table :data="exams" border>
        <el-table-column prop="id" label="考试ID" width="90" />
        <el-table-column prop="name" label="考试名" />
        <el-table-column prop="startTime" label="开始时间" width="180" />
        <el-table-column prop="endTime" label="结束时间" width="180" />
        <el-table-column label="操作" width="140">
          <template #default="scope">
            <el-button type="primary" size="small" @click="goSubmit(scope.row)">去答题</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { getStudentExams } from '../api'

const exams = ref([])
const router = useRouter()

async function load() {
  exams.value = await getStudentExams()
}

function goSubmit(row) {
  router.push(`/student/exam/${row.id}`)
}

onMounted(load)
</script>
