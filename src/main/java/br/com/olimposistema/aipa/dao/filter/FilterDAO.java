package br.com.olimposistema.aipa.dao.filter;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import br.com.olimposistema.aipa.filtrable.IFiltrable;
import br.com.olimposistema.aipa.filtro.FiltroStartPositionAndMaxResult;

public class FilterDAO<T> {

	private EntityManager em;
	private Class<T> classe;

	public FilterDAO(EntityManager em, Class<T> persistedClass) {
		this.em = em;
		this.classe = persistedClass;
	}

	public List<T> filter(T model) {	
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> q = cb.createQuery(classe);
		
		Root<T> r = q.from(classe);
		
		List<Predicate> predicates = new BuscaFieldsPreenchidos<T>(model)
			.busca()
			.transformaFieldsEmWhere(q,cb, r);
		
		q.where((Predicate[]) predicates.toArray(new Predicate[0]));
		
		q.select(r)
			.distinct(true)
			.orderBy(cb.desc(r.get("id")));
		
		TypedQuery<T> tq = em.createQuery(q);
		return tq.getResultList();
	}
	
	public List<T> filter(IFiltrable<T> ifiltrable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> q = cb.createQuery(classe);
		
		Root<T> r = q.from(classe);
		
		List<Predicate> predicates = new BuscaFieldsPreenchidos<IFiltrable<T>>(ifiltrable)
			.busca()
			.transformaFieldsEmWhere(q,cb, r);
		
		q.where((Predicate[]) predicates.toArray(new Predicate[0]));
		
		q.select(r)
			.distinct(true)
			.orderBy(cb.desc(r.get("id")));
		
		TypedQuery<T> tq = em.createQuery(q);
		new FiltroStartPositionAndMaxResult().add(ifiltrable, tq);
		return tq.getResultList();
	}

	public Long total(T model) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> q = cb.createQuery(Long.class);
		
		Root<T> r = q.from(classe);
		
		List<Predicate> predicates = new BuscaFieldsPreenchidos<T>(model)
			.busca()
			.transformaFieldsEmWhere(q,cb, r);
		
		q.where((Predicate[]) predicates.toArray(new Predicate[0]));
		
		q.distinct(true);
		q.select(cb.countDistinct(r.get("id")));
		
		TypedQuery<Long> tq = em.createQuery(q);
		return tq.getSingleResult();
	}
	
	public Long total(IFiltrable<T> ifiltrable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> q = cb.createQuery(Long.class);
		
		Root<T> r = q.from(classe);
		
		List<Predicate> predicates = new BuscaFieldsPreenchidos<IFiltrable<T>>(ifiltrable)
			.busca()
			.transformaFieldsEmWhere(q,cb, r);
		
		q.where((Predicate[]) predicates.toArray(new Predicate[0]));
		
		q.distinct(true);
		q.select(cb.countDistinct(r.get("id")));
		
		TypedQuery<Long> tq = em.createQuery(q);
		new FiltroStartPositionAndMaxResult().add(ifiltrable, tq);
		return tq.getSingleResult();
	}

}
