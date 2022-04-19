package br.com.olimposistema.aipa.service;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringService {

	private String stringManipulada;

	public StringService(String stringParaManipulacao) throws Exception {
		super();
		
		if(stringParaManipulacao == null) {
			throw new NullPointerException("A String manipulada não pode ser nula! em : "+this.getClass().getSimpleName());
		}
		this.stringManipulada = stringParaManipulacao;

	}

	/**
	 * Retorna a String maiuscula
	 * Ex: "Gonçálvês" retornara "GONÇÁLVÊS"
	 * @return
	 */

	public String maiusculo() {

		return this.stringManipulada.toUpperCase();

	}

	/**
	 * Retorna a String sem acento
	 * Ex: "Gonçálvês" retornara "Goncalves"
	 * @return
	 */

	public String semAcento() {

		String nfdNormalizedString = Normalizer.normalize(this.stringManipulada, Normalizer.Form.NFD); 
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");

	}
	/**
	 * Tira os espaços do inicio meio e fim de uma String 
	 * ex:  "  Gonçálvês    perreira  " retornara "Gonçálvês perreira"
	 * @return
	 */
	public String tiraEspacosAMais() {
		return this.stringManipulada.trim().replaceAll("\\s+"," ");
	}

	/**
	 * Retorna a String em maiusculo e sem acento
	 * Ex: "Gonçálvês" retornara "GONCALVES"
	 * @return
	 * @throws Exception 
	 */
	public String maiusculoSemAcento() throws Exception {

		String maiusculo = maiusculo();
		StringService subService = new StringService(maiusculo);
		return subService.semAcento();
	}
	/**
	 * Retorna a String em maiusculo sem acentos e sem espaços a mais que estejam no inicio meio e fim da String
	 * ex: "   Gonçálvês   perreira " retornara "GONCALVES PERREIRA"
	 * @return
	 * @throws Exception
	 */
	public String maiusculoSemAcentoESemEspacosAMais() throws Exception {

		String maiusculoSemAcento = maiusculoSemAcento();
		StringService  subService = new StringService(maiusculoSemAcento);
		return subService.tiraEspacosAMais();
		
	}
	
	/**
	 * Retorna Uma String aceitando somente numeros sem qualquer mascara ou espacos
	 * ex: "   033. 668. 021 - 96 /* ?" retornara "03366802196"
	 * @return
	 */
	public String somenteNumeros() {
		return this.stringManipulada.replaceAll("[^0-9]+", "");
	}

	/**
	 * Retorno a primeira palavra da string
	 * ex: "Xandinho Oliveira Luz" retorna "Xandinho"
	 * @return
	 */
	public String primeiraPalavra() {
		String s = this.tiraEspacosAMais();
		return s.substring(0, s.indexOf(' '));
	}
	
	
}
