package fr.swif.codecase_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Vote
 * <i>de fr.swif.codecase_api.model</i>
 * <hr>
 * <p></p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 09/06/2026
 */

// @Data est l'équivalent de @Getter @Setter @RequiredArgsConstructor
// @ToString @EqualsAndHashCode
@Data
// @Entity indique à Hibernate (ou tout autre provider JPA) que la
// classe Java est mappée à une table en base de données
@Entity
@Table(name = "Vote")
public class Vote {
  /**
   * Variable voteId
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "voteId")
  private Integer voteId;

  /**
   * Variable voteType de type VoteType
   */
  @Enumerated(EnumType.STRING) // Permet de stocker "LIKE"/"DISLIKE"
                              // au lieu de 0/1
  @Column(name = "voteType")
  private VoteType voteType;

  /**
   * Variable postId de la classe Post
   */
  @ManyToOne
  @JoinColumn(name = "postId")
  private Post postId;

  /**
   * Variable userId de la classe User
   */
  @ManyToOne
  @JoinColumn(name = "userId")
  private User userId;
}
