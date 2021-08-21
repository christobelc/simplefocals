package com.github.simplefocals.configuation;

import com.github.simplefocals.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    //authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    //authorisation of web end-points
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                //The following endpoints are accessible to unauthenticated users
            .antMatchers("/").permitAll()
            .antMatchers("/shop/**").permitAll()
            .antMatchers("/register").permitAll()
                //The following endpoints are accessible to authenticated Customer
                .antMatchers("/profile/**").hasAuthority("CUSTOMER")
                .antMatchers("/create-payment-intent").hasAuthority("CUSTOMER")
                .antMatchers("/payment").hasAuthority("CUSTOMER")
                //The following endpoints are accessible to authenticated Admin
            .antMatchers("/admin/**").hasAuthority("ADMIN")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login").permitAll()
            .defaultSuccessUrl("/")
            .usernameParameter("email")
            .passwordParameter("password")
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .and()
            .exceptionHandling()
            .and()
            .csrf()
            .disable();
    }

    //configuration for password encoder
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //allows access to resources directories that contain static content
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**"
                                            , "static/**"
                                            , "/images/**"
                                            , "/productImages/**"
                                            , "/prescription/**"
                                            , "/css/**"
                                            , "/js/**");
    }
}
