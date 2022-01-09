package matthias.expense_tracker.purchases;

import lombok.RequiredArgsConstructor;
import matthias.expense_tracker.api.model.PurchaseGroupDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Service
class PurchasesService {

    private final PurchaseGroupsDAO purchaseGroupsDAO;
    private final PurchasesDAO purchasesDAO;
    private final EntityManager entityManager;
    private final PurchasesMapper purchasesMapper;

    @Transactional
    public PurchaseGroupDto addPurchases(PurchaseGroupDto purchaseGroupDto) {
        PurchaseGroupEntity savedPurchaseGroup = purchaseGroupsDAO.saveAndFlush(purchasesMapper.fromDto(purchaseGroupDto));
        entityManager.refresh(savedPurchaseGroup);
        return purchasesMapper.toDto(savedPurchaseGroup);
    }

    public List<String> queryPurchaseNames(String query) {
        return purchasesDAO.findAllByNameContainingIgnoreCase(query)
                .stream()
                .map(PurchaseEntity::getName)
                .distinct()
                .toList();
    }
}