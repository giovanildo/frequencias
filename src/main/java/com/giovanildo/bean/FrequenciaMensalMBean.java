package com.giovanildo.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.ReadableInterval;

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
	 * Frequencia Mensal
	 */
	private FrequenciaMensal frequenciaMensal;
	private Integer planoTrabalhoId;
	private Date mesAnoFrequencia;

	// atributos a serem persistidos como atividade pesquisa
	private Date diaAtividade;
	private Date horaInicioAtividade;
	private Date horaFimAtividade;
	private String descricaoAtividade;

	private Boolean mostrarAtividades;

	public FrequenciaMensalMBean() {
		super();
		this.frequenciaMensal = new FrequenciaMensal();
	}

	public TimeZone getTimeZone() {
		TimeZone timeZone = TimeZone.getDefault();
		return timeZone;
	}

	public List<AtividadePesquisa> getAtividadesPesquisa() {
		return frequenciaMensal.getAtividades();
	}

	public List<FrequenciaMensal> getFrequenciasMensaisPorPlano() {
		return buscarFrequenciasMensaisPorPlanoDiretoNoBanco();
	}

	private List<FrequenciaMensal> buscarFrequenciasMensaisPorPlanoDiretoNoBanco() {
		if (this.planoTrabalhoId == null) {
			return null;
		}

		List<FrequenciaMensal> frequenciasPorPlano = new ArrayList<FrequenciaMensal>();
		for (FrequenciaMensal daVez : new DAO<FrequenciaMensal>(FrequenciaMensal.class).listaTodos()) {
			if (this.planoTrabalhoId == daVez.getPlanoTrabalho().getId()) {
				frequenciasPorPlano.add(daVez);
			}
		}

		return frequenciasPorPlano;
	}

	public List<Date> getFrequenciasAindaNaoCriadas() {
		return gerarFrequenciasAindaNaoCriadas(buscarFrequenciasMensaisPorPlanoDiretoNoBanco());
	}

	private List<Date> gerarFrequenciasAindaNaoCriadas(List<FrequenciaMensal> frequenciasJaCriadas) {

		if (planoTrabalhoId == null) {
			return null;
		}

		List<Date> datasJaCriadas = new ArrayList<Date>();

		for (FrequenciaMensal jaCriada : frequenciasJaCriadas) {
			datasJaCriadas.add(jaCriada.getMesAno());
		}

		PlanoTrabalho planoTrabalho = new DAO<PlanoTrabalho>(PlanoTrabalho.class).buscaPorId(planoTrabalhoId);
		List<Date> datasPossiveis = geraDatasEmUmPeriodo(planoTrabalho.getDataInicio(), planoTrabalho.getDataFim(),
				Calendar.MONTH, 1);

		return gerarListaDeDatasQueNaoEstaoNaOutraLista(datasJaCriadas, datasPossiveis);
	}

	/**
	 * 
	 * @param datasJaCriadas
	 * @param datasPossiveis
	 * @return uma array de datas que contém a datas que contém a intersação das
	 *         duas listas serve para gerar as frequencias ainda não geradas
	 */
	public List<Date> gerarListaDeDatasQueNaoEstaoNaOutraLista(List<Date> datasJaCriadas, List<Date> datasPossiveis) {
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

	public void adicionarFrequenciaMensal(Date mesAno) {
		this.frequenciaMensal = new FrequenciaMensal();
		this.frequenciaMensal.setPlanoTrabalho(new DAO<PlanoTrabalho>(PlanoTrabalho.class).buscaPorId(planoTrabalhoId));
		this.frequenciaMensal.adicionaSituacao(Situacao.PREENCHENDO);
		this.frequenciaMensal.setMesAno(mesAno);
		new DAO<FrequenciaMensal>(FrequenciaMensal.class).adiciona(this.frequenciaMensal);
		FacesContext.getCurrentInstance().addMessage("atividade",
				new FacesMessage("Frequência " + new SimpleDateFormat("MM/yyyy").format(frequenciaMensal.getMesAno())
						+ " criada com sucesso! adicione novas atividades ;)"));

	}

	public void editarFrequenciaMensal(FrequenciaMensal frequencia) {
		this.mostrarAtividades = true;
		this.frequenciaMensal = frequencia;
	}

	public void excluirAtividade(AtividadePesquisa atividade) {
		this.frequenciaMensal.removeAtividade(atividade);
	}

	public void enviarFrequenciaMensal(FrequenciaMensal frequencia) {
		SituacaoFrequenciaMensal situacao = new SituacaoFrequenciaMensal(frequencia, Situacao.ENVIADA);
		new DAO<SituacaoFrequenciaMensal>(SituacaoFrequenciaMensal.class).adiciona(situacao);
	}

	public void salvarFrequenciaMensal() {

		if (!frequenciaEhValida(this.frequenciaMensal)) {
			return;
		}

		if (frequenciaMensal.getId() == null) {
			new DAO<FrequenciaMensal>(FrequenciaMensal.class).adiciona(this.frequenciaMensal);
		} else {
			new DAO<FrequenciaMensal>(FrequenciaMensal.class).atualiza(this.frequenciaMensal);
		}

		FacesContext.getCurrentInstance().addMessage("atividade", new FacesMessage("Atividades da Frequência "
				+ new SimpleDateFormat("MM/yyyy").format(frequenciaMensal.getMesAno()) + " Salva com sucesso"));
		this.frequenciaMensal = new FrequenciaMensal();
	}

	private Boolean frequenciaEhValida(FrequenciaMensal frequencia) {

		long horasFaltando = frequencia.chExigidaEmMs() - frequencia.cargaHorariaTotal();

		if (horasFaltando < 0) {
			FacesContext.getCurrentInstance().addMessage("atividade",
					new FacesMessage("A carga horária realizada não pode ser maior do que a carga horária exigida"));
			return false;
		} else {
			if (horasFaltando == 0) {
				FacesContext.getCurrentInstance().addMessage("atividade",
						new FacesMessage("Parabéns! Você já pode enviar sua frequência!"));

			}
			if (horasFaltando > 0) {
				FacesContext.getCurrentInstance().addMessage("atividade",
						new FacesMessage("Falta " + FrequenciaMensal.cargaHorariaFormatada(horasFaltando)
								+ " trabalhadas para poder enviar a frequencia"));
				return true;
			}
			return true;
		}
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

		if (!atividadeEhValida(atividade)) {
			return;
		}

		this.frequenciaMensal.adicionaAtividade(atividade);

		this.descricaoAtividade = null;
		this.diaAtividade = null;
		this.horaFimAtividade = null;
		this.horaInicioAtividade = null;

	}

	private boolean atividadeEhValida(AtividadePesquisa atividade) {
		if (this.descricaoAtividade.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("atividade", new FacesMessage("descrição não pode ser vazia"));
			return false;
		}

		if (dataHoraJaCadastrada(atividade.getDataInicio(), atividade.getDataTermino(),
				this.frequenciaMensal.getAtividades())) {
			FacesContext.getCurrentInstance().addMessage("atividade", new FacesMessage("hora já cadastrada"));
			return false;
		}

		if (atividade.getDataTermino().before(atividade.getDataInicio())) {
			FacesContext.getCurrentInstance().addMessage("atividade",
					new FacesMessage("Hora Final deve ser maior que a hora inicial"));
			return false;
		}

		return true;

	}

	/**
	 * 
	 * @return lista de meses baseado no inicio e fim do plano de trabalho serve
	 *         para preencher um combobox no discente_frequencias_mensais
	 */
	public List<SelectItem> getMesAnoPossiveis() {

		List<SelectItem> itens = new ArrayList<SelectItem>();

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
	 * @return lista de dias de um m�s baseado no mes e ano da frequencia mensal
	 *         serve pra preencher o combo box data da atividade de pesquisa
	 * 
	 */
	public List<SelectItem> getDiasDoMes() {

		List<SelectItem> itens = new ArrayList<SelectItem>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		if (this.frequenciaMensal.getMesAno() == null) {
			return null;
		}

		Calendar calendario = new GregorianCalendar();
		Calendar calendarioInicio = new GregorianCalendar();
		Calendar calendarioFim = new GregorianCalendar();

		calendario.setTime(this.frequenciaMensal.getMesAno());

		int inicio = calendario.getActualMinimum(Calendar.DATE);
		int fim = calendario.getActualMaximum(Calendar.DATE);

		calendarioInicio.setTime(this.frequenciaMensal.getMesAno());
		calendarioFim.setTime(this.frequenciaMensal.getMesAno());

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

	/**
	 * @author giovanildo
	 * @param dataInicialDigitada
	 * @param dataFinalDigitada
	 * @param lista
	 * @return verifica se a data hora inicial ou hora final j� foram digitados
	 *         antes
	 */
	private boolean dataHoraJaCadastrada(Date dataInicialDigitada, Date dataFinalDigitada,
			List<AtividadePesquisa> lista) {
		for (AtividadePesquisa daVez : lista) {
			if (comparaIntervalosDeDataHora(dataInicialDigitada, dataFinalDigitada, daVez.getDataInicio(),
					daVez.getDataTermino())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @author giovanildo
	 * @param dataHoraDigitadaInicial
	 * @param dataFinalDigitada
	 * @param dataIniciaJaExistente
	 * @param dataFinalJaExistente
	 * @return se as datas horas estas repetidas
	 */
	private boolean comparaIntervalosDeDataHora(Date dataHoraDigitadaInicial, Date dataDigitadaFinal,
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

	public Situacao situacao() {
		return this.frequenciaMensal.situacao();
	}

	public Boolean envioFrequencia(FrequenciaMensal frequencia) {
		
		Situacao situacao = frequencia.situacao();
		Boolean editavel = situacao.equals(Situacao.PREENCHENDO) || situacao.equals(Situacao.RECUSADA);
		Boolean cargaHorariaOK = frequencia.chExigidaEmMs() - frequencia.cargaHorariaTotal() == 0;
		
		if(editavel && cargaHorariaOK) {
			return true;
		}
		return false;
	}

	public Boolean getEhEditavel() {
		if (this.frequenciaMensal == null) {
			return false;
		}

		if (this.frequenciaMensal.getId() == null) {
			return false;
		}

		Situacao situacao = situacao();

		return situacao.equals(Situacao.PREENCHENDO) || situacao.equals(Situacao.RECUSADA);
	}

	public List<PlanoTrabalho> getPlanosTrabalho() {
		return new DAO<PlanoTrabalho>(PlanoTrabalho.class).listaTodos();
	}

	// getters setters
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

	public Boolean getMostrarAtividades() {
		if (this.frequenciaMensal.getId() == null) {
			return false;
		}
		return mostrarAtividades;
	}

	public void setMostrarAtividades(Boolean mostrarAtividades) {
		this.mostrarAtividades = mostrarAtividades;
	}

}