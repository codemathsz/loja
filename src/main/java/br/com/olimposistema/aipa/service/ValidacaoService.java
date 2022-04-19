package br.com.olimposistema.aipa.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.*;
import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;

public class ValidacaoService {
	
	//Valida Se a String é somente numeros 

	public static boolean somenteNumeros(String numero) {
		return Pattern.matches("\\d+", numero );
	}
	
	//Valida se o CPF é valido
	
	public static boolean validaCpf(String cpf){ 
	    CPFValidator cpfValidator = new CPFValidator(); 
	    List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf); 
	    return erros.isEmpty();
	}  
	
	//Valida Data para sabe se ela é maior que a data atual
	
	public static boolean dataValida(Calendar data) {
		Calendar dataAtual = new GregorianCalendar();
		return(dataAtual.after(data)); 
	}
	
	public static boolean validaCnpj(String cnpj){ 
	    CNPJValidator cnpjValidator = new CNPJValidator(); 
	    List<ValidationMessage> erros = cnpjValidator.invalidMessagesFor(cnpj); 
	    return erros.isEmpty();
	} 
	
}
	

	

