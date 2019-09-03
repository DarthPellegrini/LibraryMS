package com.systemhaus.demo.domain;

import java.util.ArrayList;

public class Biblioteca {
	
	//a biblioteca terá um número n de estantes, dependendo somente da quantidade de livros
	private ArrayList<Estante> estantes;

	public Biblioteca() {
		estantes = new ArrayList<Estante>();
	}
	
	public ArrayList<Estante> getEstantes() {
		return estantes;
	}

	public void setEstantes(ArrayList<Estante> estantes) {
		this.estantes = estantes;
	}
	
	
}
