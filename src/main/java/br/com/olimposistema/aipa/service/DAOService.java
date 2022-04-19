package br.com.olimposistema.aipa.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.olimposistema.aipa.dao.DAO;
import br.com.olimposistema.aipa.model.Model;

public class DAOService {
	@Inject EntityManager em;
	
	/**
	 * Este Metodo Busca o primeiro Objeto encontrato no banco de dados
	 * usando o DAO informado para fazer a pesquisa
	 * @param daoClass
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Model buscaPrimeiroObjetoDoBanco(Class<?> daoClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		
		
		@SuppressWarnings("unchecked")
		Class<? extends DAO<Model>> classDao = (Class<? extends DAO<Model>>) daoClass;
		
		try {
			em.getTransaction().begin();
		
			DAO<Model> objectDao = criaNovoObjetoDAO(classDao, em);			
			Model model = objectDao.selectAll().get(0);			
			em.getTransaction().commit();	
			em.close();
			return model;
			
		}finally {
			EntityManagerService.LiberaConnection(em);
		}
		
	}

	/**
	 * Esta funcao Recebe um Class Dao e o seu Parâmetro 
	 * e instancia um novo objeto 
	 * @param classDao um Class que deve ter sido herdado de DAO
	 * @param em o parâmetro necessário para criar o objeto
	 * @return novo objeto do class informado
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public DAO<Model> criaNovoObjetoDAO(Class<? extends DAO<Model>> classDao, EntityManager em)
			throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		//Pegando o contrutor que recebe um entitymanager
		Constructor<? extends DAO<Model>> constructor = classDao.getConstructor(EntityManager.class);
		return constructor.newInstance(em);
	}
}
