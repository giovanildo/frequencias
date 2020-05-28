package com.giovanildo.main;

import com.giovanildo.dao.DAO;
import com.giovanildo.models.AtividadePesquisa;
import com.giovanildo.models.FrequenciaMensal;
import com.giovanildo.models.PlanoTrabalho;
import com.giovanildo.models.SituacaoFrequenciaMensal;

public class Main {
	public static void main(String[] args) {
		for (PlanoTrabalho daVez : new DAO<PlanoTrabalho>(PlanoTrabalho.class).listaTodos()) {
			System.out.println(daVez.getDescricao());
		}
		for (FrequenciaMensal daVez : new DAO<FrequenciaMensal>(FrequenciaMensal.class).listaTodos()) {
			System.out.println(daVez.getDescricao());
		}
		for (AtividadePesquisa daVez : new DAO<AtividadePesquisa>(AtividadePesquisa.class).listaTodos()) {
			System.out.println(daVez.getDescricao());
		}
		for (SituacaoFrequenciaMensal daVez : new DAO<SituacaoFrequenciaMensal>(SituacaoFrequenciaMensal.class).listaTodos()) {
			System.out.println(daVez.getSituacao());
		}
		
	}


}