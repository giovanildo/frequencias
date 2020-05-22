package com.giovanildo.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.giovanildo.dao.DAO;
import com.giovanildo.models.FrequenciaMensal;
import com.giovanildo.models.PlanoTrabalho;

@Named
@ViewScoped
public class FrequenciaMensalMBean implements Serializable {

	/**
	 * criar objetos que ir�o ser manipulados criar atributos que auxiliam na
	 * manipula��o dos objetos criar m�todos que lhe retornam listas criar m�todos
	 * crud criar p�gina apontando para m�todos desse mbean
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private FrequenciaMensal frequencia;

	private Integer planoTrabalhoId;

	public FrequenciaMensalMBean() {
		super();
		this.frequencia = new FrequenciaMensal();
	}

	public List<PlanoTrabalho> getPlanosTrabalho() {
		return new DAO<PlanoTrabalho>(PlanoTrabalho.class).listaTodos();
	}

	public void salvar() {
	}

	public void validar() {
	}

	public FrequenciaMensal getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(FrequenciaMensal frequencia) {
		this.frequencia = frequencia;
	}

	public Integer getPlanoTrabalhoId() {
		return planoTrabalhoId;
	}

	public void setPlanoTrabalhoId(Integer planoTrabalhoId) {
		this.planoTrabalhoId = planoTrabalhoId;
	}

}
