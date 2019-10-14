package com.systemhaus.demo.domain;

import java.util.Date;

import com.jgoodies.binding.beans.Model;

public class Cliente extends Model{

	/*
	 * Serial e Bindings
	 */
	private static final long serialVersionUID = 1L;
	private static final String PROPERTY_NOME = "nome";
	private static final String PROPERTY_CPF = "cpf";
	private static final String PROPERTY_TELEFONE = "telefone";
	private static final String PROPERTY_CIDADE = "cidade";
	private static final String PROPERTY_BAIRRO = "bairro";
	private static final String PROPERTY_RUA = "rua";
	private static final String PROPERTY_NUMERO = "numero";
	private static final String PROPERTY_CARTAO = "cartao";
	private static final String PROPERTY_VALIDADE = "validade";
	
	private String nome;
	private String cpf;
	private String telefone;
	private Endereco endereco;
	private Cartao cartao;
	
	public Cliente(String nome, String cpf, String telefone, Endereco endereco, Cartao cartao) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.endereco = endereco;
		this.cartao = cartao;
	}
	
	public Cliente() {
		
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		String oldValue = this.nome;
		this.nome = nome;
		firePropertyChange(PROPERTY_NOME, oldValue, this.nome);
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		String oldValue = this.cpf;
		this.cpf = cpf;
		firePropertyChange(PROPERTY_CPF, oldValue, this.cpf);
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		String oldValue = this.telefone;
		this.telefone = telefone;
		firePropertyChange(PROPERTY_TELEFONE, oldValue, this.telefone);
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public String getCidade() {
		return endereco.getCidade();
	}
	public void setCidade(String cidade) {
		String oldValue = this.getCidade();
		endereco.setCidade(cidade);
		firePropertyChange(PROPERTY_CIDADE, oldValue, cidade);
	}
	public String getBairro() {
		return endereco.getBairro();
	}
	public void setBairro(String bairro) {
		String oldValue = this.getBairro();
		endereco.setBairro(bairro);
		firePropertyChange(PROPERTY_BAIRRO, oldValue, bairro);
	}
	public String getRua() {
		return endereco.getRua();
	}
	public void setRua(String rua) {
		String oldValue = this.getRua();
		endereco.setRua(rua);
		firePropertyChange(PROPERTY_RUA, oldValue, rua);
	}
	public int getNumero() {
		return endereco.getNumero();
	}
	public void setNumero(int numero) {
		int oldValue = this.getNumero();
		endereco.setNumero(numero);
		firePropertyChange(PROPERTY_NUMERO, oldValue, numero);
	}
	public Cartao getCartao() {
		return cartao;
	}
	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}
	public int getCodigo() {
		return cartao.getCodigo();
	}
	public void setCodigo(int codigo) {
		int oldValue = this.getCodigo();
		cartao.setCodigo(codigo);
		firePropertyChange(PROPERTY_CARTAO, oldValue, codigo);
	}
	public Date getValidade() {
		return cartao.getValidade();
	}
	public void setValidade(Date validade) {
		Date oldValue = this.getValidade();
		cartao.setValidade(validade);
		firePropertyChange(PROPERTY_VALIDADE, oldValue, validade);
	}
	
	
}
