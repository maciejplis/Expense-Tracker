package matthias.expense_tracker.purchases;

import lombok.RequiredArgsConstructor;
import matthias.expense_tracker.api.PurchasesApi;
import matthias.expense_tracker.api.model.PurchaseGroupDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/purchases")
class PurchasesController implements PurchasesApi {

    private final PurchasesService purchasesService;

    @Override
    @PostMapping
    public ResponseEntity<PurchaseGroupDto> addPurchaseGroup(PurchaseGroupDto purchaseGroupDto) {
        return ResponseEntity.ok(purchasesService.addPurchases(purchaseGroupDto));
    }

    @Override
    @GetMapping("query-names")
    public ResponseEntity<List<String>> queryPurchaseNames(@RequestParam String query) {
        return ResponseEntity.ok(purchasesService.queryPurchaseNames(query));
    }
}
