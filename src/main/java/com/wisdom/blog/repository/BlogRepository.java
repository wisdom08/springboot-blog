package com.wisdom.blog.repository;

import com.wisdom.blog.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository  extends JpaRepository<Blog, Long> {

}
