package com.example.UberProject_ReviewService.adapters;


import com.example.UberProject_ReviewService.Dtos.CreateReviewDto;
import com.example.UberProject_ReviewService.models.Review;

public interface CreateReviewDtoToReviewAdapter {

    public Review createDto(CreateReviewDto createReviewDto);
}
