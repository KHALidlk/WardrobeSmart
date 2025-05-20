package org.example.outfit.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.example.outfit.model.Piece;
import org.example.outfit.model.Wardrobe;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "outfits")
public class Outfit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "pants_id")
    private Piece pants;

    @ManyToOne
    @JoinColumn(name = "top_id")
    private Piece top;

    @ManyToOne
    @JoinColumn(name = "shoes_id")
    private Piece shoes;

    private Boolean liked = false;
    private Boolean saved = false;

    @Enumerated(EnumType.STRING)
    private Piece.Style style;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "outfit_occasions", joinColumns = @JoinColumn(name = "outfit_id"))
    @Column(name = "occasion")
    private List<Piece.Occasion> occasions;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "outfit_seasons", joinColumns = @JoinColumn(name = "outfit_id"))
    @Column(name = "season")
    private List<Piece.Season> seasons;

    @ManyToOne
    @JoinColumn(name = "wardrobe_id")
    @JsonBackReference
    private Wardrobe wardrobe;
}
