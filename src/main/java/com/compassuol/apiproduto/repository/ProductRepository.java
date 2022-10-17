package com.compassuol.apiproduto.repository;

import com.compassuol.apiproduto.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT p FROM Product p WHERE "
            + "(:max IS NULL OR p.price <= :max) AND "
            + "(:min IS NULL OR p.price >= :min) AND "
            + "(:name IS NULL OR lower(p.name) LIKE lower(concat('%', :name, '%')))")
    public Page<Product> findBySearch(@Param("min") double min, @Param("max") double max, @Param("name") String name, Pageable pageable);


}
