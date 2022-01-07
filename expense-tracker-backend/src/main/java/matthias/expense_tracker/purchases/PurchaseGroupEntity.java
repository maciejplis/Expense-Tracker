package matthias.expense_tracker.purchases;

import lombok.Getter;
import lombok.Setter;
import matthias.expense_tracker.common.BaseEntity;
import matthias.expense_tracker.purchases.shops.ShopEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@Table(name = "purchase_group")
@Entity
public class PurchaseGroupEntity extends BaseEntity {

    private LocalDate date;

    @ManyToOne(optional = false)
    private ShopEntity shop;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "purchase_group_id")
    private List<PurchaseEntity> purchases;
}
