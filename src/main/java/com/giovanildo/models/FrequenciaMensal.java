package com.giovanildo.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "frequencia_mensal", schema = "pesquisa", uniqueConstraints = {})
public class FrequenciaMensal implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String descricao;
	private PlanoTrabalho planoTrabalho;
	private Date mesAno;
	private Collection<SituacaoFrequenciaMensal> historicoSituacao;
	private Collection<AtividadePesquisa> atividades;

	@Id
	@GeneratedValue
	@Column(name = "id_frequencia_mensal")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "mes_ano")
	@Temporal(TemporalType.DATE)
	public Date getMesAno() {
		return mesAno;
	}

	public void setMesAno(Date mesAno) {
		this.mesAno = mesAno;
	}

	public FrequenciaMensal() {
		super();
	}

	@Column(name = "descricao")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "frequenciaMensal")
	public Collection<SituacaoFrequenciaMensal> getHistoricoSituacao() {
		return historicoSituacao;
	}

	public void setHistoricoSituacao(Collection<SituacaoFrequenciaMensal> historicoSituacao) {
		this.historicoSituacao = historicoSituacao;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_plano_trabalho", unique = false, nullable = true, insertable = true, updatable = true)
	public PlanoTrabalho getPlanoTrabalho() {
		return planoTrabalho;
	}

	public void setPlanoTrabalho(PlanoTrabalho planoTrabalho) {
		this.planoTrabalho = planoTrabalho;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "frequenciaMensal")
	public Collection<AtividadePesquisa> getAtividades() {
		return atividades;
	}

	public void setAtividades(Collection<AtividadePesquisa> atividades) {
		this.atividades = atividades;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FrequenciaMensal other = (FrequenciaMensal) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
