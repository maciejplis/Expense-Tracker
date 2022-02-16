package matthias.expense_tracker.purchases

import matthias.expense_tracker.IntegrationTestSpecification
import matthias.expense_tracker.purchases.categories.CategoriesDAO
import matthias.expense_tracker.purchases.categories.CategoryEntity
import matthias.expense_tracker.purchases.shops.ShopEntity
import matthias.expense_tracker.purchases.shops.ShopsDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

import static groovy.json.JsonOutput.toJson
import static java.time.LocalDate.parse
import static org.hamcrest.Matchers.hasSize
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Transactional
class PurchasesIntegrationTest extends IntegrationTestSpecification {

    @Autowired
    PurchaseGroupsDAO purchaseGroupsDAO

    @Autowired
    PurchasesDAO purchasesDAO

    @Autowired
    ShopsDAO shopsDAO

    @Autowired
    CategoriesDAO categoriesDAO

    def "Should return 200 (OK) and save new purchase group"() {
        given:
            ShopEntity purchaseShop = shopsDAO.save(new ShopEntity(name: "shop name"))
            CategoryEntity purchaseCategory1 = categoriesDAO.save(new CategoryEntity(name: "category 1 name"))
            CategoryEntity purchaseCategory2 = categoriesDAO.save(new CategoryEntity(name: "category 2 name"))
            String requestBody = newPurchaseGroupRequestBody(purchaseShop.id, purchaseCategory1.id, purchaseCategory2.id)

        when:
            def result = mvc
                .perform(post("/purchases")
                    .content(requestBody)
                    .contentType(APPLICATION_JSON))
                .andDo(print())

        then: "Should save purchase group to db"
            def purchaseGroups = purchaseGroupsDAO.findAll()
            purchaseGroups.size() == 1
            with(purchaseGroups.get(0)) {
                id != null
                date == parse("2020-12-20")
                shop.id == purchaseShop.id
                shop.name == "shop name"
                purchases.size() == 2
                with(purchases.get(0)) {
                    id != null
                    name == "purchase 1"
                    amount == 1d
                    price == 1.99d
                    description == "purchase 1 description"
                    category.id == purchaseCategory1.id
                    category.name == "category 1 name"
                }
                with(purchases.get(1)) {
                    id != null
                    name == "purchase 2"
                    amount == 0.234d
                    price == 29.99d
                    description == "purchase 2 description"
                    category.id == purchaseCategory2.id
                    category.name == "category 2 name"
                }
            }

        and: "Should return 200 (OK) with newly created purchases group"
            with(result) {
                andExpect(status().isOk())
                andExpect(jsonPath('$.id').isNotEmpty())
                andExpect(jsonPath('$.date').value("2020-12-20"))
                andExpect(jsonPath('$.shop.id').value(purchaseShop.id.toString()))
                andExpect(jsonPath('$.shop.name').value("shop name"))
                andExpect(jsonPath('$.purchases', hasSize(2)))

                andExpect(jsonPath('$.purchases[0].id').isNotEmpty())
                andExpect(jsonPath('$.purchases[0].name').value("purchase 1"))
                andExpect(jsonPath('$.purchases[0].amount').value(1))
                andExpect(jsonPath('$.purchases[0].price').value(1.99))
                andExpect(jsonPath('$.purchases[0].description').value("purchase 1 description"))
                andExpect(jsonPath('$.purchases[0].category.id').value(purchaseCategory1.id.toString()))
                andExpect(jsonPath('$.purchases[0].category.name').value("category 1 name"))

                andExpect(jsonPath('$.purchases[1].id').isNotEmpty())
                andExpect(jsonPath('$.purchases[1].name').value("purchase 2"))
                andExpect(jsonPath('$.purchases[1].amount').value(0.234))
                andExpect(jsonPath('$.purchases[1].price').value(29.99))
                andExpect(jsonPath('$.purchases[1].description').value("purchase 2 description"))
                andExpect(jsonPath('$.purchases[1].category.id').value(purchaseCategory2.id.toString()))
                andExpect(jsonPath('$.purchases[1].category.name').value("category 2 name"))
            }
    }

    def "Should return 200 (OK) and purchase names matching query"() {
        given:
            CategoryEntity category = categoriesDAO.save(new CategoryEntity(name: "category"))
            purchasesDAO.saveAll([
                new PurchaseEntity(name: "purchase 1", amount: 1, price: 0.99, category: category),
                new PurchaseEntity(name: "purchase 2", amount: 1, price: 0.99, category: category),
                new PurchaseEntity(name: "purchase 11", amount: 1, price: 0.99, category: category)
            ])

        when:
            def result = mvc
                .perform(get("/purchases/query-names")
                    .queryParam("query", "chase 1"))
                .andDo(print())

        then: "Should return 200 (OK) and list of names"
            with(result) {
                andExpect(status().isOk())
                andExpect(jsonPath('$', hasSize(2)))
                andExpect(jsonPath('$[0]').value("purchase 1"))
                andExpect(jsonPath('$[1]').value("purchase 11"))
            }

    }

    private String newPurchaseGroupRequestBody(UUID shopId, UUID ...categoryIds) {
        return toJson([
            shop     : [id: shopId],
            date     : "2020-12-20",
            purchases: [
                [
                    name       : "purchase 1",
                    amount     : 1,
                    price      : 1.99,
                    description: "purchase 1 description",
                    category   : [id: categoryIds[0]]
                ],
                [
                    name       : "purchase 2",
                    amount     : 0.234,
                    price      : 29.99,
                    description: "purchase 2 description",
                    category   : [id: categoryIds[1]]
                ]
            ]
        ])
    }
}
