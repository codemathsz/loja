package br.com.olimposistema.aipa.imagem;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.olimposistema.aipa.model.Model;
import br.com.olimposistema.aipa.vraptorcrud.ReflectionUtil;

public class NotUpdateImageDao {
	
	private EntityManager em;
	
	public NotUpdateImageDao(EntityManager em) {
		this.em = em;
	}

	public void execute(Model modelASerAtualizado) {
		try {
			Model modelAntigoBanco = em.find(modelASerAtualizado.getClass(), modelASerAtualizado.getId());
			notUpdateSingleImage(modelASerAtualizado, modelAntigoBanco);
			notUpdateMultipleImage(modelASerAtualizado, modelAntigoBanco);
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao Atualizar Model sem atualizar imagens em .notupdate: " + e.getMessage());
		}
	}
	
	private void notUpdateSingleImage(Model modelASerAtualizado, Model modelAntigoBanco)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<String> nameFields = new ReflectionUtil(modelAntigoBanco.getClass()).getNameFieldsForObject(SingleImage.class);
		
		if(nameFields == null) {
			return;
		}
		
		for (String nameField : nameFields) {
			ManipulationProperty manipulationModelAntigoBanco = new ManipulationProperty(nameField, modelAntigoBanco);
			ManipulationProperty manipulationModelASerAtualizado = new ManipulationProperty(nameField, modelASerAtualizado);
			
			Imagem imagem = manipulationModelAntigoBanco.getPropertyImage();
			manipulationModelASerAtualizado.setPropertyImage(imagem);
		}
	}
	
	private void notUpdateMultipleImage(Model modelASerAtualizado, Model modelAntigoBanco)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<String> nameFields = new ReflectionUtil(modelAntigoBanco.getClass()).getNameFieldsForObject(MultipleImage.class);
		
		if(nameFields == null) {
			return;
		}
		
		for (String nameField : nameFields) {
			ManipulationProperty manipulationModelAntigoBanco = new ManipulationProperty(nameField, modelAntigoBanco);
			ManipulationProperty manipulationModelASerAtualizado = new ManipulationProperty(nameField, modelASerAtualizado);
			
			List<Imagem> images = manipulationModelAntigoBanco.getPropertyListImage();
			manipulationModelASerAtualizado.setPropertyListImage(images);
		}
	}
}
