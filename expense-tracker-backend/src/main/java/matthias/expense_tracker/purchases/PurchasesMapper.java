package matthias.expense_tracker.purchases;

import matthias.expense_tracker.purchases.categories.CategoryEntity;
import matthias.expense_tracker.purchases.dtos.PurchaseDtoWrite;
import matthias.expense_tracker.purchases.dtos.PurchaseListDtoWrite;
import matthias.expense_tracker.purchases.shops.ShopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
interface PurchasesMapper {

    @Mapping(target = "category", source = "categoryId")
    @Mapping(target = "id", ignore = true)
    PurchaseEntity fromDtoWrite(PurchaseDtoWrite purchase);

    List<PurchaseEntity> fromDtoWrite(List<PurchaseDtoWrite> purchases);

    @Mapping(target = "shop", source = "shopId")
    @Mapping(target = "id", ignore = true)
    PurchaseListEntity fromDtoWrite(PurchaseListDtoWrite purchaseList);

    default CategoryEntity category(UUID id) {
        CategoryEntity category = new CategoryEntity();
        category.setId(id);
        return category;
    }

    default ShopEntity shop(UUID id) {
        ShopEntity shop = new ShopEntity();
        shop.setId(id);
        return shop;
    }
}
