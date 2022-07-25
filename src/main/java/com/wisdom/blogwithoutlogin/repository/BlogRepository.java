package com.wisdom.blogwithoutlogin.repository;

import com.wisdom.blogwithoutlogin.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository  extends JpaRepository<Blog, Long> {

}
