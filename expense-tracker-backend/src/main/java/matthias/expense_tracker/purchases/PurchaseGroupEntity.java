package matthias.expense_tracker.purchases;

import lombok.Getter;
import lombok.Setter;
import matthias.expense_tracker.common.AuditEntity;
import matthias.expense_tracker.purchases.shops.ShopEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@Table(name = "purchase_group")
@Entity
class PurchaseGroupEntity extends AuditEntity {

    private LocalDate date;

    @ManyToOne(optional = false)
    private ShopEntity shop;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "group_id")
    private List<PurchaseEntity> purchases;
}
