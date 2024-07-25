package com.bookmymovie.payment.helper;

import com.bookmymovie.core.error.Error;
import com.bookmymovie.payment.model.PaymentResponseAck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@Slf4j
@PropertySource("classpath:application.yml")
public class StatusMapper {

    @Value("${acknowledgement.code}")
    String ackCode;

    @Value("${success.code}")
    String successCode;

    @Value("${success.msg}")
    String successMessage;

    @Value("#{${error.code.map}}")
    private Map<String, String> errorCodeMap;

    @Value("#{${error.msg.map}}")
    private Map<String, String> errorMsgMap;

    public void mapSuccessCodeMsg(PaymentResponseAck paymentResponseAck) {
        paymentResponseAck.setSuccessCode(successCode);
        paymentResponseAck.setSuccessMessage(successMessage);
    }

    public void mapAckCode(PaymentResponseAck paymentResponseAck) {
        paymentResponseAck.setAcknowledgeCode(ackCode);
    }

    public Error mapErrorCodeMsg(String exceptionType) {
        Error error = new Error();
        error.setErrCode(!errorCodeMap.get(exceptionType).isEmpty() ? errorCodeMap.get(exceptionType) : "005");
        error.setErrMessage(!errorMsgMap.get(exceptionType).isEmpty() ?errorMsgMap.get(exceptionType) : "Error!");
        return error;
    }
}
