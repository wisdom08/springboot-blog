package com.wisdom.blog.controller;

import com.wisdom.blog.dto.article.*;
import com.wisdom.blog.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public List<ArticleResponseDto> getArticles() {
        return articleService.getArticles();
    }

    @GetMapping("{articleId}")
    public ResponseEntity<ArticleResponseDetailDto> getArticleById(@PathVariable long articleId) {
        return new ResponseEntity<>(articleService.getArticleById(articleId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ArticleResponseDetailDto> saveArticle(@RequestBody @Valid ArticleSaveRequestDto articleSaveRequestDto) {
        articleSaveRequestDto.setName(getCurrentUsername());
        return new ResponseEntity<>(articleService.saveArticle(articleSaveRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("{articleId}")
    public ResponseEntity<ArticleUpdateResponseDto> updateArticle(@PathVariable long articleId, @RequestBody @Valid ArticleUpdateRequestDto blogSaveRequestDto) {
        blogSaveRequestDto.setName(getCurrentUsername());
        return new ResponseEntity<>(articleService.updateArticle(articleId, blogSaveRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("{articleId}")
    public ResponseEntity<String> deleteBlogById(@PathVariable long articleId) {
        articleService.deleteArticleById(articleId, getCurrentUsername());
        return new ResponseEntity<>(String.format("게시글 ID %d 번 게시글을 삭제했습니다.", articleId), HttpStatus.OK);
    }

    private static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails)principal).getUsername();
    }
}
