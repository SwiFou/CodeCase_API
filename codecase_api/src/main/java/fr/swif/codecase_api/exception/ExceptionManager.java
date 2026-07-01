package fr.swif.codecase_api.exception;


import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * ExceptionManager
 * <i>de fr.swif.codecase_api.exception</i>
 * <hr>
 * <p>Gestionnaire des Exceptions, dédié à rassembler la gestion des exceptions
 * majeures et centralisables côté API</p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 12/06/2026
 */

// @RestControllerAdvice est la combinaison de :
// - @ControllerAdvice, qui intercepte les exceptions levées par les controllers
// et permet de retourner n'importe quel type de réponse
// - @ResponseBody, qui permet que la valeur retour soit sérialisée en JSON
// automatiquement
@Slf4j
@RestControllerAdvice
public class ExceptionManager {

  /**
   * Méthode handleCodeCaseException(CodeCaseApiException codeCaseApiException)
   *
   *<i>de ExceptionManager</i>
   *<h1></h1>
   *<hr>
   *<p>Méthode qui gère les exceptions métier de type CodeCaseException.
   * Elle crée une réponse avec le code d'état HTTP et le corps de la réponse</p>
   * @param codeCaseApiException Le type d'exception
   * @return Le statut avec le body qui contient le message récupéré de
   * MessageErreur
   */
  // @ExceptionHandler permet de définir la logique pour traiter et répondre aux
  // exceptions traitées en paramètre
  @ExceptionHandler(CodeCaseApiException.class)
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<MessageClientApiErreur> handleCodeCaseApiException(
      CodeCaseApiException codeCaseApiException) {
    final MessagesErreur messagesErreur =
        codeCaseApiException.getMessagesErreur();
    final HttpStatus status = messagesErreur.getHttpStatus();
    final MessageClientApiErreur body = creationMessageClientApi(messagesErreur);

    return ResponseEntity.status(status).body(body);
  }

  /**
   * Méthode handleExceptions
   * (MethodArgumentNotValidException methodArgumentNotValidException)
   *
   *<i>de ExceptionManager</i>
   *<h1></h1>
   *<hr>
   *<p>Méthode qui gère les erreurs de validation Bean (@Valid) sur les
   * objets reçus en paramètre de controller</p>
   * @param methodArgumentNotValidException Le type d'exception
   * @return Le statut avec le body qui contient le message récupéré de
   * MessageErreur + un message personnalisé listant les champs invalides
   */
  // @ExceptionHandler permet de définir la logique pour traiter et répondre aux
  // exceptions traitées en paramètre
  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<MessageClientApiErreur> handleExceptions(
      MethodArgumentNotValidException methodArgumentNotValidException) {
    final MessagesErreur messagesErreur = MessagesErreur.BAD_REQUEST;
    final HttpStatus status = messagesErreur.getHttpStatus();

    // getBindingResult() récupère l'ensemble des erreurs de validation
    // détectées par Bean Validation sur l'objet annoté avec @Valid
    final String message = methodArgumentNotValidException.getBindingResult()
        // getFieldErrors() ne retourne que les erreurs liées aux champs (et non
        // aux erreurs globales de l'objet)
        .getFieldErrors()
        .stream()
        // Pour chaque champ en erreur, on construit un message
        // "nomDuChamp : messageDeLAnnotation"
        .map(fieldError -> fieldError.getField() + " : "
        + fieldError.getDefaultMessage())
        // collect() permet de regrouper tous les messages en une seule chaîne,
        // séparés par ", "
        .collect(Collectors.joining(", "));

    final MessageClientApiErreur body = creationMessageClientApi(messagesErreur, message);

    return ResponseEntity.status(status).body(body);
  }

  /**
   * Méthode handleExceptions (MethodArgumentTypeMismatchException exception)
   *
   *<i>de ExceptionManager</i>
   *<h1></h1>
   *<hr>
   *<p>Méthode qui gère les erreurs de type sur les paramètres d'URL.
   * Par exemple : /posts/{id} où id attend un Long, mais reçoit "abc".</p>
   * @param exception Le type d'exception
   * @return Le statut avec le body qui contient le message récupéré de
   * MessageErreur + un message personnalisé
   */
  // @ExceptionHandler permet de définir la logique pour traiter et répondre aux
  // exceptions traitées en paramètre
  @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<MessageClientApiErreur> handleExceptions(
      MethodArgumentTypeMismatchException exception) {
    final MessagesErreur messagesErreur = MessagesErreur.BAD_REQUEST;
    final HttpStatus status = messagesErreur.getHttpStatus();
    final String paramName = exception.getName();

    // Les if traitent le cas où il y a un null car des paramètres de
    // MethodArgumentTypeMismatchException sont @Nullable
    String requiredTypeName = "unknown";
    if (exception.getRequiredType() != null) {
      requiredTypeName = exception.getRequiredType().getSimpleName();
    }

    String providedTypeName = "unknown";
    if (exception.getValue() != null) {
      providedTypeName = exception.getValue().getClass().getSimpleName();
    }

    final String message = String.format("Valeur invalide pour le paramètre : "
        + "'%s'. Type attendu : '%s', type reçu : '%s'.", paramName,
        requiredTypeName, providedTypeName);

    final MessageClientApiErreur body =
        creationMessageClientApi(messagesErreur, message);

    return ResponseEntity.status(status).body(body);
  }

  /**
   * Méthode handleExceptions (NoResourceFoundException exception)
   *
   *<i>de ExceptionManager</i>
   *<h1></h1>
   *<hr>
   *<p>Méthode qui gère les erreurs de requête où la ressource est introuvable</p>
   * @param exception Le type d'exception
   * @return Le statut avec le body qui contient le message récupéré de
   * MessageErreur + un message personnalisé
   */
  // @ExceptionHandler permet de définir la logique pour traiter et répondre aux
  // exceptions traitées en paramètre
  @ExceptionHandler(value = {NoResourceFoundException.class})
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<MessageClientApiErreur> handleExceptions(
      NoResourceFoundException exception) {
    final MessagesErreur messagesErreur = MessagesErreur.NOT_FOUND;
    final HttpStatus status = messagesErreur.getHttpStatus();

    final String message = String.format("Ressource introuvable : '%s' sur la "
        + "route '%s'.", exception.getHttpMethod(), exception.getResourcePath());

    final MessageClientApiErreur body =
        creationMessageClientApi(messagesErreur, message);

    return ResponseEntity.status(status).body(body);
  }

  /**
   * Méthode handleExceptions (Exception exception)
   *
   *<i>de ExceptionManager</i>
   *<h1></h1>
   *<hr>
   *<p>Méthode qui gère toutes les exceptions non attrapées par les méthodes
   * précédentes</p>
   * @param exception Le type d'exception
   * @return Le statut avec le body qui contient le message récupéré de
   * MessageErreur
   */
  // @ExceptionHandler permet de définir la logique pour traiter et répondre aux
  // exceptions traitées en paramètre
  @ExceptionHandler(value = {Exception.class})
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<MessageClientApiErreur> handleExceptions(
      Exception exception) {
    final MessagesErreur messagesErreur = MessagesErreur.INTERNAL_SERVER_ERROR;
    final HttpStatus status = messagesErreur.getHttpStatus();

    final MessageClientApiErreur body = creationMessageClientApi(messagesErreur);

    log.error("Erreur inattendue", exception);
    return ResponseEntity.status(status).body(body);
  }

  /**
   * Méthode creationMessageClientApi
   * (MessagesErreur messagesErreur, String message)
   *
   *<i>de ExceptionManager</i>
   *<h1></h1>
   *<hr>
   *<p>Permet de passer un message personnalisé dans le handlerExceptions</p>
   * @param messagesErreur Le message de la classe MessageErreur
   * @param message Le message personnalisé
   * @return
   */
  public MessageClientApiErreur creationMessageClientApi(
      MessagesErreur messagesErreur, String message) {
    return new MessageClientApiErreur(messagesErreur, message);
  }

  /**
   * Méthode creationMessageClientApi (MessagesErreur messagesErreur)
   *
   *<i>de ExceptionManager</i>
   *<h1></h1>
   *<hr>
   *<p>Permet de récupèrer le message depuis l'enum MessageErreur
   * automatiquement</p>
   * @param messagesErreur Le message de la classe MessageErreur
   * @return
   */
  public MessageClientApiErreur creationMessageClientApi(
      MessagesErreur messagesErreur) {
    final String message = messagesErreur.getDescription();

    return new MessageClientApiErreur(messagesErreur, message);
  }
}
