package com.systemhaus.demo.domain;

import java.time.LocalDate;
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
	private static final String PROPERTY_CODCARTAO = "codCartao";
	private static final String PROPERTY_VALIDADE = "validade";
	
	private int id;
	private String nome;
	private String cpf;
	private String telefone;
	private Endereco endereco;
	private Cartao cartao;
	private boolean ativo;
	
	public Cliente(String nome, String cpf, String telefone, 
			String cidade, String bairro, String rua, int numero, 
			String codigo, Date validade) {
		super();
		this.endereco = new Endereco();
		this.cartao = new Cartao();
		this.setNome(nome);
		this.setCpf(cpf);
		this.setTelefone(telefone);
		this.getEndereco().setCidade(cidade);
		this.getEndereco().setBairro(bairro);
		this.getEndereco().setRua(rua);
		this.getEndereco().setNumero(numero);
		this.getCartao().setCodigo(codigo);
		this.setAtivo(true);
	}
	
	public Cliente(String nome, String cpf, String telefone, Endereco endereco, Cartao cartao) {
		super();
		this.setEndereco(endereco);
		this.setCartao(cartao);
		this.setNome(nome);
		this.setCpf(cpf);
		this.setTelefone(telefone);
		this.setAtivo(true);
	}
	
	public Cliente() {
		super();
		this.endereco = new Endereco();
		this.cartao = new Cartao();
		this.setAtivo(true);
		this.clear();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		String oldValue = this.nome;
		this.nome = nome;
		this.cartao.setNome(nome);
		firePropertyChange(PROPERTY_NOME, oldValue, this.nome);
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		if(cpf.matches("[0-9]{11}")) {
			String oldValue = this.cpf;
			this.cpf = cpf;
			firePropertyChange(PROPERTY_CPF, oldValue, this.cpf);
		}else
			this.cpf = "";
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		if(telefone.matches("55[0-9]{2}9[0-9]{8}")) {
			String oldValue = this.telefone;
			this.telefone = telefone;
			firePropertyChange(PROPERTY_TELEFONE, oldValue, this.telefone);
		}else
			this.telefone = "";
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
	public String getCodCartao() {
		return cartao.getCodigo();
	}
	public void setCodCartao(String codCartao) {
		String oldValue = this.getCodCartao();
		cartao.setCodigo(codCartao);
		firePropertyChange(PROPERTY_CODCARTAO, oldValue, codCartao);
	}
	public Date getValidade() {
		return cartao.getValidade();
	}
	public void setValidade(Date validade) {
		Date oldValue = this.getValidade();
		cartao.setValidade(validade);
		firePropertyChange(PROPERTY_VALIDADE, oldValue, validade);
	}
	
	public void setAllDataFrom(Cliente from) {
		this.setNome(from.getNome());
		this.setCpf(from.getCpf());
		this.setTelefone(from.getTelefone());
		this.setCidade(from.getCidade());
		this.setBairro(from.getBairro());
		this.setRua(from.getRua());
		this.setNumero(from.getNumero());
		this.setCodCartao(from.getCodCartao());
		this.setValidade(from.getValidade());
	}
	
	public Cliente copy() {
		return new Cliente(nome,cpf,telefone,endereco,cartao);
	}
	
	public boolean validate() {
		return (!cpf.equals("") && !telefone.equals("") && endereco.validate() && cartao.validate());
	}
	
	public void clear() {
		this.nome = "";
		this.cpf = "";
		this.telefone = "";
		this.endereco = new Endereco();
		this.cartao = new Cartao();
	}

	public LocalDate getValidadeAsLocalDate() {
		return cartao.getValidadeAsLocalDate();
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
}
