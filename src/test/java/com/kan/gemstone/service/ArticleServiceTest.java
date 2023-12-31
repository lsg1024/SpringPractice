package com.kan.gemstone.service;

import com.kan.gemstone.DTO.ArticleForm;
import com.kan.gemstone.repository.ArticleRepository;
import com.kan.gemstone.entity.Article;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest // 해당 클래스는 스프링부트와 연동되어 테스팅된다
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @Order(5)
    void index() {
        // 예상
        Article a = new Article(1L, "1번", "내용1");
        Article b = new Article(2L, "2번", "내용2");
        Article c = new Article(3L, "3번", "내용3");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));

        // 실제
        List<Article> articles = articleService.index();

        // 비교 검증
        assertEquals(expected.toString(), articles.toString());

    }

    @Test
    @Order(4)
    void show_real() {
        // 예상
        Long id = 1L;
        Article expected = new Article(id, "1번", "내용1");

        // 실제
        Article article = articleService.show(id);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Order(2)
    void show_fake() {
        // 예상
        Long id = -1L;
        Article expected = null;

        // 실제
        Article article = articleService.show(id);

        // 비교
        assertEquals(expected, article);
    }

    @Test
    @Order(1)
    @Transactional
    void create_success() {
        // 예상
        String title = "4번";
        String content = "내용4";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        // 실제
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Order(3)
    void create_false() {
        // 예상
        String title = "4번";
        String content = "내용4";
        ArticleForm dto = new ArticleForm(4L, title, content);
        Article expected = null;

        // 실제
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected, article);
    }


    @Test
    void update_success() {

        // 예상
        Long id = 1L;
        String title = "1번 수정";
        String content = "내용1 수정";
        ArticleForm dto = new ArticleForm(id, title, content);

        // 실제
        Article updatedArticle = articleService.update(id, dto);

        // 비교
        assertEquals(title, updatedArticle.getTitle());
        assertEquals(content, updatedArticle.getContent());
    }

    @Test
    void update_false() {

        // 예상
        Long id = 1L;
        String title = "1번 수정";
        String content = "내용1 수정";
        ArticleForm dto = new ArticleForm(id, title, content);

        // 실제
        Article updatedArticle = articleService.update(id, dto);

        // 비교
        assertNotEquals("1번", updatedArticle.getTitle());
        assertNotEquals("내용1", updatedArticle.getContent());
    }

    @Test
    void delete() {

        // 예상
        Long id = 1L;
        String title = "1번";
        String content = "내용1";
        Article article = new Article(id, title, content);
        articleRepository.save(article);

        // 실제
        Article deletedArticle = articleService.delete(id);

        // 비교
        assertNotNull(deletedArticle); // 실제로 지워졌는지 확인
        assertEquals(id, deletedArticle.getId()); // 삭제된 id 값이 예상과 일치하는지
    }
}