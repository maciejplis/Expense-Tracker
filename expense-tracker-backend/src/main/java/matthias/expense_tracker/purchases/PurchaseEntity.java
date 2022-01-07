package matthias.expense_tracker.purchases;

import lombok.Getter;
import lombok.Setter;
import matthias.expense_tracker.common.BaseEntity;
import matthias.expense_tracker.purchases.categories.CategoryEntity;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@Table(name = "purchase")
@Entity
public class PurchaseEntity extends BaseEntity {

    private String name;

    private String description;

    private float price;

    private float amount;

    @ManyToOne(optional = false)
    private CategoryEntity category;
}
