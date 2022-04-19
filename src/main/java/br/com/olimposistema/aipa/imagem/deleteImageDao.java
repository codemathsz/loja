package br.com.olimposistema.aipa.imagem;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.olimposistema.aipa.model.Model;
import br.com.olimposistema.aipa.vraptorcrud.ReflectionUtil;

public class deleteImageDao {

	private EntityManager em;

	public deleteImageDao(EntityManager em) {
		this.em = em;
	}
	
	public void execute(Model model) {
		
		try {
			model = em.find(model.getClass(), model.getId());
			removeImages(model);
		}catch (Exception e) {
			throw new RuntimeException("Erro ao Remover Imagens em deleteImageDao: " + e.getMessage());
		}
	}
	
	
	private void removeImages(Model model) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		removeSingleImage(model);
		removeMultipleImage(model);
	}

	private void removeSingleImage(Model model)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		List<String> nameFields = new ReflectionUtil(model.getClass()).getNameFieldsForObject(SingleImage.class);
		for (String nameField : nameFields) {
			ManipulationProperty manipulationProperty = new ManipulationProperty(nameField, model);
			Imagem imagem = manipulationProperty.getPropertyImage();
			if(imagem != null) {
				em.remove(imagem);
			}
		}
	}
	
	private void removeMultipleImage(Model model)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		List<String> nameFields = new ReflectionUtil(model.getClass()).getNameFieldsForObject(MultipleImage.class);
		for (String nameField : nameFields) {
			ManipulationProperty manipulationProperty = new ManipulationProperty(nameField, model);
			List<Imagem> propertyListImage = manipulationProperty.getPropertyListImage();
			
			if(propertyListImage != null) {
				for (Imagem property : propertyListImage) {		
					if(property != null) {
						em.remove(property);
					}
				}
			}
			
		}
	}
}
