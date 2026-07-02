package fr.swif.codecase_api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MessagesErreur {

  // Pour User
  ALL_USERS_NOT_FOUND(HttpStatus.NOT_FOUND, "Les utilisateurs cherchés sont introuvables"),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "L'utilisateur cherché est introuvable"),
  USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "Cet utilisateur existe déjà"),
  USER_DISABLED(HttpStatus.FORBIDDEN, "Ce compte est désactivé"),
  USER_ALREADY_ANONYMISED(HttpStatus.CONFLICT, "Ce compte est déjà anonymisé"),
  EMAIL_USER_ALREADY_EXIST(HttpStatus.CONFLICT, "Cette adresse mail est déjà enregistrée"),
  AVATAR_USER_ALREADY_EXIST(HttpStatus.CONFLICT, "Cette photo de profil est déjà enregistrée"),

  // Pour Post
  ALL_POSTS_NOT_FOUND(HttpStatus.NOT_FOUND, "Les posts cherchés sont introuvables"),
  POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Le post cherché est introuvable"),
  POST_ALREADY_EXISTS(HttpStatus.CONFLICT, "Le post existe déjà"),

  // Pour Commentaire
  COMMENTAIRE_NOT_FOUND(HttpStatus.NOT_FOUND, "Commentaire introuvable"),

  // Pour Vote
  VOTE_ALREADY_EXISTS(HttpStatus.CONFLICT, "Vous avez déjà voté sur ce post"),

  // Pour Sanction
  SANCTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Sanction introuvable"),

  // Messages génériques
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inconnue est survenue"),
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "Cette requête est invalide"),
  NOT_FOUND(HttpStatus.NOT_FOUND, "La page demandée est introuvable"),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Authentification requise"),
  FORBIDDEN(HttpStatus.FORBIDDEN, "Accès refusé");

  private final HttpStatus httpStatus;
  private final String description;

}
