package com.skip.the.dishes.products;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.SnippetRegistry;
import capital.scalable.restdocs.section.SectionSnippet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.FilterConfig;

import static capital.scalable.restdocs.AutoDocumentation.*;
import static capital.scalable.restdocs.jackson.JacksonResultHandlers.prepareJackson;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.limitJsonArrayLength;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.replaceBinaryContent;
import static org.springframework.restdocs.cli.CliDocumentation.curlRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class MockMvcBase {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @MockBean
    private FilterConfig filterConfig;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(prepareJackson(objectMapper))
                .alwaysDo(commonDocumentation())
                .apply(documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(8080)
                        .and().snippets()
                        .withDefaults(
                                curlRequest(),
                                httpRequest(),
                                httpResponse(),
                                requestFields(),
                                responseFields(),
                                pathParameters(),
                                requestParameters(),
                                description(),
                                methodAndPath(),
                                buildSection()
                        )
                )
                .build();
    }

    private SectionSnippet buildSection() {
        return sectionBuilder()
                .snippetNames(
                        SnippetRegistry.PATH_PARAMETERS,
                        SnippetRegistry.REQUEST_PARAMETERS,
                        SnippetRegistry.REQUEST_FIELDS,
                        SnippetRegistry.RESPONSE_FIELDS,
                        SnippetRegistry.CURL_REQUEST,
                        SnippetRegistry.HTTP_RESPONSE
                )
                .skipEmpty(true)
                .build();
    }

    private RestDocumentationResultHandler commonDocumentation() {
        return document("{class-name}/{method-name}", preprocessRequest(), commonResponsePreprocessor());
    }

    private OperationResponsePreprocessor commonResponsePreprocessor() {
        return preprocessResponse(replaceBinaryContent(), limitJsonArrayLength(objectMapper), prettyPrint());
    }
}
