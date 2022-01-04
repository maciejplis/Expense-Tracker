package matthias.expense_tracker.purchases.shops.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ShopDtoRead {

    private UUID id;
    private String name;
}
