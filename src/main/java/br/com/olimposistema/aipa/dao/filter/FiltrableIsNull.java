package br.com.olimposistema.aipa.dao.filter;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Usada para marcar o FIELD que representa o maxresult 
 * para trazer o resultado da consulta
 * @author Tiago Luz
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface FiltrableIsNull {
}
