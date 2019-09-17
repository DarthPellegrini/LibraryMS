package com.systemhaus.demo;

import java.util.ArrayList;
import java.util.List;

import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Estante;
import com.systemhaus.demo.domain.Prateleira;
import com.systemhaus.demo.domain.Livro;

public class FakeServer {

	private Biblioteca biblioteca;
	private Livro livroBuscado;
	
	public FakeServer () {
		createLibrary();
	}
	
	private void createLibrary() {
		biblioteca = new Biblioteca();
		ArrayList<Estante> estantes = new ArrayList<Estante>();
		ArrayList<Prateleira> prateleiras = new ArrayList<Prateleira>();
		prateleiras.add(new Prateleira());
		estantes.add(new Estante(prateleiras));
		biblioteca.setEstantes(estantes);
	}	
	
	/*
	 * Método recursivo infalível de inserção de livros
	 */
	public boolean addNewBookRoutine(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas, 
			int quantCopias) {
		//verifica se o livro inserido possui todos os campos válidos
		if (validateBook(iSBN, edicao, titulo, autor, editora, numeroPaginas))
			for (int quantLivros = 0; quantLivros < quantCopias; quantLivros++) 
				if(addBook(iSBN, edicao, titulo, autor, editora, numeroPaginas)) {
					biblioteca.addDisponivel(iSBN);
					return true;
				}else 
					//caso a prateleira esteja cheia
					if(biblioteca.getLastEstante().addPrateleira(new Prateleira()))
						if(addBook(iSBN, edicao, titulo, autor, editora, numeroPaginas)) {
							biblioteca.addDisponivel(iSBN);
							return true;
						}else
							addNewBookRoutine(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias);
					else {
						//caso a estante esteja cheia
						biblioteca.addEstante();
						if(addBook(iSBN, edicao, titulo, autor, editora, numeroPaginas)) {
							biblioteca.addDisponivel(iSBN);
							return true;
						}else
							addNewBookRoutine(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias);
					}
		else
			return false;
		return false; 
	}
	
	public boolean validateBook(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas) {
		Livro l = new Livro(iSBN, edicao, titulo, autor, editora, numeroPaginas, false);
		return l.validate();
	}
	
	public boolean addBook(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas) {
		return biblioteca.getLastPrateleira().addLivro(
				new Livro(iSBN, edicao, titulo, autor, editora, numeroPaginas, false)
				);
	}
	
	public Livro findBook(String iSBN, String edicao, String titulo, String autor, String editora, String numeroPaginas) {
		//buscando em todas as estantes e em todos as prateleiras
		for (Estante e : biblioteca.getEstantes()) 
			for (Prateleira p : e.getPrateleiras()) 
				for (Livro l : p.getLivros())
					//a seleção de parâmetros será mais fácil no BD, já que poderão ou não ser incluídas no select.
					if ( (l.getISBN().equals(iSBN) || iSBN.isEmpty()) && (l.getEdicao() == strToInt(edicao) || edicao.isEmpty()) && 
					(l.getTitulo().equals(titulo) || titulo.isEmpty()) && (l.getAutor().equals(autor) || autor.isEmpty()) && 
					(l.getEditora().equals(editora) || editora.isEmpty()) && 
					(l.getNumeroPaginas() == strToInt(numeroPaginas) || numeroPaginas.isEmpty())) {
						livroBuscado = l;
						return l;
					}
		return null;
	}
	
	public int findWithdrawnBookCount(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas) {
		//buscando em todas as estantes e em todos as prateleiras
		int withdraw = 0;
		for (Estante e : biblioteca.getEstantes()) 
			for (Prateleira p : e.getPrateleiras()) 
				for (Livro l : p.getLivros())
					//a seleção de parâmetros será mais fácil no BD, já que poderão ou não ser incluídas no select.
					if ( (l.getISBN().equals(iSBN)) && (l.getEdicao() == edicao) && 
					(l.getTitulo().equals(titulo)) && (l.getAutor().equals(autor)) && 
					(l.getEditora().equals(editora)) && (l.getNumeroPaginas() == numeroPaginas) && 
					!l.isRetirado()) {
						withdraw++;
					}
		return withdraw;
	}
	
	//método para testes, será removido em breve
	public Biblioteca getB() {
		return biblioteca;
	}
	
	//TODO: para adicionar livros, chamar o método de adição, e para remover, o de remoção
	public boolean editBook(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas, 
			int quantCopias) {
		//quantidade de livros máxima que pode ser deletada
		int booksToDelete = findWithdrawnBookCount(iSBN, edicao, titulo, autor, editora, numeroPaginas);
		for (Estante e : biblioteca.getEstantes()) 
			for (Prateleira p : e.getPrateleiras()) 
				for (Livro l : p.getLivros())
					//a seleção de parâmetros será mais fácil no BD, já que poderão ou não ser incluídas no select.
					if ( (l.getISBN().equals(livroBuscado.getISBN())) && (l.getEdicao() == livroBuscado.getEdicao()) && 
					(l.getTitulo().equals(livroBuscado.getTitulo())) && (l.getAutor().equals(livroBuscado.getAutor())) && 
					(l.getEditora().equals(livroBuscado.getEditora())) && (l.getNumeroPaginas() == livroBuscado.getNumeroPaginas())) {
						l.setISBN(iSBN);
						l.setEdicao(edicao);
						l.setTitulo(titulo);
						l.setAutor(autor);
						l.setEditora(editora);
						l.setNumeroPaginas(numeroPaginas);
						//caso o número de cópias seja menor que o anterior, os livros que:
						//não tiverem sido retirados serão removidos
						//a quantidade dependerá de quantos livros estão disponíveis
						
					}
		return true;
	}
	
	//TODO: remover os livros baseado na sua disponibilidade
	public boolean deleteBook(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas,
			int quantRem, int delete) {
		//criando uma lista para saber em quais prateleiras estão os livros a serem deletados
		List<Prateleira> prateleiras = new ArrayList<Prateleira>();
		Livro livroParaDeletar = new Livro(iSBN, edicao, titulo, autor, editora, numeroPaginas, false);
		//percorrendo a lista duas vezes, uma para marcar as prateleiras com livros a serem removidos
		for (Estante e : biblioteca.getEstantes()) 
			for (Prateleira p : e.getPrateleiras()) 
				for (Livro l : p.getLivros())
					if ( l.getISBN().equals(iSBN) && l.getEdicao() == edicao && l.getTitulo().equals(titulo) && l.getAutor().equals(autor) && 
					l.getEditora().equals(editora) && l.getNumeroPaginas() == numeroPaginas && !l.isRetirado() && !prateleiras.contains(p))
						prateleiras.add(p);
		//e na outra para removê-los, isso evita erros durante a remoção
		int removidos = 0;
		for (Prateleira p : prateleiras) {
			if (removidos < quantRem)
				break;
			else
				break;
		}
		if (prateleiras.size() == 0)
			return false;
		else
			return true;
	}
	
	public int strToInt(String s) {
		int i = 0;
		try {
			i = Integer.parseInt(s);
		}catch(Exception e){
			//do nothing, will return zero either way
		}
		return i;
	}
}
