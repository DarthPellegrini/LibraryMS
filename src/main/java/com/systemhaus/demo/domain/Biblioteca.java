package com.systemhaus.demo.domain;

import java.util.ArrayList;
import java.util.List;

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
	
	//retorna as prateleiras da última estante adicionada
	public List<Prateleira> getLastPrateleiras() {
		return this.getEstantes().get(this.getEstantes().size()-1).getPrateleiras();
	}
	
	//retorna os livros da última prateleira adicionadas
	public List<Livro> getLastLivros(){
		return this.getEstantes().get(this.getEstantes().size()-1).getPrateleiras().get(this.getEstantes().get(this.getEstantes().size()-1).getPrateleiras().size()-1).getLivros();
	}
	
	//TODO: métodos de retorno de todas as prateleiras e todos os livros
}
