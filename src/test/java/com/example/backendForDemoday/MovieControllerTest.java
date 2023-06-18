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

import java.util.Collections;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {
    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie("123", "Movie Title", "2023-05-28", "https://example.com/trailer",
                "https://example.com/poster", Arrays.asList("https://example.com/backdrop1", "https://example.com/backdrop2"),
                Arrays.asList("Action", "Thriller"));
    }

    @Test
    public void testGetMovies() {
        // Arrange
        List<Movie> movies = Arrays.asList(movie);
        when(movieService.findAllMovies()).thenReturn(movies);

        // Act
        ResponseEntity<List<Movie>> response = movieController.getMovies();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movies, response.getBody());
        verify(movieService, times(1)).findAllMovies();
    }

    @Test
    public void testGetSingleMovie() {
        // Arrange
        Optional<Movie> optionalMovie = Optional.of(movie);
        when(movieService.findMovieByImdbId("123")).thenReturn(optionalMovie);

        // Act
        ResponseEntity<Optional<Movie>> response = movieController.getSingleMovie("123");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(optionalMovie, response.getBody());
        verify(movieService, times(1)).findMovieByImdbId("123");
    }

    @Test
    public void testGetMovies_NoMoviesFound() {
        // Arrange
        when(movieService.findAllMovies()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Movie>> response = movieController.getMovies();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(movieService, times(1)).findAllMovies();
    }


}

