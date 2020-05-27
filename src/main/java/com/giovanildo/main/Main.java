package com.giovanildo.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Calendar calendario = new GregorianCalendar();
		Calendar calendarioInicio = new GregorianCalendar();
		Calendar calendarioFim = new GregorianCalendar();
		calendario.setTime(new Date());

		int inicio = calendario.getActualMinimum(Calendar.DATE); 
		int fim = calendario.getActualMaximum(Calendar.DATE);				
		
		calendarioInicio.set(Calendar.DATE, inicio);
		calendarioFim.set(Calendar.DATE, fim);
		
		List<Date> datas = geraDatasEmUmPeriodo(calendarioInicio.getTime(), calendarioFim.getTime(), Calendar.DATE, 1);
		
		for (Date daVez : datas) {
			System.out.println(sdf.format(daVez));
		}
	}

	public static List<Date> geraDatasEmUmPeriodo(Date dataInicial, Date dataFinal, Integer oQueIncrementar,
			Integer incremento) {
		List<Date> datasEmUmPeriodo = new ArrayList<>();
		Calendar calendarioInicial = new GregorianCalendar();
		calendarioInicial.setTime(dataInicial);

		Calendar calendarioFinal = new GregorianCalendar();
		calendarioFinal.setTime(dataFinal);

		while (calendarioInicial.before(calendarioFinal)) {
			Date resultado = calendarioInicial.getTime();
			datasEmUmPeriodo.add(resultado);
			calendarioInicial.add(oQueIncrementar, incremento);
		}
		return datasEmUmPeriodo;
	}
}