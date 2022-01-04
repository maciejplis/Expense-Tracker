package matthias.expense_tracker.purchases.categories;

import lombok.RequiredArgsConstructor;
import matthias.expense_tracker.purchases.categories.dtos.CategoryDtoRead;
import matthias.expense_tracker.purchases.categories.dtos.CategoryDtoWrite;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class CategoriesService {

    private final CategoriesDAO categoriesDAO;
    private final CategoriesMapper categoriesMapper;

    List<CategoryDtoRead> getExpenseCategories() {
        return categoriesMapper.toDtoRead(categoriesDAO.findAll());
    }

    void addNewCategory(CategoryDtoWrite category) {
        categoriesDAO.save(categoriesMapper.fromDtoWrite(category));
    }
}
