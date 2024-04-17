package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.model.Blog;
import rikkei.academy.model.Category;
import rikkei.academy.service.blog.IBlogService;
import rikkei.academy.service.category.ICategoryService;

import java.util.Optional;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IBlogService blogService;

    @GetMapping("/list")
    public String list(Model model) {
        Iterable<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "/category/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("category", new Category());
        return "/category/add";
    }

    @PostMapping("/save")
    public String doAdd(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/category/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        model.addAttribute("category", category.get());
        return "/category/edit";
    }

    @PostMapping("/doEdit")
    public String doEdit(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/category/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        model.addAttribute("category", category.get());
        return "/category/delete";
    }

    @PostMapping("/delete/{id}")
    public String doDelete(@PathVariable Long id) {
        categoryService.remove(id);
        return "redirect:/category/list";
    }

//    @GetMapping("/view-province/{id}")
//    public ModelAndView viewProvince(@PathVariable("id") Long id){
//        Optional<Province> provinceOptional = provinceService.findById(id);
//        if(!provinceOptional.isPresent()){
//            return new ModelAndView("/error.404");
//        }
//
//        Iterable<Customer> customers = customerService.findAllByProvince(provinceOptional.get());
//
//        ModelAndView modelAndView = new ModelAndView("/province/view");
//        modelAndView.addObject("province", provinceOptional.get());
//        modelAndView.addObject("customers", customers);
//        return modelAndView;
//    }

    @GetMapping("/view-category/{id}")
    public String viewCategory(@PathVariable Long id, Model model) {
        Optional<Category> categoryOptional = categoryService.findById(id);

        Iterable<Blog> blogs = blogService.findAllByCategory(categoryOptional.get());

        model.addAttribute("blogs", blogs);
        model.addAttribute("category", categoryOptional.get());
        return "/category/view";
    }
}
