package org.example.day31jpaexercisecapstone1.Repository;

import org.example.day31jpaexercisecapstone1.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
