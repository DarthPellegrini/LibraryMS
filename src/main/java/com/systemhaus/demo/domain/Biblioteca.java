package com.systemhaus.demo.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Biblioteca {
	
	//a biblioteca terá um número n de estantes, dependendo somente da quantidade de livros
	private List<Estante> estantes;
	private Map<String,int[]> regLivros;

	public Biblioteca() {
		estantes = new ArrayList<Estante>();
		regLivros = new HashMap<String,int[]>();
	}
	
	public List<Estante> getEstantes() {
		return estantes;
	}

	public void setEstantes(ArrayList<Estante> estantes) {
		this.estantes = estantes;
	}
	
	public void addEstante() {
		estantes.add(new Estante());
	}
	
	//retorna a última estante adicionada
	public Estante getLastEstante() { 
		return this.getEstantes().get(this.getEstantes().size()-1);
	}
	
	//retorna a última prateleira da última estante adicionada
	public Prateleira getLastPrateleira() {
		return this.getLastEstante().getPrateleiras().get(getLastEstante().getPrateleiras().size()-1);
	}
	
	//retorna os livros da última prateleira adicionadas
	public List<Livro> getLastLivros(){
		return this.getLastPrateleira().getLivros();
	}
	
	//retorna o hashmap de todos os livros registrados
	public Map<String,int[]> getRegistroDeLivros(){
		return this.regLivros;
	}
	
	public boolean allTheBooksAreAvailable(String key) {
		return this.regLivros.get(key)[0] == this.regLivros.get(key)[1];
	}
	
	/**
	 * Caso todos os livros estejam disponíveis, não há problema em deletar alguns exemplares,
	 * senão, é necessário conferir que o número atual de livros depois da remoção 
	 * não será inferior ao de livros disponíveis
	 */
	public boolean havingOnlyThisAmountOfCopiesWontCauseProblems(String key, int quant) {
		return this.regLivros.get(key)[0] == this.regLivros.get(key)[1] ? true :
				quant >= this.regLivros.get(key)[1] ;
	}
	
	public void addDisponivel(String key, int quant) {
		int value[] = new int[2];
		if(!this.regLivros.containsKey(key)) {
			value[0] = quant;
			value[1] = quant;
		}else {
			value[0] = this.regLivros.get(key)[0]++;
			value[1] = this.regLivros.get(key)[1]++;
		}
		this.regLivros.put(key, value);
	}
	public void remDisponivel(String key, int quant) {
		int value[] = {this.regLivros.get(key)[0]-=quant,
				this.regLivros.get(key)[1]-=quant};
		this.regLivros.put(key, value);
	}
	public boolean addRetirado(String key) {
		if(this.regLivros.get(key)[1] + 1 > this.regLivros.get(key)[0])
			return false;
		int value[] = {this.regLivros.get(key)[0]
				,this.regLivros.get(key)[1]++};
		this.regLivros.put(key, value);
		return true;
	}
	public boolean remRetirado(String key) {
		if(this.regLivros.get(key)[1] - 1 < 0)
			return false;
		int value[] = {this.regLivros.get(key)[0]
				,this.regLivros.get(key)[1]--};
		this.regLivros.put(key, value);
		return true;
	}
	
	//TODO: métodos de retorno de todas as prateleiras e todos os livros
}
