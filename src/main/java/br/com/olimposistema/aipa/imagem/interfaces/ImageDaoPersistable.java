package br.com.olimposistema.aipa.imagem.interfaces;
import br.com.olimposistema.aipa.model.Model;

public interface ImageDaoPersistable<T extends Model> {
	
	T selectPorId(T model);
	void delete(T model);
	T update(T model);
	T updateWithImage(T model, String propertyName);
	
}
