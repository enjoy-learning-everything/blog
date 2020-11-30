package cn.xinglongfei.blog.config;

import cn.xinglongfei.blog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Phoenix on 2020/11/17
 */
@Configuration
//public class WebConfig extends WebMvcConfigurerAdapter {
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //设置拦截器，拦截管理页面，但不拦截管理页面的登录页面和登录提交页面
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/admin/**")
                .excludePathPatterns("/admin").excludePathPatterns("/admin/login");
    }
}
