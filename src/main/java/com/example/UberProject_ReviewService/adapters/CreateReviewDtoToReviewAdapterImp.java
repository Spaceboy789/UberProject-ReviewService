package com.example.UberProject_ReviewService.adapters;


import com.example.UberProject_ReviewService.Dtos.CreateReviewDto;
import com.example.UberProject_ReviewService.Repositories.BookingRepository;
import com.example.UberProject_ReviewService.models.Booking;
import com.example.UberProject_ReviewService.models.Review;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateReviewDtoToReviewAdapterImp implements CreateReviewDtoToReviewAdapter {

    private final BookingRepository bookingRepository;

    public CreateReviewDtoToReviewAdapterImp(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Review createDto(CreateReviewDto createReviewDto) {
        Optional<Booking> booking = bookingRepository.findById(createReviewDto.getBookingId());

        return booking.map(value -> Review.builder()
                .rating(createReviewDto.getRating())
                .booking(value)
                .content(createReviewDto.getContent()).build()).orElse(null);
    }
}
