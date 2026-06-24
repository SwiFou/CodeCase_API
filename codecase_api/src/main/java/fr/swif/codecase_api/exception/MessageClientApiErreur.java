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
public record MessageClientApiErreur(MessagesErreur code, String message) {

}
