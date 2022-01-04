package matthias.expense_tracker.purchases.dtos;

import lombok.Getter;
import lombok.Setter;
import matthias.expense_tracker.purchases.PurchaseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PurchaseListDtoWrite {

    private LocalDate date;
    private UUID shopId;

    private List<PurchaseEntity> purchases;
}
