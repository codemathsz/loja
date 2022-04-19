package br.com.loja.model;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;


import br.com.loja.rn.converterDataDeEnParaCalendar;
import br.com.olimposistema.aipa.imagem.Imagem;
import br.com.olimposistema.aipa.model.Model;

@Entity
public class Produto extends Model {
	
	// ATTRIBUTES
	@NotEmpty(message="{produto.nome.null}")
	@Size(min = 3, max = 100, message = "{produto.nome.size}")
	private String nome;
	
	@NotNull(message = "{produto.valor.null}")
	@Min(value = 0, message = "{produto.valor.min}")
	private double valor;
	
	@NotEmpty(message="{produto.descricao.null}")
	@Type(type = "text")
	private String descricao;
	
	@Temporal(TemporalType.DATE)
	@NotNull(message = "{produto.dataValidate.null}")
	private Calendar dataValidade;
	
	@ManyToOne
	@NotNull(message="{produto.categoria.null}")
	private Categoria categoria;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER, orphanRemoval = true)
	private Imagem imagem;
	
	
	// GETTERS AND SETTERS
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getValor() {
		return valor;
	}
	
	public String getValorMoney() {
		
		String formatValor = NumberFormat.getCurrencyInstance(new Locale("pt","BR")).format(valor);
		
		 return formatValor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public Calendar getDataValidade() {
		return dataValidade;
	}
	
	public String getDataValidadeFormat() {
		 
		return new  SimpleDateFormat("dd/MM/yyyy").format(dataValidade.getTime());
	}
	public void setDataValidade(Calendar dataValidade) {
		this.dataValidade = dataValidade;
	}

	
	public void setDataValidadeEn(String data) {
		
		if (data == null) return;

		this.dataValidade = new converterDataDeEnParaCalendar().executa(data);
		
	}
	
	public Imagem getImagem() {
		return imagem;
	}
	
	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}
	
	
}
