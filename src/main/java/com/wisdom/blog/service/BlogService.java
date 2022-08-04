package com.wisdom.blog.service;

import com.wisdom.blog.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {
    BlogResponseDetailDto saveBlog(BlogSaveRequestDto blogSaveRequestDto);

    List<BlogResponseDto> getBlogs();

    BlogResponseDetailDto getBlogById(long id);

    BlogUpdateResponseDto updateBlog(long id, BlogUpdateRequestDto blogSaveRequestDto);

    void deleteBlogById(long id, String name);
}
