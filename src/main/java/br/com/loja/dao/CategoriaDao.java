package br.com.loja.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.loja.model.Categoria;
import br.com.loja.model.Usuario;
import br.com.olimposistema.aipa.dao.DAO;

public class CategoriaDao extends DAO<Categoria> {
	@Deprecated
	public CategoriaDao() {super(null, null);}
	
	@Inject
	public CategoriaDao (EntityManager em) {
		super(em, Categoria.class);
	}
	
	@RequestScoped
	public Categoria existeCategoriaCom(String nome) {
		String jpql = "select c from Categoria as c where c.nome= :pNome";
		Query query = em.createQuery(jpql);
		
		query.setParameter("pNome", nome);
		try {
			
			Categoria categoria = (Categoria) query.getSingleResult();
			
			return categoria;
		} catch (Exception e) {
			return null;
		}
	}
}
