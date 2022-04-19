package br.com.loja.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.loja.dao.UsuarioDao;
import br.com.loja.model.Usuario;

// Definindo controller
@Controller
@Path("login")
public class LoginController {
	
	
	@Inject Validator validator;
	@Inject Result result;
	@Inject UsuarioDao usuarioDao;
	@Inject HttpSession session;
	
	// Método que redirecionta para o arquivo login
	@Get("")
	public void login() {
		session.removeAttribute("usuarioLogado");
	}
	
	@Post
	public void autenticar(String email, String senha) {
		boolean senhaVazia = senha != null;
		boolean emailVazio = email != null;

		validator.ensure(emailVazio, new SimpleMessage("erro", "Campo do email vazio"));
		validator.ensure(senhaVazia, new SimpleMessage("erro", "Campo da senha vazio"));

		System.out.println("Senha: "+senha+ "\nEmail: "+email);
		validator.onErrorRedirectTo(this).login();
		
		// autentica o usuario
		Usuario usuario = usuarioDao.existeUsuarioCom(email, senha);
		session.setAttribute("usuarioLogado", usuario);
		validator.addIf(usuario == null, new SimpleMessage("erro", "Email ou senha são inválidos..	."));
		validator.onErrorRedirectTo(this).login();
		result.redirectTo(ProdutosController.class).produtos();
	}
}
