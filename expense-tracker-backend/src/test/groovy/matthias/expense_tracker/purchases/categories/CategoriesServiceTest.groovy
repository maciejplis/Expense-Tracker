package matthias.expense_tracker.purchases.categories

import matthias.expense_tracker.api.model.CategoryDto
import spock.lang.Specification
import spock.lang.Subject

import javax.persistence.EntityExistsException

import static java.util.UUID.randomUUID
import static org.mapstruct.factory.Mappers.getMapper

class CategoriesServiceTest extends Specification {

    static def idSamples = (0..9).collect { randomUUID() }

    def categoryDto1 = new CategoryDto(name: "category 1")

    CategoriesDAO categoriesDAO = Mock()
    CategoriesMapper categoriesMapper = getMapper(CategoriesMapper)

    @Subject
    CategoriesService categoriesService = new CategoriesService(categoriesDAO, categoriesMapper)

    def "Should return list of categories"() {
        when:
            def result = categoriesService.getPurchaseCategories()

        then: "Should get categories"
            1 * categoriesDAO.findAll() >> [
                new CategoryEntity(id: idSamples[0], name: "category 1"),
                new CategoryEntity(id: idSamples[1], name: "category 2")
            ]

        and:
            with(result.get(0)) {
                id == idSamples[0]
                name == "category 1"
            }
            with(result.get(1)) {
                id == idSamples[1]
                name == "category 2"
            }
    }

    def "Should save new category and return it"() {
        when:
            def result = categoriesService.addPurchaseCategory(categoryDto1)

        then: "Should check if category already exists"
            1 * categoriesDAO.existsByName(categoryDto1.name) >> false

        and: "Should save entity"
            1 * categoriesDAO.save(_ as CategoryEntity) >> { args ->
                CategoryEntity category = args[0]
                category.id = idSamples[0]
                return category
            }

        and: "Should return saved entity"
            with(result) {
                id == idSamples[0]
                name == "category 1"
            }
    }

    def "Should throw EntityExistsException when category already exists"() {
        when:
            categoriesService.addPurchaseCategory(categoryDto1)

        then: "Should check if category already exists"
            1 * categoriesDAO.existsByName(categoryDto1.name) >> true

        and: "EntityExistsException is thrown"
            thrown(EntityExistsException)
    }
}
