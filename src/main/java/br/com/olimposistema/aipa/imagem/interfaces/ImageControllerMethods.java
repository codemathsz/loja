package br.com.olimposistema.aipa.imagem.interfaces;

import javax.validation.Valid;

import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.olimposistema.aipa.imagem.Imagem;
import br.com.olimposistema.aipa.model.Model;

public interface ImageControllerMethods<T extends Model> {
	 void imagens(T model);
	 void addImagens(UploadedFile[] files, @Valid T mercado) throws Exception;
	 void removeImage(T mercado, Imagem imagem) throws Exception;
}
