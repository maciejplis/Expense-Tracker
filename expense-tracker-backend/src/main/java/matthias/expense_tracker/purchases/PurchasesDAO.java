package matthias.expense_tracker.purchases;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface PurchasesDAO extends JpaRepository<PurchaseEntity, UUID> {
    List<PurchaseEntity> findAllByNameContainingIgnoreCase(String query);
}
