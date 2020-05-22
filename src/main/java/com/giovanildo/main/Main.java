package com.giovanildo.main;

import com.giovanildo.dao.DAO;
import com.giovanildo.models.PlanoTrabalho;

public class Main {
	public static void main(String[] args) {
		PlanoTrabalho planoTrabalho = new PlanoTrabalho();
		planoTrabalho.setDescricao("plano teste");
		new DAO<PlanoTrabalho>(PlanoTrabalho.class).adiciona(planoTrabalho);
	}
}