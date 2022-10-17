package com.compassuol.apiproduto.controller;

import com.compassuol.apiproduto.dto.ProductDto;
import com.compassuol.apiproduto.entity.Product;
import com.compassuol.apiproduto.repository.ProductRepository;
import com.compassuol.apiproduto.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

class ProductControllerTest {

    @InjectMocks
    private ProductController controller;
    @InjectMocks
    private ProductService service;
    @Mock
    private ProductRepository repository;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        this.controller = new ProductController(service);
    }

    @Test
    void deveriaBuscarPorId() {
        int id = 1;

        Product product = new Product(id, "Iphone", new String("celular"), 3800d);
        Optional<Product> optional = Optional.of(product);

        Mockito.when(repository.findById(id)).thenReturn(optional);

        ResponseEntity<ProductDto> responseProduct = controller.findById(id);

        Assert.isTrue(responseProduct.getStatusCode().equals(HttpStatus.OK), Objects.requireNonNull(responseProduct.getBody()).toString());
        Mockito.verify(repository, Mockito.times(1)).findById(id);

    }

    @Test
    void buscarTodos() {

        Pageable pageable = PageRequest.of(0, 3);
        Page<Product> page = Mockito.mock(Page.class);

        Mockito.when(repository.findAll(pageable)).thenReturn(page);
        ResponseEntity<Page<ProductDto>> productPage = controller.list(0, 3, pageable);
        Assert.isTrue(productPage.getStatusCode().equals(HttpStatus.OK));
        Mockito.verify(repository, Mockito.times(1)).findAll(pageable);

    }

    @Test
    void deveriaSalvarProduto() {

        ProductDto productDto = new ProductDto(1, "Iphone", new String("celular"), 3800d);
        Product product = productDto.toProduct();

        Mockito.when(repository.save(product)).thenReturn(product);

        ResponseEntity<ProductDto> responseProduct = controller.create(productDto);
        Assert.isTrue(responseProduct.getStatusCode().equals(HttpStatus.OK), Objects.requireNonNull(responseProduct.getBody()).toString());
        Mockito.verify(repository, Mockito.times(1)).save(product);
    }

    @Test
    void deveriaAtualizarProduto() {
        int id = 1;

        ProductDto productDto = new ProductDto(1, "Iphone", new String("celular"), 3800d);
        Product product = productDto.toProduct();
        Optional<Product> optional = Optional.of(product);

        Mockito.when(repository.findById(id)).thenReturn(optional);
        Mockito.when(repository.save(product)).thenReturn(product);

        ResponseEntity<ProductDto> responseProduct = controller.update(id, productDto);
        Assert.isTrue(responseProduct.getStatusCode().equals(HttpStatus.OK), Objects.requireNonNull(responseProduct.getBody()).toString());
        Mockito.verify(repository, Mockito.times(1)).save(product);
    }

    @Test
    void deveriaDeletarProduto() {
        int id = 1;

        Product product = new Product(id, "Iphone", new String("celular"), 3800d);
        Optional<Product> optional = Optional.of(product);

        Mockito.when(repository.findById(id)).thenReturn(optional);
        ResponseEntity<Void> responseProduct = controller.delete(id);

        Assert.isTrue(responseProduct.getStatusCode().equals(HttpStatus.OK));
        Mockito.verify(repository, Mockito.times(1)).deleteById(id);


    }

}