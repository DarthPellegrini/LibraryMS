package com.systemhaus.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.systemhaus.demo.dao.memory.LivroDAO;
import com.systemhaus.demo.dao.memory.EstanteDAO;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Estante;
import com.systemhaus.demo.domain.EstanteRepository;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRepository;
import com.systemhaus.demo.domain.Prateleira;

public class Server {

	private Biblioteca biblioteca;
	private Livro livroTemp; //livro buscado na biblioteca que poderá ser modificado ou excluído 
	private EstanteRepository estanteRepository;
	private LivroRepository livroRepository;
	
	public Server () {
		createLibrary();
		this.estanteRepository = new EstanteDAO(biblioteca);
		this.livroRepository = new LivroDAO(biblioteca);
	}
	
	public Server(EstanteRepository estanteRepository, LivroRepository livroRepository) {
		this.estanteRepository = estanteRepository;
		this.livroRepository = livroRepository;
		createLibrary();
	}
	
	private void createLibrary() {
		biblioteca = new Biblioteca();
	}	
	
	/**
	 * Método recursivo infalível de inserção de livros
	 */
	public boolean addNewBookRoutine(Livro livro, int quantCopias) {
		//verifica se o livro inserido possui todos os campos válidos
		if (livro.validate()) {
			for (int quantLivros = 0; quantLivros < quantCopias; quantLivros++) {
				Livro copy = livro.copy();
				livroRepository.save(copy);
				if(!addBook(copy)) {
					estanteRepository.addEstante();
					addBook(copy);
				}
			}
		} else {
			return false;
		}
		return true; 
	}
	
	public boolean addBook(Livro livro) {
		Prateleira p = estanteRepository.getPrateleiraWithEmptySpace();
		return p == null ? false : p.addLivro(livro);
	}
	
	public int returnBookCount(String iSBN) {
		return biblioteca.getRegistroDeLivros().get(iSBN)[0];
	}
	
	public Livro findBook(String iSBN, String edicao, String titulo, String autor, String editora, String numeroPaginas) {
		return livroRepository.findByExample(new Livro(iSBN, strToInt(edicao), titulo, autor, editora, strToInt(numeroPaginas), false));
	}
	
	public boolean editBook(String iSBNOriginal, String iSBN, int edicao, String titulo, String autor, String editora, int numeroPaginas, 
			int quantCopias) {
		//caso o número de cópias final seja menor que o disponível, os livros que:
		int quantNoAcervo = biblioteca.getRegistroDeLivros().get(iSBN)[0];
		if(!biblioteca.havingOnlyThisAmountOfCopiesWontCauseProblems(iSBN, quantCopias))
			return false;
		if(quantCopias > quantNoAcervo)
			this.addNewBookRoutine(new Livro(iSBN, edicao, titulo, autor, editora, numeroPaginas, false), quantCopias);
		else
			if(quantCopias < quantNoAcervo) 
				this.deleteBook(quantNoAcervo-quantCopias);
		//alteração dos dados
		for (Estante e : biblioteca.getEstantes()) 
			for (Prateleira p : e.getPrateleiras()) 
				for (Livro l : p.getLivros())
					//a seleção de parâmetros será mais fácil no BD, já que poderão ou não ser incluídas no select.
					if ( l.getISBN().equals(iSBNOriginal)) {
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
			organizeLibrary();
		}else {
			//remoção dos livros (no modo de edição de livro, removendo cópias que não tenham sido retiradas)
			biblioteca.remDisponivel(livroTemp.getISBN(), delete);
			for (Prateleira p : prateleiras)
				for(ListIterator<Livro> lIt = p.getLivros().listIterator(p.getLivros().size());
						lIt.hasPrevious();) {
					Livro l = lIt.previous();
				    if (livros.contains(l) && !l.isRetirado()) {
				        lIt.remove();
				        if (--delete <= 0) {
				        	organizeLibrary();
					    	return true;
				        }
				    }
				}
		}
		return true;
	}
	
	/**
	 * Método que organiza a biblioteca, preenchendo os espaços vazios deixados pela remoção de livros
	 */
	public void organizeLibrary() {
		//removendo estantes vazias
		removeEmptyEstantes();
		
		//verificando se há necessidade de reorganizar a biblioteca
		if(needsReorganization())
			for(ListIterator<Estante> eIt = biblioteca.getEstantes().listIterator(biblioteca.getEstantes().size());
					eIt.hasPrevious();) {
				Estante e = eIt.previous();
				for(ListIterator<Prateleira> pIt = e.getPrateleiras().listIterator(e.getPrateleiras().size());
						pIt.hasPrevious();) {
					Prateleira p = pIt.previous();
					for(ListIterator<Livro> lIt = p.getLivros().listIterator(p.getLivros().size());
							lIt.hasPrevious();) {
						Livro l = lIt.previous();
						//adiciona o livro no primeiro espaço vazio
						addBook(l);
						lIt.remove();
						if(!needsReorganization())
							return;
					}
				}
				//caso a estante esteja vazia, será removida
				if(e.isEmpty())
					eIt.remove();
			}	
	}
	
	/**
	 * Verifica se a biblioteca precisa ser reorganizada
	 */
	public boolean needsReorganization() {
		for (Estante e : biblioteca.getEstantes())
			if(!e.isFull() && e != biblioteca.getLastEstante())
				return true;
		return false;
	}
	
	/**
	 * Remove estantes vazias
	 */
	public void removeEmptyEstantes() {
		for(ListIterator<Estante> eIt = biblioteca.getEstantes().listIterator();
				eIt.hasNext();) {
			Estante e = eIt.next();
			if (e.isEmpty())
				eIt.remove();
		}
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
	
	public int getCountOfEstantes() {
		return biblioteca.getEstantes().size();
	}
}
