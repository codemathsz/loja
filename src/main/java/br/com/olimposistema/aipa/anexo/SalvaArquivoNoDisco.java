package br.com.olimposistema.aipa.anexo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import br.com.caelum.vraptor.observer.upload.UploadedFile;

public class SalvaArquivoNoDisco {

	private UploadedFile file;
	private String path;

	public SalvaArquivoNoDisco(UploadedFile file, String path) {
		this.file = file;
		this.path = path;
	}
	
	public void salvaArquivo() {
		try {
			InputStream fileInputStream = file.getFile();
			String pathArquivo = path;
			
			
			try(FileOutputStream fileOutputStream = new FileOutputStream(pathArquivo);  
		    	BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
		    	BufferedInputStream bis = new BufferedInputStream(fileInputStream)){
		    	
		    	escreveArquivoNoDisco(bos, bis);    
				    
		    }
			
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void escreveArquivoNoDisco(BufferedOutputStream bos, BufferedInputStream bis) throws IOException {
		int data = bis.read();
		while(data != -1) {
			bos.write(data);
			bos.flush();
			data = bis.read();
		}
	}
}
