package br.com.loja.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.IncludeParameters;
import br.com.caelum.vraptor.validator.Validator;
import br.com.loja.dao.CategoriaDao;
import br.com.loja.dao.ProdutoDao;
import br.com.loja.interceptors.somenteLogado;
import br.com.loja.model.Produto;
import br.com.olimposistema.aipa.model.Model;

@Controller
@Path("formProduto")
public class FormProdutoController {
	@Inject HttpSession session;
	@Inject ProdutoDao daoProduto;
	@Inject Result result;
	@Inject Validator validator;
	
	@Inject CategoriaDao daoCategoria;
	
	@Get("")
	@somenteLogado
	public void formProduto() {
		result.include("categoria", daoCategoria.selectAll());
	}
	
	@Post("salvaProduto")
	@somenteLogado
	@IncludeParameters
	public void salvaProduto(@Valid Produto produto) {
		validator.onErrorRedirectTo(this).formProduto();
		daoProduto.insertOrUpdate(produto);
		result.redirectTo(ProdutosController.class).produtos();
	}
}
