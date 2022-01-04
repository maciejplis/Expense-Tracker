package matthias.expense_tracker.purchases;

import lombok.RequiredArgsConstructor;
import matthias.expense_tracker.purchases.dtos.PurchaseListDtoWrite;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class PurchasesService {

    private final ExpensesDAO expensesDAO;
    private final PurchasesMapper purchasesMapper;

    void addPurchases(PurchaseListDtoWrite purchaseList) {
        expensesDAO.save(purchasesMapper.fromDtoWrite(purchaseList));
    }
}
