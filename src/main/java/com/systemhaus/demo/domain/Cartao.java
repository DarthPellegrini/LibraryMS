package com.systemhaus.demo.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cartao {

	private String codigo; //codigo unico do cartao
	private LocalDate validade; //validade do cartao
	private ArrayList<LivroRetirado> livrosRetirados; //lista de livros retirados pelo cliente
	
	public Cartao(String codigo) {
		this.codigo = codigo;
		livrosRetirados = new ArrayList<LivroRetirado>();
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		if(codigo.matches("[0-9]{16}"))
			this.codigo = codigo;
	}
	public LocalDate getValidade() {
		return validade;
	}
	public void setValidade(LocalDate validade) {
		if(validade.isAfter(LocalDate.now()))
			this.validade = validade;
	}
	public ArrayList<LivroRetirado> getLivrosRetirados() {
		return livrosRetirados;
	}
	public void setLivrosRetirados(ArrayList<LivroRetirado> livrosRetirados) {
		this.livrosRetirados = livrosRetirados;
	}

	public void clear() {
		this.codigo = "";
		this.validade.atStartOfDay();
	}
	
	
}
