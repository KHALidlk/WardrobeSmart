package org.example.outfit.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wardrobes")
public class Wardrobe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "wardrobe", cascade = CascadeType.ALL)
    private List<Outfit> outfits = new ArrayList<>();

    @OneToMany(mappedBy = "wardrobe", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Piece> pieces = new ArrayList<>();

    @OneToOne(mappedBy = "wardrobe")
    @JsonBackReference("user-wardrobe")  // Add this annotation
    private User user;
}
