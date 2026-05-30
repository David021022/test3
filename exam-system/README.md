# 在线考试系统（MVP）

## 1. 技术栈
- 后端：Spring Boot 3.3 + Spring Security 6 + JPA + MySQL + JWT
- 前端：Vue3 + Vite + Element Plus + Pinia + Vue Router

## 2. 目录
- `backend`：后端工程（IDEA 打开）
- `frontend`：前端工程（VS Code 打开）

## 3. 本地启动前准备
1. 启动 MySQL 8（默认账号密码先按 `root/root`）
2. 修改后端配置文件：`backend/src/main/resources/application.yml`
- `spring.datasource.username`
- `spring.datasource.password`

## 4. 启动后端（IDEA 或命令行）
```bash
cd backend
./mvnw clean package -DskipTests
java -jar target/exam-system-0.0.1-SNAPSHOT.jar
```

验证健康检查：
- `GET http://localhost:8080/api/health`

说明：
- 首次启动会自动执行 `schema.sql` 创建库表
- 会自动创建/修复演示账号（密码统一 `123456`）

## 5. 启动前端
```bash
cd frontend
npm install
npm run dev
```

访问：
- `http://localhost:5173`

## 6. 演示账号
- 管理员：`admin / 123456`
- 教师：`teacher1 / 123456`
- 学生：`student1 / 123456`

## 7. 核心 API
- `POST /api/auth/login`
- `GET/POST /api/users`
- `GET/POST /api/questions`
- `GET/POST /api/papers`
- `GET/POST /api/exams`
- `GET /api/student/exams`
- `POST /api/student/exams/{examId}/submit`
- `GET /api/student/results`
- `GET /api/teacher/exams/{examId}/results`

统一返回：
```json
{ "code": 0, "message": "ok", "data": {} }
```

统一认证：
- Header: `Authorization: Bearer <token>`

## 8. 推荐演示流程
1. 用 `admin` 登录，创建课程
2. 用 `teacher1` 登录，创建题目
3. 创建试卷并加入题目
4. 发布考试（开始时间设为当前之前1分钟，结束时间设为未来）
5. 用 `student1` 登录，在“我的考试”提交答案
6. 查看学生成绩与教师成绩查询页面

## 9. 打包
后端：
```bash
cd backend
./mvnw clean package -DskipTests
```

前端：
```bash
cd frontend
npm run build
```
