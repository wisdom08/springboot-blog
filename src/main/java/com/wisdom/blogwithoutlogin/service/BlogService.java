package com.wisdom.blogwithoutlogin.service;

import com.wisdom.blogwithoutlogin.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {
    BlogResponseDetailDto saveBlog(BlogSaveRequestDto blogSaveRequestDto);

    List<BlogResponseDto> getBlogs();

    BlogResponseDetailDto getBlogById(long id);

    BlogUpdateResponseDto updateBlog(long id, BlogUpdateRequestDto blogSaveRequestDto);

    void deleteBlogById(String password, long id);

    boolean checkPassword(long id, String password);
}
