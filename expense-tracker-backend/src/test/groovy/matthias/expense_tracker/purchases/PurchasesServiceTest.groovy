package matthias.expense_tracker.purchases

import matthias.expense_tracker.api.model.CategoryDto
import matthias.expense_tracker.api.model.PurchaseDto
import matthias.expense_tracker.api.model.PurchaseGroupDto
import matthias.expense_tracker.api.model.ShopDto
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import javax.persistence.EntityManager
import java.time.LocalDate

import static java.util.UUID.randomUUID
import static org.mapstruct.factory.Mappers.getMapper

class PurchasesServiceTest extends Specification {

    static def idSamples = (0..9).collect { randomUUID() }

    def purchaseDto1 = new PurchaseDto(name: "purchase 1", amount: 1, price: 1.99, description: "purchase 1 description", category: new CategoryDto(id: randomUUID()))
    def purchaseDto2 = new PurchaseDto(name: "purchase 2", amount: 0.234, price: 29.99, description: "purchase 2 description", category: new CategoryDto(id: randomUUID()))
    def purchaseGroup = new PurchaseGroupDto(purchases: [purchaseDto1, purchaseDto2], date: LocalDate.parse("2020-12-20"), shop: new ShopDto(id: randomUUID()))

    PurchaseGroupsDAO purchaseGroupsDAO = Mock()
    PurchasesDAO purchasesDAO = Mock()
    EntityManager entityManager = Mock()
    PurchasesMapper purchasesMapper = getMapper(PurchasesMapper)

    @Subject
    PurchasesService purchasesService = new PurchasesService(purchaseGroupsDAO, purchasesDAO, entityManager, purchasesMapper)

    def "Should save new PurchaseGroup and return it"() {
        when:
            def result = purchasesService.addPurchases(purchaseGroup)

        then: "Should save entity"
            1 * purchaseGroupsDAO.saveAndFlush(_ as PurchaseGroupEntity) >> { args ->
                PurchaseGroupEntity purchaseGroup = args[0]
                purchaseGroup.id = idSamples[0]
                purchaseGroup.purchases[0].id = idSamples[1]
                purchaseGroup.purchases[1].id = idSamples[2]
                return purchaseGroup
            }

        and: "Should refresh saved entity"
            1 * entityManager.refresh(_ as PurchaseGroupEntity) >> { args ->
                PurchaseGroupEntity purchaseGroup = args[0]
                purchaseGroup.shop.name = "shop name"
                purchaseGroup.purchases[0].category.name = "category 1"
                purchaseGroup.purchases[1].category.name = "category 2"
            }

        and: "Should return saved and refreshed entity"
            with(result) {
                id == idSamples[0]
                shop == new ShopDto(id: purchaseGroup.shop.id, name: "shop name")
                date == LocalDate.parse("2020-12-20")
                with(purchases.find { it.id == idSamples[1] }) {
                    id == idSamples[1]
                    name == "purchase 1"
                    amount == 1
                    price == 1.99
                    description == "purchase 1 description"
                    category == new CategoryDto(id: purchaseDto1.category.id, name: "category 1")
                }
                with(purchases.find { it.id == idSamples[2] }) {
                    id == idSamples[2]
                    name == "purchase 2"
                    amount == 0.234
                    price == 29.99
                    description == "purchase 2 description"
                    category == new CategoryDto(id: purchaseDto2.category.id, name: "category 2")
                }
            }
    }

    @Unroll
    def "Should return distinct list of purchase names containing given phrase: #queryPhrase"() {
        given:
            def purchases = [
                new PurchaseEntity(name: "purchase 1"),
                new PurchaseEntity(name: "purchase 2"),
                new PurchaseEntity(name: "purchase 2")
            ]

        when:
            def result = purchasesService.queryPurchaseNames(queryPhrase)

        then: "Should return matching purchases"
            1 * purchasesDAO.findAllByNameContainingIgnoreCase(queryPhrase) >> purchases.findAll { it.name.containsIgnoreCase(queryPhrase) }

        and: "Should return distinct list of names"
            result == expectedNames

        where:
            queryPhrase   | expectedNames
            "purchase"    | ["purchase 1", "purchase 2"]
            "hASe"        | ["purchase 1", "purchase 2"]
            "purchase 1"  | ["purchase 1"]
            "purchase 99" | []
    }
}
