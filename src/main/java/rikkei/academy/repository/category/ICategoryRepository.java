package rikkei.academy.repository.category;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Category;

@Repository
public interface ICategoryRepository extends PagingAndSortingRepository<Category, Long> {
}
