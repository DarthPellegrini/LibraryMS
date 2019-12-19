package com.systemhaus.demo.domain;

public class RegLivros {

	private int id;
	private String isbn;
	private int quantLivrosNoCatalogo;
	private int quantLivrosParaRetirar;
	
	public RegLivros() {
		
	}

	public RegLivros(String isbn, int quantLivrosNoCatalogo) {
		super();
		this.setIsbn(isbn);
		this.setQuantLivrosNoCatalogo(quantLivrosNoCatalogo);
		this.setQuantLivrosParaRetirar(quantLivrosNoCatalogo);
	}
	
	public RegLivros(String isbn, int quantLivrosNoCatalogo, int quantLivrosParaRetirar) {
		super();
		this.setIsbn(isbn);
		this.setQuantLivrosNoCatalogo(quantLivrosNoCatalogo);
		this.setQuantLivrosParaRetirar(quantLivrosParaRetirar);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getQuantLivrosNoCatalogo() {
		return quantLivrosNoCatalogo;
	}

	public void setQuantLivrosNoCatalogo(int quantLivrosNoCatalogo) {
		this.quantLivrosNoCatalogo = quantLivrosNoCatalogo;
	}

	public int getQuantLivrosParaRetirar() {
		return quantLivrosParaRetirar;
	}

	public void setQuantLivrosParaRetirar(int quantLivrosParaRetirar) {
		this.quantLivrosParaRetirar = quantLivrosParaRetirar;
	}
	
	public int getMaxDeletionNumber() {
		return this.getQuantLivrosNoCatalogo()-this.getQuantLivrosParaRetirar();
	}
}
