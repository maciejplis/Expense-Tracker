package matthias.expense_tracker.purchases.shops;

import lombok.RequiredArgsConstructor;
import matthias.expense_tracker.purchases.shops.dtos.ShopDtoRead;
import matthias.expense_tracker.purchases.shops.dtos.ShopDtoWrite;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class ShopsService {

    private final ShopsDAO shopsDAO;
    private final ShopsMapper shopsMapper;

    List<ShopDtoRead> getShops() {
        return shopsMapper.toDtoRead(shopsDAO.findAll());
    }

    void addShop(ShopDtoWrite shop) {
        shopsDAO.save(shopsMapper.fromDtoWrite(shop));
    }
}
