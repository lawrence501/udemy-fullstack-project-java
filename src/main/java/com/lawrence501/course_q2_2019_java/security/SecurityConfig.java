package com.lawrence501.course_q2_2019_java.security;

import static com.lawrence501.course_q2_2019_java.security.SecurityConstants.REGISTER_URLS;

import com.lawrence501.course_q2_2019_java.services.CustomUserDetailsService;

import static com.lawrence501.course_q2_2019_java.security.SecurityConstants.H2_URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter { // provides default security config that we can
                                                                   // override

  @Autowired
  private JwtAuthenticationEntrypoint unauthorizedHandler;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().headers().frameOptions()
        .sameOrigin() // To enable H2
        .and().authorizeRequests()
        .antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css",
            "/**/*.js")
        .permitAll().antMatchers(REGISTER_URLS).permitAll().antMatchers(H2_URL).permitAll().anyRequest()
        .authenticated();
  }

}