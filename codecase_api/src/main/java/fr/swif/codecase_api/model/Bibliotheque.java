package fr.swif.codecase_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Bibliotheque
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
@Table(name = "Bibliotheque")
public class Bibliotheque {
  /**
   * Variable bibliothequeId
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name= "bibliothequeId")
  private Integer bibliothequeId;

  /**
   * Variable bibliothequeLibelle
   */
  @Size(min = 3, max = 20)
  @Column(name = "bibliothequeLibelle")
  private String bibliothequeLibelle;

  /**
   * Variable userId de la classe User
   */
  @ManyToOne
  @JoinColumn(name = "userId")
  private User userId;
}
