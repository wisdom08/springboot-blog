package com.wisdom.blog.repository;

import com.wisdom.blog.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
