package br.com.olimposistema.aipa.anexo;

import java.io.File;
import java.io.IOException;

public class DeletaArquivoDoDisco {

	private String path;
	
	public DeletaArquivoDoDisco(String path) {
		this.path = path;
	}
	
	public void deleta() throws IOException {
		try {
			File file = new File(this.path); 
			file.delete();
		} catch (Exception e) {
			throw new IOException("Erro ao deletar File do Disco");
		}
	}
}
