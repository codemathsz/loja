package br.com.olimposistema.aipa.service;

import java.util.List;

public class Util {

	public static boolean isPositivo(Integer valor) {
		return valor != null && valor > 0;
	}
	
	public static boolean isPositivo(Double valor) {
		return valor != null && valor > 0;
	}
	
	public static boolean isPositivo(Long valor) {
		return valor != null && valor > 0;
	}

	public static boolean isNull(Object object) {
		return object == null;
	}
	public static boolean isNotNull(Object object) {
		return object != null;
	}

	public static boolean isNullOrEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	public static boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}
	
	

}
