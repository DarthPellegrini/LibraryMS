package com.systemhaus.demo.domain;

import java.util.ArrayList;
import java.util.Date;

public class Cartao {

	private int codigo; //codigo unico do cartao
	private Date validade; //validade do cartao
	private ArrayList<LivroRetirado> livrosRetirados; //lista de livros retirados pelo cliente
	
	public Cartao(int codigo) {
		this.codigo = codigo;
		livrosRetirados = new ArrayList<LivroRetirado>();
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Date getValidade() {
		return validade;
	}
	public void setValidade(Date validade) {
		this.validade = validade;
	}
	public ArrayList<LivroRetirado> getLivrosRetirados() {
		return livrosRetirados;
	}
	public void setLivrosRetirados(ArrayList<LivroRetirado> livrosRetirados) {
		this.livrosRetirados = livrosRetirados;
	}
	
	
}
