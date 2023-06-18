package com.example.backendForDemoday;

import com.example.backendForDemoday.movies.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie("123", "Movie Title", "2023-05-28", "https://example.com/trailer",
                "https://example.com/poster", Arrays.asList("https://example.com/backdrop1", "https://example.com/backdrop2"),
                Arrays.asList("Action", "Thriller"));
    }

    @Test
    public void testFindAllMovies() {
        // Arrange
        List<Movie> movies = Arrays.asList(movie);
        when(movieRepository.findAll()).thenReturn(movies);

        // Act
        List<Movie> result = movieService.findAllMovies();

        // Assert
        assertEquals(movies, result);
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    public void testFindMovieByImdbId() {
        // Arrange
        Optional<Movie> optionalMovie = Optional.of(movie);
        when(movieRepository.findMovieByImdbId("123")).thenReturn(optionalMovie);

        // Act
        Optional<Movie> result = movieService.findMovieByImdbId("123");

        // Assert
        assertEquals(optionalMovie, result);
        verify(movieRepository, times(1)).findMovieByImdbId("123");
    }

    @Test
    public void testFindAllMovies_NoMoviesFound() {
        // Arrange
        when(movieRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Movie> result = movieService.findAllMovies();

        // Assert
        assertEquals(Arrays.asList(), result);
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    public void testFindMovieByImdbId_MovieNotFound() {
        // Arrange
        when(movieRepository.findMovieByImdbId("123")).thenReturn(Optional.empty());

        // Act
        Optional<Movie> result = movieService.findMovieByImdbId("123");

        // Assert
        assertEquals(Optional.empty(), result);
        verify(movieRepository, times(1)).findMovieByImdbId("123");
    }
}

