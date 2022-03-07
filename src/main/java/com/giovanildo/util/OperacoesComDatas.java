package com.giovanildo.util;

import java.util.ArrayList;
import java.util.Collections;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.ReadableInterval;

/**
 * Classe que auxilia fornecendo m�todos �teis e g�nericos de datas. Esses
 * m�todos estavam anteriormente na classe FrequenciaMensalMBean, e na classe
 * modelo FrequenciaMensal. Para melhorar a leitura e o desacoplamento do
 * c�digo, foi separado.
 * 
 * Assim, al�m de ser �til nessas classes podem ser reutilizados mais facilmente
 * em outras classes.
 * 
 * @author giovanildo
 *
 */
public class OperacoesComDatas {

	/**
	 * Coloca a data com dia 1, assim da pra comparar duas datas somente levando em
	 * considera��o o mes e o ano
	 * 
	 * @param data
	 * @return
	 */
	public static GregorianCalendar fazerDataFicarApenasComMesAno(Date data) {
		GregorianCalendar mesAno = new GregorianCalendar();
		mesAno.setTime(data);
		mesAno.set(Calendar.DATE, 1);
		return mesAno;
	}
	
	/***
	 * Retorna data com a hora zerada
	 * Retorna somente dia m�s e ano
	 * @return
	 */
	public static Date getDateWithoutTimeUsingCalendar() {
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);

	    return calendar.getTime();
	}
	

	/**
	 * 
	 * @param datasJaCriadas
	 * @param datasPossiveis
	 * @return gera uma array de datas que cont�m a intersec��o inversa das duas
	 *         listas. Serve para gerar as frequencias ainda n�o geradas
	 */
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

		Collections.sort(naoCriadas);

		return naoCriadas;
	}

	/**
	 * 
	 * @param data
	 * @param hora
	 * @return pega uma date com data e outra date com hora e minuto e combina as
	 *         duas em uma date s�.
	 */
	public static Date combinaDataEhora(Date data, Date hora) {

		Calendar aData = new GregorianCalendar();
		aData.setTime(data);

		Calendar aHora = new GregorianCalendar();
		aHora.setTime(hora);

		Calendar aDataHora = new GregorianCalendar();
		aDataHora.set(Calendar.DAY_OF_MONTH, aData.get(Calendar.DAY_OF_MONTH));
		aDataHora.set(Calendar.MONTH, aData.get(Calendar.MONTH));
		aDataHora.set(Calendar.YEAR, aData.get(Calendar.YEAR));
		aDataHora.set(Calendar.HOUR_OF_DAY, aHora.get(Calendar.HOUR_OF_DAY));
		aDataHora.set(Calendar.MINUTE, aHora.get(Calendar.MINUTE));
		aDataHora.set(Calendar.SECOND, aHora.get(Calendar.SECOND));

		return aDataHora.getTime();
	}

	/**
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @param oQueIncrementar
	 * @param incremento
	 * @return gera uma lista de datas, usando como referencia uma data inicial e
	 *         outra final. O que incrementar refere-se, se � m�s ano dia, etc. O
	 *         incremento � o quanto se quer incrementar.
	 * 
	 *         ex: quero uma lista de dias entre 01/05/2020 - 06/05/2020 dataInicial
	 *         : 01/05/2020 dataFinal: 06/05/2020 O que incrementar: Dia Incremento:
	 *         1
	 * 
	 *         geraDatasEmUmPeriodo(dataInicial, dataFinal, Calendar.DAY_OF_MONTH,
	 *         1)
	 * 
	 *         resultado:
	 * 
	 *         01/05/2020 02/05/2020 03/05/2020 04/05/2020 05/05/2020 06/05/2020
	 * 
	 */
	public static List<Date> geraDatasEmUmPeriodo(Date dataInicial, Date dataFinal, Integer oQueIncrementar,
			Integer incremento) {

		List<Date> datasEmUmPeriodo = new ArrayList<Date>();
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

	/**
	 * 
	 * @param dataHoraDigitadaInicial
	 * @param dataDigitadaFinal
	 * @param dataInicialJaExistente
	 * @param dataFinalJaExistente
	 * @return verifica se um intervalo de data e hora est� contido dentro de outro
	 *         intervalo de hora.
	 */
	public static boolean comparaIntervalosDeDataHora(Date dataHoraDigitadaInicial, Date dataDigitadaFinal,
			Date dataInicialJaExistente, Date dataFinalJaExistente) {
		try {
			Interval intervalDigitado = new Interval(new DateTime(dataHoraDigitadaInicial),
					new DateTime(dataDigitadaFinal));
			Interval intervaloNoBancoDeDados = new Interval(new DateTime(dataInicialJaExistente),
					new DateTime(dataFinalJaExistente));

			ReadableInterval readableInterval = intervaloNoBancoDeDados;

			if (intervalDigitado.overlaps(readableInterval)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @param cargaHoraria
	 * @return formata a carga hor�ria de milisegundos para o formato hora e minutos
	 *         por extenso
	 */
	public static String cargaHorariaFormatada(Long cargaHoraria) {
		long hora = TimeUnit.MILLISECONDS.toHours(cargaHoraria);
		long minutos = TimeUnit.MILLISECONDS.toMinutes(cargaHoraria) - TimeUnit.HOURS.toMinutes(hora);

		if (hora == 0 && minutos == 0)
			return "";

		if (hora == 0)
			return String.format("%02d min", minutos);

		if (minutos == 0)
			return String.format("%02d Hs", hora);

		return String.format("%02d hs, %02d min", hora, minutos);
	}

}
