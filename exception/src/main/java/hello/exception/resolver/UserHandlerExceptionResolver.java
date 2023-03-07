package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.web.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private static final String CONTENT_TYPE = "application/json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex ) {
        try {
            if ( ex instanceof UserException ) {
                log.info( "UserException resolver to 400" );
                String accept = request.getHeader( "accept" );
                response.setStatus( HttpServletResponse.SC_BAD_REQUEST );
                if ( accept.equals( CONTENT_TYPE ) ) {

                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put( "ex", ex.getClass() );
                    errorResult.put( "message", ex.getMessage() );

                    response.setContentType( CONTENT_TYPE );
                    response.setCharacterEncoding( "UTF-8" );
                    response.getWriter().write( objectMapper.writeValueAsString( errorResult ) );

                    return new ModelAndView();
                }
                else {
                    return new ModelAndView( "error/4xx" );
                }

            }
        }
        catch ( IOException e ) {
            log.info( "ex ", e );
        }

        return null;
    }

}
