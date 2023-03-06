package hello.exception;

import hello.exception.filter.LogFilter;
import hello.exception.interceptor.LogInterceptor;
import hello.exception.resolver.MyHandlerExceptionResolver;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LogInterceptor())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns(
//                        "/css/**", "/*.ico"
//                        , "/error", "/error-page/**" //오류 페이지 경로
//                ); 
//    }


    @Override
    public void extendHandlerExceptionResolvers( List<HandlerExceptionResolver> resolvers ) {
        resolvers.add( new MyHandlerExceptionResolver() );
    }

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter( new LogFilter() );
        filterRegistrationBean.setOrder( 1 );
        filterRegistrationBean.addUrlPatterns( "/*" );
        filterRegistrationBean.setDispatcherTypes( DispatcherType.REQUEST );
        // 에러에는 필터를 실행 할 이유가 없음

        return filterRegistrationBean;
    }

}
