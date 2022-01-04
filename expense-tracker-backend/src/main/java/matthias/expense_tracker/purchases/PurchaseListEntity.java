package matthias.expense_tracker.purchases;

import lombok.Getter;
import lombok.Setter;
import matthias.expense_tracker.common.BaseEntity;
import matthias.expense_tracker.purchases.shops.ShopEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@Table(name = "purchase_list")
@Entity
public class PurchaseListEntity extends BaseEntity {

    private LocalDate date;

    @ManyToOne
    private ShopEntity shop;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "purchase_list_id")
    private List<PurchaseEntity> purchases;
}
