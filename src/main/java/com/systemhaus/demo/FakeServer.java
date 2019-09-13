package com.systemhaus.demo;

import java.util.ArrayList;
import java.util.List;

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
	public boolean addNewBookRoutine(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas,
			int quantCopias) {
		//verifica se o livro inserido possui todos os campos válidos e se o mesmo livro já não foi inserido
		if (validateBook(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias)
				&& findABook(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias) == null)
			for (int quantLivros = 0; quantLivros < quantCopias; quantLivros++) 
				if(addBook(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias))
					return true;
				else 
					//caso a prateleira esteja cheia
					if(biblioteca.getLastEstante().addPrateleira(new Prateleira()))
						if(addBook(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias))
							return true;
						else
							addNewBookRoutine(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias);
					else {
						//caso a estante esteja cheia
						biblioteca.addEstante();
						if(addBook(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias))
							return true;
						else
							addNewBookRoutine(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias);
					}
		else
			return false;
		//o java quer que quer colocar esse return aqui, mas, se o if for falso, cairá no else acima, wtf java?
		return false; 
	}
	
	public boolean validateBook(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas,
			int quantCopias) {
		Livro l = new Livro(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias);
		return l.validate();
	}
	
	public boolean addBook(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas,
			int quantCopias) {
		return biblioteca.getLastPrateleira().addLivro(
				new Livro(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias)
				);
	}
	
	public Livro findABook(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas, int quantCopias) {
		//buscando em todas as estantes e em todos as prateleiras
		for (Estante e : biblioteca.getEstantes()) 
			for (Prateleira p : e.getPrateleiras()) 
				for (Livro l : p.getLivros()) {
					//a seleção de parâmetros será mais fácil no BD, já que poderão ou não ser incluídas no select.
					if ( (l.getISBN().equals(iSBN) || iSBN.isEmpty()) && (l.getEdicao() == edicao || edicao == 0) && 
					(l.getTitulo().equals(titulo) || titulo.isEmpty()) && (l.getAutor().equals(autor) || autor.isEmpty()) && 
					(l.getEditora().equals(editora) || editora.isEmpty()) && (l.getNumeroPaginas() == numeroPaginas || numeroPaginas == 0) && 
					(l.getQuantCopias() == quantCopias || quantCopias == 0)) {
						return l;
					}
				}
		return null;
	}
	
	public boolean deleteBook(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas, int quantCopias) {
		//criando uma lista para saber quais livros serão deletados
		List<Livro> deletados = new ArrayList<Livro>();
		//percorrendo a lista duas vezes, uma para marcar livros para serem removidos
		for (Estante e : biblioteca.getEstantes()) 
			for (Prateleira p : e.getPrateleiras()) 
				for (Livro l : p.getLivros())
					if ( l.getISBN().equals(iSBN) && l.getEdicao() == edicao && l.getTitulo().equals(titulo) && l.getAutor().equals(autor) && 
					l.getEditora().equals(editora) && l.getNumeroPaginas() == numeroPaginas && l.getQuantCopias() == quantCopias && 
					!deletados.contains(l))
						deletados.add(l);
		//e na outra para removê-los, isso evita erros durante a remoção
		for (Estante e : biblioteca.getEstantes()) 
			for (Prateleira p : e.getPrateleiras())
				p.getLivros().removeAll(deletados);
		if (deletados.size() == 0)
			return false;
		else
			return true;
	}
	
	//TODO: finish this method
	public void updateBookCounter() {
		
	}
	
}
