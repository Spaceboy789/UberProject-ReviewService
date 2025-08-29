package com.example.UberProject_ReviewService.controllers;


import com.example.UberProject_ReviewService.Dtos.CreateReviewDto;
import com.example.UberProject_ReviewService.Dtos.ReviewDto;
import com.example.UberProject_ReviewService.adapters.CreateReviewDtoToReviewAdapterImp;
import com.example.UberProject_ReviewService.models.Review;
import com.example.UberProject_ReviewService.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final CreateReviewDtoToReviewAdapterImp createReviewDtoToReviewAdapterImp;



    public ReviewController(ReviewService reviewService, CreateReviewDtoToReviewAdapterImp createReviewDtoToReviewAdapterImp) {
        this.reviewService = reviewService;
        this.createReviewDtoToReviewAdapterImp = createReviewDtoToReviewAdapterImp;
    }

    @PostMapping
    public ResponseEntity<?> publishReview(@RequestBody CreateReviewDto request) {
        Review incomingReview = this.createReviewDtoToReviewAdapterImp.createDto(request);
        if (incomingReview == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Review review = this.reviewService.publishReview(incomingReview);
        ReviewDto response = ReviewDto.builder().id(review.getId())
                .content(review.getContent())
                .booking(review.getBooking().getId())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt()).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<?> getReviewById(@PathVariable Long reviewId) {
           try{
               Optional<Review> review = this.reviewService.findReviewById(reviewId);
               if(review.isPresent()){
                   return new ResponseEntity<>(review.get(), HttpStatus.OK);
               }else{
                   return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body("Review not found with id: " + reviewId);
               }
           } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                       .body("An unexpected error occurred");
           }

    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReviewById(@PathVariable Long reviewId) {
        try {
            boolean isDeleted = this.reviewService.deleteReviewById(reviewId);
            if(!isDeleted) return new ResponseEntity<>("Unable to delete Review", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId, @RequestBody Review request){
        try {
            Review review = this.reviewService.updateReview(reviewId, request);
            return new ResponseEntity<>(review, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
