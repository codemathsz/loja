package br.com.olimposistema.aipa.filtrable;

public interface IFiltrable<T> {

	Boolean isAtivo();
	void setAtivo(Boolean ativo);
	public Integer getStartPosition();
	public void setStartPosition(Integer startPosition);
	public Integer getMaxResult();
	public void setMaxResult(Integer maxResult);
	
}
