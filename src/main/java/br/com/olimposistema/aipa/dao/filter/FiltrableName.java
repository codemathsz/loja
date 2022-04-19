package br.com.olimposistema.aipa.dao.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Quando esta anotação é colocada faz com o método filtro filtre o campo
 * independendo de maiuscula e minuscula e também pela parte inicial da string exemplo:
 * "TIA" filtra registros "tiago"
 * @author Tiago Luz
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FiltrableName {

}
