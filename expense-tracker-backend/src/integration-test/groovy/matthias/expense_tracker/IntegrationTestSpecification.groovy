package matthias.expense_tracker

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@AutoConfigureMockMvc
@ActiveProfiles("it")
@SpringBootTest
class IntegrationTestSpecification extends Specification{

    @Autowired
    MockMvc mvc
}
