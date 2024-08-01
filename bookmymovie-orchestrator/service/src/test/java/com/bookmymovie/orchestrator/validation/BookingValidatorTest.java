package com.bookmymovie.orchestrator.validation;

import com.bookmymovie.orchestrator.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookingValidatorTest {

    private BookingValidator bookingValidatorUnderTest;

    @BeforeEach
    void setUp() {
        bookingValidatorUnderTest = new BookingValidator();
        bookingValidatorUnderTest.seatBookLimit = 0;
    }

    @Test
    void testValidateBooking() {
        // Setup
        final BookingRequest bookingRequest = BookingRequest.builder()
                .booking(Booking.builder()
                        .viewerId(0L)
                        .firstName("firstName")
                        .lastName("lastName")
                        .mobile("mobile")
                        .email("email")
                        .movieId(0L)
                        .movieName("movieName")
                        .theaterId(0L)
                        .theaterName("theaterName")
                        .screenId(0L)
                        .screenName("screenName")
                        .showdate("showdate")
                        .seatBook(List.of(SeatBook.builder().build()))
                        .payment(Payment.builder()
                                .paymentCategory("paymentCategory")
                                .upi(UPI.builder()
                                        .upiId("upiId")
                                        .upiName("upiName")
                                        .build())
                                .netBanking(NetBanking.builder()
                                        .bankName("bankName")
                                        .netBankingId("netBankingId")
                                        .netBankingPassword("netBankingPassword")
                                        .build())
                                .debitCard(DebitCard.builder()
                                        .bankName("bankName")
                                        .cardNumber("cardNumber")
                                        .cvv("cvv")
                                        .expiry("expiry")
                                        .build())
                                .creditCard(CreditCard.builder()
                                        .bankName("bankName")
                                        .cardNumber("cardNumber")
                                        .cvv("cvv")
                                        .expiry("expiry")
                                        .build())
                                .build())
                        .build())
                .build();

        // Run the test
        final Boolean result = bookingValidatorUnderTest.validateBooking(bookingRequest);

        // Verify the results
        assertThat(result).isFalse();
    }
}
