package matthias.expense_tracker.purchases


import matthias.expense_tracker.api.model.PurchaseGroupDto
import matthias.expense_tracker.configuration.ResponseExceptionHandler
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Subject

import static groovy.json.JsonOutput.toJson
import static java.util.UUID.randomUUID
import static org.hamcrest.Matchers.hasSize
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class PurchasesControllerTest extends Specification {

    static def idSamples = (0..9).collect { randomUUID() }

    PurchasesService purchasesService = Mock()

    @Subject
    MockMvc purchasesAPI = standaloneSetup(new PurchasesController(purchasesService))
        .setControllerAdvice(new ResponseExceptionHandler())
        .build()

    def "Should return 200 (OK) and saved purchases group"() {
        when:
            def result = purchasesAPI
                .perform(post("/purchases")
                    .content(newPurchaseGroupRequestBody())
                    .contentType(APPLICATION_JSON))
                .andDo(print())

        then: "Should save new purchases group"
            purchasesService.addPurchases(_ as PurchaseGroupDto) >> { args ->
                PurchaseGroupDto purchaseGroupDto = args[0]
                purchaseGroupDto.id = idSamples[0]
                purchaseGroupDto.purchases[0].id = idSamples[1]
                purchaseGroupDto.purchases[1].id = idSamples[2]
                purchaseGroupDto.shop.name = "shop 1"
                purchaseGroupDto.purchases[0].category.name = "category 1"
                purchaseGroupDto.purchases[1].category.name = "category 2"

                return purchaseGroupDto
            }

        and: "Should return 200 (OK) with newly created purchases group"
            with(result) {
                andExpect(status().isOk())
                andExpect(jsonPath('$.id').value(idSamples[0].toString()))
//                andExpect(jsonPath('$.date').value("2020-12-20")) // TODO Date formatting
                andExpect(jsonPath('$.shop.id').value(idSamples[3].toString()))
                andExpect(jsonPath('$.shop.name').value("shop 1"))
                andExpect(jsonPath('$.purchases', hasSize(2)))

                andExpect(jsonPath('$.purchases[0].id').value(idSamples[1].toString()))
                andExpect(jsonPath('$.purchases[0].name').value("purchase 1"))
                andExpect(jsonPath('$.purchases[0].amount').value(1))
                andExpect(jsonPath('$.purchases[0].price').value(1.99))
                andExpect(jsonPath('$.purchases[0].description').value("purchase 1 description"))
                andExpect(jsonPath('$.purchases[0].category.id').value(idSamples[4].toString()))
                andExpect(jsonPath('$.purchases[0].category.name').value("category 1"))

                andExpect(jsonPath('$.purchases[1].id').value(idSamples[2].toString()))
                andExpect(jsonPath('$.purchases[1].name').value("purchase 2"))
                andExpect(jsonPath('$.purchases[1].amount').value(0.234))
                andExpect(jsonPath('$.purchases[1].price').value(29.99))
                andExpect(jsonPath('$.purchases[1].description').value("purchase 2 description"))
                andExpect(jsonPath('$.purchases[1].category.id').value(idSamples[5].toString()))
                andExpect(jsonPath('$.purchases[1].category.name').value("category 2"))
            }
    }

    def "Should return 200 (OK) and list of purchases names"() {
        when:
            def result = purchasesAPI
                .perform(get("/purchases/query-names")
                    .param("query", "query"))
                .andDo(print())

        then: "Should get purchases names"
            1 * purchasesService.queryPurchaseNames("query") >> ["purchase 1", "purchase 2"]

        and: "Should return 200 (OK) and list of names"
            with(result) {
                andExpect(status().isOk())
                andExpect(jsonPath('$', hasSize(2)))
                andExpect(jsonPath('$[0]').value("purchase 1"))
                andExpect(jsonPath('$[1]').value("purchase 2"))
            }
    }

    private static String newPurchaseGroupRequestBody() {
        return toJson([
            shop     : [id: idSamples[3]],
            date     : "2020-12-20",
            purchases: [
                [
                    name       : "purchase 1",
                    amount     : 1,
                    price      : 1.99,
                    description: "purchase 1 description",
                    category   : [id: idSamples[4]]
                ],
                [
                    name       : "purchase 2",
                    amount     : 0.234,
                    price      : 29.99,
                    description: "purchase 2 description",
                    category   : [id: idSamples[5]]
                ]
            ]
        ])
    }
}
