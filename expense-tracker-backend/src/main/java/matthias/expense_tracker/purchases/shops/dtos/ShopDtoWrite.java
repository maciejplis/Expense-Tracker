package matthias.expense_tracker.purchases.shops.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ShopDtoWrite {

    @NotBlank(message = "Shop name can't be blank")
    private String name;
}
