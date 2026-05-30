import { defineStore } from 'pinia'

const KEY = 'exam_auth'

export const useAuthStore = defineStore('auth', {
  state: () => {
    const saved = localStorage.getItem(KEY)
    if (saved) {
      try {
        return JSON.parse(saved)
      } catch (e) {
      }
    }
    return {
      token: '',
      userId: null,
      username: '',
      role: '',
    }
  },
  actions: {
    setAuth(payload) {
      this.token = payload.token
      this.userId = payload.userId
      this.username = payload.username
      this.role = payload.role
      localStorage.setItem(KEY, JSON.stringify(this.$state))
    },
    logout() {
      this.token = ''
      this.userId = null
      this.username = ''
      this.role = ''
      localStorage.removeItem(KEY)
    },
  },
})
