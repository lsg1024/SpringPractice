package com.kan.gemstone.api;

import com.kan.gemstone.DTO.CommentDTO;
import com.kan.gemstone.annotation.RunningTime;
import com.kan.gemstone.entity.Comment;
import com.kan.gemstone.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {

    private final CommentService commentService;

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 목록 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDTO>> comments(@PathVariable Long articleId) {
        // 서비스를 통해
        List<CommentDTO> dtos = commentService.comments(articleId);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDTO> create(@PathVariable Long articleId,
                                             @RequestBody CommentDTO commentDTO) {

        CommentDTO createDTO = commentService.create(articleId, commentDTO);

        // 응답 결과
        return ResponseEntity.status(HttpStatus.OK).body(createDTO);
    }

    // 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable Long id,
                                             @RequestBody CommentDTO commentDTO) {

        CommentDTO updateDTO = commentService.update(id, commentDTO);

        // 응답 결과
        return ResponseEntity.status(HttpStatus.OK).body(updateDTO);
    }

    // 댓글 삭제
    @RunningTime
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDTO> delete(@PathVariable Long id) {

        CommentDTO deleteDTO = commentService.delete(id);

        // 응답 결과
        return ResponseEntity.status(HttpStatus.OK).body(deleteDTO);
    }
}
