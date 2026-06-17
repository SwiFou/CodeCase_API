package fr.swif.codecase_api.model;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Retention signifie que l'annotation est accessible à l'exécution via
// reflection, donc exploitable programmatiquement
@Retention(RetentionPolicy.RUNTIME)
// @Target est une restriction. Ici restriction aux champs (FIELD)
@Target(ElementType.FIELD)
public @interface PersonalData {
  String categorie() default "general";
  String usage();
  boolean canBeDeleted() default true;
}
