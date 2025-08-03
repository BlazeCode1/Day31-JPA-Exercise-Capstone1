package org.example.day31jpaexercisecapstone1.Repository;

import org.example.day31jpaexercisecapstone1.Model.MerchantStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantStockRepository extends JpaRepository<MerchantStock,Integer> {
}
