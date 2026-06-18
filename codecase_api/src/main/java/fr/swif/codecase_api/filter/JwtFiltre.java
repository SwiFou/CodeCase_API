package fr.swif.codecase_api.filter;

import fr.swif.codecase_api.configuration.JwtUtils;
import fr.swif.codecase_api.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JwtFiltre
 * <i>de fr.swif.codecase_api.filter</i>
 * <hr>
 * <p></p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 18/06/2026
 */

@Component
@RequiredArgsConstructor
public class JwtFiltre extends OncePerRequestFilter {

  private final CustomUserDetailsService customUserDetailsService;
  private final JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {



  }
}
