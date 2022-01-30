package matthias.expense_tracker.purchases.shops


import matthias.expense_tracker.api.model.ShopDto
import matthias.expense_tracker.configuration.ResponseExceptionHandler
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Subject

import javax.persistence.EntityExistsException

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

class ShopsControllerTest extends Specification {

    static def idSamples = (0..9).collect { randomUUID() }

    ShopsService shopsService = Mock()

    @Subject
    MockMvc shopsAPI = standaloneSetup(new ShopsController(shopsService))
        .setControllerAdvice(new ResponseExceptionHandler())
        .build()

    def "Should return 200 (OK) and list of shops"() {
        when:
            def result = shopsAPI
                .perform(get("/purchases/shops"))
                .andDo(print())

        then: "Should get shops"
            shopsService.getPurchaseShops() >> [
                new ShopDto(id: idSamples[0], name: "shop 1"),
                new ShopDto(id: idSamples[1], name: "shop 2")
            ]

        and: "Should return 200 (OK) with shops list"
            with(result) {
                andExpect(status().isOk())
                andExpect(jsonPath('$', hasSize(2)))
                andExpect(jsonPath('$[0].id').value(idSamples[0].toString()))
                andExpect(jsonPath('$[0].name').value("shop 1"))
                andExpect(jsonPath('$[1].id').value(idSamples[1].toString()))
                andExpect(jsonPath('$[1].name').value("shop 2"))
            }
    }

    def "Should return 200 (OK) and saved shop"() {
        when:
            def result = shopsAPI
                .perform(post("/purchases/shops")
                    .content(newShopRequestBody())
                    .contentType(APPLICATION_JSON))
                .andDo(print())

        then: "Should save new shop"
            shopsService.addPurchaseShop(_ as ShopDto) >> { args ->
                ShopDto shopDto = args[0]
                shopDto.id = idSamples[0]
                return shopDto
            }

        and: "Should return 200 (OK) with newly created shop"
            with(result) {
                andExpect(status().isOk())
                andExpect(jsonPath('$.id').value(idSamples[0].toString()))
                andExpect(jsonPath('$.name').value("shop 1"))
            }
    }

    def "Should return 409 (CONFLICT) when shop already exists"() {
        when:
            def result = shopsAPI
                .perform(post("/purchases/shops")
                    .content(newShopRequestBody())
                    .contentType(APPLICATION_JSON))
                .andDo(print())

        then: "Should try to save new shop"
            shopsService.addPurchaseShop(_ as ShopDto) >> {
                throw new EntityExistsException("Shop already exists")
            }

        and: "Should return 409 (CONFLICT)"
            result.andExpect(status().isConflict())
    }

    private static String newShopRequestBody() {
        return toJson([name: "shop 1"])
    }
}
