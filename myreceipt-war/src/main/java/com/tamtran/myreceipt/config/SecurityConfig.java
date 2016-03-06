package com.tamtran.myreceipt.config;

/**
 * Created by Tam Tran on 2/21/2015.
 */

import com.tamtran.myreceipt.MyAuthEntryPoint;
import com.tamtran.myreceipt.exception.mapper.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("edu.umsl.esi.floorplan")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    MyAuthEntryPoint myAuthEntryPoint;

    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.inMemoryAuthentication().withUser("user").password("user123").roles("USER");
        auth.inMemoryAuthentication().withUser("manager").password("manager123").roles("MANAGER");
        auth.inMemoryAuthentication().withUser("admin").password("admin123").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        myAccessDeniedHandler.setErrorPage("error");
        http
            .authorizeRequests()
                .antMatchers("/", "/list*", "/resources/**", "/accessdenied").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/home").failureUrl("/home?error")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/logout")
                .and()
                .exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
//            .exceptionHandling().accessDeniedPage("/accessdenied");
//            .exceptionHandling().authenticationEntryPoint(myAuthEntryPoint);
//        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
