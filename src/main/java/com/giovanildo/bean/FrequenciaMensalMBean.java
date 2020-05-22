package com.giovanildo.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.giovanildo.models.FrequenciaMensal;

@Named
@ViewScoped
public class FrequenciaMensalMBean implements Serializable {
	
	/**
	 * criar objetos que irão ser manipulados
	 * criar atributos que auxiliam na manipulação dos objetos
	 * criar métodos que lhe retornam listas
	 * criar métodos crud
	 * criar página apontando para métodos desse mbean
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
