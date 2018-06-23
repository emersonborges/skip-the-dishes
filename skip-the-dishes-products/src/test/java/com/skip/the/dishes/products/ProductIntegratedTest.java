package com.skip.the.dishes.products;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.skip.the.dishes.products.TestUtils.payload;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductIntegratedTest extends MockMvcBase {

    private static final String PRODUCTS_PATH = "/products";

    @Test
    public void shouldCreateProduct() throws Exception {
        mockMvc.perform(post("/products")
                .header(CONTENT_TYPE, "application/json")
                .content(payload("product.json")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name", is("Product name")))
                .andExpect(jsonPath("$.description", is("Product description")));
    }

    @Test
    public void shouldNotCreateProduct_whenIsInvalid() throws Exception {
        mockMvc.perform(post(PRODUCTS_PATH)
                .header(CONTENT_TYPE, "application/json")
                .content(payload("invalid-product.json")))
                .andExpect(status().isBadRequest());
    }

}
