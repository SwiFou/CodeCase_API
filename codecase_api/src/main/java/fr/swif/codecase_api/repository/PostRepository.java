package fr.swif.codecase_api.repository;

import fr.swif.codecase_api.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * PostRepository
 *<i>de fr.swif.codecase_api.repository</i>
 *<hr>
 *<p>Interface CRUD pour Post</p>
 *
 *@author Calderoli Alexandre
 *@version 0.0.1
 *@since 11/06/2026
 */

// @Repository est un stéréotype Spring qui marque une classe
// comme couche d'accès aux données (DAO)
@Repository
public interface PostRepository  extends CrudRepository<Post, Integer> {

}
