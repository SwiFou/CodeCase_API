package fr.swif.codecase_api.exception;

/**
 * MessageClientApiErreur
 * <i>de fr.swif.codecase_api.exception</i>
 * <hr>
 * <p>Classe permettant de structurer le body JSON renvoyé au client, ici
 * CodeCase_Web</p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 24/06/2026
 */

// Source : https://josealopez.dev/en/blog/spring-boot-global-exception-handling
// record → raccourci pour créer des classes permettant de
// transporter des données
public record MessageClientApiErreur(MessagesErreur code, String message) {

}
