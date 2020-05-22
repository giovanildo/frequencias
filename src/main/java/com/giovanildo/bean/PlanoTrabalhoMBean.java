package com.giovanildo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.giovanildo.dao.DAO;
import com.giovanildo.models.PlanoTrabalho;


@Named
@ViewScoped
public class PlanoTrabalhoMBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * criar objetos que irão ser manipulados
	 * criar atributos que auxiliam na manipulação dos objetos
	 * criar métodos que lhe retornam listas
	 * criar métodos crud
	 * criar página apontando para métodos desse mbean
	 * 
	 */
	private PlanoTrabalho planoTrabalho;
	
	public void salvar() {
		new DAO<PlanoTrabalho>(PlanoTrabalho.class).adiciona(planoTrabalho);
		this.setPlanoTrabalho(new PlanoTrabalho());
	}
	
	public List<PlanoTrabalho> getPlanosTrabalho(){
		List<PlanoTrabalho> planos = new ArrayList<PlanoTrabalho>();
		planos = new DAO<PlanoTrabalho>(PlanoTrabalho.class).listaTodos();
		return planos;
	}
	
	public PlanoTrabalhoMBean() {
		super();
		setPlanoTrabalho(new PlanoTrabalho());
	}
	
	public PlanoTrabalho getPlanoTrabalho() {
		return planoTrabalho;
	}

	public void setPlanoTrabalho(PlanoTrabalho planoTrabalho) {
		this.planoTrabalho = planoTrabalho;
	}
	

}
