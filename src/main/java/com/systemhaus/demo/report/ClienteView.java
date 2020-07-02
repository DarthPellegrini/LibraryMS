package com.systemhaus.demo.report;

import java.util.Date;
import java.text.SimpleDateFormat;

public class ClienteView {
 
	private String CPF;
	private String nome; 
	private String telefone;
	private String cidade;
	private String rua;
	private String bairro;
	private int numero;
	private String codigo;
	private Date validade;
	
	public ClienteView () {
		super();
	}
	
	public ClienteView(String CPF, String nome, String telefone, String cidade, String bairro,String rua,int numero,String codigo ,Date validade) {
		super();
		this.CPF = CPF;
		this.nome = nome;
		this.telefone = telefone;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.numero = numero;
		this.codigo = codigo;
		this.validade = validade;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getValidade() {
		return validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return this.getRua() + ", " + String.valueOf(this.getNumero()) + ", " + this.getBairro() + " - " + this.getCidade();
	}
	
	public String getCartao() {
		return this.getCodigo() + " " +  new SimpleDateFormat("MM/yy").format(this.getValidade()); 
	}
}
