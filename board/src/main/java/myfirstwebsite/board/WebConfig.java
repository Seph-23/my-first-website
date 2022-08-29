package myfirstwebsite.board;

import myfirstwebsite.board.interceptor.LogInterceptor;
import myfirstwebsite.board.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LogInterceptor())
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/css/**", "/*.ico", "/error");

    registry.addInterceptor(new LoginCheckInterceptor())
          .order(2)
          .addPathPatterns("/**")
          .excludePathPatterns("/", "/members/login", "/login/loginForm", "/logout", "/css/**",
            "/error");
  }
}
