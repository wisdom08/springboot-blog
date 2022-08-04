package com.wisdom.blog.controller;

import com.wisdom.blog.dto.*;
import com.wisdom.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping()
    public List<BlogResponseDto> getBlogs() {
        return blogService.getBlogs();
    }

    @GetMapping("{id}")
    public ResponseEntity<BlogResponseDetailDto> getBlogById(@PathVariable long id) {
        return new ResponseEntity<>(blogService.getBlogById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BlogResponseDetailDto> saveBlog(@RequestBody @Valid BlogSaveRequestDto blogSaveRequestDto) {
        blogSaveRequestDto.setName(getCurrentUsername());
        return new ResponseEntity<>(blogService.saveBlog(blogSaveRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<BlogUpdateResponseDto> updateBlog(@PathVariable long id, @RequestBody @Valid BlogUpdateRequestDto blogSaveRequestDto) {
        blogSaveRequestDto.setName(getCurrentUsername());
        return new ResponseEntity<>(blogService.updateBlog(id, blogSaveRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBlogById(@PathVariable long id, Authentication authentication) {
        blogService.deleteBlogById(id, getCurrentUsername());
        return new ResponseEntity<>(String.format("게시글 ID %d 번 게시글을 삭제했습니다.", id), HttpStatus.OK);
    }

    private static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails)principal).getUsername();
    }
}
