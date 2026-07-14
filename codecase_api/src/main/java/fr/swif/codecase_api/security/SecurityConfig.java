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
import org.springframework.security.config.http.SessionCreationPolicy;
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
   * Méthode authenticationManager
   *
   *<i>de SecurityConfig</i>
   *<h1></h1>
   *<hr>
   *<p>Méthode permettant de vérifier une tentative d'authentification et de
   * dire si elle est valide ou non.
   * Depuis Spring Security 7, plus besoin de throws Exception, car c'est
   * devenue une unchecked exception</p>
   * @param httpSecurity Objet de configuration de Spring Security permettant de
   *                     récupérer l'AuthenticationManagerBuilder partagé
   * @param mdpEncoder Encodeur utilisé pour comparer le mot de passe saisi avec
   *                   celui qui est stocké en BDD
   * @return AuthenticationManager configuré avec le service utilisateur et
   * l'encodeur de mot de passe
   */
  // @Bean sert à déclarer manuellement un bean Spring (c'est un objet géré par
  // le conteneur Ioc)
  @Bean
  public AuthenticationManager authenticationManager(
      HttpSecurity httpSecurity, PasswordEncoder mdpEncoder) {

    AuthenticationManagerBuilder authenticationManagerBuilder =
        /* getSharedObject() récupère un objet partagé que Spring Security
        attache automatiquement à HttpSecurity pendant
        la phase de configuration */

        /* AuthenticationManagerBuilder est un objet "constructeur" :
        il permet de définir comment l'authentification doit se faire
        (quelle source d'utilisateurs, quel encodeur de mot de passe)
        avant de fabriquer le vrai AuthenticationManager */
        httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

    authenticationManagerBuilder
        /* Indique comment retrouver un User à partir de son identifiant et
        de son mot de passe */
        .userDetailsService(customUserDetailsService)
        /* Indique comment comparer le mot de passe saisi avec celui hashé
        en BDD */
        .passwordEncoder(mdpEncoder);

    // Fabrication de l'objet AuthenticationManager
    return authenticationManagerBuilder.build();
  }

  /**
   * Méthode securityFilterChain
   *
   *<i>de SecurityConfig</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode sert à définir la configuration de sécurité HTTP appliquée
   * à toutes les requêtes entrantes de l'application</p>
   * @param httpSecurity Objet permettant de configurer les règles de sécurité
   *                     HTTP (CSRF, autorisations, filtres)
   * @return SecurityFilterChain construit, avec application des règles de
   * sécurité pour toutes les requêtes HTTP de l'application
   */
  // @Bean sert à déclarer manuellement un bean Spring (c'est un objet géré par
  // le conteneur Ioc)
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
    return httpSecurity
        /* On désactive le CSRF ici, car c'est une authentification stateless
        (sans état). Il n'y a pas séssion crée par le serveur, le token JWT est
        généré puis stocké dans le client (localStorage, mémoire, etc.) et
        à chaque nouvelle requête le token est lu par JwtFiltre et est vérifié
        par JwtUtils. */
        .csrf(AbstractHttpConfigurer::disable)
        /* SessionCreationPolicy.STATELESS permet de ne jamais créer de session
         HTTP côté serveur quoi qu'il arrive (si rien n'est mis, par défaut
         c'est IF_RQUIRED → créer une session seulement si un mécanisme interne
         en a besoin) */
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // authorizeHttpRequests permet de définir les règles d'autorisation
        .authorizeHttpRequests(
            authorize ->
            authorize
                // Les requêtes suivantes sont accessibles sans authentification
                .requestMatchers("/user/authentification/inscription").permitAll()
                .requestMatchers("/user/authentification/connexion").permitAll()
                .requestMatchers("/user/authentification/deconnexion").permitAll()
                // Pour toutes les autres requêtes
                .anyRequest()
                // Il faut être authentifié
                .authenticated())
        /* addFilterBefore permet de constituer le filtre personnalisé avant
        l'exécution du filtre standard de Spring Security
        (Il est obligatoire de mettre UsernamePasswordAuthenticationFilter) */
        .addFilterBefore(new JwtFiltre(customUserDetailsService, jwtUtils),
            UsernamePasswordAuthenticationFilter.class)
        // Construction et retourne l'objet SecurityFilterChain configuré
        .build();
  }

}


