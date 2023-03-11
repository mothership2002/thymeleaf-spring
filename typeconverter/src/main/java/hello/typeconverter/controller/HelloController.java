package hello.typeconverter.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @GetMapping( "/hello-v1" )
    public String helloV1( HttpServletRequest req, HttpServletResponse resp ) {
        String data = req.getParameter( "data" );
        checkLog( data , data.getClass());
        return "ok";
    }

    @GetMapping("/hello-v2")
    public String helloV2 ( @RequestParam Integer data ) {
        checkLog( data , data.getClass());
        return "ok";
    }

    public static void checkLog(Object data, Class<?> classType) {
        log.info( "Data = {}" , data );
        log.info( "Data-Type = {}" , classType );
    }
}
