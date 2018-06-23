package com.skip.the.dishes.products.domain;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Builder
@NoArgsConstructor(access = PRIVATE)
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Product {
    @Setter
    private String id;
    private String name;
    private String description;

    public String doBusinessLogic() {
        return "Business Logic";
    }
}
