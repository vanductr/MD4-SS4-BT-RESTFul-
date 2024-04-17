package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.model.Blog;
import rikkei.academy.model.Category;
import rikkei.academy.service.blog.IBlogService;
import rikkei.academy.service.category.ICategoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/social-network")
public class RESTFulAPIController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IBlogService blogService;

    // Xem Danh sách Các Category
    @GetMapping("/category-list")
    public ResponseEntity<Iterable<Category>> showCategoryList() {
        List<Category> categories = (List<Category>) categoryService.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // Xem Danh sách Các Bài viết
    @GetMapping("/blog-list")
    public ResponseEntity<Iterable<Blog>> showBlogList() {
        List<Blog> blogs = (List<Blog>) blogService.findAll();
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    // Xem Danh sách Các bài viết của 1 Category
    @GetMapping("/blog-list-of-category/{id}")
    public ResponseEntity<Iterable<Blog>> showBlogListOfCategory(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        List<Blog> blogs = (List<Blog>) blogService.findAllByCategory(categoryOptional.get());
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    // Xem chi tiết một bài viết
    @GetMapping("/blog-detail/{id}")
    public ResponseEntity<Blog> showBlogDetail(@PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        if (!blog.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blog.get(), HttpStatus.OK);
    }
}
