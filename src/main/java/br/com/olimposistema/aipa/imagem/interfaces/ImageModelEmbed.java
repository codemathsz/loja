package br.com.olimposistema.aipa.imagem.interfaces;

import br.com.olimposistema.aipa.imagem.Imagem;

public interface ImageModelEmbed {

	void addImagem(Imagem imagem);
	boolean removeImagem(Imagem imagem);
	Imagem getImagem();
	void setImagem(Imagem imagem);
}
