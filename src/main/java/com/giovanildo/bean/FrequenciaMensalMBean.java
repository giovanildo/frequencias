package com.giovanildo.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.giovanildo.models.FrequenciaMensal;

@Named
@ViewScoped
public class FrequenciaMensalMBean implements Serializable {
	
	/**
	 * criar objetos que ir�o ser manipulados
	 * criar atributos que auxiliam na manipula��o dos objetos
	 * criar m�todos que lhe retornam listas
	 * criar m�todos crud
	 * criar p�gina apontando para m�todos desse mbean
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private FrequenciaMensal frequencia;
	
	public FrequenciaMensalMBean() {
		super();
		this.frequencia = new FrequenciaMensal();
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

}
