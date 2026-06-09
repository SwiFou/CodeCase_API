package fr.swif.codecase_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * User
 * <i>de fr.swif.codecase_api.model</i>
 * <hr>
 * <p></p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 09/06/2026
 */

@Data
@Entity
@Table(name = "User_")
public class User {
  /**
   * Variable userId
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "userId")
  private Integer userId;

  /**
   * Variable userPseudo
   */
  @Size(min = 3, max = 20)
  @Column(name = "userPseudo")
  private String userPseudo;

  /**
   * Variable userMdp
   */
  @Column(name = "userMdp")
  private String userMdp;

  /**
   * Variable userEmail
   */
  @Email
  @Column(name = "userEmail")
  private String userEmail;

  /**
   * Variable userRole
   */
  @Size(max = 8)
  @NotBlank
  @Column(name = "userRole")
  private String userRole;

  /**
   * Variable userDateCreationCompte
   */
  @NotNull
  @Column(name = "userDateCreationCompte")
  private LocalDate userDateCreationCompte;

  /**
   * Variable userDerniereConnexion
   */
  @NotNull
  @Column(name = "userDerniereConnexion")
  private LocalDate userDerniereConnexion;

  /**
   * Variable userAvatar
   */
  @NotBlank
  @Size(max = 250)
  private String userAvatar;

  /**
   * Variable userMfaActif
   */
  @Column(name = "userMfaActif")
  private boolean userMfaActif;
}
