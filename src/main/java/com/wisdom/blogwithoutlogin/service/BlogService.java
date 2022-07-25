package com.wisdom.blogwithoutlogin.service;

import com.wisdom.blogwithoutlogin.model.Blog;
import com.wisdom.blogwithoutlogin.model.BlogSaveRequestDto;
import com.wisdom.blogwithoutlogin.model.BlogUpdateRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {
    Blog saveBlog(BlogSaveRequestDto blogSaveRequestDto);

    List<Blog> getBlogs();

    Blog getBlogById(long id);

    Blog updateBlog(long id, BlogUpdateRequestDto blogSaveRequestDto);

    void deleteBlogById(long id);
}
