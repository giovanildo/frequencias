package com.giovanildo.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "situacao_frequencia_mensal", schema = "pesquisa", uniqueConstraints = {})
public class SituacaoFrequenciaMensal {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((frequenciaMensal == null) ? 0 : frequenciaMensal.hashCode());
		result = prime * result + id;
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
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
		SituacaoFrequenciaMensal other = (SituacaoFrequenciaMensal) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (frequenciaMensal == null) {
			if (other.frequenciaMensal != null)
				return false;
		} else if (!frequenciaMensal.equals(other.frequenciaMensal))
			return false;
		if (id != other.id)
			return false;
		if (situacao != other.situacao)
			return false;
		return true;
	}

	private int id;
	private FrequenciaMensal frequenciaMensal;
	private Situacao situacao;
	private Date data;

	@Id
	@GeneratedValue
	@Column(name = "id_situacao_frequencia_mensal")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Enumerated(EnumType.ORDINAL)
	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	@Column(name = "data")
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_frequencia_mensal", unique = false, nullable = true, insertable = true, updatable = true)
	public FrequenciaMensal getFrequenciaMensal() {
		return frequenciaMensal;
	}

	public void setFrequenciaMensal(FrequenciaMensal frequenciaMensal) {
		this.frequenciaMensal = frequenciaMensal;
	}
}
