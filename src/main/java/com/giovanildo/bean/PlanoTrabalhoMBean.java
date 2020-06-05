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
	 * criar objetos que irão ser manipulados criar atributos que auxiliam na
	 * manipulação dos objetos criar métodos que lhe retornam listas criar métodos
	 * crud criar página apontando para métodos desse mbean
	 * 
	 */
	private PlanoTrabalho planoTrabalho;

	public void excluirPlano(PlanoTrabalho plano) {
		new DAO<PlanoTrabalho>(PlanoTrabalho.class).remove(plano);
		System.out.println("removendo: " + plano.getDescricao());
	}
	public void editarPlano(PlanoTrabalho plano) {
		this.planoTrabalho = plano;
		System.out.println("carregar: " + plano.getDescricao());
	}

	public void salvar() {
		if(this.planoTrabalho.getId() == null) {
			new DAO<PlanoTrabalho>(PlanoTrabalho.class).adiciona(planoTrabalho);
		} else {
			new DAO<PlanoTrabalho>(PlanoTrabalho.class).atualiza(planoTrabalho);
		}
		
		this.setPlanoTrabalho(new PlanoTrabalho());
	}

	public List<PlanoTrabalho> getPlanosTrabalho() {
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
