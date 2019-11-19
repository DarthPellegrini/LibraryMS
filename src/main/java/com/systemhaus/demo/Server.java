package com.systemhaus.demo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.systemhaus.demo.dao.memory.LivroDAO;
import com.systemhaus.demo.dao.memory.ClienteDAO;
import com.systemhaus.demo.dao.memory.EnderecoDAO;
import com.systemhaus.demo.dao.memory.EstanteDAO;
import com.systemhaus.demo.dao.memory.LivroRetiradoDAO;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Cartao;
import com.systemhaus.demo.domain.EstanteRepository;
import com.systemhaus.demo.domain.LivroRetiradoRepository;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRepository;
import com.systemhaus.demo.domain.LivroRetirado;
import com.systemhaus.demo.domain.Prateleira;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.ClienteRepository;
import com.systemhaus.demo.domain.EnderecoRepository;

public class Server {

	private EstanteRepository estanteRepository;
	private LivroRepository livroRepository;
	private ClienteRepository clienteRepository;
	private EnderecoRepository enderecoRepository;
	private LivroRetiradoRepository livroRetiradoRepository;
	
	public Server () {
		Biblioteca biblioteca = new Biblioteca();
		this.estanteRepository = new EstanteDAO(biblioteca);
		this.livroRepository = new LivroDAO(biblioteca);
		this.clienteRepository = new ClienteDAO(biblioteca);
		this.enderecoRepository = new EnderecoDAO(biblioteca);
		this.livroRetiradoRepository = new LivroRetiradoDAO(biblioteca);
		//facilitando os testes
		this.addNewBookRoutine(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false), 1);
		this.addNewBookRoutine(new Livro("9780123456790", "Guerra de tronos: coroa espinhosa", "George Martinho", "Saraiva", 1, 250, false), 42);
		this.addNewBookRoutine(new Livro("9780123456791", "Príncipe dos Espinhos", "Agatha Marinho", "LP&M", 1, 250, false), 3);
		this.addNewBookRoutine(new Livro("9780123456792", "A arte da Guerra", "Xing crishong", "Saraiva", 1, 250, false), 12);
		this.addNewBookRoutine(new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false), 7);
		this.addCliente(new Cliente("Leonardo","02789345274","5551999999999","Rio Pardo","Higienópolis","Almirante Alexandrino",281,"4000979800448877",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
		this.addCliente(new Cliente("Pellegrini","02789345280","5551999999998","Porto Alegre","Higienópolis","Marechal Floriano",925,"4000979800448882",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
		this.addCliente(new Cliente("Eduarda","02789345281","5551999999997","Santa Cruz","Centro","Governador Pinheiro",856,"4000979800448876",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
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
					livro.setRetirado(true);
					return livroRetiradoRepository.save(livro, cartao, "R") ? 0 : 1;
					// 0 = sucesso | 1 = erro de quantidade insuficiente
				} else return 4; //erro todos os exemplares retirados
			} else return 3; //erro cliente não preenchido
		} else return 2; //erro livro não preenchido
	}
	
	public LivroRetirado findLivroRetirado(Livro livro, Cliente cliente) {
		return livroRetiradoRepository.findLivroRetirado(livro, cliente.getCartao());
	}
	
	public List<LivroRetirado> findSimilarLivroRetirado(Livro livro, Cliente cliente) {
		return livroRetiradoRepository.findSimilarLivroRetirado(livro, cliente.getCartao());
	}
	
	public int devolucao(LivroRetirado livroRetirado) {
		return livroRetiradoRepository.devolver(livroRetirado, "D");
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
