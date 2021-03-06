package com.systemhaus.demo;

import com.systemhaus.demo.domain.Login;
import com.systemhaus.demo.domain.LoginRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.systemhaus.demo.dao.EstanteDAO;
import com.systemhaus.demo.dao.LivroDAO;
import com.systemhaus.demo.dao.ClienteDAO;
import com.systemhaus.demo.dao.LivroRetiradoDAO;
import com.systemhaus.demo.dao.LoginDAO;
import com.systemhaus.demo.dao.RegLivrosDAO;
import com.systemhaus.demo.domain.EstanteRepository;
import com.systemhaus.demo.domain.LivroRetiradoRepository;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRepository;
import com.systemhaus.demo.domain.LivroRetirado;
import com.systemhaus.demo.domain.RegLivrosRepository;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.ClienteRepository;

public class Server {

	private EstanteRepository estanteRepository;
	private LivroRepository livroRepository;
	private RegLivrosRepository regLivrosRepository;
	private ClienteRepository clienteRepository;
	private LivroRetiradoRepository livroRetiradoRepository;
	private LoginRepository loginRepository;
	private MainWindow mainWindow; 
	private Login login = new Login();
	
	public Server () {
		this.estanteRepository = new EstanteDAO();
		this.livroRepository = new LivroDAO();
		this.regLivrosRepository = new RegLivrosDAO();
		this.clienteRepository = new ClienteDAO();
		this.livroRetiradoRepository = new LivroRetiradoDAO();
		this.loginRepository = new LoginDAO();
	}
	
	public Server(EstanteRepository estanteRepository, LivroRepository livroRepository, 
			ClienteRepository clienteRepository, LivroRetiradoRepository livroRetiradoRepository,
			RegLivrosRepository regLivrosRepository, LoginRepository loginRepository) {
		this.estanteRepository = estanteRepository;
		this.livroRepository = livroRepository;
		this.clienteRepository = clienteRepository;
		this.livroRetiradoRepository = livroRetiradoRepository;
		this.regLivrosRepository = regLivrosRepository;
		this.loginRepository = loginRepository;
	}
	
	public void setClienteRepository(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	public void setEstanteRepository(EstanteRepository estanteRepository) {
		this.estanteRepository = estanteRepository;
	}
	
	public void setLivroRepository(LivroRepository livroRepository) {
		this.livroRepository = livroRepository;
	}
	
	public void setLivroRetiradoRepository(LivroRetiradoRepository livroRetiradoRepository) {
		this.livroRetiradoRepository = livroRetiradoRepository;
	}
	
	public void setRegLivrosRepository(RegLivrosRepository regLivrosRepository) {
		this.regLivrosRepository = regLivrosRepository;
	}
	
	public void serLoginRepository(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}
	
	/*
	 * PARTE 1
	 * Métodos relacionados à Inserção de Livros
	 */
	
	/**
	 * Método de inserção de livros
	 */
	@Transactional
	public boolean addNewBookRoutine(Livro livro, int quantCopias) {
		//verifica se o livro inserido possui todos os campos válidos
		if (livro.validate() && quantCopias > 0) {
			for (int quantLivros = 0; quantLivros < quantCopias; quantLivros++) {
				Livro copy = livro.copy();
				if(!estanteRepository.addBook(copy)) {
					estanteRepository.addEstante();
					estanteRepository.addBook(copy);
				}
				livroRepository.save(copy);
				regLivrosRepository.addDisponivel(copy.getISBN());
			}
		} else {
			return false;
		}
		return true; 
	}
	
	
	public int returnBookCount(String iSBN) {
		return regLivrosRepository.returnBookCount(iSBN);
	}
	
	public int returnAvailableBookCount(String iSBN) {
		return regLivrosRepository.returnAvailableBookCount(iSBN);
	}
	
	public List<Livro> findSimilarBooks(Livro l){
		return livroRepository.findBySimilarExample(l,false, false);
	}
	
	public List<Livro> findTakenBooks(Livro l){
		return livroRepository.findBySimilarExample(l,true, true);
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
		livroRepository.update(livro);
		return true;
	}
	
	public boolean deleteBook(String iSBNOriginal, int delete) {
		//primeiramente verificando se podemos deletar todos os livros (se não há nenhum retirado)
		if(!regLivrosRepository.allTheBooksAreAvailable(iSBNOriginal) && delete == 0)
			return false; 
		
		if(delete == 0) {
			//remoção de todos os livros com os dados pesquisados
			regLivrosRepository.deleteThisRegLivros(regLivrosRepository.findRegLivrosForThis(iSBNOriginal));
			livroRepository.deleteAllTheseBooks(iSBNOriginal);
		}else {
			//remoção dos livros (no modo de edição de livro, removendo cópias que não tenham sido retiradas)
			regLivrosRepository.remDisponivel(iSBNOriginal, delete);
			livroRepository.deleteOnlyTheseBooks(iSBNOriginal, delete);
		}
		
		return true;
	}
	
	/*
	 * Método que retorna a quantidade de estantes na biblioteca
	 */
	public long getCountOfEstantes() {
		return estanteRepository.getCountOfEstantes();
	}
	
	public Livro initializeLivro(Livro livro) {
		return livroRepository.initializeLivro(livro);
	}
	
	public void generateLivroReport() {
		livroRepository.generateLivroReport();
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
			if(!clienteRepository.thereAreTooManySimilarAddresses(cliente.getEndereco())) {
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
		if((!clienteRepository.thisCpfAlreadyExists(cliente.getCpf()) && cliente.validate()) 
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
	public boolean deleteClient(Cliente cliente) {
		if(livroRetiradoRepository.findSimilarLivroRetirado(new Livro(), cliente).size() == 0) {
			clienteRepository.delete(cliente);
			return true;
		} else
			return false;
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
	
	public void generateClienteReport() {
		clienteRepository.generateClienteReport();
	}
	
	/*
	 * PARTE 3
	 * Métodos relacionados às transações
	 */
	
	public List<Livro> findAvailableBooks(Livro l){
		return livroRepository.findBySimilarExample(l,true, false);
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
	
	public List<LivroRetirado> findSimilarLivroRetirado(Livro livro, Cliente cliente) {
		return livroRetiradoRepository.findSimilarLivroRetirado(livro, cliente);
	}
	
	public int estenderRetirada(LivroRetirado livroRetirado) {
		if (livroRetirado.getTotalRenovacoes() < 3 && livroRetirado.getDataDevolucaoAsLocalDate().isBefore(LocalDate.now())) {
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
	
	public void generateLivroRetiradoReport() {
		livroRetiradoRepository.generateLivroRetiradoPendenteReport();
	}
	
	public void generateLivroRetiradoHistoricoReport() {
		livroRetiradoRepository.generateLivroRetiradoHistoricoReport();;
	}
	
	public void generateLivroRetiradoAtrasadoReport() {
		livroRetiradoRepository.generateLivroRetiradoAtrasadoReport();;
	}
	
	/*
	 * PARTE 4
	 * Métodos de Login
	 */
	
	/*
	 * Só frisando, NÃO SE USA PASSWORD COMO STRING PURA SOLTA NO CÓDIGO
	 * PELAMORDEDEUS USE CRIPTOGRAFIA E UM MÉTODO SEGURO DE LOGIN
	 */
	public Boolean logIn(String user, String pass) {
		login.setUser(user); login.setPass(pass);
		login.setTipoAcesso(loginRepository.logIn(login));
		if (null != login.getTipoAcesso()) {
			mainWindow.setEnabled(true);
			login.setLogged(true);
			return true;
		} else
			return false;
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

	protected void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	public Boolean isUserLoggedIn() {
		return this.login.isLogged();
	}
	
	public int getUserAccessLevel() {
		return this.login.getTipoAcesso().getNivelAcesso();
	}
}
