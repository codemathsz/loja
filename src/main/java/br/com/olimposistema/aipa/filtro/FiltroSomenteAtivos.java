package br.com.olimposistema.aipa.filtro;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.olimposistema.aipa.model.Model;

public class FiltroSomenteAtivos {
	public void add(List<Predicate> predicates, CriteriaBuilder cb, Root<? extends Model> root) {
		Path<String> pathAtivo = root.<String> get("ativo");
		Predicate somenteAtivos = cb.equal(pathAtivo, true);
		predicates.add(somenteAtivos);	
	}
}
