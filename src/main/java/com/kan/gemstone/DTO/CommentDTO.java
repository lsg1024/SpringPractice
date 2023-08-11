package com.kan.gemstone.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kan.gemstone.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDTO {

    private Long id;

    @JsonProperty("article_id")
    private Long articleId;
    private String nickname;
    private String body;


    public static CommentDTO createCommentDTO(Comment comment) {

        return new CommentDTO(
                comment.getId(),
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getBody()
        );

    }
}
