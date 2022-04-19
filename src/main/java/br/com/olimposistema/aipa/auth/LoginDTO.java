package br.com.olimposistema.aipa.auth;

public class LoginDTO implements Autenticavel {

	private String email;
	private String senha;
	private String idSocial;
	private String celular;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getIdSocial() {
		return idSocial;
	}
	public void setIdSocial(String idSocial) {
		this.idSocial = idSocial;
	}
	
	
}
