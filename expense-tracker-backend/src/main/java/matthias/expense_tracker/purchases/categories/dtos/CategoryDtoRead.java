package matthias.expense_tracker.purchases.categories.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CategoryDtoRead {

    private UUID id;
    private String name;
}
