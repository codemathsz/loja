package br.com.loja.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.olimposistema.aipa.model.Model;

@Entity
public class Categoria extends Model {
	@NotNull(message="{categoria.nome.null}")
	@Size(min = 3, max = 100, message = "{categoria.nome.size}")
	@Column(unique = true)
	private String nome;
	
	@OneToMany(mappedBy = "categoria")
	private List<Produto> produto;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
