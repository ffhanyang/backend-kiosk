package com.ffhanyang.kiosk.controller.commons;

import com.ffhanyang.kiosk.error.NotFoundException;
import com.ffhanyang.kiosk.error.ServiceRuntimeException;
import com.ffhanyang.kiosk.error.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.ffhanyang.kiosk.controller.commons.ApiResult.ERROR;

@ControllerAdvice
public class GeneralExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ResponseEntity<ApiResult<?>> newResponse(Throwable throwable, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(ERROR(throwable, status), headers, status);
    }

    // 헨들러 못찾음
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        return newResponse(e, HttpStatus.NOT_FOUND);
    }

    /**
     * 종료된 스트림 혹은 잘못된 상태
     * null전달 등 잘못된 인자
     * 파라미터 타입의 불일치
     * JSON 파싱 실패 혹은 JSON 잘못 전달
     * 경로 파라미터 안주거나 잘못된 경로 파라미터
     * 파일 업로드 실패
     */
    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class,
        TypeMismatchException.class, HttpMessageNotReadableException.class,
        MissingServletRequestParameterException.class, MultipartException.class})
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        log.debug("Bad request exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }

    // 지원하지 않는 미디어 타입 요청
    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<?> handleHttpMediaTypeException(Exception e) {
        return newResponse(e, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    // 지원하지 않는 메소드 요청
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowedException(Exception e) {
        return newResponse(e, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 서비스 런타임 에러(직접 정의했던 에러들)
    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity<?> handleServiceRuntimeException(ServiceRuntimeException e) {
        if (e instanceof NotFoundException) {
            return newResponse(e, HttpStatus.NOT_FOUND);
        }
        if (e instanceof UnauthorizedException) {
            return newResponse(e, HttpStatus.UNAUTHORIZED);
        }

        log.warn("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 진짜 예상치 못한 에러
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
