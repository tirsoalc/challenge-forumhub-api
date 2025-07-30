CREATE TABLE topics (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  message VARCHAR(1000) NOT NULL,
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL,
  status ENUM('ABERTO', 'SOLUCIONADO') DEFAULT 'ABERTO',
  author_id BIGINT NOT NULL,
  course_id BIGINT NOT NULL,

  CONSTRAINT fk_topics_author_id
    FOREIGN KEY (author_id) REFERENCES users(id),

  CONSTRAINT fk_topics_course_id
    FOREIGN KEY (course_id) REFERENCES courses(id),

  INDEX idx_topics_author_id (author_id),
  INDEX idx_topics_course_id (course_id),
  INDEX idx_topics_created_at (created_at)
);