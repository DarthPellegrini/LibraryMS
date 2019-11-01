package com.systemhaus.demo;

import java.util.ArrayList;
import java.util.List;

import com.systemhaus.demo.dao.memory.LivroDAO;
import com.systemhaus.demo.dao.memory.ClienteDAO;
import com.systemhaus.demo.dao.memory.EnderecoDAO;
import com.systemhaus.demo.dao.memory.EstanteDAO;
import com.systemhaus.demo.dao.memory.EventoDAO;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Cartao;
import com.systemhaus.demo.domain.EstanteRepository;
import com.systemhaus.demo.domain.EventoRepository;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRepository;
import com.systemhaus.demo.domain.Prateleira;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.ClienteRepository;
import com.systemhaus.demo.domain.EnderecoRepository;

public class Server {

	private EstanteRepository estanteRepository;
	private LivroRepository livroRepository;
	private ClienteRepository clienteRepository;
	private EnderecoRepository enderecoRepository;
	private EventoRepository eventoRepository;
	
	public Server () {
		Biblioteca biblioteca = new Biblioteca();
		this.estanteRepository = new EstanteDAO(biblioteca);
		this.livroRepository = new LivroDAO(biblioteca);
		this.clienteRepository = new ClienteDAO(biblioteca);
		this.enderecoRepository = new EnderecoDAO(biblioteca);
		this.eventoRepository = new EventoDAO(biblioteca);
	}
	
	public Server(EstanteRepository estanteRepository, LivroRepository livroRepository, 
			ClienteRepository clienteRepository, EnderecoRepository enderecoRepository,
			EventoRepository eventoRepository) {
		this.estanteRepository = estanteRepository;
		this.livroRepository = livroRepository;
		this.clienteRepository = clienteRepository;
		this.enderecoRepository = enderecoRepository;
		this.eventoRepository = eventoRepository;
	}
	
	/*
	 * PARTE 1
	 * Métodos relacionados à Inserção de Livros
	 */
	
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
	
	//TODO: addLivro como método no estante repository
	public boolean addBook(Livro livro) {
		Prateleira p = estanteRepository.getPrateleiraWithEmptySpace();
		return p == null ? false : p.addLivro(livro);
	}
	
	public int returnBookCount(String iSBN) {
		return estanteRepository.returnBookCount(iSBN);
	}
	
	public int returnAvailableBookCount(String iSBN) {
		return estanteRepository.returnAvailableBookCount(iSBN);
	}
	
	public List<Livro> findSimilarBooks(Livro l){
		return livroRepository.findBySimilarExample(l,false);
	}
	
	public boolean editBook(String iSBNOriginal, Livro livro, int quantCopias) {
		//caso o número de cópias final seja menor que o disponível, os livros que:
		int quantNoAcervo = estanteRepository.returnBookCount(iSBNOriginal);
		if(!estanteRepository.havingOnlyThisAmountOfCopiesWontCauseProblems(iSBNOriginal, quantCopias))
			return false;
		if(quantCopias > quantNoAcervo)
			this.addNewBookRoutine(livro, quantCopias-quantNoAcervo);
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
	
	/*
	 * Método que verifica se a biblioteca precisa ser reorganizada
	 */
	public boolean needsReorganization() {
		return estanteRepository.needsReorganization();
	}
	
	/*
	 * Método que retorna a quantidade de estantes na biblioteca
	 */
	public int getCountOfEstantes() {
		return estanteRepository.getCountOfEstantes();
	}
	
	/*
	 * PARTE 2
	 * Métodos relacionados à inserção de Clientes
	 */
	
	/*
	 * Método de adição de clientes
	 */
	public int addClienteRoutine(Cliente cliente) {
		if(!clienteRepository.thisCpfAlreadyExists(cliente.getCPF()))
			return this.addCliente(cliente.copy());
		return 3; //erro de cpf
	}
	
	public int addCliente(Cliente cliente) {
		if(cliente.validate()) {
			if(!enderecoRepository.thereAreTooManySimilarAddresses(cliente.getEndereco())) {
				clienteRepository.save(cliente);
				return 0; //sucesso
			}else return 2; //erro de muitos endereços
		}else return 1; //erro de validação
	}
	
	/*
	 * Método de busca de clientes
	 */
	public List<Cliente> findSimilarClients(Cliente cliente){
		return clienteRepository.findSimilarClients(cliente);
	}
	
	/*
	 * Método de edição do cliente
	 */
	public boolean editClient(String CPF, Cliente cliente) {
		if(!clienteRepository.thisCpfAlreadyExists(cliente.getCPF()) 
			|| (cliente.getCPF().equals(CPF) && clienteRepository.thisCpfAlreadyExists(cliente.getCPF()))) {
			clienteRepository.edit(CPF, cliente.copy());
			return true;
		}
		return false;
	}
	
	/*
	 * Método de exclusão do cliente
	 */
	public void deleteClient(String CPF) {
		clienteRepository.delete(CPF);
	}
	
	/*
	 * Gera um novo código para o cartão do cliente
	 */
	public void generateNewCodigoCartao(Cliente cliente) {
		clienteRepository.createValidCode(cliente);
	}
	
	/*
	 * PARTE 3
	 * Métodos relacionados às inserções
	 */
	
	public List<Livro> findAvailableBooks(Livro l){
		return livroRepository.findBySimilarExample(l,true);
	}
	
	public int retirada(Livro livro, Cartao cartao) {
		if (livro.validate()) {
			if (cartao.validate()) {
				if(!livro.isRetirado()) {
					eventoRepository.save(livro, cartao, "R");
					return 0; //sucesso
				} else return 3; //erro todos os exemplares retirados
			} else return 2; //erro cliente não preenchido
		} else return 1; //erro livro não preenchido
	}
	
	/**
	 * Converte String para Inteiro com retorno automático de 0 no caso de caracteres inválidos
	 * @param s inteiro a ser convertido
	 * @return inteiro resultando
	 */
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
