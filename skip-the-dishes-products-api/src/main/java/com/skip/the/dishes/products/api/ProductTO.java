package com.skip.the.dishes.products.api;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProductTO {
    private String name;
    private String description;
}
