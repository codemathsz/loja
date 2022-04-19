package br.com.olimposistema.aipa.imagem;

import java.util.ArrayList;
import java.util.List;

import br.com.olimposistema.aipa.model.Model;

public class AddMultipleImageModel extends ManipulationProperty {
	
	public AddMultipleImageModel(String nameProperty, Model model) {
		super(nameProperty,model);
	}
	
	public void execute(List<Imagem> imagens) {
		
		try {
		
			if(super.getPropertyListImage() == null) {
				super.setPropertyListImage(new ArrayList<Imagem>());
			}
			
			if(imagens != null && !imagens.isEmpty()) {
				
				for (Imagem imagem : imagens) {
					imagem.salvaNoDisco();
					super.getPropertyListImage().add(imagem);
				}
			}
		
		}catch (Exception e) {
			throw new RuntimeException("Erro ao adicionar imagem no Model, erro em: AddMultipleImageModel verifique se o nome da property, seus getters/setters e o modelo passados estão corretos");
		}
		
	}
	
	
}
