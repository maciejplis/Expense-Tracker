package matthias.expense_tracker.purchases.shops;

import lombok.RequiredArgsConstructor;
import matthias.expense_tracker.api.ShopsApi;
import matthias.expense_tracker.api.model.ShopDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/purchases/shops")
class ShopsController implements ShopsApi {

    private final ShopsService shopsService;

    @Override
    @GetMapping
    public ResponseEntity<List<ShopDto>> getPurchaseShops() {
        return ResponseEntity.ok(shopsService.getPurchaseShops());
    }

    @Override
    @PostMapping
    public ResponseEntity<ShopDto> addPurchaseShop(@RequestBody ShopDto shop) {
        return ResponseEntity.ok(shopsService.addPurchaseShop(shop));
    }
}
