package hello.exception.exhandler.advice;

import hello.exception.exhandler.ErrorResult;
import hello.exception.web.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "hello.exception.api.advice")
public class ExControllerAdvice {

    @ExceptionHandler( IllegalArgumentException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ErrorResult illegalExHandler( IllegalArgumentException ex ) {
        log.error( "[exceptionHandler] ex", ex );
        return new ErrorResult( "BAD", ex.getMessage() );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler( UserException ex ) {
        log.error( "[exceptionHandler] ex", ex );
        return new ResponseEntity<>( new ErrorResult( "USER-EX", ex.getMessage() ), HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    public ErrorResult exHandler( Exception ex ) {
        log.error( "[exceptionHandler] ex", ex );
        return new ErrorResult( "EX", "내부 오류" );
    }


}
