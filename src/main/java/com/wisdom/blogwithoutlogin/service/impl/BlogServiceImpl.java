package com.wisdom.blogwithoutlogin.service.impl;

import com.wisdom.blogwithoutlogin.exception.ResourceNotFoundException;
import com.wisdom.blogwithoutlogin.model.Blog;
import com.wisdom.blogwithoutlogin.model.BlogSaveRequestDto;
import com.wisdom.blogwithoutlogin.model.BlogUpdateRequestDto;
import com.wisdom.blogwithoutlogin.repository.BlogRepository;
import com.wisdom.blogwithoutlogin.service.BlogService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public Blog saveBlog(BlogSaveRequestDto blogSaveRequestDto) {
        Blog blog = blogSaveRequestDto.toEntity();
        return blogRepository.save(blog);
    }

    @Override
    public List<Blog> getBlogs() {
        return blogRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
    }

    @Override
    public Blog getBlogById(long id) {
        return blogRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Blog", "Id", id));
    }

    @Override
    public Blog updateBlog(long id, BlogUpdateRequestDto blogSaveRequestDto) {
        Blog existingBlog = blogRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Blog", "Id", id));

        Blog blog = blogSaveRequestDto.toEntity();
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
