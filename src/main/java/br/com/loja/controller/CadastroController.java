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
import br.com.loja.dao.UsuarioDao;
import br.com.loja.model.Usuario;
import br.com.olimposistema.aipa.dao.DAO;

@Controller
@Path("cadastro")
public class CadastroController {
	
	@Inject EntityManager em;
	@Inject Result result;
	@Inject UsuarioDao usuarioDao;
	@Inject Validator validator;
	@Inject HttpSession session;
	
	@Get("")
	public void cadastro() {
		
	}
	@IncludeParameters
	@Post("salvar")
	public void salvar(@Valid Usuario usuario, String confirmaSenha) {
		boolean senhasIguais = usuario.getSenha().equals(confirmaSenha);
		validator.ensure(senhasIguais, new SimpleMessage("erro", "* A confirmação da senha estão diferentes..."));
		validator.onErrorRedirectTo(this).cadastro();
		usuarioDao.insert(usuario);
		session.setAttribute("usuarioLogado", usuario);
		result.redirectTo(ProdutosController.class).produtos();
		
		System.out.println("");
		System.out.println("         Usuario cadastrado!        ");
		System.out.println("------------------------------------");
		System.out.println("| Nome:  "+usuario.getNome());
		System.out.println("| Email: "+usuario.getEmail());
		System.out.println("| Senha: "+usuario.getSenha());
		System.out.println("------------------------------------");
		System.out.println("");
	}
}
