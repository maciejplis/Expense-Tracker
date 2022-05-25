package matthias.expense_tracker.purchases;

import lombok.Getter;
import lombok.Setter;
import matthias.expense_tracker.common.BaseEntity;
import matthias.expense_tracker.purchases.categories.CategoryEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "purchase")
@Entity
class PurchaseEntity extends BaseEntity {

    private String name;

    private String description;

    private double price;

    private double amount;

    @ManyToOne(optional = false)
    private CategoryEntity category;
}
