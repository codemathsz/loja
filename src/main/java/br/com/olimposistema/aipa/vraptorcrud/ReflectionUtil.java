package br.com.olimposistema.aipa.vraptorcrud;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fornece Recursos da API Reflections de uma forma mais alto nível ao programador
 * @author Tiago Luz
 *
 */
public class ReflectionUtil {
	private Class<?> classe;

	/**
	 * Passa a classe que sera utilizada para manipulação com reflections
	 * @param classe
	 */
	public ReflectionUtil(Class<?> classe) {
		this.classe = classe;
	}
	
	/**
	 * Retorna o nome dos atributos da classe mesmo herdados, fazendo um filtro para trazer somente
	 * aqueles que tem a anotação passada como parâmetro
	 * @param filter é a anotação que deve usada para fazer o filtro nos atributos
	 * @return uma lista com o nome dos atributos filtrados
	 */
	public List<String> getNameFieldsForObject(Class<? extends Annotation> filter) {
		List<String> names = new ArrayList<String>();
		ArrayList<Field> fields = getFieldsIncludingSuperClass();
		for (Field field : fields) {
			if(field.isAnnotationPresent(filter)){
				names.add(field.getName());
			}
		}
		return names;
	}
	
	/**
	 * Retorna o nome dos atributos da classe mesmo herdados
	 * @return uma lista com o nome dos atributos 
	 */
	public List<String> getNameFieldsForObject() {
		List<String> names = new ArrayList<String>();
		ArrayList<Field> fields = getFieldsIncludingSuperClass();
		for (Field field : fields) {
			names.add(field.getName());
		}
		return names;
	}
	
	/**
	 * Retorna todos os Fields da classe incluindo os da SuperClasse
	 * @return Retorna todos os Fields da classe incluindo os da SuperClasse
	 */
	public ArrayList<Field> getFieldsIncludingSuperClass(){
		ArrayList<Field> fields = new ArrayList<Field>();
		Class<?> aux = this.classe;
		 while(aux != Object.class){
			fields.addAll(Arrays.asList(aux.getDeclaredFields()));
			aux = aux.getSuperclass();
		}
		
		 return fields;
	}
	
	/**
	 * Retorna o Nome da classe simples com a Primeira Letra Minuscula
	 * Ex: caso nome da Classe: CategoriaRest irá tranformar em: categoriaRest
	 */
	public String getNameClassAgreed() {
		String simpleName = this.classe.getSimpleName();
		String nameAgreed = simpleName.substring(0,1).toLowerCase().concat(simpleName.substring(1));
		return nameAgreed;
	}
	
	public Field getField(String nameField) throws NoSuchFieldException, SecurityException {
		 Field field = this.classe.getDeclaredField(nameField);
		return field;
	}
	
}
