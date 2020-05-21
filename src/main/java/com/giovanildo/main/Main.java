package com.giovanildo.main;

import com.giovanildo.dao.DAO;
import com.giovanildo.models.FrequenciaMensal;

public class Main {
	public static void main(String[] args) {
		for (FrequenciaMensal daVez : new DAO<FrequenciaMensal>(FrequenciaMensal.class).listaTodos()) {
			System.out.println(daVez.getMesAno());
		}
	}
}