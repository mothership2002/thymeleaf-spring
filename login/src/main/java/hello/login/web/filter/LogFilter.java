package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [ {} ] , [ {} ] ", uuid, requestURI);
            chain.doFilter(request, response);  // 매우 중요
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [ {} ], [ {} ]", uuid, requestURI);
        }
        String[] a = {"Asdf","Asdfewr","!2354123","asdfwe"};
        Arrays.stream(a).forEach(s -> System.out.println(s));

    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
