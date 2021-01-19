package com.github.springsecuritydemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                    .antMatchers("/product/**").hasRole("USER")
//                    .antMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().and()
//                .httpBasic();

        http
                .authorizeRequests()
                .antMatchers("/product/**").hasAuthority("USER")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
     }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // in memory test
//        auth
//            .inMemoryAuthentication()
//            .withUser("admin").password("{noop}adminpass").roles("ADMIN", "USER") // 管理员，同事具有 ADMIN,USER权限，可以访问所有资源
//            .and()
//            .withUser("spring").password("{noop}123456").roles("USER"); // 普通用户，只能访问有限的资源。


         //db user test
        auth.userDetailsService(userDetailsService)// 设置自定义的userDetailsService
                .passwordEncoder(passwordEncoder());

     }

     @Autowired
     private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        //为了演示方便，我们使用NoOpPasswordEncoder（这个就是不加密）
        return NoOpPasswordEncoder.getInstance();
    }

}
