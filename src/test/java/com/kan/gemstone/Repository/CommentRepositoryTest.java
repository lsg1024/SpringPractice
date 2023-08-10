package com.kan.gemstone.Repository;

import com.kan.gemstone.entity.Article;
import com.kan.gemstone.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest // JPA와 연동 테스트
class CommentRepositoryTest {

    @Autowired CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {

        /* Case 1: 4번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 4L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(4L, "4번", "내용4");
            Comment a = new Comment(1L, article, "Park", "댓글1");
            Comment b = new Comment(2L, article, "Kim", "댓글2");
            Comment c = new Comment(3L, article, "Choi", "댓글3");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글 조회");

        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        // 'Park'의 모든 댓글 조회
        {
            // 입력 데이터를 준비
            String nickname = "Park";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상하기
            Comment a = new Comment(1L, new Article(4L, "4번", "내용4"), "Park", "댓글1");
            Comment b = new Comment(4L, new Article(5L, "5번", "내용5"), "Park", "댓글4");
            Comment c = new Comment(7L, new Article(6L, "6번", "내용6"), "Park", "댓글7");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 검증
            assertEquals(expected.toString(), comments.toString());

        }

        // Kim의 모든 댓글 조회
        {
            String nickname = "Kim";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상하기
            Comment a = new Comment(2L, new Article(4L, "4번", "내용4"), "Kim", "댓글2");
            Comment b = new Comment(5L, new Article(5L, "5번", "내용5"), "Kim", "댓글5");
            Comment c = new Comment(8L, new Article(6L, "6번", "내용6"), "Kim", "댓글8");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 검증
            assertEquals(expected.toString(), comments.toString());

        }

        // null의 모든 댓글 조회
        {
            String nickname = null;

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상하기
            List<Comment> expected = commentRepository.findAll();

            // 검증
            assertNotEquals(expected.toString(), comments.toString());

        }

        // **의 모든 댓글 조회
        {
            String nickname = "";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상하기
            List<Comment> expected = commentRepository.findAll();

            // 검증
            assertEquals(expected.toString(), comments.toString());
        }

        // "i"의 모든 댓글 조회
        {

        }
    }
}