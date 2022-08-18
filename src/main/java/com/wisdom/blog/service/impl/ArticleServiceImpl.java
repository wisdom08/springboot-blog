package com.wisdom.blog.service.impl;

import com.wisdom.blog.dto.article.*;
import com.wisdom.blog.exception.ResourceNotFoundException;
import com.wisdom.blog.exception.UnauthorizedException;
import com.wisdom.blog.model.Article;
import com.wisdom.blog.repository.ArticleRepository;
import com.wisdom.blog.service.ArticleService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public ArticleResponseDetailDto saveArticle(ArticleSaveRequestDto articleSaveRequestDto) {
        Article savedArticle = articleRepository.save(articleSaveRequestDto.toEntity());
        return new ArticleResponseDetailDto(savedArticle.getName(), savedArticle.getTitle(), savedArticle.getContents(), savedArticle.getCreatedDate());
    }

    @Override
    public List<ArticleResponseDto> getArticles() {
        List<Article> articles = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
        return articles.stream().map(m -> new ArticleResponseDto(m.getName(), m.getTitle(), m.getContents(), m.getCreatedDate())).collect(Collectors.toList());
    }

    @Override
    public ArticleResponseDetailDto getArticleById(long id) {
        Article article = exists(id);
        return new ArticleResponseDetailDto(article.getName(), article.getTitle(), article.getContents(), article.getCreatedDate());
    }

    @Override
    public ArticleUpdateResponseDto updateArticle(long id, ArticleUpdateRequestDto articleUpdateRequestDto) {
        Article existingArticle = exists(id);
        Article article = articleUpdateRequestDto.toEntity();
        if (!(article.getName().equals(existingArticle.getName()))) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "작성자만 수정할 수 있습니다.");
        }
        existingArticle.setTitle(article.getTitle());
        existingArticle.setContents(article.getContents());
        articleRepository.save(existingArticle);
        return new ArticleUpdateResponseDto(existingArticle.getName(), existingArticle.getTitle(), existingArticle.getContents(), existingArticle.getModifiedDate());
    }

    @Override
    public void deleteArticleById(long id, String name) {
        Article existingArticle = exists(id);
        if (!(name.equals(existingArticle.getName()))) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "작성자만 삭제할 수 있습니다.");
        }
        articleRepository.deleteById(id);
    }

    private Article exists(long id) {
        return  articleRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Blog", "Id", id));
    }
}
