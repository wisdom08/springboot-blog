
package com.wisdom.blog.service;

import com.wisdom.blog.dto.comment.CommentRequestDto;
import com.wisdom.blog.dto.comment.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(long postId, CommentRequestDto commentRequestDto);

    List<CommentResponseDto> getCommentsByArticleId(long postId);

    CommentResponseDto getCommentById(Long postId, Long commentId);

    CommentResponseDto updateComment(Long postId, long commentId, CommentRequestDto commentRequest);

    void deleteComment(Long postId, Long commentId, String currentUsername);
}
