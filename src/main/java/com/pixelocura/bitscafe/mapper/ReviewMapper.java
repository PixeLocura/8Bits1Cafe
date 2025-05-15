package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.ReviewDTO;
import com.pixelocura.bitscafe.model.entity.Review;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper {
    private final ModelMapper modelMapper;

    public ReviewDTO toDTO(Review review) {
        ReviewDTO dto = modelMapper.map(review, ReviewDTO.class);
        dto.setUserId(review.getUser().getId());
        dto.setGameId(review.getGame().getId());
        return dto;
    }
}