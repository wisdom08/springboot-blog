package com.wisdom.blog.controller;

import com.wisdom.blog.dto.*;
import com.wisdom.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
        return new ResponseEntity<>(blogService.saveBlog(blogSaveRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<BlogUpdateResponseDto> updateBlog(@PathVariable long id, @RequestBody @Valid BlogUpdateRequestDto blogSaveRequestDto) {
        return new ResponseEntity<>(blogService.updateBlog(id, blogSaveRequestDto), HttpStatus.OK);
    }

    @PostMapping("{id}")
    public boolean checkPassword(@RequestBody Map<String, String> passwordMap, @PathVariable long id) {
        return blogService.checkPassword(id, passwordMap.get("password"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBlogById(@RequestBody Map<String, String> passwordMap, @PathVariable long id) {
        blogService.deleteBlogById(passwordMap.get("password"), id);
        return new ResponseEntity<>(String.format("게시글 ID %d 번 게시글을 삭제했습니다.", id), HttpStatus.OK);
    }
}
