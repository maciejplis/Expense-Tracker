package matthias.expense_tracker.purchases.shops;

import lombok.RequiredArgsConstructor;
import matthias.expense_tracker.api.model.ShopDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;

@Service
@RequiredArgsConstructor
class ShopsService {

    private final ShopsDAO shopsDAO;
    private final ShopsMapper shopsMapper;

    List<ShopDto> getPurchaseShops() {
        return shopsMapper.toDto(shopsDAO.findAll());
    }

    @Transactional
    public ShopDto addPurchaseShop(ShopDto shopDto) {
        if(shopsDAO.existsByName(shopDto.getName())) {
            throw new EntityExistsException("Shop with such name already exist");
        }

        ShopEntity savedShop = shopsDAO.save(shopsMapper.fromDto(shopDto));
        return shopsMapper.toDto(savedShop);
    }
}
