package matthias.expense_tracker.purchases.categories

import matthias.expense_tracker.api.model.CategoryDto
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

class CategoriesControllerTest extends Specification {

    static def idSamples = (0..9).collect { randomUUID() }

    CategoriesService categoriesService = Mock()

    @Subject
    MockMvc categoriesAPI = standaloneSetup(new CategoriesController(categoriesService))
        .setControllerAdvice(new ResponseExceptionHandler())
        .build()

    def "Should return 200 (OK) and list of categories"() {
        when:
            def result = categoriesAPI
                .perform(get("/purchases/categories"))
                .andDo(print())

        then: "Should get categories"
            categoriesService.getPurchaseCategories() >> [
                new CategoryDto(id: idSamples[0], name: "category 1"),
                new CategoryDto(id: idSamples[1], name: "category 2")
            ]

        and: "Should return 200 (OK) with categories list"
            with(result) {
                andExpect(status().isOk())
                andExpect(jsonPath('$', hasSize(2)))
                andExpect(jsonPath('$[0].id').value(idSamples[0].toString()))
                andExpect(jsonPath('$[0].name').value("category 1"))
                andExpect(jsonPath('$[1].id').value(idSamples[1].toString()))
                andExpect(jsonPath('$[1].name').value("category 2"))
            }
    }

    def "Should return 200 (OK) and saved category"() {
        when:
            def result = categoriesAPI
                .perform(post("/purchases/categories")
                    .content(newCategoryRequestBody())
                    .contentType(APPLICATION_JSON))
                .andDo(print())

        then: "Should save new category"
            categoriesService.addPurchaseCategory(_ as CategoryDto) >> { args ->
                CategoryDto categoryDto = args[0]
                categoryDto.id = idSamples[0]
                return categoryDto
            }

        and: "Should return 200 (OK) with newly created category"
            with(result) {
                andExpect(status().isOk())
                andExpect(jsonPath('$.id').value(idSamples[0].toString()))
                andExpect(jsonPath('$.name').value("category 1"))
            }
    }

    def "Should return 409 (CONFLICT) when category already exists"() {
        when:
            def result = categoriesAPI
                .perform(post("/purchases/categories")
                    .content(newCategoryRequestBody())
                    .contentType(APPLICATION_JSON))
                .andDo(print())

        then: "Should try to save new category"
            categoriesService.addPurchaseCategory(_ as CategoryDto) >> {
                throw new EntityExistsException("Category already exists")
            }

        and: "Should return 409 (CONFLICT)"
            result.andExpect(status().isConflict())
    }

    private static String newCategoryRequestBody() {
        return toJson([name: "category 1"])
    }
}
