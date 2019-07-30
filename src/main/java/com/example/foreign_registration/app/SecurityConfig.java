package com.example.foreign_registration.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/h2").permitAll()
                    .antMatchers("/assessmentList").hasRole("USER")
                    .antMatchers("/main").hasRole("USER")
                    .antMatchers("/").hasRole("USER")
                    .antMatchers("/checkFilter").hasRole("USER")
                    .antMatchers("/assessmentDetails/addRequirement").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/logout").logoutSuccessUrl("/")
                .and().formLogin().loginPage("/login");
    }

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from user where username=?")
                .authoritiesByUsernameQuery("select username, role from user_role where username=?");
    }
}
