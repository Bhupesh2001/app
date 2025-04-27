package com.moviebooking.app.repository;

import com.moviebooking.app.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Find by unique login ID
    Optional<User> findByLoginId(String loginId);

    // Find by unique email
    Optional<User> findByEmail(String email);

    // Check if login ID exists (for registration validation)
    boolean existsByLoginId(String loginId);

    // Check if email exists (for registration validation)
    boolean existsByEmail(String email);
}