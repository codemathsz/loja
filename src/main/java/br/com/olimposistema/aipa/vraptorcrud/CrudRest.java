package br.com.olimposistema.aipa.vraptorcrud;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.olimposistema.aipa.auth.ApiSeguro;
import br.com.olimposistema.aipa.dao.DAO;
import br.com.olimposistema.aipa.interceptor.Api;
import br.com.olimposistema.aipa.model.Model;

public class CrudRest<T extends Model> {
	
	@Inject Result result;
	@Inject Validator validator;
	@Inject EntityManager em;
	private DAO<T> dao;
	private Class<T> modelClass;

	/**
	 * Construtor padrao de para herdar o comportamento de um crud
	 * @param dao deve ser passado um objeto do Tipo DAO por exemplo categoriaDAO
	 */
	public CrudRest(Class<T> modelClass, DAO<T> dao) {
		super();
		this.dao = dao;
		this.modelClass = modelClass;
			
	}
	
	@Get("") @Api @ApiSeguro
	public void lista() throws Exception {
		List<T> lista = dao.selectAll();
		new SerializeResponse(result, modelClass).serializeJson(lista);
	}
	
	@Get("todos") @Api @ApiSeguro
	public void listaTodos() throws Exception {
		List<T> lista = pesquisaLista();
		new SerializeResponse(result, modelClass).serializeJson(lista);
	}
	
	@Post("") @Consumes(value="application/json") @Api @ApiSeguro
	public void adicionaRest(@Valid T model) {	
		T modelInserido = dao.insert(model);
		new SerializeResponse(result, modelClass).serializeJson(modelInserido);	
	}
	
	@Get("{id}") @Api @ApiSeguro
	public void pesquisaId(Integer id) throws Exception {
		T model = this.modelClass.newInstance();
		model.setId(id);
		T modelSelecionado = dao.selectPorId(model);
		new SerializeResponse(result, modelClass).serializeJson(modelSelecionado);
	}
	
	@Put("{id}") @Consumes("application/json") @Api @ApiSeguro
	public void atualizaCompleto(@Valid T model, Integer id) {
		validator.onErrorSendBadRequest();
		model.setId(id);
		T modelAtualizado = dao.update(model);
		em.getTransaction().commit();
		new SerializeResponse(result, modelClass).serializeJson(modelAtualizado);
	}
	
	@Delete("{id}") @Api @ApiSeguro
	public void deleta(T model, Integer id) {
		model.setId(id);
		dao.delete(model);
		new SerializeResponse(result, modelClass).serializeJson(model);
	}
	
	
	/**
	 * usa o Dao para retornar os registros do Modelo do Banco de dados
	 * @return
	 * @throws Exception
	 */
	private List<T> pesquisaLista() throws Exception {
		try {
			List<T> lista = dao.selectAllComInativos();
			return lista;
			
		}catch (Exception e) {
			throw new Exception("Nao foi Possivel Obter Lista do Banco de Dados "+e.getMessage());
		}
	}
	
}
