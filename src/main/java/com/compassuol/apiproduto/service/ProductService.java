package com.compassuol.apiproduto.service;

import com.compassuol.apiproduto.config.exception.ResourceNotFoundException;
import com.compassuol.apiproduto.dto.ProductDto;
import com.compassuol.apiproduto.entity.Product;
import com.compassuol.apiproduto.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }


    public Product findById(Integer id) {
        Optional<Product> product = repository.findById(id);
        return product.orElseThrow(ResourceNotFoundException::new);
    }

    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }


    public Page<ProductDto> findBySearch(double min, double max, String name, Pageable pageable) {
        Page<Product> list = repository.findBySearch(min, max, name, pageable);

        if (!list.isEmpty()) {
            return list.map(product -> new ProductDto(product));
        } else
            throw new ResourceNotFoundException();
    }

    @Transactional
    public Product save(Product product) {
        return repository.save(product);
    }

    @Transactional
    public Product update(Product product) {
        this.findById(product.getId());
        return repository.save(product);
    }

    @Transactional
    public void delete(Integer id) {
        Product product = this.findById(id);
        if (product != null) {
            repository.deleteById(id);
        }
    }

}
