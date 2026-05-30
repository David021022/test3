<template>
  <div class="page-wrap">
    <el-card>
      <div class="card-title">课程管理</div>
      <div class="toolbar">
        <el-input v-model="form.name" placeholder="课程名" style="width: 220px" />
        <el-input v-model="form.description" placeholder="描述" style="width: 280px" />
        <el-button type="primary" @click="add">新增课程</el-button>
      </div>
      <el-table :data="courses" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="课程名" />
        <el-table-column prop="description" label="描述" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createCourse, getCourses } from '../api'

const courses = ref([])
const form = reactive({ name: '', description: '' })

async function load() {
  courses.value = await getCourses()
}

async function add() {
  if (!form.name) {
    ElMessage.warning('请输入课程名')
    return
  }
  await createCourse(form)
  ElMessage.success('新增成功')
  form.name = ''
  form.description = ''
  await load()
}

onMounted(load)
</script>
