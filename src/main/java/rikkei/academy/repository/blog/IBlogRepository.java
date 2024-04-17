package rikkei.academy.repository.blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Blog;
import rikkei.academy.model.Category;

@Repository
public interface IBlogRepository extends PagingAndSortingRepository<Blog, Long> {
    @Query("SELECT b FROM Blog b JOIN b.categories c WHERE c = :category")
    Iterable<Blog> findAllByCategory(@Param("category") Category category);

    Page<Blog> findAllByTitleContaining(String title, Pageable pageable);
}
