package com.wisdom.blogwithoutlogin.service.impl;

import com.wisdom.blogwithoutlogin.dto.*;
import com.wisdom.blogwithoutlogin.exception.ResourceNotFoundException;
import com.wisdom.blogwithoutlogin.model.Blog;
import com.wisdom.blogwithoutlogin.repository.BlogRepository;
import com.wisdom.blogwithoutlogin.service.BlogService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public BlogResponseDetailDto saveBlog(BlogSaveRequestDto blogSaveRequestDto) {
        Blog savedBlog = blogRepository.save(blogSaveRequestDto.toEntity());
        return new BlogResponseDetailDto(savedBlog.getName(), savedBlog.getTitle(), savedBlog.getContents(), savedBlog.getCreatedDate());
    }

    @Override
    public List<BlogResponseDto> getBlogs() {
        List<Blog> blogs = blogRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
        return blogs.stream().map(m -> new BlogResponseDto(m.getName(), m.getTitle(), m.getContents(), m.getCreatedDate())).collect(Collectors.toList());
    }

    @Override
    public BlogResponseDetailDto getBlogById(long id) {
        Blog blog = exists(id);
        return new BlogResponseDetailDto(blog.getName(), blog.getTitle(), blog.getContents(), blog.getCreatedDate());
    }

    @Override
    public BlogUpdateResponseDto updateBlog(long id, BlogUpdateRequestDto blogUpdateRequestDto) {

        Blog existingBlog = exists(id);

        String inputPassword = blogUpdateRequestDto.getPassword();
        boolean validatePassword = checkPassword(id, inputPassword);
        if (!validatePassword) {
            throw new RuntimeException("비밀번호를 다시 입력해주세요.");
        }

        Blog blog = blogUpdateRequestDto.toEntity();
        existingBlog.setTitle(blog.getTitle());
        existingBlog.setContents(blog.getContents());
        blogRepository.save(existingBlog);
        return new BlogUpdateResponseDto(existingBlog.getName(), existingBlog.getTitle(), existingBlog.getContents(), existingBlog.getModifiedDate());
    }

    @Override
    public void deleteBlogById(String password, long id) {
        boolean validatePassword = checkPassword(id, password);
        System.out.println("validatePassword = " + validatePassword);

        if (!validatePassword) {
            throw new RuntimeException("비밀번호를 다시 입력해주세요.");
        }
        blogRepository.deleteById(id);
    }

    @Override
    public boolean checkPassword(long id, String password) {
        Blog blog = exists(id);
        return password.equals(blog.getPassword());
    }

    private Blog exists(long id) {
        return  blogRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Blog", "Id", id));
    }
}
