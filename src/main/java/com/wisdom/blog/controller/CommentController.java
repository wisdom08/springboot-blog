package com.wisdom.blog.controller;

import com.wisdom.blog.dto.comment.CommentRequestDto;
import com.wisdom.blog.dto.comment.CommentResponseDto;
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

    @PostMapping("/{articleId}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable(value = "articleId") long articleId,
                                                           @Valid @RequestBody CommentRequestDto commentRequestDto){
        commentRequestDto.setName(getCurrentUsername());
        return new ResponseEntity<>(commentService.createComment(articleId, commentRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{articleId}")
    public List<CommentResponseDto> getCommentsByPostId(@PathVariable(value = "articleId") Long articleId){
        return commentService.getCommentsByArticleId(articleId);
    }

    @GetMapping("/{articleId}/{commentId}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable(value = "articleId") Long articleId,
                                                     @PathVariable(value = "commentId") Long commentId){
        CommentResponseDto commentDto = commentService.getCommentById(articleId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PutMapping("/{articleId}/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable(value = "articleId") Long articleId,
                                                    @PathVariable(value = "commentId") Long commentId,
                                                    @Valid @RequestBody CommentRequestDto commentRequestDto){
        commentRequestDto.setName(getCurrentUsername());
        CommentResponseDto updatedComment = commentService.updateComment(articleId, commentId, commentRequestDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{articleId}/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "articleId") Long articleId,
                                                @PathVariable(value = "commentId") Long commentId){
        commentService.deleteComment(articleId, commentId, getCurrentUsername());
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

    private static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername();
    }
}
