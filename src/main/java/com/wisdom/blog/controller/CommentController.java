package com.wisdom.blog.controller;

import com.wisdom.blog.dto.CommentRequestDto;
import com.wisdom.blog.dto.CommentResponseDto;
import com.wisdom.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{blogId}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable(value = "blogId") long blogId,
                                                           @Valid @RequestBody CommentRequestDto commentRequestDto){
        commentRequestDto.setName(getCurrentUsername());
        return new ResponseEntity<>(commentService.createComment(blogId, commentRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{blogId}")
    public List<CommentResponseDto> getCommentsByPostId(@PathVariable(value = "blogId") Long postId, @PathVariable String blogId){
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/{blogId}/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable(value = "blogId") Long blogId,
                                                     @PathVariable(value = "id") Long commentId){
        CommentResponseDto commentDto = commentService.getCommentById(blogId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PutMapping("/{blogId}/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable(value = "blogId") Long blogId,
                                                    @PathVariable(value = "id") Long commentId,
                                                    @Valid @RequestBody CommentRequestDto commentRequestDto){
        commentRequestDto.setName(getCurrentUsername());
        CommentResponseDto updatedComment = commentService.updateComment(blogId, commentId, commentRequestDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{blogId}/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "blogId") Long blogId,
                                                @PathVariable(value = "id") Long commentId){
        commentService.deleteComment(blogId, commentId, getCurrentUsername());
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

    private static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername();
    }
}
