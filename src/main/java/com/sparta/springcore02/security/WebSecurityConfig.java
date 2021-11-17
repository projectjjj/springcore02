package com.sparta.springcore02.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                // image 폴더를 login 없이 허용
                .antMatchers("/images/**").permitAll()
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                //어떤 요청이 오더라도 로그인 과정이 없으면 로그인하도록함.
                //심지어 css 파일까지도 인증받아야한다. 그래서 ... 위에 2줄 추가.
                .and()
                .formLogin()
                .loginPage("/user/login")
                .failureUrl("/user/login/error")
                //적어도 로그인 페이지는
                .defaultSuccessUrl("/")
                //로그인 완료되었을 때 이동할 페이지
                .permitAll()
                //허용
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .permitAll()
                //로그아웃도 허용
                .and()
                .exceptionHandling()
                .accessDeniedPage("/user/forbidden");
    }
}