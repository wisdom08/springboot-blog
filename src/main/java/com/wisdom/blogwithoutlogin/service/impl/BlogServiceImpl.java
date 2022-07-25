package com.wisdom.blogwithoutlogin.service.impl;

import com.wisdom.blogwithoutlogin.exception.ResourceNotFoundException;
import com.wisdom.blogwithoutlogin.model.Blog;
import com.wisdom.blogwithoutlogin.repository.BlogRepository;
import com.wisdom.blogwithoutlogin.service.BlogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public List<Blog> getBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Blog getBlogById(long id) {
        return blogRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Blog", "Id", id));
    }

    @Override
    public Blog updateBlog(long id, Blog blog) {
        Blog existingBlog = blogRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Blog", "Id", id));
        existingBlog.setTitle(blog.getTitle());
        existingBlog.setContents(blog.getContents());
        blogRepository.save(existingBlog);
        return existingBlog;
    }

    @Override
    public void deleteBlogById(long id) {
        blogRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Blog", "Id", id));
        blogRepository.deleteById(id);
    }
}
