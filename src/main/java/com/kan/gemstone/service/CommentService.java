package com.kan.gemstone.service;

import com.kan.gemstone.DTO.CommentDTO;
import com.kan.gemstone.repository.ArticleRepository;
import com.kan.gemstone.repository.CommentRepository;
import com.kan.gemstone.entity.Article;
import com.kan.gemstone.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    public List<CommentDTO> comments(Long articleId) {
//        // 조회
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//
//        // 반환 엔티티 -> DTO
//        List<CommentDTO> dtos = new ArrayList<CommentDTO>();
//        for (int i = 0; i < comments.size(); i++) {
//            Comment c = comments.get(i);
//            CommentDTO dto = CommentDTO.createCommentDTO(c);
//            dtos.add(dto);
//        }
//
//        // 반환
//        return dtos;

        // 스트림을 이용한 방식
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(CommentDTO::createCommentDTO)
                .collect(Collectors.toList());

    }

    @Transactional // DB에 접근하므로 문제가 생길시 롤백 처리가 가능하게 사용해줌
    public CommentDTO create(Long articleId, CommentDTO commentDTO) {

        // 게시글 조회 및 예외 처리
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다")); //예외 처리


        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(commentDTO, article);

        // 댓글 앤티티 DB 저장
        Comment created = commentRepository.save(comment);

        // DTO 값 반환
        return CommentDTO.createCommentDTO(created);
    }

    @Transactional
    public CommentDTO update(Long id, CommentDTO commentDTO) {

        // 업데이트시 발생하는 예외 처리
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없음"));

        // 댓글 수정
        target.patch(commentDTO);

        // DB로 갱신
        Comment updated = commentRepository.save(target);

        // 댓글 엔티티를 DTO 로 변환 및 반환
        return CommentDTO.createCommentDTO(updated);

    }

    @Transactional
    public CommentDTO delete(Long id) {
        // 댓글 조회 및 예외 처리
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다"));

        // 댓글 삭제
        commentRepository.delete(target);

        // 댓글 DTO 반환
        return CommentDTO.createCommentDTO(target);
    }
}
