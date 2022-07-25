package com.wisdom.blogwithoutlogin.service;

import com.wisdom.blogwithoutlogin.dto.BlogResponseDetailDto;
import com.wisdom.blogwithoutlogin.dto.BlogResponseDto;
import com.wisdom.blogwithoutlogin.model.Blog;
import com.wisdom.blogwithoutlogin.dto.BlogSaveRequestDto;
import com.wisdom.blogwithoutlogin.dto.BlogUpdateRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {
    Blog saveBlog(BlogSaveRequestDto blogSaveRequestDto);

    List<BlogResponseDto> getBlogs();

    BlogResponseDetailDto getBlogById(long id);

    Blog updateBlog(long id, BlogUpdateRequestDto blogSaveRequestDto);

    void deleteBlogById(long id);
}
