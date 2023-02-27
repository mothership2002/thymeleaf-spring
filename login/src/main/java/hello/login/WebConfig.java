package hello.login;

import hello.login.web.argumentResolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Autowired
//    public LoginCheckFilter loginCheckFilter;

//    @Bean
//    public FilterRegistrationBean logFilter() {
//        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
//
//        filterFilterRegistrationBean.setFilter(new LogFilter()); // loginCheckFilter
//        filterFilterRegistrationBean.setOrder(1);
//        filterFilterRegistrationBean.addUrlPatterns("/*");
//
//        return filterFilterRegistrationBean;
//    }

//    @Bean
//    public FilterRegistrationBean loginFilter() {
//        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
//
//        filterFilterRegistrationBean.setFilter(new loginCheckFilter());
//        filterFilterRegistrationBean.setOrder(2);
//        filterFilterRegistrationBean.addUrlPatterns("/*");
//
//        return filterFilterRegistrationBean;
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }
}
