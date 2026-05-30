import axios from 'axios'
import { useAuthStore } from '../store/auth'
import router from '../router'

const service = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
})

service.interceptors.request.use((config) => {
  const store = useAuthStore()
  if (store.token) {
    config.headers.Authorization = `Bearer ${store.token}`
  }
  return config
})

service.interceptors.response.use(
  (response) => {
    const payload = response.data
    if (payload.code !== 0) {
      return Promise.reject(new Error(payload.message || '请求失败'))
    }
    return payload.data
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      const store = useAuthStore()
      store.logout()
      router.replace('/login')
    }
    return Promise.reject(error)
  },
)

export default service
