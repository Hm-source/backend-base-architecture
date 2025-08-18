package org.example.basic.configuration;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    private ProblemDetail build(HttpStatus status, String message) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, message);
        pd.setTitle(status.getReasonPhrase());
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> Map.of("field", e.getField(), "reason", e.getDefaultMessage()))
            .toList();

        log.warn("[400] MethodArgumentNotValidException: {}", errors);
        ProblemDetail pd = build(HttpStatus.BAD_REQUEST, "요청 바디 검증 실패");
        pd.setProperty("errors", errors);
        return pd;
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleConstraintViolation(
        jakarta.validation.ConstraintViolationException ex) {
        var errors = ex.getConstraintViolations().stream()
            .map(v -> Map.of("property", v.getPropertyPath().toString(), "reason", v.getMessage()))
            .toList();

        log.warn("[400] ConstraintViolationException: {}", errors);
        ProblemDetail pd = build(HttpStatus.BAD_REQUEST, "요청 파라미터/비즈니스 검증 실패");
        pd.setProperty("errors", errors);
        return pd;
    }

    @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class)
    public ResponseEntity<ProblemDetail> handleRSE(
        org.springframework.web.server.ResponseStatusException ex) {
        HttpStatus status = (HttpStatus) ex.getStatusCode();
        ProblemDetail pd = build(status, ex.getReason() != null ? ex.getReason() : "요청 처리 실패");
        log.warn("[{}] ResponseStatusException: {}", status.value(), ex.getReason());
        return ResponseEntity.status(status).body(pd);
    }

    @ExceptionHandler(org.springframework.validation.BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleBindException(org.springframework.validation.BindException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> Map.of("field", e.getField(), "reason", e.getDefaultMessage()))
            .toList();
        log.warn("[400] BindException: {}", errors);
        ProblemDetail pd = build(HttpStatus.BAD_REQUEST, "요청 파라미터 바인딩 실패");
        pd.setProperty("errors", errors);
        return pd;
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleNotReadable(
        org.springframework.http.converter.HttpMessageNotReadableException ex) {
        log.warn("[400] NotReadable: {}", ex.getMostSpecificCause().getMessage());
        return build(HttpStatus.BAD_REQUEST, "요청 본문을 읽을 수 없습니다(JSON 형식 확인)");
    }

    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleTypeMismatch(
        org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex) {
        String msg = "파라미터 타입 불일치: " + ex.getName() + " should be " + ex.getRequiredType();
        log.warn("[400] TypeMismatch: {}", msg);
        return build(HttpStatus.BAD_REQUEST, msg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail handleEtc(Exception ex) {
        log.error("[500] Unhandled", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류");
    }
}
