package org.example.outfit.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.example.outfit.model.Piece;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PieceDTO {
    private Long id;
    private Piece.Category category;
    private Piece.Status status;
    private String description;
    private List<Piece.Color> colors;
    private List<Piece.Season> seasons;
    private Double price;
    private Date purchaseDate;
    private Piece.Style style;
    private List<Piece.Occasion> occasions;
    private Piece.Fabric fabric;
    private Piece.Type type;
    private boolean pattern;
    private int rating;
    private boolean available;
    private boolean liked;
    private String imageUrl;

    // Only include the ID of the wardrobe to avoid circular references
    private Long wardrobeId;
}

