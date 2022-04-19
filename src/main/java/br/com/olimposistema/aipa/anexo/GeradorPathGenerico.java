package br.com.olimposistema.aipa.anexo;

import javax.inject.Inject;

import br.com.caelum.vraptor.environment.Environment;

/**
 * Classe irá gerar o path em disco, para arquivos genericamente com o nome da classe
 * Ex: Passada a Classe Processo será gerada o path PATH_ANEXO/Processo/nomearquivo.xx
 * @author Tiago Luz
 */
public class GeradorPathGenerico implements GeradorPath {
	
	@Inject Environment env;
	
	/**
	 * Gera o caminho para o arquivo
	 */
	public String geraPath(String fileName) {
		return env.get("path.anexo")+"/"+fileName;
	}
	
}