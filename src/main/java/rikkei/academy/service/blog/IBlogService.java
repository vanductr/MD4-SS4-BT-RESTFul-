package rikkei.academy.service.blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rikkei.academy.model.Blog;
import rikkei.academy.model.Category;
import rikkei.academy.service.IGenerateService;

public interface IBlogService extends IGenerateService<Blog> {
    Iterable<Blog> findAllByCategory(Category category);

    Page<Blog> findAll(Pageable pageable);

    Page<Blog> findAllByTitleContaining(String title, Pageable pageable);
}
