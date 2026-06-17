package fr.swif.codecase_api.security;

import fr.swif.codecase_api.model.User;
import fr.swif.codecase_api.repository.UserRepository;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService
 * <i>de fr.swif.codecase_api.security</i>
 * <hr>
 * <p></p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 17/06/2026
 */

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userEmail)
      throws UsernameNotFoundException {

    User user = userRepository.findByUserEmail(userEmail)
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable" + userEmail));
    return new UserDetailsImpl(user);
  }

  
}
