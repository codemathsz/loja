package br.com.olimposistema.aipa.imagem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import br.com.olimposistema.aipa.model.Model;

public class ManipulationProperty {

	protected String nameProperty;
	protected Model model;

	public ManipulationProperty(String nameProperty, Model model) {
		this.nameProperty = nameProperty;
		this.model = model;
	}
	
	public Imagem getPropertyImage() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = model.getClass().getMethod(propertyToGet(), null);
		return (Imagem) method.invoke(model, null);
	}
	
	public void setPropertyImage(Imagem image) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = model.getClass().getMethod(propertyToSet(), Imagem.class);
		method.invoke(model, image);
	}
	
	public List<Imagem> getPropertyListImage() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = model.getClass().getMethod(propertyToGet(), null);
		return (List<Imagem>) method.invoke(model, null);
	}
	
	public void setPropertyListImage(List<Imagem> images) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = model.getClass().getMethod(propertyToSet(), List.class);
		method.invoke(model, images);
	}
	
	public String propertyToGet() {
		return "get"+nameProperty.substring(0,1).toUpperCase().concat(nameProperty.substring(1));
	}
	public String propertyToSet() {
		return "set"+nameProperty.substring(0,1).toUpperCase().concat(nameProperty.substring(1));
	}
	
	
}
