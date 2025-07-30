CREATE TABLE replies (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  message VARCHAR(1000) NOT NULL,
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL,
  author_id BIGINT NOT NULL,
  topic_id BIGINT NOT NULL,

  CONSTRAINT fk_replies_author_id
    FOREIGN KEY (author_id) REFERENCES users(id),

  CONSTRAINT fk_replies_topic_id
    FOREIGN KEY (topic_id) REFERENCES topics(id),

  INDEX idx_replies_author_id (author_id),
  INDEX idx_replies_topic_id (topic_id),
  INDEX idx_replies_created_at (created_at)
);