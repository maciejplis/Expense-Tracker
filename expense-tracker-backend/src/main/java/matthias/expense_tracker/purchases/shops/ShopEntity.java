package matthias.expense_tracker.purchases.shops;

import lombok.Getter;
import lombok.Setter;
import matthias.expense_tracker.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "purchase_shop")
@Entity
public class ShopEntity extends BaseEntity {

    private String name;
}
