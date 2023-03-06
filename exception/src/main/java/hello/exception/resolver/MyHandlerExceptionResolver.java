package hello.exception.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex ) {
        try {
            if ( ex instanceof IllegalArgumentException ) {
                response.sendError( HttpServletResponse.SC_BAD_REQUEST, ex.getMessage() );
                log.info( " IllegalArgumentException resolver is 400" );
                return new ModelAndView();
            }
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }

        return null;
    }

}
