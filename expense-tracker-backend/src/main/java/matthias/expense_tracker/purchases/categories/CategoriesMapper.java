package matthias.expense_tracker.purchases.categories;


import matthias.expense_tracker.purchases.categories.dtos.CategoryDtoRead;
import matthias.expense_tracker.purchases.categories.dtos.CategoryDtoWrite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
interface CategoriesMapper {

    CategoryDtoRead toDtoRead(CategoryEntity purchaseCategory);

    List<CategoryDtoRead> toDtoRead(List<CategoryEntity> purchaseCategory);

    @Mapping(target = "id", ignore = true)
    CategoryEntity fromDtoWrite(CategoryDtoWrite expenseCategoryDto);
}
