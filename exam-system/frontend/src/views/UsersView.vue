<template>
  <div class="page-wrap">
    <el-card>
      <div class="card-title">用户管理</div>
      <div class="toolbar">
        <el-input v-model="form.username" placeholder="用户名" style="width: 160px" />
        <el-input v-model="form.password" placeholder="密码" style="width: 160px" />
        <el-select v-model="form.role" placeholder="角色" style="width: 130px">
          <el-option label="管理员" value="ADMIN" />
          <el-option label="教师" value="TEACHER" />
          <el-option label="学生" value="STUDENT" />
        </el-select>
        <el-switch v-model="form.status" active-text="启用" inactive-text="禁用" />
        <el-button type="primary" @click="addUser">新增用户</el-button>
      </div>
      <el-table :data="users" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="role" label="角色" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">{{ scope.row.status ? '启用' : '禁用' }}</template>
        </el-table-column>
        <el-table-column label="操作" width="280">
          <template #default="scope">
            <el-button size="small" @click="toggleStatus(scope.row)">切换状态</el-button>
            <el-button size="small" type="warning" @click="resetPwd(scope.row)">重置密码</el-button>
            <el-button size="small" type="danger" @click="remove(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createUser, deleteUser, getUsers, resetPassword, updateUser } from '../api'

const users = ref([])
const form = reactive({ username: '', password: '123456', role: 'STUDENT', status: true })

async function loadUsers() {
  users.value = await getUsers()
}

async function addUser() {
  if (!form.username || !form.password) {
    ElMessage.warning('请输入用户名和密码')
    return
  }
  await createUser(form)
  ElMessage.success('新增成功')
  form.username = ''
  form.password = '123456'
  await loadUsers()
}

async function toggleStatus(row) {
  await updateUser(row.id, { role: row.role, status: !row.status })
  ElMessage.success('修改成功')
  await loadUsers()
}

async function resetPwd(row) {
  await resetPassword(row.id, { newPassword: '123456' })
  ElMessage.success('密码已重置为 123456')
}

async function remove(row) {
  await deleteUser(row.id)
  ElMessage.success('删除成功')
  await loadUsers()
}

onMounted(loadUsers)
</script>
