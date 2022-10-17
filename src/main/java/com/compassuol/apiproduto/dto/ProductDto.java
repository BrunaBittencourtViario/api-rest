package com.compassuol.apiproduto.dto;


import com.compassuol.apiproduto.entity.Product;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ProductDto implements Serializable {

    private Integer id;
    @NotBlank(message = "{name.not.blank}")
    private String name;
    private String description;
    private double price;

    public ProductDto() {
    }

    public ProductDto(Integer id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product toProduct() {
        return new Product(this.id, this.name, this.description, this.price);
    }

    public static Page<ProductDto> converterLista(Page<Product> product) {

        return product
                .map(ProductDto::new);
    }

}
