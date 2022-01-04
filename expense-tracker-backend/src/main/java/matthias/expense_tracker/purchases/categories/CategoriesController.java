package matthias.expense_tracker.purchases.categories;

import lombok.RequiredArgsConstructor;
import matthias.expense_tracker.purchases.categories.dtos.CategoryDtoRead;
import matthias.expense_tracker.purchases.categories.dtos.CategoryDtoWrite;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
class CategoriesController {

    private final CategoriesService expensesService;

    @GetMapping
    public List<CategoryDtoRead> getExpenseCategories() {
        return expensesService.getExpenseCategories();
    }

    @PostMapping
    public void addNewCategory(@RequestBody CategoryDtoWrite expenseCategoryDto) {
        expensesService.addNewCategory(expenseCategoryDto);
    }
}


