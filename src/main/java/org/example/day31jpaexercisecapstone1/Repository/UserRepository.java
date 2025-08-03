package org.example.day31jpaexercisecapstone1.Repository;

import org.example.day31jpaexercisecapstone1.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
