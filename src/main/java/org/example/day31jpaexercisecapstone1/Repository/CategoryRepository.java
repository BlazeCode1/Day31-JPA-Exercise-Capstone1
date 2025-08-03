package org.example.day31jpaexercisecapstone1.Repository;

import org.example.day31jpaexercisecapstone1.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
