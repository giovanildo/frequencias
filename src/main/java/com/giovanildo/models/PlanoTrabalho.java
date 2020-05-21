package com.giovanildo.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//@Entity
//@Table(name = "plano_trabalho", schema = "pesquisa", uniqueConstraints = {})
public class PlanoTrabalho {

	private Integer id;
	private String descricao;
	private Collection<FrequenciaMensal> frequenciasMensais;

	@Id
	@GeneratedValue
	@Column(name = "id_plano_trabalho")
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

	@OneToMany(mappedBy = "planoTrabalho") //Configuração não existe no sigaa
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
}
