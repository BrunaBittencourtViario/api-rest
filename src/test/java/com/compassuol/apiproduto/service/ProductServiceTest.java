package com.compassuol.apiproduto.service;

import com.compassuol.apiproduto.entity.Product;
import com.compassuol.apiproduto.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.Optional;

class ProductServiceTest {

    @InjectMocks
    private ProductService service;
    @Mock
    private ProductRepository repository;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        this.service = new ProductService(repository);
    }

    @Test
    void deveriaBuscarPorId() {
        int id = 1;

        Product product = new Product(id, "Iphone", new String("celular"), 3800d);
        Optional<Product> optional = Optional.of(product);

        Mockito.when(repository.findById(id)).thenReturn(optional);

        Product productById = service.findById(id);

        Assert.isTrue(productById != null, "Produto encontrado pelo ID: " + id);
        Mockito.verify(repository, Mockito.times(1)).findById(id);

    }

    @Test
    void buscarTodos() {

        Pageable pageable = PageRequest.of(0, 3);
        Page<Product> page = Mockito.mock(Page.class);

        Mockito.when(repository.findAll(pageable)).thenReturn(page);

        Page<Product> productPage = service.findAll(pageable);
        Assertions.assertTrue(!productPage.isEmpty());
        Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void deveriaSalvarProduto() {

        Product product = new Product(1, "Iphone", new String("celular"), 3800d);
        service.save(product);
        Mockito.verify(repository, Mockito.times(1)).save(product);
    }

    @Test
    void deveriaAtualizarProduto() {
        Product product = new Product(1, "Iphone", new String("celular"), 3700d);
        service.save(product);
        Mockito.verify(repository, Mockito.times(1)).save(product);
    }

    @Test
    void deveriaDeletarProduto() {
        int id = 1;

        Product product = new Product(id, "Iphone", new String("celular"), 3800d);
        Optional<Product> optional = Optional.of(product);

        Mockito.when(repository.findById(id)).thenReturn(optional);
        service.delete(id);

        Mockito.verify(repository, Mockito.times(1)).deleteById(id);

    }

}