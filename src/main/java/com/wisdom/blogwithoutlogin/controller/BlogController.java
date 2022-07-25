package com.wisdom.blogwithoutlogin.controller;

import com.wisdom.blogwithoutlogin.model.Blog;
import com.wisdom.blogwithoutlogin.model.BlogSaveRequestDto;
import com.wisdom.blogwithoutlogin.model.BlogUpdateRequestDto;
import com.wisdom.blogwithoutlogin.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Blog> getBlogs() {
        return blogService.getBlogs();
    }

    @GetMapping("{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable long id) {
        return new ResponseEntity<>(blogService.getBlogById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Blog> saveBlog(@RequestBody @Valid BlogSaveRequestDto blogSaveRequestDto) {
        return new ResponseEntity<>(blogService.saveBlog(blogSaveRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable long id, @RequestBody @Valid BlogUpdateRequestDto blogSaveRequestDto) {
        return new ResponseEntity<>(blogService.updateBlog(id, blogSaveRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBlogById(@PathVariable long id) {
        blogService.deleteBlogById(id);
        return new ResponseEntity<>(String.format("게시글 ID %d 번 게시글을 삭제했습니다.", id), HttpStatus.OK);
    }
}
