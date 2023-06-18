package com.example.backendForDemoday;

import com.example.backendForDemoday.movies.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {
    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private Review review;

    @BeforeEach
    void setUp() {
        review = new Review("Review Body", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    public void testCreateReview() {
        // Arrange
        Map<String, String> payload = new HashMap<>();
        payload.put("reviewBody", "Review Body");
        payload.put("imdbId", "123");

        when(reviewService.createReview("Review Body", "123")).thenReturn(review);

        // Act
        ResponseEntity<Review> response = reviewController.createReview(payload);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(review, response.getBody());
        verify(reviewService, times(1)).createReview("Review Body", "123");
    }


}

