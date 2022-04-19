package br.com.olimposistema.aipa.imagem;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.observer.upload.UploadSizeLimit;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.olimposistema.aipa.dao.DAO;
import br.com.olimposistema.aipa.imagem.dao.ImagemDAO;
import br.com.olimposistema.aipa.imagem.interfaces.ImageDaoPersistable;
import br.com.olimposistema.aipa.interceptor.Api;
import br.com.olimposistema.aipa.model.Model;
import br.com.olimposistema.aipa.vraptorcrud.SerializeResponse;

@Controller
@Path("/img")
public class ImageController {
	
	private static final int TAMANHO_ARQUIVO = 50; //MB
	
	
	@Inject Result result;
	@Inject Validator validator;
	@Inject EntityManager em;
	@Inject ImagemDAO imagemDao;
	@Inject Environment env;
	//@Inject GeradorPathGenerico geradorPathGenerico;

	@Get("/get/{nomeImagem}")
	public File get(String nomeImagem) {
		
		if(nomeImagem == null || nomeImagem.isEmpty()) {
			result.use(Results.http()).sendError(400, "Você deve passar o nome da imagem exemplo: /img/Mercado/teste.pdf");
			return null;
		}
				
		try {
			File file = new File(env.get("path.imagens")+"/"+nomeImagem);
			if(!file.exists()) {
				result.use(Results.http()).sendError(404, "a imagem que você tentou buscar não existe");
				return null;
			}
			return file;
		
		} catch (Exception e) {
			result.use(Results.http()).sendError(500, e.getMessage());
			return null;
		} 
	}
	
	@Post("add") @UploadSizeLimit(sizeLimit=TAMANHO_ARQUIVO * 1024 * 1024, fileSizeLimit=TAMANHO_ARQUIVO * 1024 * 1024) @Api
	public void add(ImgDTO imgDTO) throws Exception {
		
		//Validações
		if(imgDTO.getFiles() == null|| imgDTO.getFiles().length < 1) {
			result.use(Results.http()).sendError(400, "precisa ser passado um array de files Ex: imgDTO.files = [binary]");
			return;
		}
		
		if(imgDTO.getClassModel() == null || imgDTO.getClassModel().isEmpty()) {
			result.use(Results.http()).sendError(400, "precisa ser passada a classe do Modelo que deseja inserir a imagem Ex: imgDTO.classModel = 'Produto'");
			return;
		}
		
		if(imgDTO.getIdModel() == null || imgDTO.getIdModel() < 1) {
			result.use(Results.http()).sendError(400, "precisa ser passado o id do Model a qual quer inserir a imagem Ex: imgDTO.idModel: 1");
			return;
		}
		
		if(imgDTO.getPropertyName() == null || imgDTO.getPropertyName().isEmpty()) {
			result.use(Results.http()).sendError(400, "precisa ser passado o nome da propriedade do Model a qual quer inserir a imagem Ex: imgDTO.propertyName: 'imagemDeCapa'");
			return;
		}
		
		Model model;
		try {
			model = criaModelApartirDoNome(imgDTO.getClassModel());
		}catch (Exception e) {
			result.use(Results.http()).sendError(400, "Não foi Possível criar um Model com o nome passado: verifique a propriedade imgDTO.propertyName");
			return;
		}
		
		DAO<Model> dao;
		try {
			dao = criaDaoApartirDoNomeModel(imgDTO.getClassModel());
		}catch (Exception e) {
			result.use(Results.http()).sendError(400, "Não foi Possível criar um DAO com o nome passado: verifique a propriedade imgDTO.propertyName");
			return;
		}
		
		model.setId(imgDTO.getIdModel());
		model = dao.selectPorId(model);
		
		ImageDaoPersistable<Model> daoImagePersistable;
		try {
			daoImagePersistable = (ImageDaoPersistable<Model>) dao;
		}catch (Exception e) {
			throw new Exception("Não foi possível realizar operação pois o "+dao.getClass().getName()+" não implementa a interface ImageDaoPersistable");
		}
		
		//transforma files em images conforme oque vir de files
		List<Imagem> imagens = filesToImagens(imgDTO.getFiles());
		
		try {
			adicionaImagensNoModel(model, imgDTO.getPropertyName(), imagens);
		}catch (Exception e) {
			throw new Exception("Não foi possível Adicionar a Lista de Imagens No Model porque: "+e.getMessage());
		}
		
		model = daoImagePersistable.updateWithImage(model, imgDTO.getPropertyName());
		em.getTransaction().commit();
		
		List<Imagem> imagensDoModel = buscaImagensNoModel(model, imgDTO.getPropertyName());
		
		new SerializeResponse(result, Imagem.class).serializeJson(imagensDoModel);
		
	}

	@Get("list")
	@Api
	public void list(ImgDTO imgDTO) throws Exception {
		
		if(imgDTO.getClassModel() == null || imgDTO.getClassModel().isEmpty()) {
			result.use(Results.http()).sendError(400, "precisa ser passada a classe do Modelo que deseja inserir a imagem Ex: imgDTO.classModel = 'Produto'");
			return;
		}
		
		if(imgDTO.getIdModel() == null || imgDTO.getIdModel() < 1) {
			result.use(Results.http()).sendError(400, "precisa ser passado o id do Model a qual quer inserir a imagem Ex: imgDTO.idModel: 1");
			return;
		}
		
		if(imgDTO.getPropertyName() == null || imgDTO.getPropertyName().isEmpty()) {
			result.use(Results.http()).sendError(400, "precisa ser passado o nome da propriedade do Model a qual quer inserir a imagem Ex: imgDTO.propertyName: 'imagemDeCapa'");
			return;
		}
		
		Model model;
		try {
			model = criaModelApartirDoNome(imgDTO.getClassModel());
		}catch (Exception e) {
			result.use(Results.http()).sendError(400, "Não foi Possível criar um Model com o nome passado: verifique a propriedade imgDTO.propertyName");
			return;
		}
		
		DAO<Model> dao;
		try {
			dao = criaDaoApartirDoNomeModel(imgDTO.getClassModel());
		}catch (Exception e) {
			result.use(Results.http()).sendError(400, "Não foi Possível criar um DAO com o nome passado: verifique a propriedade imgDTO.propertyName");
			return;
		}
		
		model.setId(imgDTO.getIdModel());
		model = dao.selectPorId(model);
		
		
		try {
			List<Imagem> imagens = buscaImagensNoModel(model, imgDTO.getPropertyName());
			new SerializeResponse(result, Imagem.class).serializeJson(imagens);
		
		}catch (Exception e) {
			throw new Exception("Não foi possível Buscar a Lista de Imagens No Model porque: "+e.getMessage());
		}		
		
	}
	
	@Delete("remove")
	@Api
	public void remove(ImgDTO imgDTO) throws Exception {
		
		if(imgDTO.getClassModel() == null || imgDTO.getClassModel().isEmpty()) {
			result.use(Results.http()).sendError(400, "precisa ser passada a classe do Modelo que deseja inserir a imagem Ex: imgDTO.classModel = 'Produto'");
			return;
		}
		
		if(imgDTO.getIdModel() == null || imgDTO.getIdModel() < 1) {
			result.use(Results.http()).sendError(400, "precisa ser passado o id do Model a qual quer inserir a imagem Ex: imgDTO.idModel: 1");
			return;
		}
		
		if(imgDTO.getPropertyName() == null || imgDTO.getPropertyName().isEmpty()) {
			result.use(Results.http()).sendError(400, "precisa ser passado o nome da propriedade do Model a qual quer inserir a imagem Ex: imgDTO.propertyName: 'imagemDeCapa'");
			return;
		}
		
		if(imgDTO.getIdImage() == null || imgDTO.getIdImage() <= 0) {
			result.use(Results.http()).sendError(400, "precisa ser passado o id da imagem a qual quer remover  Ex: imgDTO.idImage: 1");
			return;
		}
		
		
		Model model;
		try {
			model = criaModelApartirDoNome(imgDTO.getClassModel());
		}catch (Exception e) {
			result.use(Results.http()).sendError(400, "Não foi Possível criar um Model com o nome passado: verifique a propriedade imgDTO.propertyName");
			return;
		}
		
		DAO<Model> dao;
		try {
			dao = criaDaoApartirDoNomeModel(imgDTO.getClassModel());
		}catch (Exception e) {
			result.use(Results.http()).sendError(400, "Não foi Possível criar um DAO com o nome passado: verifique a propriedade imgDTO.propertyName");
			return;
		}
		
		model.setId(imgDTO.getIdModel());
		model = dao.selectPorId(model);
		
		ImageDaoPersistable<Model> daoImagePersistable;
		try {
			daoImagePersistable = (ImageDaoPersistable<Model>) dao;
		}catch (Exception e) {
			throw new Exception("Não foi possível realizar operação pois o "+dao.getClass().getName()+" não implementa a interface ImageDaoPersistable");
		}
		
		Imagem imagem = imagemDao.selectPorId(imgDTO.getIdImage());
		
		try {
			Boolean foiRemovida = removeImagemNoModel(model, imgDTO.getPropertyName(), imagem);
			
			if(!foiRemovida) {
				result.use(Results.http()).sendError(404, "Não foi Possível Remover a imagem pois ela não existe");
				return;
			}
			
			
			model = daoImagePersistable.updateWithImage(model, imgDTO.getPropertyName());
			imagemDao.delete(imagem);
			em.getTransaction().commit();
			new SerializeResponse(result, Imagem.class).serializeJson(imagem);
			
		}catch (Exception e) {
			throw new Exception("Não foi possível Adicionar a Lista de Imagens No Model porque: "+e.getMessage());
		}
		
	}
	
	
	private Boolean removeImagemNoModel(Model model, String propertyName, Imagem imagem) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = model.getClass().getMethod(propertyToRemove(propertyName), Imagem.class);
		Boolean foiRemovido = (Boolean) method.invoke(model, imagem);
		return foiRemovido;
	}

	private List<Imagem> buscaImagensNoModel(Model model, String propertyName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = model.getClass().getMethod(propertyToList(propertyName), null);
		List<Imagem> imagens = (List<Imagem>) method.invoke(model, null);
		return imagens;
	}

	private List<Imagem> filesToImagens(UploadedFile[] list) {
		List<Imagem> imagens = new ArrayList<Imagem>();
		for (UploadedFile file : list) {
			Imagem imagem = new Imagem(file);
			imagens.add(imagem);
		}
		return imagens;
	}

	private void adicionaImagensNoModel(Model model, String propertyName, List<Imagem> imagens) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = model.getClass().getMethod(propertyToAdd(propertyName), List.class);
		method.invoke(model, imagens);
	}
	
	private String propertyToAdd(String property) {
		return "add"+property.substring(0,1).toUpperCase().concat(property.substring(1));
	}
	
	private String propertyToList(String property) {
		return "list"+property.substring(0,1).toUpperCase().concat(property.substring(1));
	}
	
	private String propertyToRemove(String property) {
		return "remove"+property.substring(0,1).toUpperCase().concat(property.substring(1));
	}

	private Model criaModelApartirDoNome(String classModelString) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> classModel = Class.forName(env.get("package.model")+"."+classModelString);
		return (Model) classModel.newInstance();
	}

	private DAO<Model> criaDaoApartirDoNomeModel(String classModelString) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> classDao = Class.forName(env.get("package.dao")+"."+classModelString+"DAO");
		Constructor<? extends DAO<Model>> constructor = (Constructor<? extends DAO<Model>>) classDao.getConstructor(EntityManager.class);
		return constructor.newInstance(em);
	}
	
}
