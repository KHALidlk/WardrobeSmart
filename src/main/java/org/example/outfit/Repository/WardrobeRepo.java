package org.example.outfit.Repository;

import org.example.outfit.model.Wardrobe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WardrobeRepo extends JpaRepository<Wardrobe, Long> {
    Optional<Wardrobe> findByUserId(Long userId);
}
