package com.bookmymovie.orchestrator.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.orchestrator.converter.BookingConverter;
import com.bookmymovie.orchestrator.constant.CommonConstants;
import com.bookmymovie.orchestrator.constant.ExceptionConstants;
import com.bookmymovie.orchestrator.helper.StatusMapper;
import com.bookmymovie.orchestrator.model.BookingRequest;
import com.bookmymovie.orchestrator.model.BookingResponseAcknowledge;
import com.bookmymovie.orchestrator.model.order.OrderRequest;
import com.bookmymovie.orchestrator.model.order.OrderResponse;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@Slf4j
public class OrchestratorService {

    @Autowired
    private BookingConverter bookingConverter;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private RestTemplate restTemplate;

    public BookingResponseAcknowledge createBooking(BookingRequest bookingRequest) {
        BookingResponseAcknowledge ack = new BookingResponseAcknowledge();
        try {
            String txnId = init(bookingRequest);
            ack.setTransactionId(txnId);
            OrderRequest orderRequest = bookingConverter.convertBookingToOrder(bookingRequest);
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
            end();
        }
        return ack;
    }

    private String init(BookingRequest bookingRequest) {
        UUID uuid = UUID.randomUUID();
        String txnId = "BKEX" + uuid.toString().replaceAll("-","");
        //Save BookingRequestTracker in DB: TxnId,BookingRequest,txnStatus,receiveReqTimestamp
        return txnId;
    }

    private void end() {
        //Update BookingRequestTracker in DB: TxnId,BookingInterimResponse,txnStatus,interimResTimestamp
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
                HttpEntity<OrderRequest> orderRequestHttpEntity = new HttpEntity<OrderRequest>(orderRequest);
                ResponseEntity<OrderResponse> orderResponseEntity = restTemplate.exchange(CommonConstants.API_ORDER_NEW, HttpMethod.POST, orderRequestHttpEntity, OrderResponse.class);
                if (orderResponseEntity.getStatusCode().is2xxSuccessful()) {
                    log.info("Order Microservice Interim Response: " + orderResponseEntity.getBody());
                } else {
                    log.error("Order Microservice Connectivity Error!");
                }
            } catch(Exception ex) {
                log.error("Exception Occurs!");
                ex.printStackTrace();
            }
        }
    }
}
