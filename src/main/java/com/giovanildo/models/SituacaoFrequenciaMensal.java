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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "situacao_frequencia_mensal", schema = "pesquisa", uniqueConstraints = {})
public class SituacaoFrequenciaMensal {
	@Id
	@GeneratedValue
	@Column(name = "id_situacao_frequencia_mensal", unique = true, nullable = false, insertable = true, updatable = true)
	private int id;
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_frequencia_mensal", unique = false, nullable = true, insertable = true, updatable = true)
	private FrequenciaMensal frequenciaMensal;
	
	@Enumerated(EnumType.ORDINAL)
	private Situacao situacao;

	@Column(name = "data", columnDefinition = "timestamp with time zone")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	public SituacaoFrequenciaMensal() {
		super();
	}

	public SituacaoFrequenciaMensal(FrequenciaMensal frequenciaMensal) {
		super();
		this.frequenciaMensal = frequenciaMensal;
		this.situacao = Situacao.PREENCHENDO;
		this.data = new Date();
	}

	public SituacaoFrequenciaMensal(FrequenciaMensal frequenciaMensal, Situacao situacao) {
		super();
		this.frequenciaMensal = frequenciaMensal;
		this.situacao = situacao;
		this.data = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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
		SituacaoFrequenciaMensal other = (SituacaoFrequenciaMensal) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
