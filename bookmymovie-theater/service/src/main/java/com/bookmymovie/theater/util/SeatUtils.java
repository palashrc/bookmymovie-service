package com.bookmymovie.theater.util;

import com.bookmymovie.theater.model.Screen;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SeatUtils {

    public static List<com.bookmymovie.theater.entity.Seat> generateSeatsForScreen(Screen screen, List<com.bookmymovie.theater.entity.Seat> seatEntityList, Long screenId) {
        log.info("Total Rows in this Screen = " + screen.getTotalRows());
        log.info("Number of Seats in each Row = " + screen.getNumberOfSeatsInEachRow());
        log.info("Row-Name list = " + screen.getRowNames());
        log.info("Row-Name with Row-Type mapping = " + screen.getRowNameTypeMap());
        log.info("Row-Type with Row-Price mapping = " + screen.getRowTypePriceMap());
        log.info("START - Seats distribution for this Screen:");
        System.out.println();
        for(int r = 0; r < screen.getTotalRows(); r++) {
            for(int c = 1; c <= screen.getNumberOfSeatsInEachRow(); c++) {
                System.out.print(screen.getRowNames().get(r).trim() + c + "-" + screen.getRowNameTypeMap().get(screen.getRowNames().get(r).trim()) + "-" + screen.getRowTypePriceMap().get(screen.getRowNameTypeMap().get(screen.getRowNames().get(r).trim())) + ", ");
                com.bookmymovie.theater.entity.Seat seatEntity = new com.bookmymovie.theater.entity.Seat();
                seatEntity.setScreenId(screenId);
                seatEntity.setSeatNumber(screen.getRowNames().get(r).trim() + c);
                seatEntity.setSeatType(screen.getRowNameTypeMap().get(screen.getRowNames().get(r).trim()));
                seatEntity.setSeatPrice(screen.getRowTypePriceMap().get(screen.getRowNameTypeMap().get(screen.getRowNames().get(r).trim())));
                seatEntity.setOperational(Boolean.TRUE);
                seatEntityList.add(seatEntity);
            }
            System.out.println();
        }
        System.out.println();
        log.info("COMPLETED - Seats distribution for this Screen.");
        System.out.println(seatEntityList);
        System.out.println(seatEntityList.size());

        return seatEntityList;
    }
}
