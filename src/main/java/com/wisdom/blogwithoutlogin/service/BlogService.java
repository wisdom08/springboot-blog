package com.wisdom.blogwithoutlogin.service;

import com.wisdom.blogwithoutlogin.model.Blog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {
    Blog saveBlog(Blog blog);

    List<Blog> getBlogs();

    Blog getBlogById(long id);

    Blog updateBlog(long id, Blog blog);

    void deleteBlogById(long id);
}
