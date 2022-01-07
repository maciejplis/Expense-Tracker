package matthias.expense_tracker.purchases.shops;

import matthias.expense_tracker.api.model.ShopDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
interface ShopsMapper {

    ShopDto toDto(ShopEntity shop);

    List<ShopDto> toDto(List<ShopEntity> shops);

    @Mapping(target = "id", ignore = true)
    ShopEntity fromDto(ShopDto shopDto);
}
