package com.bookmymovie.theater.util;

import com.bookmymovie.theater.entity.Seat;
import com.bookmymovie.theater.model.Screen;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SeatUtilsTest {

    @Test
    void testGenerateSeatsForScreen() {
        // Setup
        final Screen screen = Screen.builder()
                .totalRows(0)
                .numberOfSeatsInEachRow(0)
                .rowNames(List.of("value"))
                .rowNameTypeMap(Map.ofEntries(Map.entry("value", "value")))
                .rowTypePriceMap(Map.ofEntries(Map.entry("value", "value")))
                .build();
        final List<Seat> seatEntityList = List.of(Seat.builder()
                .screenId(0L)
                .seatNumber("seatNumber")
                .seatType("seatType")
                .seatPrice("seatPrice")
                .operational(false)
                .build());
        final List<Seat> expectedResult = List.of(Seat.builder()
                .screenId(0L)
                .seatNumber("seatNumber")
                .seatType("seatType")
                .seatPrice("seatPrice")
                .operational(false)
                .build());

        // Run the test
        final List<Seat> result = SeatUtils.generateSeatsForScreen(screen, seatEntityList, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
