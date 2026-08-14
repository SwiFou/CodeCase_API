# CodeCase API

Module API REST du projet **CodeCase**, un réseau social permettant aux développeurs de partager des posts de code.

Ce module fait partie d'une application Maven multi-module, développée dans le cadre d'un stage CDA (Concepteur Développeur d'Applications) à l'AFPA.

## 📌 Contexte

CodeCase API expose les services REST consommés par le module `codecase_web` (frontend Thymeleaf). L'authentification est gérée via une architecture **stateless JWT** (aucune session côté serveur, CSRF désactivé).

- **Deadline projet :** 17/07/2026
- **Port :** `9002`

## 🛠️ Stack technique

- Java / Spring Boot `4.0.6`
- Spring Security `7.0.5`
- Spring Data JPA / Hibernate pour l'ORM
- Lombok (boilerplate ex : `@RequiredArgsConstructor`, `@Getter`, etc.)
- JJWT `0.12.6` (génération/validation des tokens JWT)
- Maven (outil de gestion de projet et de build)
- Base de données : MySQL

## 🏗️ Architecture

### Sécurité / Authentification

- `SecurityConfig` : configuration stateless (pas de session, CSRF désactivé)
- `JwtUtils` : génération et validation des tokens JWT
- `JwtFiltre` (`OncePerRequestFilter`) : interception des requêtes pour vérifier le token
- `UserDetailsImpl` : wrapper implémentant `UserDetails` (pattern Adapter), séparé de l'entité JPA `User`
- `CustomUserDetailsService` : chargement des utilisateurs pour Spring Security

### Gestion des erreurs

- `CodeCaseApiException` : exception métier centralisée
- `MessagesErreur` : enum des messages d'erreur
- `ExceptionManager` (`@RestControllerAdvice`) : gestionnaire d'erreurs centralisé

### Choix d'architecture

- **Pas de DTO** : choix délibéré, les entités sont exposées directement pour l'instant (implémentation de DTO dans le futur)
- **`Role` en enum** (`VISITEUR`, `MEMBRE`, `ADMIN`, `MODO`) plutôt que des sous-classes
- **`Vote`** : entité unique avec enum `LIKE`/`DISLIKE` (remplace deux relations distinctes)
- **`Sanction`** : relations distinctes émetteur/cible vers `User`
- **`Bibliotheque`** : favoris utilisateur
- **`Langage`** / **`Technologie`** : entités de "balisage" des posts (langage du code et quelle technologie représente ce code)

## 🔐 Authentification — Endpoints

| Méthode | Endpoint | Description |
|---|---|---|
| `POST` | `/inscription` | Création d'un compte utilisateur |
| `POST` | `/connexion` | Authentification et génération du token JWT |

<!-- À COMPLÉTER : lister les autres endpoints (posts, votes, etc.) au fur et à mesure -->

## 🚀 Lancement du projet

```bash
# Depuis la racine du projet multi-module
cd codecase_api
mvn spring-boot:run
```

L'API sera accessible sur `http://localhost:9002`.

### Configuration

Les propriétés de configuration (base de données, secret JWT, etc.) se trouvent dans :
```
src/main/resources/application.properties
```

<!-- À COMPLÉTER : variables d'environnement / clé secrète JWT à définir -->

## 🧪 Tests

Les tests sont effectués manuellement sur Postman pour le moment.
D'autres tests viendront par la suite notamment des tests Mockito, des tests de bout en bout avec Selenium
<!-- À COMPLÉTER : collections de tests, Mockito, etc. -->

## 📄 RGPD

Le service `UserService` implémente une anonymisation conforme RGPD (`anonymisationUser()`) :
- Adresse mail remplacé par `deleted-{id}@anonymized.invalid`
- Le pseudo remplacé par `Utilisateur supprimé-{id}`
- Le mot de passe remplacé par un placeholder non-hashable

## 👥 Auteur

Projet réalisé par Alexandre CALDEROLI dans le cadre du titre CDA à l'AFPA.
