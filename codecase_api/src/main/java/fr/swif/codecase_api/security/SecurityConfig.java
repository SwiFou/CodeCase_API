package fr.swif.codecase_api.security;

import fr.swif.codecase_api.exception.CodeCaseApiException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig
 * <i>de fr.swif.codecase_api.security</i>
 * <hr>
 * <p></p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 16/06/2026
 */

// @Configutration indique qu'une classe déclare une ou plusieurs méthodes
// annotées par @Bean
@Configuration
// @EnableWebSecuity
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws CodeCaseApiException {
    httpSecurity
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll());

    return httpSecurity.build();
  }
}
