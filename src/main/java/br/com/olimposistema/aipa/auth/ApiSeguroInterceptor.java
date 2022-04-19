package br.com.olimposistema.aipa.auth;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.view.Results;
import br.com.olimposistema.aipa.auth.token.TokenService;

@Intercepts
@AcceptsWithAnnotations(ApiSeguro.class)
public class ApiSeguroInterceptor {
	
	@Inject HttpServletRequest request;
	@Inject Result result;
	
	@AroundCall
	public void autenticar(SimpleInterceptorStack stack) {
		
		boolean tokenEhValido = false;
		
		try {
			String authorizationHeader = obtemAuthorizationHeader();
			
			String token = extracTokenOfHeader(authorizationHeader);			
			tokenEhValido = new TokenService().validaToken(token);
			
			if(tokenEhValido) {
				stack.next();
			}else {
				throw new InterceptionException("Token Invalido - Seu token esta errado ou a data de validade dele venceu");
			}
				
		}catch (Exception e) {
			result.use(Results.http()).sendError(401, e.getMessage());
		}
		
	}


	private String extracTokenOfHeader(String authorizationHeader) {
		return authorizationHeader.substring("Bearer".length()).trim();
	}
	

	private String obtemAuthorizationHeader() throws InterceptionException {
		try{
		
			String authorizationHeader = request.getHeader("Authorization");
		
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {		
				throw new InterceptionException("Authorization header precisa ser provido");
			}
			
			return authorizationHeader;
		}catch (InterceptionException e) {
			throw new InterceptionException("Authorization header precisa ser provido");
		}
		
	}

}
