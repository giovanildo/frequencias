package com.giovanildo.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AtividadePesquisaMBean implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * página com as frequências que a serem preenchidas e enviadas
	 */
	public static final String FREQUENCIAS_MENSAIS_PARA_DISCENTE_ENVIAR = "/pesquisa/homologacao_bolsas_sipac/discente_frequencias_mensais.jsf";

}
