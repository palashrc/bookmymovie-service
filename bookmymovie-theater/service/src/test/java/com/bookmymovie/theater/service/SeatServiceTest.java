package com.bookmymovie.theater.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.Error;
import com.bookmymovie.theater.constant.ExceptionConstants;
import com.bookmymovie.theater.converter.ScreenConverter;
import com.bookmymovie.theater.converter.SeatConverter;
import com.bookmymovie.theater.entity.Screen;
import com.bookmymovie.theater.helper.StatusMapper;
import com.bookmymovie.theater.model.Seat;
import com.bookmymovie.theater.model.SeatRequest;
import com.bookmymovie.theater.model.SeatResponse;
import com.bookmymovie.theater.repository.ScreenRepository;
import com.bookmymovie.theater.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @Mock
    private ScreenConverter mockScreenConverter;
    @Mock
    private SeatConverter mockSeatConverter;
    @Mock
    private ScreenRepository mockScreenRepository;
    @Mock
    private SeatRepository mockSeatRepository;
    @Mock
    private StatusMapper mockStatusMapper;

    @InjectMocks
    private SeatService seatServiceUnderTest;

    @Test
    void testCreateSeats() throws Exception {
        // Setup
        final SeatRequest seatRequest = SeatRequest.builder()
                .seat(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build())
                .build();
        final SeatResponse expectedResult = SeatResponse.builder()
                .seats(List.of(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockScreenRepository.findById(0L)).thenReturn(Optional.of(Screen.builder().build()));

        // Configure ScreenConverter.convertEntityToModel(...).
        final com.bookmymovie.theater.model.Screen screen = com.bookmymovie.theater.model.Screen.builder()
                .totalRows(0)
                .numberOfSeatsInEachRow(0)
                .rowNames(List.of("value"))
                .rowNameTypeMap(Map.ofEntries(Map.entry("value", "value")))
                .rowTypePriceMap(Map.ofEntries(Map.entry("value", "value")))
                .build();
        when(mockScreenConverter.convertEntityToModel(Screen.builder().build())).thenReturn(screen);

        // Configure SeatRepository.saveAll(...).
        final Iterable<com.bookmymovie.theater.entity.Seat> seats = List.of(
                com.bookmymovie.theater.entity.Seat.builder()
                        .screenId(0L)
                        .seatNumber("seatNumber")
                        .seatType("seatType")
                        .seatPrice("seatPrice")
                        .operational(false)
                        .build());
        when(mockSeatRepository.saveAll(List.of(com.bookmymovie.theater.entity.Seat.builder()
                .screenId(0L)
                .seatNumber("seatNumber")
                .seatType("seatType")
                .seatPrice("seatPrice")
                .operational(false)
                .build()))).thenReturn(seats);

        // Run the test
        final SeatResponse result = seatServiceUnderTest.createSeats(seatRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(SeatResponse.builder()
                .seats(List.of(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testCreateSeats_ScreenRepositoryReturnsAbsent() {
        // Setup
        final SeatRequest seatRequest = SeatRequest.builder()
                .seat(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build())
                .build();
        final SeatResponse expectedResult = SeatResponse.builder()
                .seats(List.of(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockScreenRepository.findById(0L)).thenReturn(Optional.empty());
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final SeatResponse result = seatServiceUnderTest.createSeats(seatRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testCreateSeats_ScreenConverterThrowsCoversionException() throws Exception {
        // Setup
        final SeatRequest seatRequest = SeatRequest.builder()
                .seat(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build())
                .build();
        final SeatResponse expectedResult = SeatResponse.builder()
                .seats(List.of(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockScreenRepository.findById(0L)).thenReturn(Optional.of(Screen.builder().build()));
        when(mockScreenConverter.convertEntityToModel(Screen.builder().build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final SeatResponse result = seatServiceUnderTest.createSeats(seatRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testCreateSeats_SeatRepositoryReturnsNoItems() throws Exception {
        // Setup
        final SeatRequest seatRequest = SeatRequest.builder()
                .seat(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build())
                .build();
        final SeatResponse expectedResult = SeatResponse.builder()
                .seats(List.of(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockScreenRepository.findById(0L)).thenReturn(Optional.of(Screen.builder().build()));

        // Configure ScreenConverter.convertEntityToModel(...).
        final com.bookmymovie.theater.model.Screen screen = com.bookmymovie.theater.model.Screen.builder()
                .totalRows(0)
                .numberOfSeatsInEachRow(0)
                .rowNames(List.of("value"))
                .rowNameTypeMap(Map.ofEntries(Map.entry("value", "value")))
                .rowTypePriceMap(Map.ofEntries(Map.entry("value", "value")))
                .build();
        when(mockScreenConverter.convertEntityToModel(Screen.builder().build())).thenReturn(screen);

        when(mockSeatRepository.saveAll(List.of(com.bookmymovie.theater.entity.Seat.builder()
                .screenId(0L)
                .seatNumber("seatNumber")
                .seatType("seatType")
                .seatPrice("seatPrice")
                .operational(false)
                .build()))).thenReturn(Collections.emptyList());

        // Run the test
        final SeatResponse result = seatServiceUnderTest.createSeats(seatRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(SeatResponse.builder()
                .seats(List.of(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testUpdateSeatOperational() throws Exception {
        // Setup
        final SeatRequest seatRequest = SeatRequest.builder()
                .seat(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build())
                .build();
        final SeatResponse expectedResult = SeatResponse.builder()
                .seats(List.of(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure SeatRepository.findById(...).
        final Optional<com.bookmymovie.theater.entity.Seat> seat = Optional.of(
                com.bookmymovie.theater.entity.Seat.builder()
                        .screenId(0L)
                        .seatNumber("seatNumber")
                        .seatType("seatType")
                        .seatPrice("seatPrice")
                        .operational(false)
                        .build());
        when(mockSeatRepository.findById(0L)).thenReturn(seat);

        // Configure SeatRepository.save(...).
        final com.bookmymovie.theater.entity.Seat seat1 = com.bookmymovie.theater.entity.Seat.builder()
                .screenId(0L)
                .seatNumber("seatNumber")
                .seatType("seatType")
                .seatPrice("seatPrice")
                .operational(false)
                .build();
        when(mockSeatRepository.save(com.bookmymovie.theater.entity.Seat.builder()
                .screenId(0L)
                .seatNumber("seatNumber")
                .seatType("seatType")
                .seatPrice("seatPrice")
                .operational(false)
                .build())).thenReturn(seat1);

        // Configure SeatConverter.convertEntityToModel(...).
        final Seat seat2 = Seat.builder()
                .seatId(0L)
                .screenId(0L)
                .operational(false)
                .build();
        when(mockSeatConverter.convertEntityToModel(com.bookmymovie.theater.entity.Seat.builder()
                .screenId(0L)
                .seatNumber("seatNumber")
                .seatType("seatType")
                .seatPrice("seatPrice")
                .operational(false)
                .build())).thenReturn(seat2);

        // Run the test
        final SeatResponse result = seatServiceUnderTest.updateSeatOperational(seatRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(SeatResponse.builder()
                .seats(List.of(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testUpdateSeatOperational_SeatRepositoryFindByIdReturnsAbsent() {
        // Setup
        final SeatRequest seatRequest = SeatRequest.builder()
                .seat(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build())
                .build();
        final SeatResponse expectedResult = SeatResponse.builder()
                .seats(List.of(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockSeatRepository.findById(0L)).thenReturn(Optional.empty());
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final SeatResponse result = seatServiceUnderTest.updateSeatOperational(seatRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateSeatOperational_SeatConverterThrowsCoversionException() throws Exception {
        // Setup
        final SeatRequest seatRequest = SeatRequest.builder()
                .seat(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build())
                .build();
        final SeatResponse expectedResult = SeatResponse.builder()
                .seats(List.of(Seat.builder()
                        .seatId(0L)
                        .screenId(0L)
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure SeatRepository.findById(...).
        final Optional<com.bookmymovie.theater.entity.Seat> seat = Optional.of(
                com.bookmymovie.theater.entity.Seat.builder()
                        .screenId(0L)
                        .seatNumber("seatNumber")
                        .seatType("seatType")
                        .seatPrice("seatPrice")
                        .operational(false)
                        .build());
        when(mockSeatRepository.findById(0L)).thenReturn(seat);

        // Configure SeatRepository.save(...).
        final com.bookmymovie.theater.entity.Seat seat1 = com.bookmymovie.theater.entity.Seat.builder()
                .screenId(0L)
                .seatNumber("seatNumber")
                .seatType("seatType")
                .seatPrice("seatPrice")
                .operational(false)
                .build();
        when(mockSeatRepository.save(com.bookmymovie.theater.entity.Seat.builder()
                .screenId(0L)
                .seatNumber("seatNumber")
                .seatType("seatType")
                .seatPrice("seatPrice")
                .operational(false)
                .build())).thenReturn(seat1);

        when(mockSeatConverter.convertEntityToModel(com.bookmymovie.theater.entity.Seat.builder()
                .screenId(0L)
                .seatNumber("seatNumber")
                .seatType("seatType")
                .seatPrice("seatPrice")
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final SeatResponse result = seatServiceUnderTest.updateSeatOperational(seatRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
