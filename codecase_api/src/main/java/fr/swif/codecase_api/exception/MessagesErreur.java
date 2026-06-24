package fr.swif.codecase_api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MessagesErreur {

  // Pour User
  USER_NOT_FOND(HttpStatus.NOT_FOUND, "L'utilisateur cherché est introuvable"),
  USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "Cet utilisateur existe déjà"),
  USER_DISABLED(HttpStatus.FORBIDDEN, "Ce compte est désactivé"),

  // Pour Post
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
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Authentification requise"),
  FORBIDDEN(HttpStatus.FORBIDDEN, "Accès refusé");

  private final HttpStatus httpStatus;
  private final String description;

}
