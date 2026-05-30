<template>
  <div class="login-wrap">
    <el-card class="login-card">
      <div class="title">在线考试系统登录</div>
      <el-form :model="form" label-width="70px">
        <el-form-item label="账号">
          <el-input v-model="form.username" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-button type="primary" style="width: 100%" :loading="loading" @click="onLogin">登录</el-button>
      </el-form>
      <div class="tips">默认账号：admin / teacher1 / student1，默认密码：123456</div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api'
import { useAuthStore } from '../store/auth'

const form = reactive({ username: '', password: '' })
const loading = ref(false)
const router = useRouter()
const authStore = useAuthStore()

async function onLogin() {
  if (!form.username || !form.password) {
    ElMessage.warning('请输入账号和密码')
    return
  }
  loading.value = true
  try {
    const data = await login(form)
    authStore.setAuth(data)
    router.replace('/')
  } catch (e) {
    ElMessage.error(e.message || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-wrap {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-card {
  width: 420px;
}

.title {
  font-size: 22px;
  font-weight: 700;
  text-align: center;
  margin-bottom: 20px;
}

.tips {
  margin-top: 12px;
  color: #64748b;
  font-size: 13px;
}
</style>
