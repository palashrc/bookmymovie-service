package com.bookmymovie.theater.helper;

import com.bookmymovie.core.error.Error;
import com.bookmymovie.theater.model.CityResponse;
import com.bookmymovie.theater.model.ScreenResponse;
import com.bookmymovie.theater.model.SeatResponse;
import com.bookmymovie.theater.model.TheaterResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@Slf4j
@PropertySource("classpath:application.yml")
public class StatusMapper {

    @Value("${success.code}")
    String successCode;

    @Value("${success.msg}")
    String successMessage;

    @Value("#{${error.code.map}}")
    private Map<String, String> errorCodeMap;

    @Value("#{${error.msg.map}}")
    private Map<String, String> errorMsgMap;

    public void mapSuccessCodeMsg(CityResponse cityResponse) {
        cityResponse.setSuccessCode(successCode);
        cityResponse.setSuccessMessage(successMessage);
    }

    public void mapSuccessCodeMsg(TheaterResponse theaterResponse) {
        theaterResponse.setSuccessCode(successCode);
        theaterResponse.setSuccessMessage(successMessage);
    }

    public void mapSuccessCodeMsg(ScreenResponse screenResponse) {
        screenResponse.setSuccessCode(successCode);
        screenResponse.setSuccessMessage(successMessage);
    }

    public void mapSuccessCodeMsg(SeatResponse seatResponse) {
        seatResponse.setSuccessCode(successCode);
        seatResponse.setSuccessMessage(successMessage);
    }

    public Error mapErrorCodeMsg(String exceptionType) {
        Error error = new Error();
        error.setErrCode(!errorCodeMap.get(exceptionType).isEmpty() ? errorCodeMap.get(exceptionType) : "005");
        error.setErrMessage(!errorMsgMap.get(exceptionType).isEmpty() ?errorMsgMap.get(exceptionType) : "Error!");
        return error;
    }
}
