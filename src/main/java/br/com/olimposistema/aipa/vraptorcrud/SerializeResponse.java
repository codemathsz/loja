package br.com.olimposistema.aipa.vraptorcrud;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.Serializer;
import br.com.caelum.vraptor.view.Results;

/**
 * Classe Responsavel Por serializar Objetos Na resposta para JSON
 * @author Tiago Luz
 *
 */
public class SerializeResponse {
	
	private Result result;
	private Class<?> modelClass;
	
	public SerializeResponse(Result result, Class<?> modelClass) {
		super();
		this.result = result;
		this.modelClass = modelClass;
	}
	
	public Serializer getSerializer(Object object) {
		Serializer serializer = this.result.use(Results.json()).withoutRoot().from(object);
		incluiAtributosAnotados(serializer);
		excluiAtributosAnotados(serializer);
		return serializer;
	}

	public void serializeJson(Object object) {
		Serializer serializer = this.result.use(Results.json()).withoutRoot().from(object);
		incluiAtributosAnotados(serializer);
		excluiAtributosAnotados(serializer);
		serializer.serialize();
	}

	/**inclui atributos anotados com serializeCrud
	 * no Model
	 * @param serializer
	 */
	private void incluiAtributosAnotados(Serializer serializer) {
		List<String> nameFields = getNameFieldsAndParametersForObjectIfSerializeCrud();
		nameFields.forEach(name->serializer.include(name));
	}
	
	/**exclui anotados com NotSerializeCrud
	 * no Model
	 * @param serializer
	 */
	private void excluiAtributosAnotados(Serializer serializer) {
		List<String> nameFields = getNameFieldsAndParametersForObjectIfNotSerializeCrud();
		nameFields.forEach(name->serializer.exclude(name));
	}
	
	/**
	 * Retorna o nome dos atributos da classe mesmo herdados, fazendo um filtro para trazer somente
	 * aqueles que tem a anotação passada como parâmetro
	 * @return uma lista com o nome dos atributos filtrados
	 */
	private List<String> getNameFieldsAndParametersForObjectIfSerializeCrud() {
		Class<SerializeCrud> filter = SerializeCrud.class;
		List<String> names = new ArrayList<String>();
		ArrayList<Field> fields = new ReflectionUtil(this.modelClass).getFieldsIncludingSuperClass();
		for (Field field : fields) {
			if(field.isAnnotationPresent(filter)){			
				names.add(field.getName());
				//inclui anotacoes passadas como parametro
				SerializeCrud annotation = field.getAnnotation(filter);
				String[] params = annotation.include();
				if(params != null) {
					for (String param : params) {
						names.add(param);
					}
				}				
			}
		}
		return names;
	}
	
	/**
	 * Retorna o nome dos atributos da classe mesmo herdados, fazendo um filtro para trazer somente
	 * aqueles que tem a anotação passada como parâmetro
	 * @return uma lista com o nome dos atributos filtrados
	 */
	private List<String> getNameFieldsAndParametersForObjectIfNotSerializeCrud() {
		Class<NotSerializeCrud> filter = NotSerializeCrud.class;
		List<String> names = new ArrayList<String>();
		ArrayList<Field> fields = new ReflectionUtil(this.modelClass).getFieldsIncludingSuperClass();
		for (Field field : fields) {
			if(field.isAnnotationPresent(filter)){			
				names.add(field.getName());				
			}
		}
		return names;
	}
	
}