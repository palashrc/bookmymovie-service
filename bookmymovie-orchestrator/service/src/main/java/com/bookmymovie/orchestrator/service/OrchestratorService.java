package com.bookmymovie.orchestrator.service;

import com.bookmymovie.core.error.BookingException;
import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.orchestrator.constant.CommonConstants;
import com.bookmymovie.orchestrator.converter.BookingConverter;
import com.bookmymovie.orchestrator.constant.ExceptionConstants;
import com.bookmymovie.orchestrator.helper.StatusMapper;
import com.bookmymovie.orchestrator.metrics.MetricsContainerService;
import com.bookmymovie.orchestrator.model.*;
import com.bookmymovie.orchestrator.validation.BookingValidator;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Service
@Slf4j
public class OrchestratorService {

    private String txnId;

    @Value("${order.service.url}")
    String orderServiceUrl;

    @Autowired
    private BookingValidator bookingValidator;

    @Autowired
    private BookingConverter bookingConverter;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private OrchRequestTrackerService orchRequestTrackerService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MetricsContainerService metricsContainerService;

    public BookingResponseAck createBooking(BookingRequest bookingRequest) {
        BookingResponseAck ack = new BookingResponseAck();
        try {
            init(bookingRequest, ack);
            OrderRequest orderRequest = bookingConverter.convertBookingToOrder(bookingRequest, txnId);
            statusMapper.mapAckCode(ack);
            statusMapper.mapSuccessCodeMsg(ack);
            Thread thread = new Thread(new OrderAsyncProcessServiceThread(orderRequest));
            thread.start();
        } catch (BookingException ex) {
            log.error("BookingException Occurs!");
            ex.printStackTrace();
            metricsContainerService.incrementOfBookingErrCountMetric();
            ack.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.BOOKING_EXCEPTION_TYPE));
        } catch (CoversionException ex) {
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            ack.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            ack.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            ack.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        } finally {
            end(txnId, ack);
        }
        return ack;
    }

    private void init(BookingRequest bookingRequest, BookingResponseAck ack) throws BookingException {
        UUID uuid = UUID.randomUUID();
        String txnId = CommonConstants.TXN_PREFIX + uuid.toString().replaceAll("-","");
        this.txnId = txnId;
        ack.setTransactionId(txnId);
        orchRequestTrackerService.createTracker(txnId);
        if(!bookingValidator.validateBooking(bookingRequest)) {
            log.error("Booking Failed!");
            throw new BookingException();
        }
    }

    private void end(String txnId, BookingResponseAck ack) {
        orchRequestTrackerService.updateTrackerForInterimRes(txnId, ack);
    }

    class OrderAsyncProcessServiceThread implements Runnable {
        OrderRequest orderRequest;
        public OrderAsyncProcessServiceThread(OrderRequest orderRequest) {
            this.orderRequest = orderRequest;
        }
        @Override
        public void run() {
            log.info("Order Processing Asynchronous Flow...");
            try {
                 HttpEntity<OrderRequest> orderRequestHttpEntity = new HttpEntity<>(orderRequest);
                 ResponseEntity<OrderResponseAck> orderAckResponseEntity = restTemplate.exchange(orderServiceUrl, HttpMethod.POST, orderRequestHttpEntity, OrderResponseAck.class);
                 if (orderAckResponseEntity.getStatusCode().is2xxSuccessful()) {
                    log.info("Order Microservice Interim Response: " + orderAckResponseEntity.getBody());
                 } else {
                    log.error("Order Microservice Interim Response Error!");
                 }
            } catch(Exception ex) {
                log.error("Exception Occurs for Order Microservice Interim Connectivity!");
                ex.printStackTrace();
                metricsContainerService.incrementOfOrchToOrderErrCountMetric();
            }
        }
    }
}
