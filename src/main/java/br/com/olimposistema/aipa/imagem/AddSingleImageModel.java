package br.com.olimposistema.aipa.imagem;

import java.util.List;

import br.com.olimposistema.aipa.model.Model;

public class AddSingleImageModel extends ManipulationProperty {
		
	public AddSingleImageModel(String nameProperty, Model model) {
		super(nameProperty,model);
	}

	public void execute(List<Imagem> imagens) {
		try {
			if(imagens != null && !imagens.isEmpty()) {
				Imagem primeiraImagem = imagens.get(0);
				if(super.getPropertyImage() != null) {
					super.getPropertyImage().deletaNoDisco(); // deleta a imagem atual que sera sobreescrita
				}
				primeiraImagem.salvaNoDisco();
				super.setPropertyImage(primeiraImagem);
			}
		}catch (Exception e) {
			throw new RuntimeException("Erro ao adicionar imagem no Model, erro em: AddSingleImageModel verifique se o nome da property, seus getters/setters e o modelo passados estão corretos");
		}
		
	}
	
}
