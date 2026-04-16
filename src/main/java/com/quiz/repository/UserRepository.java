package com.quiz.repository;

import com.quiz.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    SOLID/GRASP highlights:
    - DIP (Dependency Inversion): Authentication/user services depend on this abstraction for user lookups.
    - GRASP Pure Fabrication: Repository centralizes persistence concerns away from domain and controller logic.
*/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
