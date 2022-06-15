package site.metacoding.restdoc;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith({ SpringExtension.class, RestDocumentationExtension.class })
public abstract class AbstractControllerTest {
    protected MockMvc mockMvc;

    protected RestDocumentationResultHandler document; // document 커스터마이징 할거임

    @BeforeEach // 메서드 실행하기 전 마다 실행됨
    private void setup(WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation) {
        // class-name => user-api-controller-test/
        this.document = MockMvcRestDocumentation.document("{class-name}/{method-name}", // 어떤식으로 폴더구조 정할건지
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()));

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .alwaysDo(document) // 항상 실행이 된다.
                .build();
    }
}
