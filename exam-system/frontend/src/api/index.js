import http from './http'

export function login(data) {
  return http.post('/auth/login', data)
}

export function getUsers() {
  return http.get('/users')
}

export function createUser(data) {
  return http.post('/users', data)
}

export function updateUser(id, data) {
  return http.put(`/users/${id}`, data)
}

export function resetPassword(id, data) {
  return http.put(`/users/${id}/password`, data)
}

export function deleteUser(id) {
  return http.delete(`/users/${id}`)
}

export function getCourses() {
  return http.get('/courses')
}

export function createCourse(data) {
  return http.post('/courses', data)
}

export function getQuestions() {
  return http.get('/questions')
}

export function createQuestion(data) {
  return http.post('/questions', data)
}

export function updateQuestion(id, data) {
  return http.put(`/questions/${id}`, data)
}

export function deleteQuestion(id) {
  return http.delete(`/questions/${id}`)
}

export function getPapers() {
  return http.get('/papers')
}

export function createPaper(data) {
  return http.post('/papers', data)
}

export function updatePaper(id, data) {
  return http.put(`/papers/${id}`, data)
}

export function deletePaper(id) {
  return http.delete(`/papers/${id}`)
}

export function getExams() {
  return http.get('/exams')
}

export function createExam(data) {
  return http.post('/exams', data)
}

export function getStudentExams() {
  return http.get('/student/exams')
}

export function submitExam(examId, data) {
  return http.post(`/student/exams/${examId}/submit`, data)
}

export function getStudentResults() {
  return http.get('/student/results')
}

export function getTeacherExamResults(examId) {
  return http.get(`/teacher/exams/${examId}/results`)
}
