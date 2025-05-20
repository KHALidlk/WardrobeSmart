package org.example.outfit.mapper;

import org.example.outfit.dto.PieceDTO;
import org.example.outfit.model.Piece;
import org.example.outfit.model.Wardrobe;
import org.springframework.stereotype.Component;

@Component
public class PieceMapperImpl implements PieceMapper {

    @Override
    public PieceDTO pieceToPieceDTO(Piece piece) {
        if (piece == null) {
            return null;
        }

        PieceDTO pieceDTO = new PieceDTO();
        pieceDTO.setId(piece.getId());
        pieceDTO.setCategory(piece.getCategory());
        pieceDTO.setStatus(piece.getStatus());
        pieceDTO.setDescription(piece.getDescription());
        pieceDTO.setColors(piece.getColors());
        pieceDTO.setSeasons(piece.getSeasons());
        pieceDTO.setPrice(piece.getPrice());
        pieceDTO.setPurchaseDate(piece.getPurchaseDate());
        pieceDTO.setStyle(piece.getStyle());
        pieceDTO.setOccasions(piece.getOccasions());
        pieceDTO.setFabric(piece.getFabric());
        pieceDTO.setType(piece.getType());
        pieceDTO.setPattern(piece.isPattern());
        pieceDTO.setRating(piece.getRating());
        pieceDTO.setImageUrl(piece.getImageUrl());
        pieceDTO.setAvailable(piece.isAvailable());

        // Only map the wardrobe ID to avoid circular references
        if (piece.getWardrobe() != null) {
            pieceDTO.setWardrobeId(piece.getWardrobe().getId());
        }

        return pieceDTO;
    }

    @Override
    public Piece pieceDTOToPiece(PieceDTO pieceDTO) {
        if (pieceDTO == null) {
            return null;
        }

        Piece piece = new Piece();
        piece.setId(pieceDTO.getId());
        piece.setCategory(pieceDTO.getCategory());
        piece.setStatus(pieceDTO.getStatus());
        piece.setDescription(pieceDTO.getDescription());
        piece.setColors(pieceDTO.getColors());
        piece.setSeasons(pieceDTO.getSeasons());
        piece.setPrice(pieceDTO.getPrice());
        piece.setPurchaseDate(pieceDTO.getPurchaseDate());
        piece.setStyle(pieceDTO.getStyle());
        piece.setOccasions(pieceDTO.getOccasions());
        piece.setFabric(pieceDTO.getFabric());
        piece.setType(pieceDTO.getType());
        piece.setPattern(pieceDTO.isPattern());
        piece.setRating(pieceDTO.getRating());
        piece.setImageUrl(pieceDTO.getImageUrl());
        piece.setAvailable(pieceDTO.isAvailable());

        // Create a Wardrobe with just the ID to avoid circular references
        if (pieceDTO.getWardrobeId() != null) {
            Wardrobe wardrobe = new Wardrobe();
            wardrobe.setId(pieceDTO.getWardrobeId());
            piece.setWardrobe(wardrobe);
        }

        return piece;
    }
}