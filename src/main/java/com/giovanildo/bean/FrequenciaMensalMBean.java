package com.giovanildo.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.giovanildo.dao.DAO;
import com.giovanildo.models.FrequenciaMensal;
import com.giovanildo.models.PlanoTrabalho;
import com.giovanildo.models.SituacaoFrequenciaMensal;

@Named
@ViewScoped
public class FrequenciaMensalMBean implements Serializable {

	/**
	 * criar objetos que irão ser manipulados criar atributos que auxiliam na
	 * manipulação dos objetos criar métodos que lhe retornam listas criar métodos
	 * crud criar página apontando para métodos desse mbean
	 * 
	 */

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

	public List<FrequenciaMensal> getFrequenciasMensais() {
		return new DAO<FrequenciaMensal>(FrequenciaMensal.class).listaTodos();
	}

	public void salvarFrequenciaMensal() {
		frequenciaMensal.setPlanoTrabalho(new DAO<PlanoTrabalho>(PlanoTrabalho.class).buscaPorId(planoTrabalhoId));
		frequenciaMensal.setMesAno(this.mesAnoFrequencia);
		new DAO<FrequenciaMensal>(FrequenciaMensal.class).adiciona(this.frequenciaMensal);
		this.frequenciaMensal = new FrequenciaMensal();
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

	public FrequenciaMensalMBean() {
		super();
		this.frequenciaMensal = new FrequenciaMensal();
	}

	public SituacaoFrequenciaMensal getSituacaoAtual() {
		SituacaoFrequenciaMensal maiorData = null;
		for (SituacaoFrequenciaMensal daVez : this.frequenciaMensal.getHistoricoSituacao()) {
			if (daVez.getData().after(maiorData.getData())) {
				maiorData = daVez;
			}
		}
		return maiorData;
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

}