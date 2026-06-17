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
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Post
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
@Table(name = "Post")
public class Post {
  /**
   * Variable postId
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "postId")
  private Integer postId;

  /**
   * Variable postTitre
   */
  @Size(min = 3, max = 100)
  @Column(name = "postTitre")
  private String postTitre;

  /**
   * Variable postDescription
   */
  @NotBlank
  @Size
  @Column(name = "postDescription")
  private String postDescription;

  /**
   * Variable postContenu
   */
  @NotBlank
  @Size(max = 5000)
  @Column(name = "postContenu")
  private String postContenu;

  /**
   * Variable userId
   */
  @ManyToOne
  @JoinColumn(name = "userId")
  private User userId;

  /**
   * Variable langageId de type langage
   */
  @ManyToOne
  @JoinColumn(name = "langageId")
  private Langage langageId;

  /**
   * Variable tagCustom, dans une Arraylist de type Tag
   */
//  Set<Tag> tagCustom;

  /**
   * Variable creationDatePost
   */
  @Column(name = "postDateCreation")
  private LocalDateTime postDateCreation;

}
