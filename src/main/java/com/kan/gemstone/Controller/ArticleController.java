package com.kan.gemstone.Controller;

import com.kan.gemstone.DTO.ArticleForm;
import com.kan.gemstone.Entitiy.Article;
import com.kan.gemstone.Repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@Slf4j // 로깅을 위한 롬복 어노테이션
public class ArticleController {
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {

        log.info(form.toString());    // println() 을 로깅으로 대체!
        Article article = form.toEntity();
        log.info(article.toString()); // println() 을 로깅으로 대체!
        Article saved = articleRepository.save(article);
        log.info(saved.toString());   // println() 을 로깅으로 대체!

        return "";
    }

    @GetMapping("/articles/{id}")
    public String showId(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // id로 데이터 가져오기 -> 아이디 값이 없다면 null 값을 반환한다 그럼 option<> 형태는 무엇인지 찾기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 가져온 데이터 모델 등록
        model.addAttribute("article", articleEntity);

        // 보여줄 페이지 설정
        return "articles/show";
    }

}
