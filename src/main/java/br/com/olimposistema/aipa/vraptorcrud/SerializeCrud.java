package br.com.olimposistema.aipa.vraptorcrud;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Quando o Crud for serializado o field que tiver essa anotação
 * será incluido na serialização
 * @author Tiago Luz
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SerializeCrud {
	String[] include() default {};
}
