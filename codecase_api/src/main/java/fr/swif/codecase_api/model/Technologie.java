package fr.swif.codecase_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Technologie
 * <i>de fr.swif.codecase_api.model</i>
 * <hr>
 * <p></p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 09/06/2026
 */

// @Data est l'équivalent de @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@Data
// @Entity indique à Hibernate (ou tout autre provider JPA) que la classe Java est mappée à une table en base de données
@Entity
@Table(name = "Technologie")
public class Technologie {
  /**
   * Variable technologieId
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "technologieId")
  private Integer technologieId;

  /**
   * Variable technologieIntitule
   */
  @NotBlank
  @Column(name = "technologieIntitule")
  private String technologieIntitule;

  /**
   * Variable userId de la classe User
   */
  @ManyToOne
  @JoinColumn(name = "userId")
  private User userId;
}
