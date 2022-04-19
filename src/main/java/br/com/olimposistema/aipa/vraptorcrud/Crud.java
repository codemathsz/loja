package br.com.olimposistema.aipa.vraptorcrud;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.RollbackException;
import javax.validation.Valid;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.Serializer;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.olimposistema.aipa.dao.DAO;
import br.com.olimposistema.aipa.interceptor.Api;
import br.com.olimposistema.aipa.model.Model;

public abstract class Crud<T extends Model> {
	
	@Inject Result result;
	@Inject Validator validator;
	private DAO<T> dao;
	private Class<T> modelClass;
	
	/**
	 * Construtor padrao de para herdar o comportamento de um crud
	 * @param dao deve ser passado um objeto do Tipo DAO por exemplo categoriaDAO
	 */
	public Crud(Class<T> modelClass, DAO<T> dao) {
		super();
		this.dao = dao;
		this.modelClass = modelClass;
	}

	/**
	 * Metodo responsavel por fazer a listagem do Model
	 */
	@Get
	public void lista() {
		
	}
	
	/**
	 * Metodo Exibe o formulario do Model, sendo utilizado tanto para adicionar um model novo
	 * quanto para editar um model jah existente
	 * @param model deve ser informado um modelo do sistema que extende da classe Model por exemplo categoria
	 */
	@Get
	public void form(T model) {
		result.include("model",model);
		
		if(model != null && model.getId() > 0) {
			model = dao.selectPorIdComInativo(model);
			result.include("model",model);
		}
	}
	
	/**
	 * Salva o model no banco de dados, caso nao tenha id ele salva um novo registro
	 * caso já tenha id ele atualiza os dados do registro que existe no banco
	 * @param model model deve ser informado um modelo do sistema que extende da classe Model por exemplo categoria
	 */
	@Post
	public void salvaForm(@Valid T model) {
		result.include("model",model);
		validator.onErrorUsePageOf(this).form(model);
		
		try {
						
			//Regra de Notificação
			if(model.getId() > 0) {
				result.include("updated", true);
			}else {
				result.include("created", true);
			}
						
			//Persistir no Banco		
			model = dao.insertOrUpdate(model);
			
			result.redirectTo(this).lista();		
		
		}  catch (Exception e) {
			validator.add(new SimpleMessage("Erro", "Erro ao Salvar Registro no Banco de Dados: "+e.getMessage()));
			validator.onErrorUsePageOf(this).form(model);
		}
	}
	
	/**
	 * Deleta o modelo informado do banco de dados caso nao exista no banco outros models que dependam dele
	 * @param model deve ser informado um modelo do sistema que extende da classe Model por exemplo categoria
	 */
	@Get
	public void deleta(T model) {
		
		try {
			
			model = dao.selectPorIdComInativo(model);
						
			if(model != null) {
				dao.delete(model);
				result.include("deleted", true);
			}else {
				result.include("deletedprevious", true);
			}
				
			result.redirectTo(this).lista();
			
		} catch (RollbackException e) {
			result.include("deleted", null);
			result.include("notdeleted", true);
			result.redirectTo(this).lista();
			
		} catch (Exception e) {
			validator.add(new SimpleMessage("Erro", "Erro ao Deletar Registro no Banco de Dados: "+e.getMessage()));
			validator.onErrorUsePageOf(this).form(model);
		} 
		
	}
	
	//######## Metodos API #########
	
	/**
	 * Retorna a lista de Models em formato json
	 * @throws Exception pode estourar uma exception que é capturada pelo ApiInterceptor
	 */
	@Api @Get @Post @Consumes("application/json")
	public void listajson() throws Exception {
		Serializer serializer = result.use(Results.json()).from(pesquisaLista());
		incluiAtributosAnotados(serializer);
		serializer.serialize();
	}

	/**inclui atributos anotados com serializeCrud
	 * no Model
	 * @param serializer
	 */
	private void incluiAtributosAnotados(Serializer serializer) {
		ReflectionUtil reflectionUtil = new ReflectionUtil(this.modelClass);
		List<String> nameFields = reflectionUtil.getNameFieldsForObject(SerializeCrud.class);
		for (String name : nameFields) {
			serializer.include(name);
		}
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
			throw new Exception("Nao foi Possivel Obter Lista de Categoria Do Banco de Dados "+e.getMessage());
		}
	}

}
