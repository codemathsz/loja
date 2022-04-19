package br.com.olimposistema.aipa.imagem;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.olimposistema.aipa.model.Model;
import br.com.olimposistema.aipa.vraptorcrud.ReflectionUtil;

public class UpdateWithImageDao {
	
	private EntityManager em;

	public UpdateWithImageDao(EntityManager em) {
		this.em = em;
	}

	public void execute(Model model, String propertyName) {
		
		try {
			
			ManipulationProperty manipulationModel = new ManipulationProperty(propertyName, model);
			
			Field field = new ReflectionUtil(model.getClass()).getField(propertyName);
			
			if(field == null) {
				throw new RuntimeException("O field passado para updateWithImage não existe");
			}
			
			if(field.isAnnotationPresent(SingleImage.class)) {
				
				Imagem imagem = manipulationModel.getPropertyImage();
				new NotUpdateImageDao(em).execute(model);
				manipulationModel.setPropertyImage(imagem);
				
			}else if (field.isAnnotationPresent(MultipleImage.class)) {
				
				List<Imagem> imagens = manipulationModel.getPropertyListImage();
				new NotUpdateImageDao(em).execute(model);
				manipulationModel.setPropertyListImage(imagens);
				
			}else {
				throw new RuntimeException("O field passado para updateWithImage não tem annotation SingleImage ou MultipleImage");
			}
			
		}catch (Exception e) {
			throw new RuntimeException("Erro ao Atualizar Model Produto sem atualizar imagens em UpdateWithImageDao: " + e.getMessage());
		}
		
	}
}
