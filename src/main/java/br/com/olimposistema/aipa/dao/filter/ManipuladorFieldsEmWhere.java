package br.com.olimposistema.aipa.dao.filter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import br.com.olimposistema.aipa.filtrable.Periodo;
import br.com.olimposistema.aipa.filtro.FiltroPeriodo;
import br.com.olimposistema.aipa.model.Model;
import br.com.olimposistema.aipa.service.StringService;
import br.com.olimposistema.aipa.service.Util;

public class ManipuladorFieldsEmWhere<T> {

	private List<Field> fields;
	private T model;

	public ManipuladorFieldsEmWhere(List<Field> fields, T model) {
		this.fields = fields;
		this.model = model;
	}

	public ManipuladorFieldsEmWhere() {

	}

	public List<Predicate> transformaFieldsEmWhere(CriteriaQuery<?> q, CriteriaBuilder cb, From<?, ?> f)
			throws IllegalArgumentException {
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (Util.isNullOrEmpty(fields)) return predicates;

		for (Field field : fields) {
			field.setAccessible(true);

			if (ehUmaSubClasseDeModel(field)) {
				try {
					@SuppressWarnings("rawtypes")
					Join join = f.join(field.getName(), JoinType.LEFT);
					Object value;
					value = field.get(model);
					@SuppressWarnings({ "unchecked", "rawtypes" })
					List<Predicate> subPredicates = new BuscaFieldsPreenchidos(value)
						.busca()
						.transformaFieldsEmWhere(q, cb, join);
					predicates.addAll(subPredicates);
				} catch (IllegalAccessException e) {
				}

			} else if(field.isAnnotationPresent(FiltrablePath.class)) {
				String[] params = field.getAnnotation(FiltrablePath.class).value();
				if(Util.isNull(params) || params.length < 1) throw new RuntimeException("o Field "+field+" Precisa ter mais de dois elementos em @FiltrablePath Ex: @FiltrablePath({\"um\",\"dois\"})");
				
				From<?,?> join = f;
				int indexLast = params.length - 1;
				for (int i = 0; i < indexLast; i++) {
					String param = params[i];
					join = join.join(param, JoinType.LEFT);
				}
				String lastParam = params[indexLast];
				try {
					addNameFieldInPredicates(cb, join, predicates, field, lastParam);
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
			}else {
				try {
					aplicaFiltrosDoModelo(cb, f, predicates, field);		
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
			}
		}
		
		return predicates;
	}

	private void aplicaFiltrosDoModelo(CriteriaBuilder cb, From<?, ?> f, List<Predicate> predicates, Field field)
			throws IllegalAccessException {
		
		String nameField = field.getName();
		addNameFieldInPredicates(cb, f, predicates, field, nameField);
	}

	private void addNameFieldInPredicates(CriteriaBuilder cb, From<?, ?> f, List<Predicate> predicates, Field field, String nameField)
			throws IllegalAccessException {
		if(field.isAnnotationPresent(FiltrableName.class)) {
			predicates.add(cb.like(cb.upper(f.get(nameField)), stringFormaterSearch((String) field.get(model))));
		}else if(field.isAnnotationPresent(FiltrableIsNull.class)) {
			Path<Calendar> path =  f.get(nameField);
			Boolean isNull = (Boolean) field.get(model);
			if(isNull) predicates.add(cb.isNull(path));
			else predicates.add(cb.isNotNull(path));
		}else if(ehDoTipoPeriodo(field)) {
			Path<Calendar> path =  f.get(nameField);
			Periodo periodo = (Periodo) field.get(model);
			new FiltroPeriodo().add(predicates, cb,path,periodo);
		}else {
			predicates.add(cb.equal(f.get(nameField), field.get(model)));
		}
	}

	private boolean ehDoTipoPeriodo(Field field) {
		return field.getType() == Periodo.class;
	}

	private String stringFormaterSearch(String valor) {
		try {
			valor = new StringService(valor).maiusculoSemAcentoESemEspacosAMais();
		}catch (Exception e) {}
		return valor+"%";
	}

	private boolean ehUmaSubClasseDeModel(Field field) {
		return Model.class.isAssignableFrom(field.getType());
	}

}
