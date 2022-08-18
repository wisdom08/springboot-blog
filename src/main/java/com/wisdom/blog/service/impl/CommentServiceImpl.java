package com.wisdom.blog.service.impl;

import com.wisdom.blog.dto.comment.CommentRequestDto;
import com.wisdom.blog.dto.comment.CommentResponseDto;
import com.wisdom.blog.exception.ForbiddenException;
import com.wisdom.blog.exception.ResourceNotFoundException;
import com.wisdom.blog.model.Article;
import com.wisdom.blog.model.Comment;
import com.wisdom.blog.repository.ArticleRepository;
import com.wisdom.blog.repository.CommentRepository;
import com.wisdom.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentServiceImpl(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public CommentResponseDto createComment(long articleId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRequestDto.toEntity();

        Article article = existsArticle(articleId);
        comment.setArticle(article);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment.getName(), savedComment.getBody(), savedComment.getCreatedDate());
    }

    @Override
    public List<CommentResponseDto> getCommentsByArticleId(long postId) {
        List<Comment> comments = commentRepository.findByArticleId(postId);
        return comments.stream().map(m -> new CommentResponseDto(m.getName(), m.getBody(), m.getCreatedDate())).collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto getCommentById(Long articleId, Long commentId) {
        Article article = existsArticle(articleId);
        Comment comment = existsComment(commentId);
        if(!comment.getArticle().getId().equals(article.getId())){
            throw new ForbiddenException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        return new CommentResponseDto(comment.getName(), comment.getBody(), comment.getCreatedDate());
    }



    @Override
    public CommentResponseDto updateComment(Long articleId, long commentId, CommentRequestDto commentRequest) {
        Article article = existsArticle(articleId);
        Comment comment = existsComment(commentId);
        if(!comment.getArticle().getId().equals(article.getId())){
            throw new ForbiddenException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        comment.setBody(commentRequest.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return new CommentResponseDto(updatedComment.getName(), updatedComment.getBody(), updatedComment.getCreatedDate());
    }

    @Override
    public void deleteComment(Long articleId, Long commentId, String currentUsername) {
        Article article = existsArticle(articleId);

        Comment comment = existsComment(commentId);

        if(!comment.getArticle().getId().equals(article.getId())){
            throw new ForbiddenException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        commentRepository.delete(comment);
    }

    private Article existsArticle(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(
                () -> new ResourceNotFoundException("Article", "id", articleId));
    }

    private Comment existsComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));
    }
}
