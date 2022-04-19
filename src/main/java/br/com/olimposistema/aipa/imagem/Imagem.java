package br.com.olimposistema.aipa.imagem;

import java.util.Date;

import javax.enterprise.inject.spi.CDI;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.olimposistema.aipa.anexo.DeletaArquivoDoDisco;
import br.com.olimposistema.aipa.anexo.PathAnexo;
import br.com.olimposistema.aipa.anexo.SalvaArquivoNoDisco;
import br.com.olimposistema.aipa.model.Model;

@Entity
public class Imagem extends Model implements PathAnexo {
	
	@NotEmpty(message="{imagem.nome.embranco}") @Size(min=1,max=255, message="{imagem.nome.size}")
	private String nome;
	
	@Transient
	private UploadedFile file;
	
	@Deprecated public Imagem() {}
	
	public Imagem(UploadedFile file) {
		this.file = file;
	}

	public String getNome() {
		return nome;
	}
	
	@Override
	public String getPath() {
		try {
			Environment env = CDI.current().select(Environment.class).get();
			return env.get("path.imagens")+"/"+this.nome;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void salvaNoDisco() throws Exception {
		if(file == null) {
			return;
		}
		
		if(getPath() != null) {
			new DeletaArquivoDoDisco(this.getPath()).deleta();
		}
		String fileName = new Date().getTime() + '_'+ file.getFileName();
		this.nome= fileName;
		
		Environment env = CDI.current().select(Environment.class).get();
		String path = env.get("path.imagens")+"/"+this.nome;	
		new SalvaArquivoNoDisco(file, path).salvaArquivo();	
	}
	
	public void deletaNoDisco() throws Exception {
		new DeletaArquivoDoDisco(this.getPath()).deleta();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o) {
            return true;
        }
		
		if (o == null || getClass() != o.getClass()) {
            return false;
        }
		
		Imagem imagem = (Imagem) o;
		return this.getId() == imagem.getId();
	}
	
	@PreRemove
	public void onDelete() throws Exception {
		this.deletaNoDisco();
	}

	@PrePersist
	@PreUpdate
	public void inserirNoDisco() throws Exception {
		
		this.salvaNoDisco();
	}
	
}
