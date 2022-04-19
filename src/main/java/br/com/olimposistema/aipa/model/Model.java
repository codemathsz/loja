package br.com.olimposistema.aipa.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import br.com.olimposistema.aipa.service.StringService;

@MappedSuperclass
public abstract class Model {

	@Column
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected int id;
	
	@Column(updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	protected Calendar created;
	
	@Column(updatable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	protected Calendar updated;

	@Column
	protected Boolean ativo;
	
	@Column
	protected String motivoInativacao;
	
	
	//Comentar Metodo para criar estrutura do banco
	
	@PrePersist
    protected void onCreate() {
    updated = created = new GregorianCalendar();
    if(ativo == null) ativo = true;
    }

	//Comentar Metodo Para criar estrutura do banco
    @PreUpdate
    protected void onUpdate() {
    updated = new GregorianCalendar();
    }
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getCreated() {
		return created;
	}
	
	public void setCreated(Calendar created) {
		this.created = created;
	}
	
	public Calendar getUpdated() {
		return updated;
	}
	
	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}
	
	/**
	 * retorna um timestamp de criacao do modelo no formato
	 * dd/MM/yyyy HH:mm
	 * @return
	 */
	public String getCreatedFormated() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(getCreated());
	}
	
	/**
	 * retona a data de criacao no formato
	 * dd/MM/yyyy
	 * @return
	 */
	public String getCreatedDateFormated() {
		return new SimpleDateFormat("dd/MM/yyyy").format(getCreated());
	}
	
	/**
	 * retorna a hora de criacao do modelo formatado em
	 * HH:mm
	 * @return
	 */
	public String getCreatedTimeFormated() {
		return new SimpleDateFormat("HH:mm").format(getCreated());
	}
	
	/**
	 * retorna um timestamp com a atualização do modelo no formato 
	 * dd/MM/yyyy HH:mm
	 * @return
	 */
	public String getUpdatedFormated() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(getUpdated());
	}
	
	/**
	 * retorna a data de atualização do modelo no formato 
	 * dd/MM/yyyy
	 * @return
	 */
	public String getUpdatedDateFormated() {
		return new SimpleDateFormat("dd/MM/yyyy").format(getUpdated());
	}
	
	/**
	 * retorna a hora de atualização do modelo no formato 
	 * HH:mm
	 * @return
	 */
	public String getUpdatedTimeFormated() {
		return new SimpleDateFormat("HH:mm").format(getUpdated());
	}

	public Boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getMotivoInativacao() {
		return motivoInativacao;
	}

	public void setMotivoInativacao(String motivoInativacao) {
		this.motivoInativacao = motivoInativacao;
	}
	
	/**
	 * Responsavel por normalizar uma String, o padrão atual é
	 * Maiusculo, sem acento e sem espaços a mais 
	 * ex: "   Gonçálvês   perreira " retornara "GONCALVES PERREIRA"
	 * @param string
	 * @return string normalizada
	 */
	protected String normalizar(String string) {
		try {
			StringService stringService = new StringService(string);
			string = stringService.maiusculoSemAcentoESemEspacosAMais();
		}catch (Exception e) {
		
		}
		
		return string;
	}
	
	protected String normalizarNumeros(String string) {
		try {
			StringService stringService = new StringService(string);
			string = stringService.somenteNumeros();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return string;
		
	}
	
	public static boolean existeId(Model model) {
		return (model != null && model.getId() > 0);
	}
}
