package com.skip.the.dishes.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skip.the.dishes.products.api.ProductResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.skip.the.dishes.products.TestUtils.payload;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductIntegratedTest extends MockMvcBase {

    private static final String PRODUCTS_PATH = "/products";
    private static final String PRODUCTS_RESOURCE_PATH = "/products/%s";

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateProduct() throws Exception {
        createProduct();
    }

    private String createProduct() throws Exception {
        String json = mockMvc.perform(post("/products")
                .header(CONTENT_TYPE, "application/json")
                .content(payload("product.json")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name", is("Product name")))
                .andExpect(jsonPath("$.description", is("Product description")))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(json, ProductResponse.class).getId();
    }

    @Test
    public void shouldNotCreateInvalidProduct() throws Exception {
        mockMvc.perform(post(PRODUCTS_PATH)
                .header(CONTENT_TYPE, "application/json")
                .content(payload("invalid-product.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFindAProductById() throws Exception {
        String productId = createProduct();
        mockMvc.perform(get(String.format(PRODUCTS_RESOURCE_PATH, productId))
                .header(CONTENT_TYPE, "application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productId)))
                .andExpect(jsonPath("$.name", is("Product name")))
                .andExpect(jsonPath("$.description", is("Product description")));
    }

}
