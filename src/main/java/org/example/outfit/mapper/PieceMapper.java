package org.example.outfit.mapper;

import org.example.outfit.dto.PieceDTO;
import org.example.outfit.model.Piece;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PieceMapper {

    @Mapping(source = "wardrobe.id", target = "wardrobeId")
    PieceDTO pieceToPieceDTO(Piece piece);

    @Mapping(source = "wardrobeId", target = "wardrobe.id")
    Piece pieceDTOToPiece(PieceDTO pieceDTO);
}