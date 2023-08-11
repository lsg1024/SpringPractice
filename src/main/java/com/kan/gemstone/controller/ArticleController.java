package com.kan.gemstone.controller;

import com.kan.gemstone.DTO.ArticleForm;
import com.kan.gemstone.DTO.CommentDTO;
import com.kan.gemstone.entity.Article;
import com.kan.gemstone.repository.ArticleRepository;
import com.kan.gemstone.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@Slf4j // 로깅을 위한 롬복 어노테이션
public class ArticleController {
    private final ArticleRepository articleRepository;
    private final CommentService commentService;
    public ArticleController(ArticleRepository articleRepository, CommentService commentService) {
        this.articleRepository = articleRepository;
        this.commentService = commentService;

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

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String showId(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // id로 데이터 가져오기 -> 아이디 값이 없다면 null 값을 반환한다 그럼 option<> 형태는 무엇인지 찾기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDTO> commentDTOs = commentService.comments(id);


        // 가져온 데이터 모델 등록
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDTOs", commentDTOs);

        // 보여줄 페이지 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 모든 article을 가져와 묶어 전달
        List<Article> articleEntityList = articleRepository.findAll();

        // 가져온 article 묶음을 뷰로 전달
        model.addAttribute("articleList", articleEntityList);

        // 뷰 페이지 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터를 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터를 등록
        model.addAttribute("article", articleEntity);

        // 뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        // DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 엔티티를 DB로 저장한다
        // DB 기존 데이터 호출
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 기존 데이터에 값을 갱신한다
        if (target != null) {
            articleRepository.save(articleEntity); // 엔티티 DB로 갱신
        }

        // 수정 결과 페이지로 리다이렉트

        return "redirect:/articles/" + articleEntity.getId();
    }
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){

        // 삭제 대상 탐색
        Article target = articleRepository.findById(id).orElse(null);

        // 대상을 삭제
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제 완료");
        }

        // 삭제 완료 후 결과 처리 -> 리다이렉트

        return "redirect:/articles";
    }

}
