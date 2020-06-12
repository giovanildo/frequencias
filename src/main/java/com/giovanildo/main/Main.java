package com.giovanildo.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.giovanildo.dao.DAO;
import com.giovanildo.models.PlanoTrabalho;

public class Main {
	public static void main(String[] args) {

		PlanoTrabalho possivel = new DAO<PlanoTrabalho>(PlanoTrabalho.class).buscaPorId(51);
		PlanoTrabalho criada = new DAO<PlanoTrabalho>(PlanoTrabalho.class).buscaPorId(52);

		List<Date> datasPossiveis = geraDatasEmUmPeriodo(possivel.getDataInicio(), possivel.getDataFim(),
				Calendar.MONTH, 1);
		List<Date> datasCriadas = geraDatasEmUmPeriodo(criada.getDataInicio(), criada.getDataFim(), Calendar.MONTH, 1);

		List<Date> dataNaoCriadas = gerarListaDeDatasQueNaoEstaoNaOutraLista(datasCriadas, datasPossiveis);
		
		System.out.println("--------------------- Possíveis --------------------------");
		for (Date daVez : datasPossiveis) {
			System.out.println(new SimpleDateFormat("MM/yyyy").format(daVez));
		}
		System.out.println("--------------------- Criadas --------------------------");
		for (Date daVez : datasCriadas) {
			System.out.println(new SimpleDateFormat("MM/yyyy").format(daVez));
		}
		System.out.println("--------------------- Não Criadas --------------------------");

		for (Date daVez : dataNaoCriadas) {
			System.out.println(new SimpleDateFormat("MM/yyyy").format(daVez));
		}

	}

	public static List<Date> gerarListaDeDatasQueNaoEstaoNaOutraLista(List<Date> datasJaCriadas,
			List<Date> datasPossiveis) {

		List<Date> naoCriadas = new ArrayList<Date>(datasPossiveis);

		for (Date possivel : datasPossiveis) {
			for (Date jaCriada : datasJaCriadas) {
				if (jaCriada.equals(possivel)) {
					naoCriadas.remove(jaCriada);
				}
			}
			
		}
		return naoCriadas;
	}

	public static List<Date> geraDatasEmUmPeriodo(Date dataInicial, Date dataFinal, Integer oQueIncrementar,
			Integer incremento) {
		List<Date> datasEmUmPeriodo = new ArrayList<>();
		Calendar calendarioInicial = new GregorianCalendar();
		calendarioInicial.setTime(dataInicial);

		Calendar calendarioFinal = new GregorianCalendar();
		calendarioFinal.setTime(dataFinal);

		while (calendarioInicial.before(calendarioFinal) || calendarioInicial.equals(calendarioFinal)) {
			Date resultado = calendarioInicial.getTime();
			datasEmUmPeriodo.add(resultado);
			calendarioInicial.add(oQueIncrementar, incremento);
		}
		return datasEmUmPeriodo;
	}

}