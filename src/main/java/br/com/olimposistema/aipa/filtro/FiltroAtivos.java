package br.com.olimposistema.aipa.filtro;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.olimposistema.aipa.filtrable.IFiltrable;
import br.com.olimposistema.aipa.model.Model;

public class FiltroAtivos {
	public void add(List<Predicate> predicates, CriteriaBuilder cb, IFiltrable<? extends Model> filtrable, Root<? extends Model> root) {
		Path<String> pathAtivo = root.<String> get("ativo");
		if(filtrable.isAtivo() != null) {
			Predicate predicateAtivos = cb.equal(pathAtivo, filtrable.isAtivo());
			predicates.add(predicateAtivos);	
		}
		
	}
}
