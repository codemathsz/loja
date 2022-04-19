package br.com.loja.rn;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class converterDataDeEnParaCalendar {

	public Calendar executa(String data) {
		String[] dataParse = data.split("-");// DIVISOR ANO-MÊS-DIA
		
		Integer ano = Integer.parseInt(dataParse[0]);
		Integer mes = Integer.parseInt(dataParse[1]);
		Integer dia = Integer.parseInt(dataParse[2]);
		
		
		Calendar calendar = new GregorianCalendar(ano,(mes-1),dia);
		return calendar;
	}

}
