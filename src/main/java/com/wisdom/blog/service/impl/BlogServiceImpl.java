package com.wisdom.blog.service.impl;

import com.wisdom.blog.dto.*;
import com.wisdom.blog.exception.ResourceNotFoundException;
import com.wisdom.blog.exception.UnauthorizedException;
import com.wisdom.blog.model.Blog;
import com.wisdom.blog.repository.BlogRepository;
import com.wisdom.blog.service.BlogService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
        Blog blog = blogUpdateRequestDto.toEntity();
        if (!(blog.getName().equals(existingBlog.getName()))) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "작성자만 수정할 수 있습니다.");
        }
        existingBlog.setTitle(blog.getTitle());
        existingBlog.setContents(blog.getContents());
        blogRepository.save(existingBlog);
        return new BlogUpdateResponseDto(existingBlog.getName(), existingBlog.getTitle(), existingBlog.getContents(), existingBlog.getModifiedDate());
    }

    @Override
    public void deleteBlogById(long id, String name) {
        Blog existingBlog = exists(id);
        if (!(name.equals(existingBlog.getName()))) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "작성자만 삭제할 수 있습니다.");
        }
        blogRepository.deleteById(id);
    }

    private Blog exists(long id) {
        return  blogRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Blog", "Id", id));
    }
}
