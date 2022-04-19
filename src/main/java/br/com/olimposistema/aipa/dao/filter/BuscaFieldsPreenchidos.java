package br.com.olimposistema.aipa.dao.filter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.olimposistema.aipa.vraptorcrud.ReflectionUtil;

public class BuscaFieldsPreenchidos<T> {

	private T model;

	public BuscaFieldsPreenchidos(T model) {
		this.model = model;
	}

	public ManipuladorFieldsEmWhere<T> busca() {
		@SuppressWarnings("unchecked")
		Class<T> classe = (Class<T>) this.model.getClass();
		ArrayList<Field> fields = new ReflectionUtil(classe).getFieldsIncludingSuperClass();
		
		List<Field> listaFiltrada = fields.stream()
			.filter(f-> 
				tenhaValorNoField(f) && 
				casoIdEhMaiorQueZero(f) && 
				!temNotFiltrableAnotado(f) &&
				!temStartPositionAnotado(f) &&
				!temMaxResultAnotado(f)
			)
			.collect(Collectors.toList());
		
		List<String> nomefieldsFiltrados = listaFiltrada.stream()
			.map(Field::getName)
			.collect(Collectors.toList());

		System.out.println("Filtrando Por: "+nomefieldsFiltrados.toString());
		return new ManipuladorFieldsEmWhere<T>(listaFiltrada,model);
	}

	private boolean temMaxResultAnotado(Field f) {
		return f.isAnnotationPresent(FiltrableMaxResult.class);
	}

	private boolean temStartPositionAnotado(Field f) {
		return f.isAnnotationPresent(FiltrableStartPosition.class);
	}

	private boolean temNotFiltrableAnotado(Field f) {
		return f.isAnnotationPresent(NotFiltrable.class);
	}

	private boolean casoIdEhMaiorQueZero(Field f) {
		if(f.getName().equals("id")) {
			try {
				return  ((Integer) f.get(model) > 0);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	private boolean tenhaValorNoField(Field f) {
		try {
			f.setAccessible(true);
			return f.get(model) != null;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

}
