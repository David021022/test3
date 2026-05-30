# GitHub Actions 自动构建并上传产物

## 这个流程做什么
- 触发时机：
  - 推送到 `main` 分支自动触发
  - 你也可以在 GitHub 页面手动触发（`workflow_dispatch`）
- 执行内容：
  - 后端（Java 17 + Spring Boot）打包 `jar`
  - 前端（Node 20 + Vue3）构建 `dist`
  - 将二者上传为 Actions Artifacts

## 你怎么用
1. 先把本地代码推到 GitHub：
   - `git add .`
   - `git commit -m "ci: add github actions build upload"`
   - `git push origin main`
2. 打开仓库页面 -> `Actions` -> 选择 `Build And Upload`。
3. 进入某次运行记录，下方 `Artifacts` 下载：
   - `backend-jar`
   - `frontend-dist`

## 常见问题
- 如果没有自动触发：
  - 确认你推送的是 `main` 分支。
- 如果构建失败：
  - 打开失败步骤查看日志，按报错修复后再次 push。

