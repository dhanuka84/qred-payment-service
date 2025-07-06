package org.qred.payment.exception.handler;

import java.text.MessageFormat;

import org.qred.payment.exception.ErrorResponse;
import org.qred.payment.exception.Http400BadRequest;
import org.qred.payment.exception.Http404NotFoundException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import lombok.extern.slf4j.Slf4j;
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler
{
    @ExceptionHandler(Http404NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException( Http404NotFoundException ex )
    {
        return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( new ErrorResponse( "Not Found", ex.getMessage(), HttpStatus.NOT_FOUND ) );
    }

    @ExceptionHandler(Http400BadRequest.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException( Http400BadRequest ex )
    {
        ErrorResponse errorResponse = new ErrorResponse( "Bad Request", ex.getMessage(), HttpStatus.BAD_REQUEST );
        if( !ex.getErrors().isEmpty() )
        {
            errorResponse.getErrors().addAll( ex.getErrors() );
        }
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( errorResponse );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException( MethodArgumentNotValidException ex )
    {
        log.error( "Field validation error occurred while processing game request", ex );
        ErrorResponse errorResponse = new ErrorResponse( "Bad Request", "Validation failed for one or more fields.", HttpStatus.BAD_REQUEST );
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errorResponse.getErrors().add( MessageFormat.format( "{0} : {1}",((FieldError) error).getField(), error.getDefaultMessage()  ));
        });

        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( errorResponse );
    }
    
   

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleConcurrentModificationException( Exception ex )
    {
        log.error( "Concurrency error occurred while processing game request", ex );
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( new ErrorResponse( "Bad Request", "Object Already Modified by a different user.", HttpStatus.BAD_REQUEST ) );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException( Exception ex )
    {
        log.error( "An error occurred while processing game request", ex );
        return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( new ErrorResponse( "Internal Server Error", "Error wile processing the Request.", HttpStatus.INTERNAL_SERVER_ERROR ) );
    }
    
    //DTO validatoin at API level
    
}