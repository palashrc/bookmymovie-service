package com.bookmymovie.cinema.service;

import com.bookmymovie.cinema.constant.ExceptionConstants;
import com.bookmymovie.cinema.converter.MovieConverter;
import com.bookmymovie.cinema.helper.StatusMapper;
import com.bookmymovie.cinema.model.Movie;
import com.bookmymovie.cinema.model.MovieRequest;
import com.bookmymovie.cinema.model.MovieResponse;
import com.bookmymovie.cinema.repository.MovieRepository;
import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.Error;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieConverter mockMovieConverter;
    @Mock
    private MovieRepository mockMovieRepository;
    @Mock
    private StatusMapper mockStatusMapper;

    @InjectMocks
    private MovieService movieServiceUnderTest;

    @Test
    void testSaveMovie() throws Exception {
        // Setup
        final MovieRequest MovieRequest = com.bookmymovie.cinema.model.MovieRequest.builder()
                .movie(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieResponse expectedResult = MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure MovieConverter.convertModelToEntity(...).
        final com.bookmymovie.cinema.entity.Movie movie = com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build();
        when(mockMovieConverter.convertModelToEntity(Movie.builder()
                .movieId(0L)
                .operational(false)
                .build())).thenReturn(movie);

        // Configure MovieRepository.save(...).
        final com.bookmymovie.cinema.entity.Movie movie1 = com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build();
        when(mockMovieRepository.save(com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build())).thenReturn(movie1);

        // Configure MovieConverter.convertEntityToModel(...).
        final Movie movie2 = Movie.builder()
                .movieId(0L)
                .operational(false)
                .build();
        when(mockMovieConverter.convertEntityToModel(com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build())).thenReturn(movie2);

        // Run the test
        final MovieResponse result = movieServiceUnderTest.saveMovie(MovieRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testSaveMovie_MovieConverterConvertModelToEntityThrowsCoversionException() throws Exception {
        // Setup
        final MovieRequest MovieRequest = com.bookmymovie.cinema.model.MovieRequest.builder()
                .movie(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieResponse expectedResult = MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockMovieConverter.convertModelToEntity(Movie.builder()
                .movieId(0L)
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final MovieResponse result = movieServiceUnderTest.saveMovie(MovieRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testSaveMovie_MovieConverterConvertEntityToModelThrowsCoversionException() throws Exception {
        // Setup
        final MovieRequest MovieRequest = com.bookmymovie.cinema.model.MovieRequest.builder()
                .movie(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieResponse expectedResult = MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure MovieConverter.convertModelToEntity(...).
        final com.bookmymovie.cinema.entity.Movie movie = com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build();
        when(mockMovieConverter.convertModelToEntity(Movie.builder()
                .movieId(0L)
                .operational(false)
                .build())).thenReturn(movie);

        // Configure MovieRepository.save(...).
        final com.bookmymovie.cinema.entity.Movie movie1 = com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build();
        when(mockMovieRepository.save(com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build())).thenReturn(movie1);

        when(mockMovieConverter.convertEntityToModel(com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final MovieResponse result = movieServiceUnderTest.saveMovie(MovieRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetMovieById() throws Exception {
        // Setup
        final MovieRequest movieRequest = MovieRequest.builder()
                .movie(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieResponse expectedResult = MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure MovieRepository.findById(...).
        final Optional<com.bookmymovie.cinema.entity.Movie> movie = Optional.of(
                com.bookmymovie.cinema.entity.Movie.builder()
                        .operational(false)
                        .build());
        when(mockMovieRepository.findById(0L)).thenReturn(movie);

        // Configure MovieConverter.convertEntityToModel(...).
        final Movie movie1 = Movie.builder()
                .movieId(0L)
                .operational(false)
                .build();
        when(mockMovieConverter.convertEntityToModel(com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build())).thenReturn(movie1);

        // Run the test
        final MovieResponse result = movieServiceUnderTest.getMovieById(movieRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testGetMovieById_MovieRepositoryReturnsAbsent() {
        // Setup
        final MovieRequest movieRequest = MovieRequest.builder()
                .movie(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieResponse expectedResult = MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockMovieRepository.findById(0L)).thenReturn(Optional.empty());
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final MovieResponse result = movieServiceUnderTest.getMovieById(movieRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetMovieById_MovieConverterThrowsCoversionException() throws Exception {
        // Setup
        final MovieRequest movieRequest = MovieRequest.builder()
                .movie(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieResponse expectedResult = MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure MovieRepository.findById(...).
        final Optional<com.bookmymovie.cinema.entity.Movie> movie = Optional.of(
                com.bookmymovie.cinema.entity.Movie.builder()
                        .operational(false)
                        .build());
        when(mockMovieRepository.findById(0L)).thenReturn(movie);

        when(mockMovieConverter.convertEntityToModel(com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final MovieResponse result = movieServiceUnderTest.getMovieById(movieRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateMovieOperational() throws Exception {
        // Setup
        final MovieRequest movieRequest = MovieRequest.builder()
                .movie(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieResponse expectedResult = MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure MovieRepository.findById(...).
        final Optional<com.bookmymovie.cinema.entity.Movie> movie = Optional.of(
                com.bookmymovie.cinema.entity.Movie.builder()
                        .operational(false)
                        .build());
        when(mockMovieRepository.findById(0L)).thenReturn(movie);

        // Configure MovieRepository.save(...).
        final com.bookmymovie.cinema.entity.Movie movie1 = com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build();
        when(mockMovieRepository.save(com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build())).thenReturn(movie1);

        // Configure MovieConverter.convertEntityToModel(...).
        final Movie movie2 = Movie.builder()
                .movieId(0L)
                .operational(false)
                .build();
        when(mockMovieConverter.convertEntityToModel(com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build())).thenReturn(movie2);

        // Run the test
        final MovieResponse result = movieServiceUnderTest.updateMovieOperational(movieRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testUpdateMovieOperational_MovieRepositoryFindByIdReturnsAbsent() {
        // Setup
        final MovieRequest movieRequest = MovieRequest.builder()
                .movie(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieResponse expectedResult = MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockMovieRepository.findById(0L)).thenReturn(Optional.empty());
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final MovieResponse result = movieServiceUnderTest.updateMovieOperational(movieRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateMovieOperational_MovieConverterThrowsCoversionException() throws Exception {
        // Setup
        final MovieRequest movieRequest = MovieRequest.builder()
                .movie(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieResponse expectedResult = MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure MovieRepository.findById(...).
        final Optional<com.bookmymovie.cinema.entity.Movie> movie = Optional.of(
                com.bookmymovie.cinema.entity.Movie.builder()
                        .operational(false)
                        .build());
        when(mockMovieRepository.findById(0L)).thenReturn(movie);

        // Configure MovieRepository.save(...).
        final com.bookmymovie.cinema.entity.Movie movie1 = com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build();
        when(mockMovieRepository.save(com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build())).thenReturn(movie1);

        when(mockMovieConverter.convertEntityToModel(com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final MovieResponse result = movieServiceUnderTest.updateMovieOperational(movieRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetMovieByIdForUser() throws Exception {
        // Setup
        final MovieRequest movieRequest = MovieRequest.builder()
                .movie(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieResponse expectedResult = MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure MovieRepository.findById(...).
        final Optional<com.bookmymovie.cinema.entity.Movie> movie = Optional.of(
                com.bookmymovie.cinema.entity.Movie.builder()
                        .operational(false)
                        .build());
        when(mockMovieRepository.findById(0L)).thenReturn(movie);

        // Configure MovieConverter.convertEntityToModel(...).
        final Movie movie1 = Movie.builder()
                .movieId(0L)
                .operational(false)
                .build();
        when(mockMovieConverter.convertEntityToModel(com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build())).thenReturn(movie1);

        // Run the test
        final MovieResponse result = movieServiceUnderTest.getMovieByIdForUser(movieRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testGetMovieByIdForUser_MovieRepositoryReturnsAbsent() {
        // Setup
        final MovieRequest movieRequest = MovieRequest.builder()
                .movie(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieResponse expectedResult = MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockMovieRepository.findById(0L)).thenReturn(Optional.empty());
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final MovieResponse result = movieServiceUnderTest.getMovieByIdForUser(movieRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetMovieByIdForUser_MovieConverterThrowsCoversionException() throws Exception {
        // Setup
        final MovieRequest movieRequest = MovieRequest.builder()
                .movie(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieResponse expectedResult = MovieResponse.builder()
                .movie(List.of(Movie.builder()
                        .movieId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure MovieRepository.findById(...).
        final Optional<com.bookmymovie.cinema.entity.Movie> movie = Optional.of(
                com.bookmymovie.cinema.entity.Movie.builder()
                        .operational(false)
                        .build());
        when(mockMovieRepository.findById(0L)).thenReturn(movie);

        when(mockMovieConverter.convertEntityToModel(com.bookmymovie.cinema.entity.Movie.builder()
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final MovieResponse result = movieServiceUnderTest.getMovieByIdForUser(movieRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
