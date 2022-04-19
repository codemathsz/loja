package br.com.olimposistema.aipa.filtrable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.olimposistema.aipa.service.ConvertDateService;

public class Periodo {
	
	private Calendar dataInicial;
	private Calendar dataFinal;
	
	public Calendar getDataInicial() {
		return dataInicial;
	}
	
	public String getDataInicialFormatada() {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").format(ConvertDateService.toDate(dataInicial));
		}catch (Exception e) {
			return null;
		}
	}
	public void setDataInicial(Calendar dataInicial) {
		validaDataInicialEFinal(dataInicial, this.dataFinal);
		this.dataInicial = dataInicial;
	}
	public Calendar getDataFinal() {
		return dataFinal;
	}
	public String getDataFinalFormatada() {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").format(ConvertDateService.toDate(this.dataFinal));
		}catch (Exception e) {
			return null;
		}
	}
	public void setDataFinal(Calendar dataFinal) {
		validaDataInicialEFinal(this.dataInicial, dataFinal);
		this.dataFinal = dataFinal;
		//this.padronizaDataFinal2359();
	}
	
	private void validaDataInicialEFinal(Calendar dataInicial, Calendar dataFinal) {
		if(dataInicial != null && dataFinal != null) {
			
			if(dataInicial.after(dataFinal)) {
				throw new RuntimeException("a dataInicial não pode ser maior que a dataFinal");
			}
		}
	}
	
	public void padronizaDataInicialMeiaNoite() {
		if(this.dataInicial != null) {
			dataInicial.set(Calendar.HOUR, 0);
			dataInicial.set(Calendar.MINUTE, 0);
			dataInicial.set(Calendar.SECOND, 0);
			dataInicial.set(Calendar.MILLISECOND, 0);
		}
	}
	
	public void padronizaDataFinal2359() {
		if(this.dataFinal != null) {			
			dataFinal.set(Calendar.HOUR, 23);
			dataFinal.set(Calendar.MINUTE, 59);
			dataFinal.set(Calendar.SECOND, 59);
		}
	}
	
	
}
