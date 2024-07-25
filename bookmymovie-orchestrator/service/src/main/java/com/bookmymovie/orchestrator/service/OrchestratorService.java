package com.bookmymovie.orchestrator.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.orchestrator.constant.CommonConstants;
import com.bookmymovie.orchestrator.converter.BookingConverter;
import com.bookmymovie.orchestrator.constant.ExceptionConstants;
import com.bookmymovie.orchestrator.helper.StatusMapper;
import com.bookmymovie.orchestrator.model.BookingRequest;
import com.bookmymovie.orchestrator.model.BookingResponseAck;
import com.bookmymovie.orchestrator.model.OrderRequest;
import com.bookmymovie.orchestrator.model.OrderResponseAck;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Value("${order.service.url}")
    String orderServiceUrl;

    @Autowired
    private BookingConverter bookingConverter;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private OrchRequestTrackerService orchRequestTrackerService;

    @Autowired
    private RestTemplate restTemplate;

    public BookingResponseAck createBooking(BookingRequest bookingRequest) {
        BookingResponseAck ack = new BookingResponseAck();
        String txnId = StringUtils.EMPTY;
        try {
            txnId = init();
            ack.setTransactionId(txnId);
            OrderRequest orderRequest = bookingConverter.convertBookingToOrder(bookingRequest, txnId);
            statusMapper.mapAckCode(ack);
            statusMapper.mapSuccessCodeMsg(ack);
            Thread thread = new Thread(new OrderAsyncProcessServiceThread(orderRequest));
            thread.start();
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

    private String init() {
        UUID uuid = UUID.randomUUID();
        String txnId = CommonConstants.TXN_PREFIX + uuid.toString().replaceAll("-","");
        orchRequestTrackerService.createTracker(txnId);
        return txnId;
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
            }
        }
    }
}
