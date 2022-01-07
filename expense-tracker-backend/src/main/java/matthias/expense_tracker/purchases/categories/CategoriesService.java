package matthias.expense_tracker.purchases.categories;

import lombok.RequiredArgsConstructor;
import matthias.expense_tracker.api.model.CategoryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class CategoriesService {

    private final CategoriesDAO categoriesDAO;
    private final CategoriesMapper categoriesMapper;

    List<CategoryDto> getPurchaseCategories() {
        return categoriesMapper.toDto(categoriesDAO.findAll());
    }

    @Transactional
    public CategoryDto addPurchaseCategory(CategoryDto categoryDto) {
        CategoryEntity savedCategory = categoriesDAO.save(categoriesMapper.fromDto(categoryDto));
        return categoriesMapper.toDto(savedCategory);
    }
}
