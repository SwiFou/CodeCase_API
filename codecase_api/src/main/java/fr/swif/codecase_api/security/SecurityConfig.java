package fr.swif.codecase_api.security;

import fr.swif.codecase_api.configuration.JwtUtils;
import fr.swif.codecase_api.filter.JwtFiltre;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
// @EnableWebSecuity sert à activer la configuration Spring Security Web.
// Sans cette annotation, le @Bean securityFilterChain ne serait jamais
// réellement "branché" sur les requêtes HTTP entrantes
@EnableWebSecurity
// @RequiredArgsConstructor génère automatiquement un constructeur prenant en
// paramètre tous les champs final et @NotNull de la classe
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomUserDetailsService customUserDetailsService;

  private final JwtUtils jwtUtils;

  /**
   * Méthode mdpEncoder
   *
   *<i>de SecurityConfig</i>
   *<h1></h1>
   *<hr>
   *<p>Méthode permettant l'encodage du mot de passe avec le système BCrypt</p>
   * @return Le mot de passe encodé
   */
  // @Bean sert à déclarer manuellement un bean Spring (c'est un objet géré par
  // le conteneur Ioc)
  @Bean
  public PasswordEncoder mdpEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   *
   *
   *<i>de SecurityConfig</i>
   *<h1></h1>
   *<hr>
   *<p>Méthode permettant d'authentifier les utilisateurs.
   * Depuis Spring Security 7, plus besoin de throws Exception car c'est devenue
   * une unchecked exception</p>
   * @param httpSecurity
   * @param mdpEncoder
   * @return
   */
  // @Bean sert à déclarer manuellement un bean Spring (c'est un objet géré par
  // le conteneur Ioc)
  @Bean
  public AuthenticationManager authenticationManager(
      HttpSecurity httpSecurity, PasswordEncoder mdpEncoder) {
    AuthenticationManagerBuilder authenticationManagerBuilder =
        httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

    authenticationManagerBuilder
        .userDetailsService(customUserDetailsService)
        .passwordEncoder(mdpEncoder);

    return authenticationManagerBuilder.build();
  }


  // @Bean sert à déclarer manuellement un bean Spring (c'est un objet géré par
  // le conteneur Ioc)
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
    return httpSecurity.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth ->
            auth.requestMatchers("/user/auth/*").permitAll()
                .anyRequest().authenticated())
        .addFilterBefore(new JwtFiltre(customUserDetailsService, jwtUtils),
            UsernamePasswordAuthenticationFilter.class)
        .build();
  }

}


