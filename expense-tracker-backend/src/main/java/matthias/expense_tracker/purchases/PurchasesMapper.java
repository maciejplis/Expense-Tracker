package matthias.expense_tracker.purchases;

import matthias.expense_tracker.api.model.PurchaseDto;
import matthias.expense_tracker.api.model.PurchaseGroupDto;
import matthias.expense_tracker.purchases.categories.CategoryEntity;
import matthias.expense_tracker.purchases.shops.ShopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper
interface PurchasesMapper {

    @Mapping(target = "id", ignore = true)
    PurchaseEntity fromDto(PurchaseDto purchaseDto);

    List<PurchaseEntity> fromDto(List<PurchaseDto> purchaseDtos);

    PurchaseDto toDto(PurchaseEntity purchase);

    List<PurchaseDto> toDto(List<PurchaseEntity> purchases);

    @Mapping(target = "id", ignore = true)
    PurchaseGroupEntity fromDto(PurchaseGroupDto purchaseGroupDto);

    PurchaseGroupDto toDto(PurchaseGroupEntity purchaseGroup);
}
