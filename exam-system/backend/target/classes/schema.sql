-- online_exam schema for MVP
CREATE DATABASE IF NOT EXISTS online_exam DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE online_exam;

CREATE TABLE IF NOT EXISTS sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL,
  status TINYINT(1) NOT NULL,
  created_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS course (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS question (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  course_id BIGINT NOT NULL,
  title VARCHAR(1000) NOT NULL,
  type VARCHAR(20) NOT NULL,
  answer VARCHAR(500) NOT NULL,
  analysis VARCHAR(1000),
  CONSTRAINT fk_question_course FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE IF NOT EXISTS question_option (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  question_id BIGINT NOT NULL,
  option_key VARCHAR(5) NOT NULL,
  content VARCHAR(1000) NOT NULL,
  is_correct TINYINT(1) NOT NULL,
  CONSTRAINT fk_option_question FOREIGN KEY (question_id) REFERENCES question(id)
);

CREATE TABLE IF NOT EXISTS paper (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  course_id BIGINT NOT NULL,
  description VARCHAR(500),
  CONSTRAINT fk_paper_course FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE IF NOT EXISTS paper_question (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  paper_id BIGINT NOT NULL,
  question_id BIGINT NOT NULL,
  score INT NOT NULL,
  sort_order INT NOT NULL,
  CONSTRAINT fk_pq_paper FOREIGN KEY (paper_id) REFERENCES paper(id),
  CONSTRAINT fk_pq_question FOREIGN KEY (question_id) REFERENCES question(id)
);

CREATE TABLE IF NOT EXISTS exam (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  course_id BIGINT NOT NULL,
  paper_id BIGINT NOT NULL,
  start_time DATETIME NOT NULL,
  end_time DATETIME NOT NULL,
  CONSTRAINT fk_exam_course FOREIGN KEY (course_id) REFERENCES course(id),
  CONSTRAINT fk_exam_paper FOREIGN KEY (paper_id) REFERENCES paper(id)
);

CREATE TABLE IF NOT EXISTS exam_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  exam_id BIGINT NOT NULL,
  student_id BIGINT NOT NULL,
  started_at DATETIME NOT NULL,
  submitted_at DATETIME NULL,
  status VARCHAR(20) NOT NULL,
  UNIQUE KEY uk_exam_student (exam_id, student_id),
  CONSTRAINT fk_record_exam FOREIGN KEY (exam_id) REFERENCES exam(id),
  CONSTRAINT fk_record_user FOREIGN KEY (student_id) REFERENCES sys_user(id)
);

CREATE TABLE IF NOT EXISTS exam_answer (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_id BIGINT NOT NULL,
  question_id BIGINT NOT NULL,
  answer_content VARCHAR(500) NOT NULL,
  CONSTRAINT fk_answer_record FOREIGN KEY (record_id) REFERENCES exam_record(id),
  CONSTRAINT fk_answer_question FOREIGN KEY (question_id) REFERENCES question(id)
);

CREATE TABLE IF NOT EXISTS exam_result (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_id BIGINT NOT NULL UNIQUE,
  total_score INT NOT NULL,
  submitted_at DATETIME NOT NULL,
  CONSTRAINT fk_result_record FOREIGN KEY (record_id) REFERENCES exam_record(id)
);

-- default demo users, password plaintext all: 123456
-- bcrypt hash for 123456:
-- $2a$10$7EqJtq98hPqEX7fNZaFWoO4U4r4mN5nN0qJxL6YQ0Q5Xh6N9nXl9K
INSERT INTO sys_user (username, password, role, status, created_at)
SELECT 'admin', '$2a$10$7EqJtq98hPqEX7fNZaFWoO4U4r4mN5nN0qJxL6YQ0Q5Xh6N9nXl9K', 'ADMIN', 1, NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE username = 'admin');

INSERT INTO sys_user (username, password, role, status, created_at)
SELECT 'teacher1', '$2a$10$7EqJtq98hPqEX7fNZaFWoO4U4r4mN5nN0qJxL6YQ0Q5Xh6N9nXl9K', 'TEACHER', 1, NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE username = 'teacher1');

INSERT INTO sys_user (username, password, role, status, created_at)
SELECT 'student1', '$2a$10$7EqJtq98hPqEX7fNZaFWoO4U4r4mN5nN0qJxL6YQ0Q5Xh6N9nXl9K', 'STUDENT', 1, NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE username = 'student1');
