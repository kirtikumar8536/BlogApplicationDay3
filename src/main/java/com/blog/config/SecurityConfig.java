package com.blog.config;


import com.blog.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//WebSecurityConfigurerAdapter deprecated in ss 5.7

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //inorder to use @preAuthorized in controller
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF protection for simplicity in this example
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll() // Allow all to access GET endpoints
                //.antMatchers(HttpMethod.POST,"/api/**").hasRole("ROLE_ADMIN")//which url who can access
                .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll() // Allow all to access authentication endpoints
                .anyRequest().authenticated() // Require authentication for all other requests
                .and()
                .httpBasic(); // Use HTTP Basic Authentication
    }
    /*@Override
    @Bean
    protected UserDetailsService userDetailsService() {   //in memory Authentication
     // You can configure a custom UserDetailsService if needed
        // This example uses the CustomUserDetailsService provided by your application
        //object consisting of username and password
        UserDetails user = User.builder().username("pankaj")
                .password(passwordEncoder.encode("testing"))
                .roles("USER").build();
        UserDetails admin = User.builder().username("admin")
                .password(passwordEncoder.encode("Admin"))
                .roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user,admin);
    }*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getEncodedPassword());// Set the password encoder
    }
    @Bean
    public PasswordEncoder getEncodedPassword(){
        return new BCryptPasswordEncoder();// Use BCrypt for password encoding
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
