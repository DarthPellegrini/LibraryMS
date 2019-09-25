package com.systemhaus.demo;

import java.util.ArrayList;
import java.util.List;

import com.systemhaus.demo.dao.memory.LivroDAO;
import com.systemhaus.demo.dao.memory.EstanteDAO;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.EstanteRepository;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRepository;
import com.systemhaus.demo.domain.Prateleira;

public class Server {

	//private Biblioteca biblioteca;
	private EstanteRepository estanteRepository;
	private LivroRepository livroRepository;
	
	public Server () {
		Biblioteca biblioteca = new Biblioteca();
		this.estanteRepository = new EstanteDAO(biblioteca);
		this.livroRepository = new LivroDAO(biblioteca);
	}
	
	public Server(EstanteRepository estanteRepository, LivroRepository livroRepository) {
		this.estanteRepository = estanteRepository;
		this.livroRepository = livroRepository;
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
		return estanteRepository.returnBookCount(iSBN);
	}
	
	public Livro findBook(Livro l) {
		return livroRepository.findByExample(l);
	}
	
	public boolean editBook(String iSBNOriginal, Livro livro, int quantCopias) {
		//caso o número de cópias final seja menor que o disponível, os livros que:
		int quantNoAcervo = estanteRepository.returnBookCount(iSBNOriginal);
		if(!estanteRepository.havingOnlyThisAmountOfCopiesWontCauseProblems(iSBNOriginal, quantCopias))
			return false;
		if(quantCopias > quantNoAcervo)
			this.addNewBookRoutine(livro, quantCopias);
		else
			if(quantCopias < quantNoAcervo) 
				this.deleteBook(iSBNOriginal,quantNoAcervo-quantCopias);
		//alteração dos dados
		livroRepository.editByExample(iSBNOriginal, livro);
		return true;
	}
	
	public boolean deleteBook(String iSBNOriginal, int delete) {
		//criando uma lista para saber em quais prateleiras estão os livros a serem deletados
		List<Prateleira> prateleiras = new ArrayList<Prateleira>();
		List<Livro> livros = new ArrayList<Livro>();
		//primeiramente verificando se podemos deletar todos os livros (se não há nenhum retirado)
		if(!estanteRepository.allTheBooksAreAvailable(iSBNOriginal) && delete == 0)
			return false; 
		//marcando livros para serem deletados
		livroRepository.markBooksForDeletion(iSBNOriginal ,prateleiras, livros);
		if(delete == 0) {
			//remoção de todos os livros com os dados pesquisados
			livroRepository.deleteAllTheseBooks(iSBNOriginal, prateleiras, livros);
		}else {
			//remoção dos livros (no modo de edição de livro, removendo cópias que não tenham sido retiradas)
			livroRepository.deleteOnlyTheseBooks(iSBNOriginal, prateleiras, livros, delete);
		}
		organizeLibrary();
		return true;
	}
	
	/**
	 * Método que organiza a biblioteca, preenchendo os espaços vazios deixados pela remoção de livros
	 */
	public void organizeLibrary() {
		estanteRepository.organizeLibrary();	
	}
	
	public boolean needsReorganization() {
		return estanteRepository.needsReorganization();
	}
	
	/**
	 * Exibe o estado atual da biblioteca
	 * somente é usada para testes
	 */
//	public void showLibrary() {
//		for (Estante e : biblioteca.getEstantes()) {
//			System.out.println("------Estante------");
//			for (Prateleira p : e.getPrateleiras()) {
//				System.out.println("---Prateleira");
//				if(p.getLivros().size() == 0)
//					System.out.println("vazia");
//				else
//					for (Livro l : p.getLivros())
//						System.out.println(l.toString());
//			}
//		}
//	}
	
	/**
	 * Converte String para Inteiro com retorno automático de um 0 no caso de caracteres inválidos
	 * @param s
	 * @return
	 */
	public int strToInt(String s) {
		int i = 0;
		try {
			i = Integer.parseInt(s);
		}catch(Exception e){
			//do nothing, will return one either way
		}
		return i;
	}
	
	public int getCountOfEstantes() {
		return estanteRepository.getCountOfEstantes();
	}
}
