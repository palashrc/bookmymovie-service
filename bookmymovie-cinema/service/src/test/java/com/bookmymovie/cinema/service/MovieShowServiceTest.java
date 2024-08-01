package com.bookmymovie.cinema.service;

import com.bookmymovie.cinema.constant.ExceptionConstants;
import com.bookmymovie.cinema.converter.MovieShowConverter;
import com.bookmymovie.cinema.helper.StatusMapper;
import com.bookmymovie.cinema.model.MovieShow;
import com.bookmymovie.cinema.model.MovieShowRequest;
import com.bookmymovie.cinema.model.MovieShowResponse;
import com.bookmymovie.cinema.repository.MovieShowRepository;
import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.Error;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieShowServiceTest {

    @Mock
    private MovieShowConverter mockMovieShowConverter;
    @Mock
    private MovieShowRepository mockMovieShowRepository;
    @Mock
    private StatusMapper mockStatusMapper;

    @InjectMocks
    private MovieShowService movieShowServiceUnderTest;

    @Test
    void testCreateMovieShow() throws Exception {
        // Setup
        final MovieShowRequest movieShowRequest = MovieShowRequest.builder()
                .movieshow(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieShowResponse expectedResult = MovieShowResponse.builder()
                .movieshow(List.of(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure MovieShowConverter.convertModelToEntity(...).
        final com.bookmymovie.cinema.entity.MovieShow movieShow = com.bookmymovie.cinema.entity.MovieShow.builder()
                .operational(false)
                .build();
        when(mockMovieShowConverter.convertModelToEntity(MovieShow.builder()
                .movieShowId(0L)
                .movieId(0L)
                .cityId(0L)
                .operational(false)
                .build())).thenReturn(movieShow);

        // Configure MovieShowRepository.save(...).
        final com.bookmymovie.cinema.entity.MovieShow movieShow1 = com.bookmymovie.cinema.entity.MovieShow.builder()
                .operational(false)
                .build();
        when(mockMovieShowRepository.save(com.bookmymovie.cinema.entity.MovieShow.builder()
                .operational(false)
                .build())).thenReturn(movieShow1);

        // Configure MovieShowConverter.convertEntityToModel(...).
        final MovieShow movieShow2 = MovieShow.builder()
                .movieShowId(0L)
                .movieId(0L)
                .cityId(0L)
                .operational(false)
                .build();
        when(mockMovieShowConverter.convertEntityToModel(com.bookmymovie.cinema.entity.MovieShow.builder()
                .operational(false)
                .build())).thenReturn(movieShow2);

        // Run the test
        final MovieShowResponse result = movieShowServiceUnderTest.createMovieShow(movieShowRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(MovieShowResponse.builder()
                .movieshow(List.of(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testCreateMovieShow_MovieShowConverterConvertModelToEntityThrowsCoversionException() throws Exception {
        // Setup
        final MovieShowRequest movieShowRequest = MovieShowRequest.builder()
                .movieshow(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieShowResponse expectedResult = MovieShowResponse.builder()
                .movieshow(List.of(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockMovieShowConverter.convertModelToEntity(MovieShow.builder()
                .movieShowId(0L)
                .movieId(0L)
                .cityId(0L)
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final MovieShowResponse result = movieShowServiceUnderTest.createMovieShow(movieShowRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testCreateMovieShow_MovieShowConverterConvertEntityToModelThrowsCoversionException() throws Exception {
        // Setup
        final MovieShowRequest movieShowRequest = MovieShowRequest.builder()
                .movieshow(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieShowResponse expectedResult = MovieShowResponse.builder()
                .movieshow(List.of(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure MovieShowConverter.convertModelToEntity(...).
        final com.bookmymovie.cinema.entity.MovieShow movieShow = com.bookmymovie.cinema.entity.MovieShow.builder()
                .operational(false)
                .build();
        when(mockMovieShowConverter.convertModelToEntity(MovieShow.builder()
                .movieShowId(0L)
                .movieId(0L)
                .cityId(0L)
                .operational(false)
                .build())).thenReturn(movieShow);

        // Configure MovieShowRepository.save(...).
        final com.bookmymovie.cinema.entity.MovieShow movieShow1 = com.bookmymovie.cinema.entity.MovieShow.builder()
                .operational(false)
                .build();
        when(mockMovieShowRepository.save(com.bookmymovie.cinema.entity.MovieShow.builder()
                .operational(false)
                .build())).thenReturn(movieShow1);

        when(mockMovieShowConverter.convertEntityToModel(com.bookmymovie.cinema.entity.MovieShow.builder()
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final MovieShowResponse result = movieShowServiceUnderTest.createMovieShow(movieShowRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateMovieShowOperational() throws Exception {
        // Setup
        final MovieShowRequest movieShowRequest = MovieShowRequest.builder()
                .movieshow(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieShowResponse expectedResult = MovieShowResponse.builder()
                .movieshow(List.of(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure MovieShowRepository.findById(...).
        final Optional<com.bookmymovie.cinema.entity.MovieShow> movieShow = Optional.of(
                com.bookmymovie.cinema.entity.MovieShow.builder()
                        .operational(false)
                        .build());
        when(mockMovieShowRepository.findById(0L)).thenReturn(movieShow);

        // Configure MovieShowRepository.save(...).
        final com.bookmymovie.cinema.entity.MovieShow movieShow1 = com.bookmymovie.cinema.entity.MovieShow.builder()
                .operational(false)
                .build();
        when(mockMovieShowRepository.save(com.bookmymovie.cinema.entity.MovieShow.builder()
                .operational(false)
                .build())).thenReturn(movieShow1);

        // Configure MovieShowConverter.convertEntityToModel(...).
        final MovieShow movieShow2 = MovieShow.builder()
                .movieShowId(0L)
                .movieId(0L)
                .cityId(0L)
                .operational(false)
                .build();
        when(mockMovieShowConverter.convertEntityToModel(com.bookmymovie.cinema.entity.MovieShow.builder()
                .operational(false)
                .build())).thenReturn(movieShow2);

        // Run the test
        final MovieShowResponse result = movieShowServiceUnderTest.updateMovieShowOperational(movieShowRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(MovieShowResponse.builder()
                .movieshow(List.of(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testUpdateMovieShowOperational_MovieShowRepositoryFindByIdReturnsAbsent() {
        // Setup
        final MovieShowRequest movieShowRequest = MovieShowRequest.builder()
                .movieshow(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieShowResponse expectedResult = MovieShowResponse.builder()
                .movieshow(List.of(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockMovieShowRepository.findById(0L)).thenReturn(Optional.empty());
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final MovieShowResponse result = movieShowServiceUnderTest.updateMovieShowOperational(movieShowRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateMovieShowOperational_MovieShowConverterThrowsCoversionException() throws Exception {
        // Setup
        final MovieShowRequest movieShowRequest = MovieShowRequest.builder()
                .movieshow(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieShowResponse expectedResult = MovieShowResponse.builder()
                .movieshow(List.of(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure MovieShowRepository.findById(...).
        final Optional<com.bookmymovie.cinema.entity.MovieShow> movieShow = Optional.of(
                com.bookmymovie.cinema.entity.MovieShow.builder()
                        .operational(false)
                        .build());
        when(mockMovieShowRepository.findById(0L)).thenReturn(movieShow);

        // Configure MovieShowRepository.save(...).
        final com.bookmymovie.cinema.entity.MovieShow movieShow1 = com.bookmymovie.cinema.entity.MovieShow.builder()
                .operational(false)
                .build();
        when(mockMovieShowRepository.save(com.bookmymovie.cinema.entity.MovieShow.builder()
                .operational(false)
                .build())).thenReturn(movieShow1);

        when(mockMovieShowConverter.convertEntityToModel(com.bookmymovie.cinema.entity.MovieShow.builder()
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final MovieShowResponse result = movieShowServiceUnderTest.updateMovieShowOperational(movieShowRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetMovieShowByCityAndMovie() {
        // Setup
        final MovieShowRequest movieShowRequest = MovieShowRequest.builder()
                .movieshow(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieShowResponse expectedResult = MovieShowResponse.builder()
                .movieshow(List.of(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure MovieShowRepository.findByMovieIdAndCityId(...).
        final Optional<List<com.bookmymovie.cinema.entity.MovieShow>> movieShows = Optional.of(
                List.of(com.bookmymovie.cinema.entity.MovieShow.builder()
                        .operational(false)
                        .build()));
        when(mockMovieShowRepository.findByMovieIdAndCityId(0L, 0L)).thenReturn(movieShows);

        // Run the test
        final MovieShowResponse result = movieShowServiceUnderTest.getMovieShowByCityAndMovie(movieShowRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(MovieShowResponse.builder()
                .movieshow(List.of(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testGetMovieShowByCityAndMovie_MovieShowRepositoryReturnsAbsent() {
        // Setup
        final MovieShowRequest movieShowRequest = MovieShowRequest.builder()
                .movieshow(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieShowResponse expectedResult = MovieShowResponse.builder()
                .movieshow(List.of(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockMovieShowRepository.findByMovieIdAndCityId(0L, 0L)).thenReturn(Optional.empty());

        // Run the test
        final MovieShowResponse result = movieShowServiceUnderTest.getMovieShowByCityAndMovie(movieShowRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(MovieShowResponse.builder()
                .movieshow(List.of(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testGetMovieShowByCityAndMovie_MovieShowRepositoryReturnsNoItems() {
        // Setup
        final MovieShowRequest movieShowRequest = MovieShowRequest.builder()
                .movieshow(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build())
                .build();
        final MovieShowResponse expectedResult = MovieShowResponse.builder()
                .movieshow(List.of(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockMovieShowRepository.findByMovieIdAndCityId(0L, 0L)).thenReturn(Optional.of(Collections.emptyList()));

        // Run the test
        final MovieShowResponse result = movieShowServiceUnderTest.getMovieShowByCityAndMovie(movieShowRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(MovieShowResponse.builder()
                .movieshow(List.of(MovieShow.builder()
                        .movieShowId(0L)
                        .movieId(0L)
                        .cityId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }
}
