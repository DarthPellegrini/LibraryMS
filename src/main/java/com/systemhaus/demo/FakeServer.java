package com.systemhaus.demo;

import java.util.ArrayList;

import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Estante;
import com.systemhaus.demo.domain.Prateleira;
import com.systemhaus.demo.domain.Livro;

public class FakeServer {

	private Biblioteca biblioteca;
	
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
	public void addNewBookRoutine(String iSBN, String edicao, String titulo, String autor, String editora, String numeroPaginas,
			String quantCopias) {
		if (validateBook(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias))
			
			for (int x = 0; x < Integer.parseInt(quantCopias); x++) {
				if(addBook(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias))
					System.out.println(biblioteca.getLastLivros().get(biblioteca.getLastLivros().size()-1).toString());
				else {
					System.out.println("prateleira cheia");
					if(biblioteca.getLastEstante().addPrateleira(new Prateleira()))
						if(addBook(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias))
							System.out.println(biblioteca.getLastLivros().get(biblioteca.getLastLivros().size()-1).toString());
						else
							addNewBookRoutine(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias);
					else {
						System.out.println("estante cheia");
						biblioteca.addEstante();
						if(addBook(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias))
							//TODO: exibir mensagem de sucesso
							System.out.println(biblioteca.getLastLivros().get(biblioteca.getLastLivros().size()-1).toString());
						else
							addNewBookRoutine(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias);
					}
				}
			}
		else
			//TODO: exibir mensagem de erro na tela
			System.out.println("Erro - dados inválidos");
	}
	
	public boolean validateBook(String iSBN, String edicao, String titulo, String autor, String editora, String numeroPaginas,
			String quantCopias) {
		Livro l = new Livro(
				iSBN, 
				Integer.parseInt(edicao),
				titulo,
				autor,
				editora,
				Integer.parseInt(numeroPaginas),
				Integer.parseInt(quantCopias));
		return l.validate();
	}
	
	public boolean addBook(String iSBN, String edicao, String titulo, String autor, String editora, String numeroPaginas,
			String quantCopias) {
		return biblioteca.getLastPrateleira().addLivro(
				new Livro(
						iSBN, 
						Integer.parseInt(edicao),
						titulo,
						autor,
						editora,
						Integer.parseInt(numeroPaginas),
						Integer.parseInt(quantCopias))
				);
	}
	
	//test method
	public void showAllBooks() {
		
	}
}
