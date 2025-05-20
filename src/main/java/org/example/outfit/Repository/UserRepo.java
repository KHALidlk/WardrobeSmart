package org.example.outfit.Repository;
import org.example.outfit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    // If you want case-insensitive login
    @Query("SELECT u FROM User u WHERE LOWER(u.login) = LOWER(:login)")
    Optional<User> findByLogin(@Param("login") String login);}
