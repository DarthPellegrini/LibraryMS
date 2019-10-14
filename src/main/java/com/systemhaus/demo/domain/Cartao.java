package com.systemhaus.demo.domain;

import java.util.ArrayList;
import java.util.Date;

public class Cartao {

	private String codigo; //codigo unico do cartao
	private Date validade; //validade do cartao
	private ArrayList<LivroRetirado> livrosRetirados; //lista de livros retirados pelo cliente
	
	public Cartao(String codigo, Date validade) {
		this.codigo = codigo;
		this.validade = validade;
		livrosRetirados = new ArrayList<LivroRetirado>();
	}
	
	public Cartao() {
		this.clear();
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		if(codigo.matches("[0-9]{16}"))
			this.codigo = codigo;
	}
	public Date getValidade() {
		return validade;
	}
	public void setValidade(Date validade) {
		//if(validade.matches("([0-9]{2}/){2}2[0-9]{3}")) 
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
		this.validade = new Date();
		//this.validade = "00/00/0000";
	}
	
	
}
