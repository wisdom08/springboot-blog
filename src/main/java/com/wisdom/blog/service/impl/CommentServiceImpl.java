package com.wisdom.blog.service.impl;

import com.wisdom.blog.dto.CommentRequestDto;
import com.wisdom.blog.dto.CommentResponseDto;
import com.wisdom.blog.exception.ForbiddenException;
import com.wisdom.blog.exception.ResourceNotFoundException;
import com.wisdom.blog.model.Blog;
import com.wisdom.blog.model.Comment;
import com.wisdom.blog.repository.BlogRepository;
import com.wisdom.blog.repository.CommentRepository;
import com.wisdom.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BlogRepository blogRepository) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
    }

    @Override
    public CommentResponseDto createComment(long blogId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRequestDto.toEntity();

        Blog blog = existsBlog(blogId);
        comment.setBlog(blog);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment.getBody(), savedComment.getName(), savedComment.getCreatedDate());
    }

    @Override
    public List<CommentResponseDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByBlogId(postId);
        return comments.stream().map(m -> new CommentResponseDto(m.getName(), m.getBody(), m.getCreatedDate())).collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto getCommentById(Long blogId, Long commentId) {
        Blog blog = existsBlog(blogId);
        Comment comment = existsComment(commentId);
        if(!comment.getBlog().getId().equals(blog.getId())){
            throw new ForbiddenException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        return new CommentResponseDto(comment.getName(), comment.getBody(), comment.getCreatedDate());
    }



    @Override
    public CommentResponseDto updateComment(Long blogId, long commentId, CommentRequestDto commentRequest) {
        Blog blog = existsBlog(blogId);
        Comment comment = existsComment(commentId);
        if(!comment.getBlog().getId().equals(blog.getId())){
            throw new ForbiddenException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        comment.setBody(commentRequest.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return new CommentResponseDto(updatedComment.getName(), updatedComment.getBody(), updatedComment.getCreatedDate());
    }

    @Override
    public void deleteComment(Long postId, Long commentId, String currentUsername) {
        Blog blog = existsBlog(postId);

        Comment comment = existsComment(commentId);

        if(!comment.getBlog().getId().equals(blog.getId())){
            throw new ForbiddenException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        commentRepository.delete(comment);
    }

    private Blog existsBlog(Long blogId) {
        return blogRepository.findById(blogId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", blogId));
    }

    private Comment existsComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));
    }
}
