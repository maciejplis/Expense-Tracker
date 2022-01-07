package matthias.expense_tracker.purchases.categories;


import matthias.expense_tracker.api.model.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
interface CategoriesMapper {

    CategoryDto toDto(CategoryEntity category);

    List<CategoryDto> toDto(List<CategoryEntity> categories);

    @Mapping(target = "id", ignore = true)
    CategoryEntity fromDto(CategoryDto categoryDto);
}
