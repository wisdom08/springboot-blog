package com.wisdom.blog.service;

import com.wisdom.blog.dto.article.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    ArticleResponseDetailDto saveArticle(ArticleSaveRequestDto articleSaveRequestDto);

    List<ArticleResponseDto> getArticles();

    ArticleResponseDetailDto getArticleById(long id);

    ArticleUpdateResponseDto updateArticle(long id, ArticleUpdateRequestDto articleUpdateRequestDto);

    void deleteArticleById(long id, String name);
}
