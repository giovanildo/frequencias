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
	@Id
	@GeneratedValue
	@Column(name = "id_atividade_pesquisa", unique = true, nullable = false, insertable = true, updatable = true)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = FrequenciaMensal.class)
	@JoinColumn(name = "id_frequencia_mensal", unique = false, nullable = true, insertable = true, updatable = true)
	private FrequenciaMensal frequenciaMensal;

	@Column(name = "data_inicio", columnDefinition = "timestamp with time zone")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;

	@Column(name = "data_termino", columnDefinition = "timestamp with time zone")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataTermino;

	@Column(name = "descricao")
	private String descricao;

	public Long cargaHoraria() {
		return dataTermino.getTime() - dataInicio.getTime();
	}

	public String getCargaHorariaFormatada() {
		return FrequenciaMensal.cargaHorariaFormatada(cargaHoraria());
	}

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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

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
		if (id != other.id)
			return false;
		return true;
	}

}
