package com.systemhaus.demo.domain;

public class Livro {

	//private int id; //único, código que identifica o livro na biblioteca (usado somente no BD)
	private int ISBN; //pode ser repetido, para n volumes do mesmo livro
	private int edicao; //edição do livro
	private String titulo; //título do livro
	private String autor; //autor do livro
	private String editora; //editora do livro
	private int numeroPaginas; //número de páginas
	private int quantCopias; //quantidade de livros no acervo
	private int quantDisp; //quantidade de livros disponíveis para a retirada
	
	public Livro(int iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas,
			int quantCopias) {
		super();
		ISBN = iSBN;
		this.edicao = edicao;
		this.titulo = titulo;
		this.autor = autor;
		this.editora = editora;
		this.numeroPaginas = numeroPaginas;
		this.quantCopias = quantCopias;
		this.quantDisp = quantCopias;
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
	public int getQuantCopias() {
		return quantCopias;
	}
	public void setQuantCopias(int quantCopias) {
		this.quantCopias = quantCopias;
	}
	public int getQuantDisp() {
		return quantDisp;
	}
	public void setQuantDisp(int quantDisp) {
		this.quantDisp = quantDisp;
	}
	
	
}
