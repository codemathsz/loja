package br.com.loja.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.loja.dao.ProdutoDao;
import br.com.loja.interceptors.somenteLogado;
import br.com.loja.model.Produto;
import br.com.olimposistema.aipa.dao.DAO;

@Controller
@Path("produtos")
public class ProdutosController {
	
	
	@Inject HttpSession session;
	
	@Inject
	Result result;
	
	@Inject
	ProdutoDao produtoDao;
	
	@Get("")
	@somenteLogado
	public void produtos() {
		
		result.include("produtos", produtoDao.selectAll());
	}
}
