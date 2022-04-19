package br.com.olimposistema.aipa.auth;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.common.base.Strings;

public class FiltroEmailOrCelularOrIdSocialAndSenha {

	public void add(List<Predicate> predicates, CriteriaBuilder cb, Root<? extends User> root,
			Autenticavel autenticavel) {
			
		Path<String> pathEmail = root.<String> get("email");
		Path<String> pathCelular = root.<String> get("celular");
		Path<String> pathIdSocial = root.<String> get("idSocial");
		Path<String> pathSenha = root.<String> get("senha");
		
		if(temEmailOuCelularOuIdSocialESenha(autenticavel)) {
			Predicate temEmailOuCelularOuIdSocialIgual = cb.or(
				cb.equal(pathEmail, autenticavel.getEmail()), 
				cb.equal(pathCelular, autenticavel.getCelular()),
				cb.equal(pathIdSocial, autenticavel.getIdSocial())
			);
					
			Predicate temSenhaIgual = cb.equal(pathSenha, autenticavel.getSenha());
			
			predicates.add(temEmailOuCelularOuIdSocialIgual);
			predicates.add(temSenhaIgual);
		}
	
		
	}

	private boolean temEmailOuCelularOuIdSocialESenha(Autenticavel autenticavel) {
		return 
			!Strings.isNullOrEmpty(autenticavel.getEmail()) ||
			!Strings.isNullOrEmpty(autenticavel.getCelular()) ||
			!Strings.isNullOrEmpty(autenticavel.getIdSocial()) &&
			!Strings.isNullOrEmpty(autenticavel.getSenha());
	}
	
}
