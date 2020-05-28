package com.giovanildo.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "atividade_pesquisa", schema = "pesquisa", uniqueConstraints = {})
public class AtividadePesquisa {
	private int id;
	private FrequenciaMensal frequenciaMensal;
	private Date dataInicio;
	private Date dataTermino;
	private String descricao;

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public AtividadePesquisa() {
		super();
	}

	public AtividadePesquisa(FrequenciaMensal frequenciaMensal, Date dataInicio, Date dataTermino, String descricao) {
		super();
		this.frequenciaMensal = frequenciaMensal;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
		this.descricao = descricao;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "data_inicio", columnDefinition = "timestamp with time zone")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@Column(name = "data_termino", columnDefinition = "timestamp with time zone")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	@Column(name = "descricao")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_frequencia_mensal", unique = false, nullable = true, insertable = true, updatable = true)
	public FrequenciaMensal getFrequenciaMensal() {
		return frequenciaMensal;
	}

	public void setFrequenciaMensal(FrequenciaMensal frequenciaMensal) {
		this.frequenciaMensal = frequenciaMensal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((dataTermino == null) ? 0 : dataTermino.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((frequenciaMensal == null) ? 0 : frequenciaMensal.hashCode());
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
		AtividadePesquisa other = (AtividadePesquisa) obj;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (dataTermino == null) {
			if (other.dataTermino != null)
				return false;
		} else if (!dataTermino.equals(other.dataTermino))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (frequenciaMensal == null) {
			if (other.frequenciaMensal != null)
				return false;
		} else if (!frequenciaMensal.equals(other.frequenciaMensal))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
