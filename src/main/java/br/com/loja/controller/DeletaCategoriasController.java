package br.com.loja.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.loja.dao.CategoriaDao;
import br.com.loja.interceptors.somenteLogado;
import br.com.loja.model.Categoria;

@Controller
@Path("DeletaCategorias")
public class DeletaCategoriasController {
	
	@Inject CategoriaDao dao;
	@Inject Result result;
	
	@Get("/{categoria.id}")
	@somenteLogado
	public void  deletaCategorias(Categoria categoria) {
		dao.delete(categoria);
		result.redirectTo(CategoriasController.class).categorias();
	}
}
