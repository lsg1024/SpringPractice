package com.kan.gemstone.service;

import com.kan.gemstone.DTO.ArticleForm;
import com.kan.gemstone.entity.Article;
import com.kan.gemstone.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service // 서비스 객체 선언
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article save(Article article) {
        return articleRepository.save(article);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if (article.getId() != null){
            return null;
        }
        return articleRepository.save(article);
    }


    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();

        Article target = articleRepository.findById(id).orElse(null);

        if (target == null || id != article.getId()) {
            return null;
        }

        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);

        if (target == null){
            return null;
        }

        articleRepository.delete(target);
        return target;
    }

    @Transactional // 해당 메소드를 트랜잭션으로 묶는다! -> 해당 코드 실행 이전 상태로 되돌림
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto 묶음을 entity 묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(ArticleForm::toEntity)
                .collect(Collectors.toList());

        // entity 묶음 DB로 저장
        articleList.stream()
                .forEach(articleRepository::save);

//        articleRepository.saveAll(articleList);

        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패")
        );

        return articleList;
    }
}
