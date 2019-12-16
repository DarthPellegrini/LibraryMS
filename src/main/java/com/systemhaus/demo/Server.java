package com.systemhaus.demo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.systemhaus.demo.dao.memory.LivroDAO;
import com.systemhaus.demo.dao.memory.ClienteDAO;
import com.systemhaus.demo.dao.memory.EnderecoDAO;
import com.systemhaus.demo.dao.memory.EstanteDAO;
import com.systemhaus.demo.dao.memory.LivroRetiradoDAO;
import com.systemhaus.demo.dao.RegLivrosDAO;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.EstanteRepository;
import com.systemhaus.demo.domain.LivroRetiradoRepository;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRepository;
import com.systemhaus.demo.domain.LivroRetirado;
import com.systemhaus.demo.domain.Prateleira;
import com.systemhaus.demo.domain.RegLivrosRepository;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.ClienteRepository;
import com.systemhaus.demo.domain.EnderecoRepository;

public class Server {

	private EstanteRepository estanteRepository;
	private LivroRepository livroRepository;
	private RegLivrosRepository regLivrosRepository;
	private ClienteRepository clienteRepository;
	private EnderecoRepository enderecoRepository;
	private LivroRetiradoRepository livroRetiradoRepository;
	
	public Server () {
		Biblioteca biblioteca = new Biblioteca();
		this.estanteRepository = new EstanteDAO(biblioteca);
		this.livroRepository = new LivroDAO(biblioteca);
		this.regLivrosRepository = new RegLivrosDAO(biblioteca);
		this.clienteRepository = new ClienteDAO(biblioteca);
		this.enderecoRepository = new EnderecoDAO(biblioteca);
		this.livroRetiradoRepository = new LivroRetiradoDAO(biblioteca);
	}
	
	public Server (Biblioteca biblioteca) {
		this.estanteRepository = new EstanteDAO(biblioteca);
		this.livroRepository = new LivroDAO(biblioteca);
		this.regLivrosRepository = new RegLivrosDAO(biblioteca);
		this.clienteRepository = new ClienteDAO(biblioteca);
		this.enderecoRepository = new EnderecoDAO(biblioteca);
		this.livroRetiradoRepository = new LivroRetiradoDAO(biblioteca);
	}
	
	public Server(EstanteRepository estanteRepository, LivroRepository livroRepository, 
			ClienteRepository clienteRepository, EnderecoRepository enderecoRepository,
			LivroRetiradoRepository livroRetiradoRepository) {
		this.estanteRepository = estanteRepository;
		this.livroRepository = livroRepository;
		this.clienteRepository = clienteRepository;
		this.enderecoRepository = enderecoRepository;
		this.livroRetiradoRepository = livroRetiradoRepository;
	}
	
	/*
	 * PARTE 1
	 * Métodos relacionados à Inserção de Livros
	 */
	
	/**
	 * Método de inserção de livros
	 */
	public boolean addNewBookRoutine(Livro livro, int quantCopias) {
		//verifica se o livro inserido possui todos os campos válidos
		if (livro.validate() && quantCopias > 0) {
			for (int quantLivros = 0; quantLivros < quantCopias; quantLivros++) {
				Livro copy = livro.copy();
				if(!addBook(copy)) {
					estanteRepository.addEstante();
					addBook(copy);
				}
				livroRepository.save(copy);
			}
		} else {
			return false;
		}
		return true; 
	}
	
	//TODO: addLivro como método no estanteRepository ou prateleiraRepository
	public boolean addBook(Livro livro) {
		Prateleira p = estanteRepository.getPrateleiraWithEmptySpace();
		if (p == null)
			return false;
		else {
			livro.setPrateleira(p);
			return p.addLivro(livro);
		}
	}
	
	public int returnBookCount(String iSBN) {
		return regLivrosRepository.returnBookCount(iSBN);
	}
	
	public int returnAvailableBookCount(String iSBN) {
		return regLivrosRepository.returnAvailableBookCount(iSBN);
	}
	
	public List<Livro> findSimilarBooks(Livro l){
		return livroRepository.findBySimilarExample(l,false);
	}
	
	public boolean editBook(String iSBNOriginal, Livro livro, int quantCopias) {
		//caso o número de cópias final seja menor que o disponível, os livros que:
		int quantNoAcervo = regLivrosRepository.returnBookCount(iSBNOriginal);
		if(!regLivrosRepository.havingOnlyThisAmountOfCopiesWontCauseProblems(iSBNOriginal, quantCopias))
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
		if(!regLivrosRepository.allTheBooksAreAvailable(iSBNOriginal) && delete == 0)
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
		if(!clienteRepository.thisCpfAlreadyExists(cliente.getCpf()))
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
	public boolean updateClient(String cpf, Cliente cliente) {
		if(!clienteRepository.thisCpfAlreadyExists(cliente.getCpf()) 
			|| (cliente.getCpf().equals(cpf) && clienteRepository.thisCpfAlreadyExists(cliente.getCpf()))) {
			clienteRepository.update(cliente);
			return true;
		}
		return false;
	}
	
	@Deprecated
	/*
	 * Método de edição do cliente
	 */
	public boolean editClient(String cpf, Cliente cliente) {
		if(!clienteRepository.thisCpfAlreadyExists(cliente.getCpf()) 
			|| (cliente.getCpf().equals(cpf) && clienteRepository.thisCpfAlreadyExists(cliente.getCpf()))) {
			clienteRepository.edit(cpf, cliente.copy());
			return true;
		}
		return false;
	}
	
	/*
	 * Método de exclusão do cliente
	 */
	public void deleteClient(Cliente cliente) {
		clienteRepository.delete(cliente);
	}
	
	/*
	 * Método de exclusão do cliente
	 */
	@Deprecated
	public void deleteClient(String cpf) {
		clienteRepository.delete(cpf);
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
	
	public int retirada(Livro livro, Cliente cliente) {
		if (livro.validate()) {
			if (cliente.validate()) {
				livro.setRetirado(true);
				return livroRetiradoRepository.save(livro, cliente) ? 0 : 1;
				// 0 = sucesso | 1 = erro de quantidade insuficiente
			} else return 3; //erro cliente não preenchido
		} else return 2; //erro livro não preenchido
	}
	
	public LivroRetirado findLivroRetirado(Livro livro, Cliente cliente) {
		return livroRetiradoRepository.findLivroRetirado(livro, cliente);
	}
	
	public List<LivroRetirado> findSimilarLivroRetirado(Livro livro, Cliente cliente) {
		return livroRetiradoRepository.findSimilarLivroRetirado(livro, cliente);
	}
	
	public int estenderRetirada(LivroRetirado livroRetirado) {
		if (livroRetirado.getTotalRenovacoes() < 3) {
			LocalDateTime dataUltimaMovimentacao = (livroRetirado.getTotalRenovacoes() == 0 
					? livroRetirado.getRetirada().getDataRaw() 
					: livroRetirado.getLastRenovacao().getDataRaw());
			if(dataUltimaMovimentacao.plusDays(3).isBefore(LocalDateTime.now())) { 
				livroRetiradoRepository.estenderRetirada(livroRetirado);
				return 0; //sucesso
			}else return dataUltimaMovimentacao.plusDays(3).getDayOfYear()-LocalDateTime.now().getDayOfYear(); //erro n- renovação muito cedo
		}else return 1; //erro 1- limite de renovações ultrapassado
	}
	
	public int devolucao(LivroRetirado livroRetirado) {
		return livroRetiradoRepository.devolver(livroRetirado);
	}
	
	public Cliente findClientWithThisCardCode(String code) {
		return clienteRepository.findClientWithThisCardCode(code);
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
