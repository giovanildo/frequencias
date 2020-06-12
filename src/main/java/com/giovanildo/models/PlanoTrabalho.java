package com.giovanildo.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "plano_trabalho", schema = "pesquisa", uniqueConstraints = {})
public class PlanoTrabalho {

	@Id
	@GeneratedValue
	@Column(name = "id_plano_trabalho")
	private Integer id;

	@Column(name = "descricao")
	private String descricao;

	@OneToMany(mappedBy = "planoTrabalho") // Configura��o n�o existe no sigaa
	private Collection<FrequenciaMensal> frequenciasMensais;

	@Column(name = "data_inicio", columnDefinition = "timestamp with time zone")
	@Temporal(TemporalType.DATE)
	private Date dataInicio;

	@Column(name = "data_fim", columnDefinition = "timestamp with time zone")
	@Temporal(TemporalType.DATE)
	private Date dataFim;

	public Integer getId() {
		return id;
	}

	public PlanoTrabalho(Integer id, String descricao, Collection<FrequenciaMensal> frequenciasMensais) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.frequenciasMensais = frequenciasMensais;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Collection<FrequenciaMensal> getFrequenciasMensais() {
		return frequenciasMensais;
	}

	public PlanoTrabalho() {
		super();
		frequenciasMensais = new ArrayList<FrequenciaMensal>();
	}

	public void setFrequenciasMensais(Collection<FrequenciaMensal> frequenciasMensais) {
		this.frequenciasMensais = frequenciasMensais;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((frequenciasMensais == null) ? 0 : frequenciasMensais.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PlanoTrabalho other = (PlanoTrabalho) obj;
		if (dataFim == null) {
			if (other.dataFim != null)
				return false;
		} else if (!dataFim.equals(other.dataFim))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (frequenciasMensais == null) {
			if (other.frequenciasMensais != null)
				return false;
		} else if (!frequenciasMensais.equals(other.frequenciasMensais))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
