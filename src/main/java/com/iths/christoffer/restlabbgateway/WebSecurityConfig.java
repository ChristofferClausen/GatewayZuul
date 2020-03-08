package com.iths.christoffer.restlabbgateway;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.iths.christoffer.restlabbgateway.JwtConfig;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable();
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((request, response, e) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET).anonymous()
                .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
//                .antMatchers(HttpMethod.PATCH, jwtConfig.getUri()).hasRole("ADMIN")//.permitAll()
//                .antMatchers(HttpMethod.PUT, jwtConfig.getUri()).hasRole("ADMIN")//.permitAll()
//                .antMatchers(HttpMethod.DELETE, jwtConfig.getUri()).hasRole("ADMIN")//.permitAll()
                .antMatchers("/cinemas" + "/api/v1/cinemas/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();//.and().httpBasic();

//        http.authorizeRequests().anyRequest().permitAll();
//        http.authorizeRequests()
////                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/**").hasRole("USER")
//                .and().httpBasic();
        //.antMatchers("/api/v1/cinemas").hasAnyRole()
//                .antMatchers("/Cinemas").hasAnyRole()
//                .and().httpBasic();
    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.builder().username("user").password(passwordEncoder().encode("password")).roles("USER").build());
//        manager.createUser(User.builder().username("admin").password(passwordEncoder().encode("password")).roles("ADMIN").build());
//        return manager;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
