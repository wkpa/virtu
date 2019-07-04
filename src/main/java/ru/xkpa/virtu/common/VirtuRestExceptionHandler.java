package ru.xkpa.virtu.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Kurakin
 */
@ControllerAdvice
public class VirtuRestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(VirtuException.class)
    @ResponseBody
    public ResponseEntity<Object> handleException(final VirtuException ex) {
        return new ResponseEntity<>(new VirtuResponse<>(VirtuResponseStatus.ERROR, ex.getMessage()), HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new VirtuResponse<>(VirtuResponseStatus.ERROR, ex.getMessage()), HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new VirtuResponse<>(VirtuResponseStatus.ERROR, ex.getMessage()), HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.getBindingResult().getFieldErrors();
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getField() + " - " + fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(new VirtuResponse<>(VirtuResponseStatus.ERROR, errors), HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new VirtuResponse<>(VirtuResponseStatus.ERROR, "Parameter is incorrect"), HttpStatus.OK);
    }

}
