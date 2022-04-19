package br.com.olimposistema.aipa.anexo;

import javax.inject.Inject;

import br.com.caelum.vraptor.observer.upload.UploadedFile;

public class SalvaAnexo {
	
	@Inject private GeradorPath geradorPath;

	
	public void salvaAnexo(UploadedFile file) {
		SalvaArquivoNoDisco salvaArquivoNoDisco = new SalvaArquivoNoDisco(file, this.geradorPath.geraPath(file.getFileName()));
		salvaArquivoNoDisco.salvaArquivo();
	}
}
