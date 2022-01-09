package matthias.expense_tracker.purchases.categories;

import lombok.RequiredArgsConstructor;
import matthias.expense_tracker.api.CategoriesApi;
import matthias.expense_tracker.api.model.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/purchases/categories")
class CategoriesController implements CategoriesApi {

    private final CategoriesService expensesService;

    @Override
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getPurchaseCategories() {
        return ResponseEntity.ok(expensesService.getPurchaseCategories());
    }

    @Override
    @PostMapping
    public ResponseEntity<CategoryDto> addPurchaseCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(expensesService.addPurchaseCategory(categoryDto));
    }
}


