package br.com.olimposistema.aipa.auth;

import javax.servlet.http.HttpServletResponse;

import br.com.olimposistema.aipa.auth.token.GeradorToken;

public class AdicionaTokenNoHeader {

	private HttpServletResponse response;
	private GeradorToken geradorToken;

	public AdicionaTokenNoHeader(HttpServletResponse response, GeradorToken geradorToken) {
		this.response = response;
		this.geradorToken = geradorToken;
	}
	
	/**
	 * Gera um token que contem um corpo
	 * @param payload É um objeto que pode ser incluído no corpo do Token
	 * @return
	 */
	public void adiciona(User userPayload) {
		String token = geradorToken.gerarToken(userPayload);
		response.setHeader("access-control-expose-headers ", "x-access-token");
		response.setHeader("x-access-token", token);
	}
}
