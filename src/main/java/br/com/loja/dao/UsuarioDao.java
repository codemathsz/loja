package br.com.loja.dao;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.loja.model.Usuario;
import br.com.olimposistema.aipa.dao.DAO;

public class UsuarioDao extends DAO<Usuario>{

	@Deprecated
	public UsuarioDao() {super(null, null);}
	
	@Inject
	public UsuarioDao(EntityManager em) {
		super(em, Usuario.class);
		// TODO Auto-generated constructor stub
	}
	
	@RequestScoped
	public Usuario existeUsuarioCom(String email, String senha) {	
		String jpql = "select u from Usuario as u where u.email= :pEmail and u.senha = :pSenha";
		Query query = em.createQuery(jpql);
		
		query.setParameter("pEmail", email);
		query.setParameter("pSenha", senha);
		try {
			
			Usuario usuario = (Usuario) query.getSingleResult();
			
			return usuario;
		} catch (Exception e) {
			return null;
		}
	}
	
}
