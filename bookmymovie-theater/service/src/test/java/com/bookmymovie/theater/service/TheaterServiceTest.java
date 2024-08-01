package com.bookmymovie.theater.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.Error;
import com.bookmymovie.theater.constant.ExceptionConstants;
import com.bookmymovie.theater.converter.TheaterConverter;
import com.bookmymovie.theater.helper.StatusMapper;
import com.bookmymovie.theater.model.Theater;
import com.bookmymovie.theater.model.TheaterRequest;
import com.bookmymovie.theater.model.TheaterResponse;
import com.bookmymovie.theater.repository.TheaterRepository;
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
class TheaterServiceTest {

    @Mock
    private TheaterConverter mockTheaterConverter;
    @Mock
    private TheaterRepository mockTheaterRepository;
    @Mock
    private StatusMapper mockStatusMapper;

    @InjectMocks
    private TheaterService theaterServiceUnderTest;

    @Test
    void testSaveTheater() throws Exception {
        // Setup
        final TheaterRequest theaterRequest = TheaterRequest.builder()
                .theater(Theater.builder()
                        .theaterId(0L)
                        .operational(false)
                        .build())
                .build();
        final TheaterResponse expectedResult = TheaterResponse.builder()
                .theaters(List.of(Theater.builder()
                        .theaterId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure TheaterConverter.convertModelToEntity(...).
        final com.bookmymovie.theater.entity.Theater theater = com.bookmymovie.theater.entity.Theater.builder()
                .operational(false)
                .build();
        when(mockTheaterConverter.convertModelToEntity(Theater.builder()
                .theaterId(0L)
                .operational(false)
                .build())).thenReturn(theater);

        // Configure TheaterRepository.save(...).
        final com.bookmymovie.theater.entity.Theater theater1 = com.bookmymovie.theater.entity.Theater.builder()
                .operational(false)
                .build();
        when(mockTheaterRepository.save(com.bookmymovie.theater.entity.Theater.builder()
                .operational(false)
                .build())).thenReturn(theater1);

        // Configure TheaterConverter.convertEntityToModel(...).
        final Theater theater2 = Theater.builder()
                .theaterId(0L)
                .operational(false)
                .build();
        when(mockTheaterConverter.convertEntityToModel(com.bookmymovie.theater.entity.Theater.builder()
                .operational(false)
                .build())).thenReturn(theater2);

        // Run the test
        final TheaterResponse result = theaterServiceUnderTest.saveTheater(theaterRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(TheaterResponse.builder()
                .theaters(List.of(Theater.builder()
                        .theaterId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testSaveTheater_TheaterConverterConvertModelToEntityThrowsCoversionException() throws Exception {
        // Setup
        final TheaterRequest theaterRequest = TheaterRequest.builder()
                .theater(Theater.builder()
                        .theaterId(0L)
                        .operational(false)
                        .build())
                .build();
        final TheaterResponse expectedResult = TheaterResponse.builder()
                .theaters(List.of(Theater.builder()
                        .theaterId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockTheaterConverter.convertModelToEntity(Theater.builder()
                .theaterId(0L)
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final TheaterResponse result = theaterServiceUnderTest.saveTheater(theaterRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testSaveTheater_TheaterConverterConvertEntityToModelThrowsCoversionException() throws Exception {
        // Setup
        final TheaterRequest theaterRequest = TheaterRequest.builder()
                .theater(Theater.builder()
                        .theaterId(0L)
                        .operational(false)
                        .build())
                .build();
        final TheaterResponse expectedResult = TheaterResponse.builder()
                .theaters(List.of(Theater.builder()
                        .theaterId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure TheaterConverter.convertModelToEntity(...).
        final com.bookmymovie.theater.entity.Theater theater = com.bookmymovie.theater.entity.Theater.builder()
                .operational(false)
                .build();
        when(mockTheaterConverter.convertModelToEntity(Theater.builder()
                .theaterId(0L)
                .operational(false)
                .build())).thenReturn(theater);

        // Configure TheaterRepository.save(...).
        final com.bookmymovie.theater.entity.Theater theater1 = com.bookmymovie.theater.entity.Theater.builder()
                .operational(false)
                .build();
        when(mockTheaterRepository.save(com.bookmymovie.theater.entity.Theater.builder()
                .operational(false)
                .build())).thenReturn(theater1);

        when(mockTheaterConverter.convertEntityToModel(com.bookmymovie.theater.entity.Theater.builder()
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final TheaterResponse result = theaterServiceUnderTest.saveTheater(theaterRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateTheaterOperational() throws Exception {
        // Setup
        final TheaterRequest theaterRequest = TheaterRequest.builder()
                .theater(Theater.builder()
                        .theaterId(0L)
                        .operational(false)
                        .build())
                .build();
        final TheaterResponse expectedResult = TheaterResponse.builder()
                .theaters(List.of(Theater.builder()
                        .theaterId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure TheaterRepository.findById(...).
        final Optional<com.bookmymovie.theater.entity.Theater> theater = Optional.of(
                com.bookmymovie.theater.entity.Theater.builder()
                        .operational(false)
                        .build());
        when(mockTheaterRepository.findById(0L)).thenReturn(theater);

        // Configure TheaterRepository.save(...).
        final com.bookmymovie.theater.entity.Theater theater1 = com.bookmymovie.theater.entity.Theater.builder()
                .operational(false)
                .build();
        when(mockTheaterRepository.save(com.bookmymovie.theater.entity.Theater.builder()
                .operational(false)
                .build())).thenReturn(theater1);

        // Configure TheaterConverter.convertEntityToModel(...).
        final Theater theater2 = Theater.builder()
                .theaterId(0L)
                .operational(false)
                .build();
        when(mockTheaterConverter.convertEntityToModel(com.bookmymovie.theater.entity.Theater.builder()
                .operational(false)
                .build())).thenReturn(theater2);

        // Run the test
        final TheaterResponse result = theaterServiceUnderTest.updateTheaterOperational(theaterRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(TheaterResponse.builder()
                .theaters(List.of(Theater.builder()
                        .theaterId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testUpdateTheaterOperational_TheaterRepositoryFindByIdReturnsAbsent() {
        // Setup
        final TheaterRequest theaterRequest = TheaterRequest.builder()
                .theater(Theater.builder()
                        .theaterId(0L)
                        .operational(false)
                        .build())
                .build();
        final TheaterResponse expectedResult = TheaterResponse.builder()
                .theaters(List.of(Theater.builder()
                        .theaterId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockTheaterRepository.findById(0L)).thenReturn(Optional.empty());
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final TheaterResponse result = theaterServiceUnderTest.updateTheaterOperational(theaterRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateTheaterOperational_TheaterConverterThrowsCoversionException() throws Exception {
        // Setup
        final TheaterRequest theaterRequest = TheaterRequest.builder()
                .theater(Theater.builder()
                        .theaterId(0L)
                        .operational(false)
                        .build())
                .build();
        final TheaterResponse expectedResult = TheaterResponse.builder()
                .theaters(List.of(Theater.builder()
                        .theaterId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure TheaterRepository.findById(...).
        final Optional<com.bookmymovie.theater.entity.Theater> theater = Optional.of(
                com.bookmymovie.theater.entity.Theater.builder()
                        .operational(false)
                        .build());
        when(mockTheaterRepository.findById(0L)).thenReturn(theater);

        // Configure TheaterRepository.save(...).
        final com.bookmymovie.theater.entity.Theater theater1 = com.bookmymovie.theater.entity.Theater.builder()
                .operational(false)
                .build();
        when(mockTheaterRepository.save(com.bookmymovie.theater.entity.Theater.builder()
                .operational(false)
                .build())).thenReturn(theater1);

        when(mockTheaterConverter.convertEntityToModel(com.bookmymovie.theater.entity.Theater.builder()
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final TheaterResponse result = theaterServiceUnderTest.updateTheaterOperational(theaterRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
