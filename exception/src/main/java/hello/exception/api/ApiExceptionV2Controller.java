package hello.exception.api;

import hello.exception.exhandler.ErrorResult;
import hello.exception.web.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

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


    @GetMapping( "/api2/members/{id}" )
    public MemberDto getMember( @PathVariable String id ) {
        if ( id.equals( "ex" ) ) {
            throw new RuntimeException( "ex" );
        }

        if ( id.equals( "bad" ) ) {
            throw new IllegalArgumentException();
        }

        if ( id.equals( "user-ex" ) ) {
            throw new UserException();
        }

        return new MemberDto( id, "hello " + id );
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {

        private String memberId;
        private String name;

    }

}
