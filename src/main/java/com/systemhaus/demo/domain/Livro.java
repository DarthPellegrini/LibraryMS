package com.systemhaus.demo.domain;

public class Livro {

	//private int id; //único, código que identifica o livro na biblioteca (usado somente no BD)
	private int ISBN; //pode ser repetido, para n volumes do mesmo livro
	private int edicao; //edição do livro
	private String titulo; //título do livro
	private String autor; //autor do livro
	private String editora; //editora do livro
	private int numeroPaginas; //número de páginas
	private boolean disp; //disponibilidade do livro, se está ou não retirado
	
	public Livro(int iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas) {
		super();
		ISBN = iSBN;
		this.edicao = edicao;
		this.titulo = titulo;
		this.autor = autor;
		this.editora = editora;
		this.numeroPaginas = numeroPaginas;
		disp = true;
	}
	
	public int getISBN() {
		return ISBN;
	}
	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}
	public int getEdicao() {
		return edicao;
	}
	public void setEdicao(int edicao) {
		this.edicao = edicao;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getEditora() {
		return editora;
	}
	public void setEditora(String editora) {
		this.editora = editora;
	}
	public int getnumeroPaginas() {
		return numeroPaginas;
	}
	public void setnumeroPaginas(int numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}
	public boolean getDisp() {
		return disp;
	}
	public void setDisp(boolean disp) {
		this.disp = disp;
	}
	
}
