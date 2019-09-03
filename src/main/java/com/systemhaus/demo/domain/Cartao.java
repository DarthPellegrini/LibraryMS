package com.systemhaus.demo.domain;

import java.util.ArrayList;

public class Cartao {

	private int codigo; //codigo unico do cartao
	private ArrayList<LivroRetirado> livrosRetirados; //lista de livros retirados pelo cliente
	
	public Cartao(int codigo) {
		super();
		this.codigo = codigo;
		livrosRetirados = new ArrayList<LivroRetirado>();
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public ArrayList<LivroRetirado> getLivrosRetirados() {
		return livrosRetirados;
	}
	public void setLivrosRetirados(ArrayList<LivroRetirado> livrosRetirados) {
		this.livrosRetirados = livrosRetirados;
	}
	
	
}
