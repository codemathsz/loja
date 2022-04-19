package br.com.olimposistema.aipa.service;

import javax.persistence.EntityManager;

public class EntityManagerService {
	public static void LiberaConnection(EntityManager em){
		if(em.isOpen()) { //caso a conexão estiver aberta
			if(em.getTransaction().isActive()) { //caso alguma transactions estava sendo executada com esta conexao
				em.getTransaction().rollback();
			}
			em.close(); 
		}
	}
}
