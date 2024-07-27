package com.bookmymovie.orchestrator.validation;

import com.bookmymovie.orchestrator.model.BookingRequest;
import com.bookmymovie.orchestrator.model.SeatBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import java.util.List;

@Component
@Slf4j
public class BookingValidator {

    @Value("${seat.book.limit}")
    Integer seatBookLimit;

    public Boolean validateBooking(BookingRequest bookingRequest) {
        Boolean validationConfirmation = Boolean.FALSE;

        if(!ObjectUtils.isEmpty(bookingRequest)) {
            if(!ObjectUtils.isEmpty(bookingRequest.getBooking())) {
                if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getViewerId())) {
                    if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getFirstName())) {
                        if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getLastName())) {
                            if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getMobile())) {
                                if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getEmail())) {
                                    if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getMovieId())) {
                                        if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getMovieName())) {
                                            if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getTheaterId())) {
                                                if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getTheaterName())) {
                                                    if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getScreenId())) {
                                                        if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getScreenName())) {
                                                            if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getShowdate())) {
                                                                if(!CollectionUtils.isEmpty(bookingRequest.getBooking().getSeatBook())) {
                                                                    if(validateSeatBookLimit(bookingRequest.getBooking().getSeatBook())) {
                                                                        if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment())) {
                                                                            if(validatePayment(bookingRequest)) {
                                                                                validationConfirmation = Boolean.TRUE;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return validationConfirmation;
    }

    private Boolean validateSeatBookLimit(List<SeatBook> seatBook) {
        Boolean seatBookValidationConfirmation = Boolean.FALSE;
        if(seatBook.size() < seatBookLimit) {
            seatBookValidationConfirmation = Boolean.TRUE;
        }
        return seatBookValidationConfirmation;
    }

    private Boolean validatePayment(BookingRequest bookingRequest) {
        Boolean paymentValidationConfirmation = Boolean.FALSE;

        if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getPaymentCategory())) {

            if (bookingRequest.getBooking().getPayment().getPaymentCategory().equalsIgnoreCase(PaymentMode.UPI.toString())) {
                if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getUpi())) {
                    if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getUpi().getUpiId())) {
                        if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getUpi().getUpiName())) {
                            paymentValidationConfirmation = Boolean.TRUE;
                        }
                    }
                }
            }

            if (bookingRequest.getBooking().getPayment().getPaymentCategory().equalsIgnoreCase(PaymentMode.DEBITCARD.toString())) {
                if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getDebitCard())) {
                    if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getDebitCard().getCardNumber())) {
                        if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getDebitCard().getBankName())) {
                            if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getDebitCard().getCvv())) {
                                if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getDebitCard().getExpiry())) {
                                    paymentValidationConfirmation = Boolean.TRUE;
                                }
                            }
                        }
                    }
                }
            }

            if (bookingRequest.getBooking().getPayment().getPaymentCategory().equalsIgnoreCase(PaymentMode.CREDITCARD.toString())) {
                if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getCreditCard())) {
                    if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getCreditCard().getCardNumber())) {
                        if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getCreditCard().getBankName())) {
                            if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getCreditCard().getCvv())) {
                                if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getCreditCard().getExpiry())) {
                                    paymentValidationConfirmation = Boolean.TRUE;
                                }
                            }
                        }
                    }
                }
            }

            if (bookingRequest.getBooking().getPayment().getPaymentCategory().equalsIgnoreCase(PaymentMode.NETBANKING.toString())) {
                if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getNetBanking())) {
                    if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getNetBanking().getBankName())) {
                        if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getNetBanking().getNetBankingId())) {
                            if(!ObjectUtils.isEmpty(bookingRequest.getBooking().getPayment().getNetBanking().getNetBankingPassword())) {
                                    paymentValidationConfirmation = Boolean.TRUE;
                            }
                        }
                    }
                }
            }
        }

        return  paymentValidationConfirmation;
    }

    enum PaymentMode {
        PAYLATER,
        DEBITCARD,
        CREDITCARD,
        UPI,
        NETBANKING
    }
}
