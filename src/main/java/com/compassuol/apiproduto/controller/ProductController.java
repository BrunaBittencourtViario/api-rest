package com.compassuol.apiproduto.controller;

import com.compassuol.apiproduto.dto.ProductDto;
import com.compassuol.apiproduto.entity.Product;
import com.compassuol.apiproduto.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Page<ProductDto>> list(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 3)
                                                 @RequestParam int page, @RequestParam int size, @ApiIgnore Pageable pageable) {
        Page<Product> listar = service.findAll(pageable);
        Page<ProductDto> productDtos = ProductDto.converterLista(listar);
        return ResponseEntity.ok().body(productDtos);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> search(@PageableDefault(sort = "price", direction = Sort.Direction.ASC, page = 0, size = 3) @ApiIgnore Pageable pageable,
                                                   @RequestParam int page, @RequestParam int size,
                                                   @RequestParam(required = false) double max,
                                                   @RequestParam(required = false) double min,
                                                   @RequestParam(required = false) String name) {
        return ResponseEntity.ok().body(service.findBySearch(min, max, name, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable(value = "id") Integer id) {
        ProductDto productDto = service.findById(id).toProductDto();
        return ResponseEntity.ok().body(productDto);
    }


    @PostMapping("/")
    public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductDto dto) {
        Product product = service.save(dto.toProduct());
        return ResponseEntity.ok().body(product.toProductDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable(value = "id") Integer id, @RequestBody @Valid ProductDto dto) {
        dto.setId(id);
        Product product = service.update(dto.toProduct());
        return ResponseEntity.ok().body(product.toProductDto());
    }
}

