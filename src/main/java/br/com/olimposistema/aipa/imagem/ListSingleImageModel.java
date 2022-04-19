package br.com.olimposistema.aipa.imagem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.olimposistema.aipa.model.Model;

public class ListSingleImageModel extends ManipulationProperty {

	public ListSingleImageModel(String nameProperty, Model model) {
		super(nameProperty,model);
	}
	
	public List<Imagem> execute(){
		try {
			if(super.getPropertyImage() == null) {
				return new ArrayList<Imagem>();
			}
			return Arrays.asList(super.getPropertyImage());
		}catch (Exception e) {
			throw new RuntimeException("Erro ao listar imagem no Model, erro em: ListSingleImageModel verifique se o nome da property, seus getters/setters e o modelo passados estão corretos");
		}
	}
}
