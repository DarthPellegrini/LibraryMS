package com.systemhaus.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Estante;
import com.systemhaus.demo.domain.Prateleira;
import com.systemhaus.demo.domain.Livro;

public class FakeServer {

	private Biblioteca biblioteca;
	private Livro livroTemp; //livro buscado na biblioteca que poderá ser modificado ou excluído 
	
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
	
	/**
	 * Método recursivo infalível de inserção de livros
	 */
	public boolean addNewBookRoutine(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas, 
			int quantCopias) {
		//verifica se o livro inserido possui todos os campos válidos
		if (validateBook(iSBN, edicao, titulo, autor, editora, numeroPaginas))
			for (int quantLivros = 0; quantLivros < quantCopias; quantLivros++) 
				if(addBook(iSBN, edicao, titulo, autor, editora, numeroPaginas))
					biblioteca.addDisponivel(iSBN, quantCopias);
				else {
					//caso a estante esteja cheia
					biblioteca.addEstante();
					if(addBook(iSBN, edicao, titulo, autor, editora, numeroPaginas))
						biblioteca.addDisponivel(iSBN, quantCopias);
					else
						addNewBookRoutine(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantLivros);
				}
		else
			return false;
		return true; 
	}
	
	public boolean validateBook(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas) {
		Livro l = new Livro(iSBN, edicao, titulo, autor, editora, numeroPaginas, false);
		return l.validate();
	}
	
	public boolean addBook(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas) {
		Prateleira p = biblioteca.getPrateleiraWithEmptySpace();
		System.out.println("p= " + p);
		return p == null ? false : p.addLivro(
				new Livro(iSBN, edicao, titulo, autor, editora, numeroPaginas, false)
				);
	}
	
	public int returnBookCount(String iSBN) {
		return biblioteca.getRegistroDeLivros().get(iSBN)[0];
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
						livroTemp = l;
						return l;
					}
		return null;
	}
	
	public boolean editBook(String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas, 
			int quantCopias) {
		//caso o número de cópias final seja menor que o disponível, os livros que:
		int quantNoAcervo = biblioteca.getRegistroDeLivros().get(iSBN)[0];
		if(!biblioteca.havingOnlyThisAmountOfCopiesWontCauseProblems(iSBN, quantCopias))
			return false;
		if(quantCopias > quantNoAcervo)
			this.addNewBookRoutine(iSBN, edicao, titulo, autor, editora, numeroPaginas, quantCopias);
		else
			if(quantCopias < quantNoAcervo) 
				this.deleteBook(quantNoAcervo-quantCopias);
		//alteração dos dados
		for (Estante e : biblioteca.getEstantes()) 
			for (Prateleira p : e.getPrateleiras()) 
				for (Livro l : p.getLivros())
					//a seleção de parâmetros será mais fácil no BD, já que poderão ou não ser incluídas no select.
					if ( (l.getISBN().equals(livroTemp.getISBN())) && (l.getEdicao() == livroTemp.getEdicao()) && 
					(l.getTitulo().equals(livroTemp.getTitulo())) && (l.getAutor().equals(livroTemp.getAutor())) && 
					(l.getEditora().equals(livroTemp.getEditora())) && (l.getNumeroPaginas() == livroTemp.getNumeroPaginas())) {
						l.setISBN(iSBN);
						l.setEdicao(edicao);
						l.setTitulo(titulo);
						l.setAutor(autor);
						l.setEditora(editora);
						l.setNumeroPaginas(numeroPaginas);
					}
		//caso o usuário queira deletar o livro após modificá-lo (não que faça sentido, mas para evitar erros)
		livroTemp = new Livro(iSBN, edicao, titulo, autor, editora, numeroPaginas, false);
		return true;
	}
	
	public boolean deleteBook(int delete) {
		//criando uma lista para saber em quais prateleiras estão os livros a serem deletados
		List<Prateleira> prateleiras = new ArrayList<Prateleira>();
		List<Livro> livros = new ArrayList<Livro>();
		//primeiramente verificando se podemos deletar todos os livros (se não há nenhum retirado)
		//delete será igual a 0 para o caso de deletar TODOS os livros com os dados inseridos
		if(!biblioteca.allTheBooksAreAvailable(livroTemp.getISBN()) && delete == 0)
			return false;
		for(ListIterator<Estante> eIt = biblioteca.getEstantes().listIterator(biblioteca.getEstantes().size());
				eIt.hasPrevious();) {
			Estante e = eIt.previous();
			for(ListIterator<Prateleira> pIt = e.getPrateleiras().listIterator(e.getPrateleiras().size());
					pIt.hasPrevious();) {
				Prateleira p = pIt.previous();
				for(ListIterator<Livro> lIt = p.getLivros().listIterator(p.getLivros().size());
						lIt.hasPrevious();) {
					Livro l = lIt.previous();
					if ( l.getISBN().equals(livroTemp.getISBN()) && l.getEdicao() == livroTemp.getEdicao() && 
							l.getTitulo().equals(livroTemp.getTitulo()) && l.getAutor().equals(livroTemp.getAutor()) && 
							l.getEditora().equals(livroTemp.getEditora()) && l.getNumeroPaginas() == livroTemp.getNumeroPaginas() && 
							!l.isRetirado()) {
								if(!prateleiras.contains(p))
									prateleiras.add(p);
								//caso algum dos livros esteja retirado, não podemos removê-los do estoque
								if (!livros.contains(l))
									livros.add(l);
							}
				}
			}
		}
		//TODO: remover estantes vazias
		//e na outra para removê-los, isso evita erros de iteração durante a busca & remoção
		if(delete == 0) {
			//remoção de todos os livros com os dados pesquisados
			biblioteca.getRegistroDeLivros().remove(livroTemp.getISBN());
			for (Prateleira p : prateleiras) 
				p.getLivros().removeAll(livros);
		}else {
			//remoção dos livros (no modo de edição de livro, removendo cópias que não tenham sido retiradas)
			biblioteca.remDisponivel(livroTemp.getISBN(), delete);
			for (Prateleira p : prateleiras)
				for(ListIterator<Livro> lIt = p.getLivros().listIterator(p.getLivros().size());
						lIt.hasPrevious();) {
					Livro l = lIt.previous();
				    if (livros.contains(l) && !l.isRetirado()) {
				        lIt.remove();
				        if (--delete <= 0) 
					    	return true;
				    }
				}
		}
		return true;
	}
	
	//TODO: implementar o método de organizar a biblioteca e remover espaços vazios
	public void organizeLibrary() {
		
	/*
	 * Lógica:
	 * verificar se a última prateleira está vazia (se sim, remove essa prateleira e segue o carreto)
	 * verificar se todas as prateleiras anteriores a última estão cheias (senão, continua a lógica)
	 * ver as estantes de trás pra frente
	 * ver as prateleiras de trás pra frente
	 * pegar os livros 1 por 1 e ir inserindo nos espaços vazios
	 * (pode-se utilizar um addBook e delete para facilitar as trocas)
	 */
		
	}
	
	/**
	 * Exibe o estado atual da biblioteca
	 */
	public void showLibrary() {
		for (Estante e : biblioteca.getEstantes()) {
			System.out.println("------Estante------");
			for (Prateleira p : e.getPrateleiras()) {
				System.out.println("---Prateleira");
				if(p.getLivros().size() == 0)
					System.out.println("vazia");
				else
					for (Livro l : p.getLivros())
						System.out.println(l.toString());
			}
		}
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
