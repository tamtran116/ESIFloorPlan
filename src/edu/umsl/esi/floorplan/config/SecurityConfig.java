package edu.umsl.esi.floorplan.config;

/**
 * Created by Tam Tran on 2/21/2015.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("user123").roles("USER");
        auth.inMemoryAuthentication().withUser("manager").password("manager123").roles("MANAGER");
        auth.inMemoryAuthentication().withUser("admin").password("admin123").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
//                .antMatchers("/file_custom/*").access("permitAll")
//                .antMatchers("/list").access("hasRole('USER')")
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/resources/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                    .loginPage("/login").failureUrl("/accessdenied")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/uploadfloor")
                .and().logout()
                    .logoutSuccessUrl("/logout")
                .and().exceptionHandling().accessDeniedPage("/accessdenied");
    }
//    @Override
//     public void configure(WebSecurity web) throws Exception {
//        web
//           .ignoring()
//              .antMatchers("/resources/**"); // #3
//    }

}
