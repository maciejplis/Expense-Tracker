package matthias.expense_tracker.purchases.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PurchaseDtoWrite {

    private String name;
    private String description;

    private float price;
    private float amount;

    private UUID categoryId;
}
