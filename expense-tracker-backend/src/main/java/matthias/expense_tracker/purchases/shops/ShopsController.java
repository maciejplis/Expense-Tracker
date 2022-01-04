package matthias.expense_tracker.purchases.shops;

import lombok.RequiredArgsConstructor;
import matthias.expense_tracker.purchases.shops.dtos.ShopDtoRead;
import matthias.expense_tracker.purchases.shops.dtos.ShopDtoWrite;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shops")
class ShopsController {

    private final ShopsService shopsService;

    @GetMapping
    public List<ShopDtoRead> getShops() {
        return shopsService.getShops();
    }

    @PostMapping
    public void addShop(@RequestBody @Valid ShopDtoWrite shop) {
        shopsService.addShop(shop);
    }
}
