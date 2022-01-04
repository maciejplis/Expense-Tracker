package matthias.expense_tracker.purchases.categories;

import lombok.Getter;
import lombok.Setter;
import matthias.expense_tracker.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "purchase_category")
@Entity
public class CategoryEntity extends BaseEntity {

    private String name;
}
