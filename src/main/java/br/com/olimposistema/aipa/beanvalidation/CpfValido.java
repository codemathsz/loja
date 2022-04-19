package br.com.olimposistema.aipa.beanvalidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CpfValidator.class)
public @interface CpfValido {

	String message() default "Cpf invalido"; 
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String value() default "";
    
}
