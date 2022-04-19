package br.com.olimposistema.aipa.beanvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.common.base.Strings;

import br.com.olimposistema.aipa.service.ValidacaoService;

public class DataValidator implements ConstraintValidator<DataValido, String> {

	@SuppressWarnings("unused")
	private String value;
	
	@Override
	public void initialize(DataValido constraintAnnotation) {
		this.value = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(Strings.isNullOrEmpty(value)) {
			return true;
		}
		
		return ValidacaoService.validaCnpj(value);
		
	}
}
