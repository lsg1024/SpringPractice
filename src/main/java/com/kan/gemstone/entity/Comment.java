package com.kan.gemstone.entity;

import com.kan.gemstone.DTO.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 해당 댓글 엔티티 여러개가 하나의 Article에 연관된다!
    @JoinColumn(name = "article_id") // articleid 컬럼에 Article의 대표값을 저장!
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDTO commentDTO, Article article) {

        // 예외 처리
        if (commentDTO.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글 id 값이 없습니다");

        if (commentDTO.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다");

        // 엔티티 생성 및 반환
        return new Comment(
                commentDTO.getId(),
                article,
                commentDTO.getNickname(),
                commentDTO.getBody()
        );

    }

    public void patch(CommentDTO commentDTO) {
        // 예외 발생
        if (this.id != commentDTO.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 ID가 입력됨");

        // 객체를 갱신
        if (commentDTO.getNickname() != null)
            this.nickname = commentDTO.getNickname();

        if (commentDTO.getBody() != null)
            this.body = commentDTO.getBody();
    }
}
