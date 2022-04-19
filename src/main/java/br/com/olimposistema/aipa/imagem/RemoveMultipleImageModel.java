package br.com.olimposistema.aipa.imagem;

import br.com.olimposistema.aipa.model.Model;

public class RemoveMultipleImageModel extends ManipulationProperty {

	public RemoveMultipleImageModel(String nameProperty, Model model) {
		super(nameProperty,model);
	}
	
	public Boolean execute(Imagem imagem){
		try {
			
			if(super.getPropertyListImage() != null) {
				return super.getPropertyListImage().remove(imagem);
			}
			return false;
		
		}catch (Exception e) {
			throw new RuntimeException("Erro ao remover imagem no Model, erro em: RemoveMultipleImageModel verifique se o nome da property, seus getters/setters e o modelo passados estão corretos");
		}
		
	}
}
