INSERT INTO article(id, title, content) VALUES (1, '1번', '내용1');
INSERT INTO article(id, title, content) VALUES (2, '2번', '내용2');
INSERT INTO article(id, title, content) VALUES (3, '3번', '내용3');

-- article 더미 데이터
INSERT INTO article(id, title, content) VALUES (4, '4번', '내용4');
INSERT INTO article(id, title, content) VALUES (5, '5번', '내용5');
INSERT INTO article(id, title, content) VALUES (6, '6번', '내용6');

-- comment 4 더미 데이터
INSERT INTO comment(id, article_id, nickname, body) VALUES (1, 4, 'Park', '댓글1');
INSERT INTO comment(id, article_id, nickname, body) VALUES (2, 4, 'Kim', '댓글2');
INSERT INTO comment(id, article_id, nickname, body) VALUES (3, 4, 'Choi', '댓글3');

-- comment 5 더미 데이터
INSERT INTO comment(id, article_id, nickname, body) VALUES (4, 5, 'Park', '댓글4');
INSERT INTO comment(id, article_id, nickname, body) VALUES (5, 5, 'Kim', '댓글5');
INSERT INTO comment(id, article_id, nickname, body) VALUES (6, 5, 'Choi', '댓글6');

-- comment 6 더미 데이터
INSERT INTO comment(id, article_id, nickname, body) VALUES (7, 6, 'Park', '댓글7');
INSERT INTO comment(id, article_id, nickname, body) VALUES (8, 6, 'Kim', '댓글8');
INSERT INTO comment(id, article_id, nickname, body) VALUES (9, 6, 'Choi', '댓글9');