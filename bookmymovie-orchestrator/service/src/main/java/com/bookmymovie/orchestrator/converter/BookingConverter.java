package com.bookmymovie.orchestrator.converter;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.orchestrator.model.BookingRequest;
import com.bookmymovie.orchestrator.model.order.Order;
import com.bookmymovie.orchestrator.model.order.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookingConverter {

    public OrderRequest convertBookingToOrder(BookingRequest bookingRequest) throws CoversionException {
        if (bookingRequest == null) {
            log.error("Booking to Order Conversion Failed!");
            throw new CoversionException();
        }
        if (bookingRequest.getBooking() == null) {
            log.error("Booking to Order Conversion Failed!");
            throw new CoversionException();
        }
        OrderRequest orderRequest = new OrderRequest();
        Order order = new Order();
        order.setBookingId(bookingRequest.getBooking().getBookingId());
        order.setViewerId(bookingRequest.getBooking().getViewerId());
        order.setFirstName(bookingRequest.getBooking().getFirstName());
        order.setLastName(bookingRequest.getBooking().getLastName());
        order.setMobile(bookingRequest.getBooking().getMobile());
        order.setEmail(bookingRequest.getBooking().getEmail());
        order.setMovieId(bookingRequest.getBooking().getMovieId());
        order.setMovieCertificate(bookingRequest.getBooking().getMovieCertificate());
        order.setTheaterId(bookingRequest.getBooking().getTheaterId());
        order.setTheaterName(bookingRequest.getBooking().getTheaterName());
        order.setScreenId(bookingRequest.getBooking().getScreenId());
        order.setScreenName(bookingRequest.getBooking().getScreenName());
        order.setShowdate(bookingRequest.getBooking().getShowdate());
        order.setSeatBook(bookingRequest.getBooking().getSeatBook());
        orderRequest.setOrder(order);
        return orderRequest;
    }
}
