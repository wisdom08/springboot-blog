package com.wisdom.blog.service.impl;

import com.wisdom.blog.dto.*;
import com.wisdom.blog.exception.ResourceNotFoundException;
import com.wisdom.blog.exception.WrongPasswordException;
import com.wisdom.blog.model.Blog;
import com.wisdom.blog.repository.BlogRepository;
import com.wisdom.blog.service.BlogService;
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
        if (!checkPassword(id, blogUpdateRequestDto.getPassword())) {
            throw new WrongPasswordException("password", false);
        }
        Blog blog = blogUpdateRequestDto.toEntity();
        existingBlog.setTitle(blog.getTitle());
        existingBlog.setContents(blog.getContents());
        blogRepository.save(existingBlog);
        return new BlogUpdateResponseDto(existingBlog.getName(), existingBlog.getTitle(), existingBlog.getContents(), existingBlog.getModifiedDate());
    }

    @Override
    public void deleteBlogById(String password, long id) {
        if (!checkPassword(id, password)) {
            throw new WrongPasswordException("password", false);
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
