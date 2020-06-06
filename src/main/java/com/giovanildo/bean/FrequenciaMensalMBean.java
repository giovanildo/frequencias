package com.giovanildo.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.giovanildo.dao.DAO;
import com.giovanildo.models.AtividadePesquisa;
import com.giovanildo.models.FrequenciaMensal;
import com.giovanildo.models.PlanoTrabalho;
import com.giovanildo.models.Situacao;
import com.giovanildo.models.SituacaoFrequenciaMensal;

@Named
@ViewScoped
public class FrequenciaMensalMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Frequência Mensal
	 */
	private FrequenciaMensal frequenciaMensal;
	private Integer planoTrabalhoId;
	private Date mesAnoFrequencia;

	// atributos a serem persistidos como atividade pesquisa
	private Date diaAtividade;
	private Date horaInicioAtividade;
	private Date horaFimAtividade;
	private String descricaoAtividade;

	public FrequenciaMensalMBean() {
		super();
		this.frequenciaMensal = new FrequenciaMensal();
	}

	public String getSituacaoAtual() {

		return "SEM SITUAÇÃO";

//		if (this.frequenciaMensal.getHistoricoSituacao().isEmpty()) {
//			return " Não Preenchida ";
//		}
//
//		SituacaoFrequenciaMensal maiorData = null;
//		for (SituacaoFrequenciaMensal daVez : this.frequenciaMensal.getHistoricoSituacao()) {
//			if (maiorData == null) {
//				maiorData = daVez;
//			}
//			if (daVez.getData().after(maiorData.getData())) {
//				maiorData = daVez;
//			}
//		}
//
//		return maiorData.getSituacao().name();
	}

	public TimeZone getTimeZone() {
		TimeZone timeZone = TimeZone.getDefault();
		return timeZone;
	}

	public List<AtividadePesquisa> getAtividadesPesquisa() {
		return frequenciaMensal.getAtividades();
	}

	public List<FrequenciaMensal> getFrequenciasMensais() {
		return new DAO<FrequenciaMensal>(FrequenciaMensal.class).listaTodos();
	}

	public void editarFrequenciaMensal(FrequenciaMensal frequencia) {
		this.frequenciaMensal = frequencia;
		this.planoTrabalhoId = frequencia.getPlanoTrabalho().getId();
		this.mesAnoFrequencia = frequencia.getMesAno();
	}

	public void excluirFrequenciaMensal(FrequenciaMensal frequencia) {
		new DAO<FrequenciaMensal>(FrequenciaMensal.class).remove(frequencia);
	}

	public void excluirAtividade(AtividadePesquisa atividade) {
		this.frequenciaMensal.removeAtividade(atividade);
		new DAO<AtividadePesquisa>(AtividadePesquisa.class).remove(atividade);
	}

	public void enviarFrequenciaMensal() {
		SituacaoFrequenciaMensal situacao = new SituacaoFrequenciaMensal(frequenciaMensal, Situacao.ENVIADA);
		new DAO<SituacaoFrequenciaMensal>(SituacaoFrequenciaMensal.class).adiciona(situacao);
	}

	public void salvarFrequenciaMensal() {
		frequenciaMensal.setPlanoTrabalho(new DAO<PlanoTrabalho>(PlanoTrabalho.class).buscaPorId(planoTrabalhoId));
		frequenciaMensal.setMesAno(this.mesAnoFrequencia);
		if (frequenciaMensal.getId() == null) {
//			frequenciaMensal.getHistoricoSituacao().add(new SituacaoFrequenciaMensal(frequenciaMensal));
			new DAO<FrequenciaMensal>(FrequenciaMensal.class).adiciona(this.frequenciaMensal);
		} else {
			new DAO<FrequenciaMensal>(FrequenciaMensal.class).atualiza(this.frequenciaMensal);
		}

		this.frequenciaMensal = new FrequenciaMensal();
	}

	private Date combinaDataEhora(Date data, Date hora) {

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

	public void incluirAtividadeFrequencia() {

		Date dataInicio = combinaDataEhora(this.diaAtividade, this.horaInicioAtividade);
		Date dataFinal = combinaDataEhora(this.diaAtividade, this.horaFimAtividade);

		AtividadePesquisa atividade;
		atividade = new AtividadePesquisa(this.frequenciaMensal, dataInicio, dataFinal, this.descricaoAtividade);
		this.frequenciaMensal.adicionaAtividade(atividade);

		this.descricaoAtividade = null;
		this.diaAtividade = null;
		this.horaFimAtividade = null;
		this.horaInicioAtividade = null;
	}

	/**
	 * 
	 * @return lista de meses baseado no inicio e fim do plano de trabalho serve
	 *         para preencher um combobox no discente_frequencias_mensais
	 */
	public List<SelectItem> getMesAnoPossiveis() {

		List<SelectItem> itens = new ArrayList<>();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

		if (planoTrabalhoId == null) {
			return null;
		}

		PlanoTrabalho planoTrabalho = new DAO<PlanoTrabalho>(PlanoTrabalho.class).buscaPorId(planoTrabalhoId);
		List<Date> datas = geraDatasEmUmPeriodo(planoTrabalho.getDataInicio(), planoTrabalho.getDataFim(),
				Calendar.MONTH, 1);

		for (Date daVez : datas) {
			itens.add(new SelectItem(daVez, sdf.format(daVez)));
		}

		return itens;
	}

	/**
	 * 
	 * @return lista de dias de um mês baseado no mes e ano da frequencia mensal
	 *         serve pra preencher o combo box data da atividade de pesquisa
	 * 
	 */
	public List<SelectItem> getDiasDoMes() {

		List<SelectItem> itens = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		if (this.mesAnoFrequencia == null) {
			return null;
		}

		Calendar calendario = new GregorianCalendar();
		Calendar calendarioInicio = new GregorianCalendar();
		Calendar calendarioFim = new GregorianCalendar();

		calendario.setTime(this.mesAnoFrequencia);
		calendarioInicio.setTime(this.mesAnoFrequencia);
		calendarioFim.setTime(this.mesAnoFrequencia);

		int inicio = calendario.getActualMinimum(Calendar.DATE);
		int fim = calendario.getActualMaximum(Calendar.DATE);

		calendarioInicio.set(Calendar.DATE, inicio);
		calendarioFim.set(Calendar.DATE, fim);

		List<Date> datas = geraDatasEmUmPeriodo(calendarioInicio.getTime(), calendarioFim.getTime(), Calendar.DATE, 1);

		for (Date daVez : datas) {
			itens.add(new SelectItem(daVez, sdf.format(daVez)));
		}

		return itens;
	}

	public List<Date> geraDatasEmUmPeriodo(Date dataInicial, Date dataFinal, Integer oQueIncrementar,
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

	public List<PlanoTrabalho> getPlanosTrabalho() {
		return new DAO<PlanoTrabalho>(PlanoTrabalho.class).listaTodos();
	}

	public FrequenciaMensal getFrequenciaMensal() {
		return frequenciaMensal;
	}

	public void setFrequenciaMensal(FrequenciaMensal frequenciaMensal) {
		this.frequenciaMensal = frequenciaMensal;
	}

	public Integer getPlanoTrabalhoId() {
		return planoTrabalhoId;
	}

	public void setPlanoTrabalhoId(Integer planoTrabalhoId) {
		this.planoTrabalhoId = planoTrabalhoId;
	}

	public Date getHoraInicioAtividade() {
		return horaInicioAtividade;
	}

	public void setHoraInicioAtividade(Date horaInicioAtividade) {
		this.horaInicioAtividade = horaInicioAtividade;
	}

	public Date getHoraFimAtividade() {
		return horaFimAtividade;
	}

	public void setHoraFimAtividade(Date horaFimAtividade) {
		this.horaFimAtividade = horaFimAtividade;
	}

	public String getDescricaoAtividade() {
		return descricaoAtividade;
	}

	public void setDescricaoAtividade(String descricaoAtividade) {
		this.descricaoAtividade = descricaoAtividade;
	}

	public Date getDiaAtividade() {
		return diaAtividade;
	}

	public void setDiaAtividade(Date diaAtividade) {
		this.diaAtividade = diaAtividade;
	}

	public Date getMesAnoFrequencia() {
		return mesAnoFrequencia;
	}

	public void setMesAnoFrequencia(Date mesAnoFrequencia) {
		this.mesAnoFrequencia = mesAnoFrequencia;
	}

	/**
	 * 
	 * @return habilitar botão de envio de frequencia
	 */
//	public boolean cargaHorariaEhValida() {
//
//		long horasFaltando = horasFaltando();
//
//		if (horasFaltando < 0) {
//			addMensagem(MensagensArquitetura.CONTEUDO_INVALIDO,
//					"A carga horária realizada não pode ser maior do que a carga horária exigida");
//			return false;
//		}
//
//		if (horasFaltando == 0) {
//			addMensagem(MensagensArquitetura.CADASTRADO_COM_SUCESSO,
//					"Parabéns!! Você já pode enviar sua frequência ");
//			this.setEnvioFrequencia(false);
//		}
//
//		if (horasFaltando > 0) {
//			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO,
//					"Falta " + conversorMsHoraPorExtenso(horasFaltando)
//							+ " trabalhadas para poder enviar a frequencia");
//			this.setEnvioFrequencia(true);
//		}
//		return true;
//
//	}

	/**
	 * @param calendarioInicio
	 * @param calendarioFim
	 */
//	private boolean atividadeFrequenciaEhValida(Calendar calendarioInicio,
//			Calendar calendarioFim) {
//		if (obj.getDescricao().isEmpty()) {
//			addMensagem(MensagensArquitetura.CONTEUDO_INVALIDO,
//					"Descrição não pode ser vazia");
//			return false;
//		}
//		boolean mesAnoEhValido = ((calendarioInicio.get(Calendar.MONTH) == this.mes - 1 && calendarioFim
//				.get(Calendar.MONTH) == this.mes - 1) && (calendarioInicio
//				.get(Calendar.YEAR) == this.ano && calendarioFim
//				.get(Calendar.YEAR) == this.ano));
//		if (!mesAnoEhValido) {
//			addMensagem(MensagensArquitetura.CONTEUDO_INVALIDO,
//					"O mês deve ser: " + this.mes + " e o ano " + this.ano);
//			return false;
//		}
//
//		if (calendarioFim.before(calendarioInicio)) {
//			addMensagem(MensagensArquitetura.CONTEUDO_INVALIDO,
//					"Hora Final deve ser maior que a hora inicial");
//			return false;
//		}
//
//		if (dataHoraJaCadastrada(calendarioInicio.getTime(),
//				calendarioFim.getTime(), pegarFrequenciasNoBanco())) {
//			addMensagem(MensagensArquitetura.OBJETO_JA_CADASTRADO,
//					"Data e Hora");
//			return false;
//		}
//
//		long horasFaltandoIncluidoAdigitadaAgora = horasFaltandoIncluidoAdigitadaAgora();
//
//		if (horasFaltandoIncluidoAdigitadaAgora < 0) {
//			addMensagem(MensagensArquitetura.CONTEUDO_INVALIDO,
//					"A carga horária realizada não pode ser maior do que a carga horária exigida");
//			return false;
//		}
//
//		if (horasFaltandoIncluidoAdigitadaAgora == 0) {
//			addMensagem(MensagensArquitetura.CADASTRADO_COM_SUCESSO,
//					"Parabéns!! Você já pode enviar sua frequência ");
//			this.setEnvioFrequencia(false);
//		}
//
//		if (horasFaltandoIncluidoAdigitadaAgora > 0) {
//			addMensagem(
//					MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO,
//					"Falta "
//							+ conversorMsHoraPorExtenso(horasFaltandoIncluidoAdigitadaAgora)
//							+ " trabalhadas para poder enviar a frequencia");
//			this.setEnvioFrequencia(true);
//		}
//		return true;
//	}

	/**
	 * @author giovanildo
	 * @param dataInicialDigitada
	 * @param dataFinalDigitada
	 * @param lista
	 * @return verifica se a data hora inicial ou hora final já foram digitados
	 *         antes
	 */
//	private boolean dataHoraJaCadastrada(Date dataInicialDigitada,
//			Date dataFinalDigitada,
//			Collection<AtividadeFrequenciaPesquisa> lista) {
//		for (AtividadeFrequenciaPesquisa daVez : lista) {
//			if (comparaIntervalosDeDataHora(dataInicialDigitada,
//					dataFinalDigitada, daVez.getDataInicio(),
//					daVez.getDataTerminio())) {
//				return true;
//			}
//		}
//		return false;
//	}

	/**
	 * @author giovanildo
	 * @param dataHoraDigitadaInicial
	 * @param dataFinalDigitada
	 * @param dataIniciaJaExistente
	 * @param dataFinalJaExistente
	 * @return se as datas horas estas repetidas
	 */
//	private boolean comparaIntervalosDeDataHora(Date dataHoraDigitadaInicial,
//			Date dataDigitadaFinal, Date dataInicialJaExistente,
//			Date dataFinalJaExistente) {
//		try {
//			Interval intervalDigitado = new Interval(new DateTime(
//					dataHoraDigitadaInicial), new DateTime(dataDigitadaFinal));
//			Interval intervaloNoBancoDeDados = new Interval(new DateTime(
//					dataInicialJaExistente), new DateTime(dataFinalJaExistente));
//
//			ReadableInterval readableInterval = intervaloNoBancoDeDados;
//
//			if (intervalDigitado.overlaps(readableInterval)) {
//				return true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

}