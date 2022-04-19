package br.com.olimposistema.aipa.auth.token;

import java.util.Date;

import br.com.olimposistema.aipa.auth.User;

public interface GeradorToken {
	String gerarToken(User userPayload);
	String gerarToken(String usuario);
	boolean validaToken(String token);
	Date getDataValidadeTokenStartingNow();
}
