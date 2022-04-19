package br.com.olimposistema.aipa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.olimposistema.aipa.dao.filter.FilterDAO;
import br.com.olimposistema.aipa.filtrable.IFiltrable;
import br.com.olimposistema.aipa.filtrable.order.EOrdening;
import br.com.olimposistema.aipa.model.Model;
import br.com.olimposistema.aipa.service.StringService;

public class DAO<T extends Model> {
	
	protected EntityManager em;
	private Class<T> persistedClass;
	
	public DAO(EntityManager em, Class<T> persistedClass) {
		this.em = em;
		this.persistedClass = persistedClass;
	}
	
	/**
	 * Persiste um Objeto passado como parâmetro no banco de dados
	 * @param object
	 */
	public T insert(T model) {
		em.persist(model);
		return model;
	}

	/**0
	 * Persiste ou atualiza um objeto passado como parâmetro no banco de dados
	 * @param object
	 */
	public T insertOrUpdate(T model) { 
		
		if(model.getId() < 1) {
			return insert(model);
		}
		
		return update(model);
		
	}
	
	/**
	 * atualiza um objeto passado como parâmetro no banco de dados
	 * @param object
	 */
	public T update(T model) { 
		
		model = em.merge(model);
		em.persist(model);
		return model;
	}
	
	/**
	 * Deleta um Objeto passado como parâmetro no banco de dados
	 * OBS: Este Objeto deve ter o ID informado
	 * @param object
	 */
	public void delete(T model) {
		if(model!= null && model.getId() < 1) {
			throw new IdInvalidoException("Não foi Possível deletar pois o id é 0 ou inválido!  classe do registro:"+ model.getClass().getSimpleName() + " id do registro: "+model.getId());
		}
		model = em.find(persistedClass, model.getId());
		em.remove(model);
	}
	
	/**
	 * Traz um Registro Ativo pesquisando por id no banco de dados
	 * caso queira pegar registros tanto ativos como inativos usar função
	 * SelectPorIdMesmoInativo
	 * @param model
	 * @return
	 * @throws RegistroInativoException 
	 */
	public T selectPorId(T model) throws IdInvalidoException {
		int id = model.getId();
		return buscaPorId(id);
	}
	
	public T selectPorId(int id) throws IdInvalidoException {
		return buscaPorId(id);
	}

	private T buscaPorId(int id) {
		T model;
		model = em.find(this.persistedClass, id);
		
		//Verifica se o Registro esta inativo no banco de dados caso inativo estoura uma exception
		if(model == null) {
			
			throw new IdInvalidoException(
					"O Registro que tentou buscar, não existe no Banco de Dados! classe do registro:" + persistedClass.getSimpleName() + 
					" id do registro: "+id);
		}
			
//			if(!model.isAtivo()) {
//				throw new RegistroInativoException(
//						"O Registro que tentou buscar, esta inativo no Banco de Dados! classe do registro:" + model.getClass().getSimpleName() + 
//						" id do registro: "+model.getId());
//			}
		
		return model; //Sera retornado o registro encontrado, ou nulo, ou se tiver inativo estoura a exeption
	}
	
	/**
	 * busca um registro do banco de dados por id ele pode estar ativo ou inativo
	 * @param model
	 * @return
	 * @throws RegistroInativoException
	 */
	public T selectPorIdComInativo(T model){
		return em.find(  this.persistedClass, model.getId());
	}
	
	public List<T> selectAll(){
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
	    CriteriaQuery<T> query = builder.createQuery(persistedClass);
	    Root<T> root = query.from(persistedClass);
	       
	    List<Predicate> predicates = new ArrayList<Predicate>();
	    
	    //Criando Filtros Para somente Ativos
  		Path<Boolean> pathAtivo = root.<Boolean> get("ativo");
		Predicate estaAtivo = builder.equal(pathAtivo, true);
		predicates.add(estaAtivo);
	  		
	  	//Adicionando os predicatos para gerar Where Automaticamente
	  	query.where((Predicate[]) predicates.toArray(new Predicate[0]));    
	    return this.em.createQuery(query).getResultList();
	}
	
	public List<T> selectAllComInativos(){
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
	    CriteriaQuery<T> query = builder.createQuery(persistedClass);
	    query.from(persistedClass);
	    return this.em.createQuery(query).getResultList();
	}
	
	public List<T> filter(T model){
		return new FilterDAO<T>(em,persistedClass).filter(model);	
	}
	
	public List<T> filter(IFiltrable<T> model){
		return new FilterDAO<T>(em,persistedClass).filter(model);	
	}
	
	public Long filterTotal(T model) {
		return new FilterDAO<T>(em,persistedClass).total(model);
	}
	
	public Long filterTotal(IFiltrable<T> model) {
		return new FilterDAO<T>(em,persistedClass).total(model);
	}
	
	protected String stringFormaterSearch(String valor) {
		try {
			valor = new StringService(valor).maiusculoSemAcentoESemEspacosAMais();
		}catch (Exception e) {}
		return valor+"%";
	}
	
	protected String stringFormaterSearchQualquerLugar(String valor) {
		try {
			valor = new StringService(valor).maiusculoSemAcentoESemEspacosAMais();
		}catch (Exception e) {}
		return "%"+valor+"%";
	}
	
	protected Order cbOrder(EOrdening ordening,Expression<?> x) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		if(ordening == EOrdening.ASC) {
			return cb.asc(x);
		}else if(ordening == EOrdening.DESC) {
			return cb.desc(x);
		}
		return null;
	}
}
