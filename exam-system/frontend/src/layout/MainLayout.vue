<template>
  <el-container style="min-height: 100vh">
    <el-aside width="220px" style="background: #0f172a; color: #fff">
      <div style="padding: 16px; font-size: 18px; font-weight: 700">在线考试系统</div>
      <el-menu :default-active="activePath" router background-color="#0f172a" text-color="#cbd5e1" active-text-color="#67e8f9">
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item v-if="isAdmin" index="/users">用户管理</el-menu-item>
        <el-menu-item v-if="isTeacherOrAdmin" index="/courses">课程管理</el-menu-item>
        <el-menu-item v-if="isTeacherOrAdmin" index="/questions">题库管理</el-menu-item>
        <el-menu-item v-if="isTeacherOrAdmin" index="/papers">试卷管理</el-menu-item>
        <el-menu-item v-if="isTeacherOrAdmin" index="/exams">考试管理</el-menu-item>
        <el-menu-item v-if="isTeacherOrAdmin" index="/teacher-results">成绩查看</el-menu-item>
        <el-menu-item v-if="isStudent" index="/student/exams">我的考试</el-menu-item>
        <el-menu-item v-if="isStudent" index="/student/results">我的成绩</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="display: flex; justify-content: space-between; align-items: center; background: #fff; border-bottom: 1px solid #e2e8f0">
        <div>当前用户：{{ authStore.username }}（{{ authStore.role }}）</div>
        <el-button type="danger" plain @click="logout">退出登录</el-button>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '../store/auth'

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()

const activePath = computed(() => route.path)
const isAdmin = computed(() => authStore.role === 'ADMIN')
const isTeacherOrAdmin = computed(() => authStore.role === 'ADMIN' || authStore.role === 'TEACHER')
const isStudent = computed(() => authStore.role === 'STUDENT')

function logout() {
  authStore.logout()
  router.replace('/login')
}
</script>
