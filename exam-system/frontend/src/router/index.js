import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../store/auth'
import LoginView from '../views/LoginView.vue'
import MainLayout from '../layout/MainLayout.vue'
import DashboardView from '../views/DashboardView.vue'
import UsersView from '../views/UsersView.vue'
import CoursesView from '../views/CoursesView.vue'
import QuestionsView from '../views/QuestionsView.vue'
import PapersView from '../views/PapersView.vue'
import ExamsView from '../views/ExamsView.vue'
import StudentExamsView from '../views/StudentExamsView.vue'
import StudentExamSubmitView from '../views/StudentExamSubmitView.vue'
import StudentResultsView from '../views/StudentResultsView.vue'
import TeacherResultsView from '../views/TeacherResultsView.vue'

const routes = [
  { path: '/login', component: LoginView, meta: { public: true } },
  {
    path: '/',
    component: MainLayout,
    children: [
      { path: '', component: DashboardView, meta: { roles: ['ADMIN', 'TEACHER', 'STUDENT'] } },
      { path: 'users', component: UsersView, meta: { roles: ['ADMIN'] } },
      { path: 'courses', component: CoursesView, meta: { roles: ['ADMIN', 'TEACHER'] } },
      { path: 'questions', component: QuestionsView, meta: { roles: ['ADMIN', 'TEACHER'] } },
      { path: 'papers', component: PapersView, meta: { roles: ['ADMIN', 'TEACHER'] } },
      { path: 'exams', component: ExamsView, meta: { roles: ['ADMIN', 'TEACHER'] } },
      { path: 'teacher-results', component: TeacherResultsView, meta: { roles: ['ADMIN', 'TEACHER'] } },
      { path: 'student/exams', component: StudentExamsView, meta: { roles: ['STUDENT'] } },
      { path: 'student/exam/:examId', component: StudentExamSubmitView, meta: { roles: ['STUDENT'] } },
      { path: 'student/results', component: StudentResultsView, meta: { roles: ['STUDENT'] } },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, from, next) => {
  const store = useAuthStore()
  if (to.meta.public) {
    next()
    return
  }
  if (!store.token) {
    next('/login')
    return
  }
  if (to.meta.roles && !to.meta.roles.includes(store.role)) {
    next('/')
    return
  }
  next()
})

export default router
