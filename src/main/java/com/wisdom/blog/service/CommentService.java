
package com.wisdom.blog.service;

import com.wisdom.blog.dto.CommentRequestDto;
import com.wisdom.blog.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(long postId, CommentRequestDto commentRequestDto);

    List<CommentResponseDto> getCommentsByPostId(long postId);

    CommentResponseDto getCommentById(Long postId, Long commentId);

    CommentResponseDto updateComment(Long postId, long commentId, CommentRequestDto commentRequest);

    void deleteComment(Long postId, Long commentId, String currentUsername);
}
