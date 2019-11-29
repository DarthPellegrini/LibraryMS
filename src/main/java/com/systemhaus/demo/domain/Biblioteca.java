package com.systemhaus.demo.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Biblioteca {
	
	//a biblioteca terá um número n de estantes, dependendo somente da quantidade de livros
	private int id;
	private List<Estante> estantes;
	private List<Cliente> clientes;
	private Map<String,int[]> regLivros;
	private List<LivroRetirado> livrosRetirados;
	public Biblioteca() {
		setEstantes(new ArrayList<Estante>());
		setClientes(new ArrayList<Cliente>());
		regLivros = new HashMap<String,int[]>();
		setLivrosRetirados(new ArrayList<LivroRetirado>());
		this.addEstante();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Estante> getEstantes() {
		return estantes;
	}

	public void setEstantes(ArrayList<Estante> estantes) {
		this.estantes = estantes;
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<LivroRetirado> getLivrosRetirados() {
		return livrosRetirados;
	}

	public void setLivrosRetirados(List<LivroRetirado> livrosRetirados) {
		this.livrosRetirados = livrosRetirados;
	}
	
	public void addEstante() {
		estantes.add(new Estante());
	}
	
	/*
	 * retorna a última estante adicionada
	 */
	public Estante getLastEstante() { 
		return this.getEstantes().get(this.getEstantes().size()-1);
	}
	
	/*
	 * retorna a última prateleira da última estante adicionada
	 */
	public Prateleira getLastPrateleira() {
		return this.getLastEstante().getPrateleiras().get(getLastEstante().getPrateleiras().size()-1);
	}
	
	/*
	 * retorna os livros da última prateleira adicionadas
	 */
	public List<Livro> getLastLivros(){
		return this.getLastPrateleira().getLivros();
	}
	
	/*
	 * retorna o hashmap de todos os livros registrados
	 */
	public Map<String,int[]> getRegistroDeLivros(){
		return this.regLivros;
	}
	
	/*
	 * Verifica se todos os livros de um mesmo ISBN estão disponíveis para retirada
	 */
	public boolean allTheBooksAreAvailable(String key) {
		return this.regLivros.get(key)[0] == this.regLivros.get(key)[1];
	}
	
	/**
	 * Caso todos os livros estejam disponíveis, não há problema em deletar alguns exemplares,
	 * senão, é necessário conferir se o número atual de livros depois da remoção 
	 * não será inferior ao de livros disponíveis
	 */
	public boolean havingOnlyThisAmountOfCopiesWontCauseProblems(String key, int quant) {
		return this.allTheBooksAreAvailable(key) ? true : quant >= (this.regLivros.get(key)[0]-this.regLivros.get(key)[1]);
	}

	/*
	 * Adiciona um livro no catálogo na biblioteca
	 */
	public void addDisponivel(String key) {
		int value[] = new int[2];
		if(!this.regLivros.containsKey(key)) {
			value[0] = 1;
			value[1] = 1;
		}else {
			value[0] = ++this.regLivros.get(key)[0];
			value[1] = ++this.regLivros.get(key)[1];
		}
		this.regLivros.put(key, value);
	}
	/*
	 * Remove um livro do catálogo da biblioteca
	 */
	public void remDisponivel(String key, int quant) {
		int value[] = {this.regLivros.get(key)[0]-=quant,
				this.regLivros.get(key)[1]-=quant};
		this.regLivros.put(key, value);
	}
	/*
	 * Remove um livro que estava retirado e foi devolvido do catálogo
	 */
	public boolean remRetirado(String key) {
		if(this.regLivros.get(key)[1] + 1 > this.regLivros.get(key)[0])
			return false;
		int value[] = {this.regLivros.get(key)[0]
				,++this.regLivros.get(key)[1]};
		this.regLivros.put(key, value);
		return true;
	}
	
	/*
	 * Adiciona um exemplar que estava disponível e foi retirado
	 */
	public boolean addRetirado(String key) {
		if(this.regLivros.get(key)[1] - 1 < 0)
			return false;
		int value[] = {this.regLivros.get(key)[0]
				,--this.regLivros.get(key)[1]};
		this.regLivros.put(key, value);
		return true;
	}
	
	/**
	 * Retorna a primeira prateleira com espaço vazio, se houver, senão, retorna nulo
	 * @return
	 */
	public Prateleira getPrateleiraWithEmptySpace() {
		for (Estante e : estantes)
			for (Prateleira p : e.getPrateleiras())
				if(!p.isFull())
					return p;
		return null;
	}
	
	public void addCliente(Cliente cliente) {
		this.clientes.add(cliente.copy());
	}

	public void addLivroRetirado(LivroRetirado livroRetirado) {
		this.livrosRetirados.add(livroRetirado);
	}
	
}
