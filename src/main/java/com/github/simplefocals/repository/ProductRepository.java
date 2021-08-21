package com.github.simplefocals.repository;

import com.github.simplefocals.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    //method that is made by Jpa create
    List<Product> findAllByCategory_Id(Integer id);
}
