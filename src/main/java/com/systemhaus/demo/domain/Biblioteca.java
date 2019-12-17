package com.systemhaus.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
	
	//a biblioteca terá um número n de estantes, dependendo somente da quantidade de livros
	private int id;
	private List<Estante> estantes;
	private List<Cliente> clientes;
	private List<RegLivros> regLivros;
	private List<LivroRetirado> livrosRetirados;
	
	public Biblioteca() {
		setEstantes(new ArrayList<Estante>());
		setClientes(new ArrayList<Cliente>());
		setRegLivros(new ArrayList<RegLivros>());
		setLivrosRetirados(new ArrayList<LivroRetirado>());
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

	public void setEstantes(List<Estante> estantes) {
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
	 * retorna os livros da última prateleira adicionada
	 */
	public List<Livro> getLastLivros(){
		return this.getLastPrateleira().getLivros();
	}
	
	/*
	 * retorna a lista de todos os livros registrados
	 */
	public List<RegLivros> getRegLivros(){
		return this.regLivros;
	}
	
	public void setRegLivros(List<RegLivros> regLivros) {
		this.regLivros = regLivros;
	}

	/*
	 * Adiciona um livro no catálogo na biblioteca
	 */
	public void addDisponivel(String key) {
		RegLivros reg = findRegLivrosForThis(key);
		if(reg == null) {
			reg = new RegLivros(key,1,this);
			regLivros.add(reg);
		}else {
			reg.setQuantLivrosNoCatalogo(reg.getQuantLivrosNoCatalogo()+1);
			reg.setQuantLivrosParaRetirar(reg.getQuantLivrosParaRetirar()+1);
		}
	}
	/*
	 * Remove livros do catálogo da biblioteca
	 */
	public void remDisponivel(String key, int quant) {
		RegLivros reg = findRegLivrosForThis(key);
		if (reg != null) {
			reg.setQuantLivrosNoCatalogo(reg.getQuantLivrosNoCatalogo()-quant);
			reg.setQuantLivrosParaRetirar(reg.getQuantLivrosParaRetirar()-quant);
		}
	}
	/*
	 * Remove um livro que estava retirado e foi devolvido do catálogo
	 */
	public boolean remRetirado(String key) {
		RegLivros reg = findRegLivrosForThis(key);
		if (reg != null && reg.getQuantLivrosParaRetirar() + 1 <= reg.getQuantLivrosNoCatalogo()) {
			reg.setQuantLivrosParaRetirar(reg.getQuantLivrosParaRetirar()+1);
			return true;
		} else
			return false;
	}
	
	/*
	 * Adiciona um exemplar que estava disponível e foi retirado
	 */
	public boolean addRetirado(String key) {
		RegLivros reg = findRegLivrosForThis(key);
		if (reg != null && reg.getQuantLivrosParaRetirar() - 1 >= 0) {
			reg.setQuantLivrosParaRetirar(reg.getQuantLivrosParaRetirar()-1);
			return true;
		} else
			return false;
	}
	
	
	
	public RegLivros findRegLivrosForThis(String isbn) {
		for (RegLivros rl : this.getRegLivros())
			if (rl.getIsbn().contentEquals(isbn))
				return rl;
		return null;
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
