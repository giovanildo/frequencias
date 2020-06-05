package com.giovanildo.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import org.hibernate.annotations.Fetch;

@Entity
@Table(name = "frequencia_mensal", schema = "pesquisa", uniqueConstraints = {})
public class FrequenciaMensal implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String descricao;
	private PlanoTrabalho planoTrabalho;
	private Date mesAno;
	private List<SituacaoFrequenciaMensal> historicoSituacao;
	private List<AtividadePesquisa> atividades;

	@Id
	@GeneratedValue
	@Column(name = "id_frequencia_mensal", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "mes_ano", columnDefinition = "timestamp with time zone")
	@Temporal(TemporalType.DATE)
	public Date getMesAno() {
		return mesAno;
	}

	public void setMesAno(Date mesAno) {
		this.mesAno = mesAno;
	}

	public FrequenciaMensal() {
		super();
		this.atividades = new ArrayList<AtividadePesquisa>();
		this.historicoSituacao = new ArrayList<SituacaoFrequenciaMensal>();
	}

	@Column(name = "descricao")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "frequenciaMensal")
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)//https://www.guj.com.br/t/hibernate-cannot-simultaneously-fetch-multiple-bags/290551/17
	public List<SituacaoFrequenciaMensal> getHistoricoSituacao() {
		return historicoSituacao;
	}

	public void setHistoricoSituacao(List<SituacaoFrequenciaMensal> historicoSituacao) {
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

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "frequenciaMensal")
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)//https://www.guj.com.br/t/hibernate-cannot-simultaneously-fetch-multiple-bags/290551/17
	public List<AtividadePesquisa> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<AtividadePesquisa> atividades) {
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
