package com.bookmymovie.cinema.helper;

import com.bookmymovie.cinema.model.MovieResponse;
import com.bookmymovie.cinema.model.MovieShowResponse;
import com.bookmymovie.core.error.Error;
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

    public void mapSuccessCodeMsg(MovieResponse movieResponse) {
        movieResponse.setSuccessCode(successCode);
        movieResponse.setSuccessMessage(successMessage);
    }

    public void mapSuccessCodeMsg(MovieShowResponse movieShowResponse) {
        movieShowResponse.setSuccessCode(successCode);
        movieShowResponse.setSuccessMessage(successMessage);
    }

    public Error mapErrorCodeMsg(String exceptionType) {
        Error error = new Error();
        error.setErrCode(!errorCodeMap.get(exceptionType).isEmpty() ? errorCodeMap.get(exceptionType) : "005");
        error.setErrMessage(!errorMsgMap.get(exceptionType).isEmpty() ?errorMsgMap.get(exceptionType) : "Error!");
        return error;
    }
}
