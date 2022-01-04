package matthias.expense_tracker.purchases;

import lombok.RequiredArgsConstructor;
import matthias.expense_tracker.purchases.dtos.PurchaseListDtoWrite;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/purchases")
class PurchasesController {

    private final PurchasesService purchasesService;

    @PostMapping
    public void addPurchases(@RequestBody PurchaseListDtoWrite purchaseList) {
        purchasesService.addPurchases(purchaseList);
    }
}
