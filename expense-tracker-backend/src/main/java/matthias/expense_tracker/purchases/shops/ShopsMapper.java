package matthias.expense_tracker.purchases.shops;

import matthias.expense_tracker.purchases.shops.dtos.ShopDtoRead;
import matthias.expense_tracker.purchases.shops.dtos.ShopDtoWrite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
interface ShopsMapper {

    ShopDtoRead toDtoRead(ShopEntity expenseShop);

    List<ShopDtoRead> toDtoRead(List<ShopEntity> expenseShop);

    @Mapping(target = "id", ignore = true)
    ShopEntity fromDtoWrite(ShopDtoWrite expenseShop);


}
