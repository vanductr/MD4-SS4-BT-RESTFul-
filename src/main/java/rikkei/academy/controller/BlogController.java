package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rikkei.academy.model.Blog;
import rikkei.academy.model.Category;
import rikkei.academy.service.blog.IBlogService;
import rikkei.academy.service.category.ICategoryService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

//    @GetMapping(value = {"/", "/blog"})
//    public ModelAndView home(@RequestParam("search") Optional<String> search, Pageable pageable) {
//        Page<Blog> blogs;
//        if (search.isPresent()) {
//            blogs = blogService.findAllByTitleContaining(search.get(), pageable);
//        } else {
//            blogs = blogService.findAll(pageable);
//        }
//        ModelAndView modelAndView = new ModelAndView("/blog/home");
//        modelAndView.addObject("blogs", blogs);
//        return modelAndView;
//    }

    @GetMapping(value = {"/", "/blog"})
    public ModelAndView home(@RequestParam("search") Optional<String> search, Pageable pageable, @RequestParam("sort") Optional<String> sort) {
        Page<Blog> blogs;
        Sort blogSort = Sort.by("createdDate").descending(); // Sắp xếp theo thời gian tạo giảm dần mặc định
        if (sort.isPresent()) {
            if (sort.get().equals("asc")) {
                blogSort = Sort.by("createdDate").ascending();
            }
        }
        if (search.isPresent()) {
            blogs = blogService.findAllByTitleContaining(search.get(), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), blogSort));
        } else {
            blogs = blogService.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), blogSort));
        }
        ModelAndView modelAndView = new ModelAndView("/blog/home");
        modelAndView.addObject("searchValue", search.orElse(""));
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }


    @GetMapping("/create-blog")
    public ModelAndView showFormCreate() {
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("blog", new Blog());
        return modelAndView;
    }
    @PostMapping("/create-blog")
    public ModelAndView create(@ModelAttribute("blog") Blog blog) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        blog.setCreatedDate(new Date());
        blogService.save(blog);
        return modelAndView;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/blog/detail");
        Optional<Blog> blog = blogService.findById(id);
        modelAndView.addObject("blog", blog.get());
        return modelAndView;
    }

    @GetMapping("edit/{id}")
    public ModelAndView showFormEdit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/blog/edit");
        Optional<Blog> blog1 = blogService.findById(id);
        Blog blog = blog1.get();
        Date date = blog.getCreatedDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateString = formatter.format(date);
        modelAndView.addObject("blog", blog);
        modelAndView.addObject("date", dateString);
        return modelAndView;
    }
    @PostMapping("edit")
    public ModelAndView edit(@ModelAttribute("blog") Blog blog, @ModelAttribute("createdDate") String date) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date date2 = formatter.parse(date);
            blog.setCreatedDate(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        blogService.save(blog);
        return modelAndView;
    }

    @GetMapping("delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/blog/delete");
        Optional<Blog> blog = blogService.findById(id);
        modelAndView.addObject("blog", blog.get());
        return modelAndView;
    }

    @PostMapping("delete")
    public ModelAndView delete(@ModelAttribute("blog") Blog blog) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        blogService.remove(blog.getId());
        return modelAndView;
    }
}
