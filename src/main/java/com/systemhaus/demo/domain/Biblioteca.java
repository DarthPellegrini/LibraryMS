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
	
	public void addDisponivel(String key) {
		int value[] = new int[2];
		if(!this.regLivros.containsKey(key)) {
			value[0] = 0;
			value[1] = 0;
		}else {
			value[0] = this.regLivros.get(key)[0]++;
			value[1] = this.regLivros.get(key)[1]++;
		}
		this.regLivros.put(key, value);
	}
	public void remDisponivel(String key) {
		int value[] = {this.regLivros.get(key)[0]--
				,this.regLivros.get(key)[1]--};
		this.regLivros.put(key, value);
	}
	public void addRetirado(String key) {
		int value[] = {this.regLivros.get(key)[0]
				,this.regLivros.get(key)[1]++};
		this.regLivros.put(key, value);
	}
	public void remRetirado(String key) {
		int value[] = {this.regLivros.get(key)[0]
				,this.regLivros.get(key)[1]--};
		this.regLivros.put(key, value);
	}
	
	//TODO: métodos de retorno de todas as prateleiras e todos os livros
}
