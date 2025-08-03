package org.example.day31jpaexercisecapstone1.Repository;

import org.example.day31jpaexercisecapstone1.Model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant,Integer> {
}
