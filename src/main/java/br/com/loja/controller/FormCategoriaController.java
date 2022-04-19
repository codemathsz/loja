package br.com.loja.controller;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.IncludeParameters;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.loja.dao.CategoriaDao;
import br.com.loja.interceptors.somenteLogado;
import br.com.loja.model.Categoria;
import br.com.olimposistema.aipa.service.Util;

@Controller
@Path("formCategoria")
public class FormCategoriaController {
	
	@Inject EntityManager em;
	@Inject CategoriaDao categoriaDao;
	@Inject Result result;
	@Inject Validator validator;
	@Inject HttpSession session;
	
	@somenteLogado
	@Get("")
	public void formCategoria(Categoria categoria) {
		if(Util.isNotNull(categoria) && Util.isPositivo(categoria.getId())) {
			Categoria categoriaDoBanco = categoriaDao.selectPorId(categoria);
			result.include("categoria", categoriaDoBanco);
		}
	}
	
	@IncludeParameters
	@somenteLogado
	@Post("salvaCategoria")
	public void salvaCategoria(@Valid Categoria categoria) {
		
		boolean verificaNome = false;
		if (categoriaDao.existeCategoriaCom(categoria.getNome()) == null) {
			verificaNome = true;
		}
		validator.ensure(verificaNome, new SimpleMessage("erro", "Ja existe uma categoria com esse nome, por favor digite outro nome..."));
		validator.onErrorRedirectTo(this).formCategoria(categoria);
		categoriaDao.insertOrUpdate(categoria);
		result.redirectTo(CategoriasController.class).categorias();
	}
}
