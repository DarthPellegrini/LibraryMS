package com.systemhaus.demo.domain;

public class RegLivros {

	private int id;
	private String isbn;
	private int quantLivrosNoCatalogo;
	private int quantLivrosParaRetirar;
	private Biblioteca biblioteca;
	
	public RegLivros() {
		
	}

	public RegLivros(String isbn, int quantLivrosNoCatalogo, Biblioteca biblioteca) {
		super();
		this.setIsbn(isbn);
		this.setQuantLivrosNoCatalogo(quantLivrosNoCatalogo);
		this.setQuantLivrosParaRetirar(quantLivrosNoCatalogo);
		this.setBiblioteca(biblioteca);
	}
	
	public RegLivros(String isbn, int quantLivrosNoCatalogo, int quantLivrosParaRetirar, Biblioteca biblioteca) {
		super();
		this.setIsbn(isbn);
		this.setQuantLivrosNoCatalogo(quantLivrosNoCatalogo);
		this.setQuantLivrosParaRetirar(quantLivrosParaRetirar);
		this.setBiblioteca(biblioteca);
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

	public Biblioteca getBiblioteca() {
		return biblioteca;
	}

	public void setBiblioteca(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}
}
