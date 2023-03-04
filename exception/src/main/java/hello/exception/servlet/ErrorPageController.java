package hello.exception.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping( "/error-page/" )
public class ErrorPageController {

    //RequestDispatcher 상수로 정의되어 있음
    // colum selection           = edit -> shift +
    public static final String ERROR_EXCEPTION = "jakarta.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "jakarta.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "jakarta.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "jakarta.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "jakarta.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE = "jakarta.servlet.error.status_code";

    @RequestMapping( "/404" )
    public String errorPage404( HttpServletRequest req, HttpServletResponse resp ) {
        log.info( "404" );
        printErrorInfo( req );
        return "error-page/404";
    }

    @RequestMapping( "/500" )
    public String errorPage500( HttpServletRequest req, HttpServletResponse resp ) {
        log.info( "500" );
        printErrorInfo( req );
        return "error-page/500";
    }

    @RequestMapping( value = "/500", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Map<String, Object>> errorJson500( HttpServletRequest req, HttpServletResponse resp ) {

        log.info( "API ERROR PAGE 500" );
//        req.getAttributeNames().asIterator().forEachRemaining( e -> log.info( "attributeName, attribute {}, {}", e, req.getAttribute( e ) ) );

        HashMap<String, Object> resultMap = new HashMap<>();
        Exception ex = ( Exception ) req.getAttribute( ERROR_EXCEPTION );

        resultMap.put( "status", req.getAttribute( ERROR_STATUS_CODE ) );
        resultMap.put( "message", ex.getMessage() );

        Integer statusCode = ( Integer ) req.getAttribute( RequestDispatcher.ERROR_STATUS_CODE );
        return new ResponseEntity<>( resultMap, HttpStatus.valueOf( statusCode ) );
    }

    private void printErrorInfo( HttpServletRequest request ) {
//        request.getAttributeNames().asIterator().forEachRemaining(s->
//                log.info("name = {} ", s));
//        log.info((String) request.getAttribute("javax.servlet.error.exception_type"));
//        log.info((String) request.getAttribute("jakarta.servlet.error.exception_type"));
//
        log.info( "ERROR_EXCEPTION {} ", request.getAttribute( ERROR_EXCEPTION ) );
        log.info( "ERROR_EXCEPTION_TYPE {} ", request.getAttribute( ERROR_EXCEPTION_TYPE ) );
        log.info( "ERROR_MESSAGE {} ", request.getAttribute( ERROR_MESSAGE ) );
        log.info( "ERROR_REQUEST_URI {} ", request.getAttribute( ERROR_REQUEST_URI ) );
        log.info( "ERROR_SERVLET_NAME {} ", request.getAttribute( ERROR_SERVLET_NAME ) );
        log.info( "ERROR_STATUS_CODE {} ", request.getAttribute( ERROR_STATUS_CODE ) );
        log.info( "dispatcher type = {}", request.getDispatcherType() );
    }

}
