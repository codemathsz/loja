package br.com.olimposistema.aipa.imagem.dao;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Typed;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.olimposistema.aipa.dao.DAO;
import br.com.olimposistema.aipa.dao.IdInvalidoException;
import br.com.olimposistema.aipa.imagem.Imagem;

@RequestScoped
@Default
@Typed(ImagemDAO.class)
public class ImagemDAO extends DAO<Imagem> {

	@Inject  //Posso pedir o ImovelDAO injetado pois o CDI sabe como instanciar o objeto implementado no producers do plugin
	public ImagemDAO(EntityManager em) {
		super(em, Imagem.class);
	}
	
	@Deprecated public ImagemDAO() {this(null);}// uso do CDI

	public Imagem selectPorId(int idImagem) throws IdInvalidoException {
		@SuppressWarnings("deprecation")
		Imagem imagem = new Imagem();
		imagem.setId(idImagem);
		return super.selectPorId(imagem);
	}
}
