package com.systemhaus.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRetirado;

public class ServerTest {
	
	@Test
	public void testAddBook() {
		Server s = new Server();
		boolean result = s.addBook(new Livro("9780123456789", "Livro", "Agatha Cristie", "LP&M", 1, 250, false));
		assertTrue(result);
	}
	
	@Test
	public void testErrorAddingBookNoEmptySpaceAvailable() {
		Server s = new Server();
		for(int i = 0; i <= 100; i++) {
			s.addBook(new Livro("9780123456789", "Livro", "Agatha Cristie", "LP&M", 1, 250, false));
		}
		boolean result = s.addBook(new Livro("9780123456789", "Livro", "Agatha Cristie", "LP&M", 1, 250, false));
		assertFalse(result);
	}
	
	@Test
	public void testAddNewBookRoutine() {
		Server s = new Server();
		boolean result = s.addNewBookRoutine(new Livro("9780123456789", "Livro", "Agatha Cristie", "LP&M", 1, 250, false), 1);
		assertTrue(result);
	}
	
	@Test
	public void testErrorAddingNewBookRoutineWithInvalidQuantity() {
		Server s = new Server();
		boolean result = s.addNewBookRoutine(new Livro("9780123456789", "Livro", "Agatha Cristie", "LP&M", 1, 250, false), 0);
		assertFalse(result);
	}
	
	@Test
	public void testAddingNewBookRoutineToCreateANewEstate() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", "Livro", "Agatha Cristie", "LP&M", 1, 250, false), 100);
		s.addNewBookRoutine(new Livro("9780123456789", "Livro", "Agatha Cristie", "LP&M", 1, 250, false), 1);
		assertEquals(2, s.getCountOfEstantes());
	}
	
	@Test
	public void testAddNewBookRoutineCheckAvailableQuantities() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", "Livro", "Agatha Cristie", "LP&M", 1, 250, false), 1);
		assertEquals(1, s.returnBookCount("9780123456789"));
	}
	
	@Test
	public void testFindSimilarBooks() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false), 10);
		s.addNewBookRoutine(new Livro("9780123456790", "Guerra de tronos: coroa espinhosa", "George Martinho", "Saraiva", 1, 250, false), 42);
		s.addNewBookRoutine(new Livro("9780123456791", "Príncipe dos Espinhos", "Agatha Marinho", "LP&M", 1, 250, false), 3);
		s.addNewBookRoutine(new Livro("9780123456792", "A arte da Guerra", "Xing crishong", "Saraiva", 1, 250, false), 12);
		s.addNewBookRoutine(new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false), 7);
		Livro l = new Livro();
		l.setTitulo("espinhos");
		List<Livro> list = s.findSimilarBooks(l);
		assertEquals(3, list.size());
	}
	
	@Test
	public void testErrorFindSimilarBooks() {
		Server s = new Server();
		initTestServer(s);
		Livro l = new Livro();
		l.setTitulo("Xing chang chong");
		List<Livro> list = s.findSimilarBooks(l);
		assertEquals(0, list.size());
	}
	
	@Test
	public void testEditBook() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", "Livro", "Agatha Cristie", "LP&M", 1, 250, false), 2);
		s.addNewBookRoutine(new Livro("9780123456789", "Livro", "Agatha Cristie", "LP&M", 1, 250, true), 8);
		boolean result = s.editBook("9780123456789", new Livro("9780123456788", "Book", "Agatha Cristie", "LP&M", 1, 250, false), 8);
		assertTrue(result);
	}
	
	@Test
	public void testEditBookWithMoreCopiesThanExpected() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", "Livro", "Agatha Cristie", "LP&M", 1, 250, false), 2);
		s.addNewBookRoutine(new Livro("9780123456789", "Livro", "Agatha Cristie", "LP&M", 1, 250, true), 8);
		s.editBook("9780123456789", new Livro("9780123456789", "Book", "Agatha Cristie", "LP&M", 1, 250, false), 20);
		assertEquals(20, s.returnBookCount("9780123456789"));
	}
	
	@Test
	public void testErrorEditBookWithLessCopiesThanExpected() {
		Server s = new Server();
		this.initTestServer(s);
		s.retirada(new Livro("9780123456791", "Príncipe dos Espinhos", "Agatha Marinho", "LP&M", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456791", "Príncipe dos Espinhos", "Agatha Marinho", "LP&M", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		Livro l = new Livro();
		l.setTitulo("Espinhos");
		boolean result = s.editBook("9780123456791", s.findSimilarBooks(l).get(0), 1);
		assertFalse(result);
	}
	
	@Test
	public void testDeleteBook() {
		Server s = new Server();
		initTestServer(s);
		boolean result = s.deleteBook("9780123456790", 20);
		assertTrue(result);
	}
	
	@Test
	public void testErrorDeleteBookFail() {
		Server s = new Server();
		initTestServer(s);
		s.retirada(new Livro("9780123456791", "Príncipe dos Espinhos", "Agatha Marinho", "LP&M", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		boolean result = s.deleteBook("9780123456791", 0);
		assertFalse(result);
	}
	
	@Test
	public void testOrganizeLibrary() {
		Server s = new Server();
		initTestServer(s);
		boolean result1 = s.needsReorganization();
		s.deleteBook("9780123456790", 0);
		boolean result2 = s.needsReorganization();
		assertEquals(result1, result2);
	}
	
	@Test
	public void testAddClient() {
		Server s = new Server();
		int result = s.addCliente(new Cliente("Leonardo","02789345274","5551999999999","Porto Alegre","Higienópolis","Marechal Floriano",925,"4000979800448877",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
		assertEquals(0,result);
	}
	
	@Test
	public void testErrorAddClient() {
		Server s = new Server();
		initTestServer(s);
		int result = s.addCliente(new Cliente("Leonardo","02789345274","5551997359979","Porto Alegre","Higienópolis","Marechal Floriano",925,"4000979800448877",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
		assertEquals(0,result);
	}
	
	@Test
	public void testFindSimilarClients() {
		Server s = new Server();
		initTestServer(s);
		Cliente c = new Cliente();
		c.setBairro("Higi");
		List<Cliente> list = s.findSimilarClients(c);
		assertEquals(2, list.size());
	}
	
	@Test
	public void testErrorFindSimilarClients() {
		Server s = new Server();
		initTestServer(s);
		Cliente c = new Cliente();
		c.setCidade("Porta");
		List<Cliente> list = s.findSimilarClients(c);
		assertEquals(0, list.size());
	}
	
	@Test
	public void testEditClient() {
		Server s = new Server();
		initTestServer(s);
		Cliente c = new Cliente();
		c.setCpf("02789345280");
		List<Cliente> list = s.findSimilarClients(c);
		Cliente beforeEdit = list.get(0);
		s.editClient("02789345280",new Cliente("Leo Pellegrini","02789345280","5551999999999","Porto Alegre","Higienópolis","Marechal Floriano",925,"4000979800448882",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
		list = s.findSimilarClients(c);
		Cliente afterEdit = list.get(0);
		assertEquals(beforeEdit.getNome(),afterEdit.getNome());
	}
	
	@Test
	public void testErrorEditClientWithExistingCPF() {
		Server s = new Server();
		initTestServer(s);
		Cliente c = new Cliente();
		c.setNome("Leonardo");
		List<Cliente> list = s.findSimilarClients(c);
		Cliente beforeEdit = list.get(0);
		s.editClient("02789345280",new Cliente("Pellegrini","02789345281","5551999999999","Porto Alegre","Higienópolis","Marechal Floriano",925,"4000979800448882",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
		list = s.findSimilarClients(c);
		Cliente afterEdit = list.get(0);
		assertEquals(beforeEdit.getCpf(),afterEdit.getCpf());
	}
	
	@Test
	public void testErrorEditClientWithInvalidData() {
		Server s = new Server();
		initTestServer(s);
		Cliente c = new Cliente();
		c.setNome("Leonardo");
		List<Cliente> list = s.findSimilarClients(c);
		Cliente beforeEdit = list.get(0);
		s.editClient("02789345280",new Cliente("Leonardo","0278934527","555199999999","Porto Alegre","Higienópolis","Marechal Floriano",925,"4000979800448882",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
		list = s.findSimilarClients(c);
		Cliente afterEdit = list.get(0);
		assertEquals(beforeEdit.getTelefone(),afterEdit.getTelefone());
	}
	
	@Test
	public void testDeleteClient() {
		Server s = new Server();
		initTestServer(s);
		Cliente c = new Cliente();
		c.setCpf("02789345280");
		List<Cliente> list = s.findSimilarClients(c);
		int sizeBeforeDelete = list.size();
		s.deleteClient("02789345280");
		list = s.findSimilarClients(c);
		int sizeAfterDelete = list.size();
		assertNotEquals(sizeBeforeDelete,sizeAfterDelete);
	}
	
	/*
	 * Inicialização dos dados para testes de retirada
	 */
	public void initTestServer(Server s) {
		s.addNewBookRoutine(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false), 1);
		s.addNewBookRoutine(new Livro("9780123456790", "Guerra de tronos: coroa espinhosa", "George Martinho", "Saraiva", 1, 250, false), 42);
		s.addNewBookRoutine(new Livro("9780123456791", "Príncipe dos Espinhos", "Agatha Marinho", "LP&M", 1, 250, false), 3);
		s.addNewBookRoutine(new Livro("9780123456792", "A arte da Guerra", "Xing crishong", "Saraiva", 1, 250, false), 12);
		s.addNewBookRoutine(new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false), 7);
		s.addCliente(new Cliente("Leonardo","02789345274","5551999999999","Rio Pardo","Higienópolis","Almirante Alexandrino",281,"4000979800448877",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
		s.addCliente(new Cliente("Pellegrini","02789345280","5551999999998","Porto Alegre","Higienópolis","Marechal Floriano",925,"4000979800448882",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
		s.addCliente(new Cliente("Eduarda","02789345281","5551999999997","Santa Cruz","Centro","Governador Pinheiro",856,"4000979800448876",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
	}
	
	@Test
	public void testRetiradaDeLivro() {
		Server s = new Server();
		initTestServer(s);
		Cliente c = new Cliente();
		c.setNome("Leonardo");
		assertEquals(0, s.retirada(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false), s.findSimilarClients(c).get(0)));
	}
	
	@Test
	public void testErrorRetiradaDeLivroNotEnoughCopies() {
		Server s = new Server();
		initTestServer(s);
		s.retirada(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		assertEquals(1,s.retirada(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false), s.findClientWithThisCardCode("4000979800448876")));
	}
	
	@Test
	public void testPesquisaDeRetiradaDeLivro() {
		Server s = new Server();
		initTestServer(s);
		s.retirada(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456792", "A arte da Guerra", "Xing crishong", "Saraiva", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false), s.findClientWithThisCardCode("4000979800448876"));
		Livro livro = new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false);
		assertNotEquals(null,s.findLivroRetirado(s.findSimilarBooks(livro).get(0), s.findClientWithThisCardCode("4000979800448876")));
	}
	
	@Test
	public void testErrorPesquisaDeRetiradaDeLivroNotFound() {
		Server s = new Server();
		initTestServer(s); //not found
		s.retirada(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456792", "A arte da Guerra", "Xing crishong", "Saraiva", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false), s.findClientWithThisCardCode("4000979800448876"));
		assertEquals(0,s.findSimilarLivroRetirado(new Livro(), s.findClientWithThisCardCode("4000979800448882")).size());
	}
	
	@Test
	public void testRenovacaoDeRetiradaDeLivro() {
		Server s = new Server();
		initTestServer(s);
		s.retirada(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456792", "A arte da Guerra", "Xing crishong", "Saraiva", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false), s.findClientWithThisCardCode("4000979800448876"));
		Livro livro = new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false);
		List<LivroRetirado> l = s.findSimilarLivroRetirado(s.findSimilarBooks(livro).get(0), s.findClientWithThisCardCode("4000979800448876"));
		LivroRetirado lr = l.get(0);
		lr.getRetirada().setData(Date.from(LocalDateTime.now().minusDays(10).atZone(ZoneId.systemDefault()).toInstant()));
		assertEquals(0,s.estenderRetirada(lr));
	}
	
	@Test
	public void testErrorRenovacaoDeRetiradaDeLivroNotEnoughTimeElapsed() {
		Server s = new Server();
		initTestServer(s);
		s.retirada(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456792", "A arte da Guerra", "Xing crishong", "Saraiva", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false), s.findClientWithThisCardCode("4000979800448876"));
		Livro livro = new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false);
		List<LivroRetirado> l = s.findSimilarLivroRetirado(s.findSimilarBooks(livro).get(0), s.findClientWithThisCardCode("4000979800448876"));
		s.estenderRetirada(l.get(0));
		assertEquals(3,s.estenderRetirada(l.get(0)));
	}
	
	@Test
	public void testErrorRenovacaoDeRetiradaDeLivroTooManyRenewals() {
		Server s = new Server();
		initTestServer(s);
		s.retirada(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456792", "A arte da Guerra", "Xing crishong", "Saraiva", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false), s.findClientWithThisCardCode("4000979800448876"));
		Livro livro = new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false);
		List<LivroRetirado> l = s.findSimilarLivroRetirado(s.findSimilarBooks(livro).get(0), s.findClientWithThisCardCode("4000979800448876"));
		LivroRetirado lr = l.get(0);
		lr.getRetirada().setData(Date.from(LocalDateTime.now().minusDays(30).atZone(ZoneId.systemDefault()).toInstant()));
		s.estenderRetirada(lr);
		lr.getLastRenovacao().setData(Date.from(LocalDateTime.now().minusDays(20).atZone(ZoneId.systemDefault()).toInstant()));
		s.estenderRetirada(lr);
		lr.getLastRenovacao().setData(Date.from(LocalDateTime.now().minusDays(10).atZone(ZoneId.systemDefault()).toInstant()));
		s.estenderRetirada(lr);
		assertEquals(1,s.estenderRetirada(lr));
	}
	
	@Test
	public void testDevolucaoDeLivro() {
		Server s = new Server();
		initTestServer(s);
		s.retirada(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456792", "A arte da Guerra", "Xing crishong", "Saraiva", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false), s.findClientWithThisCardCode("4000979800448876"));
		Livro livro = new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false);
		List<LivroRetirado> l = s.findSimilarLivroRetirado(s.findSimilarBooks(livro).get(0), s.findClientWithThisCardCode("4000979800448876"));
		LivroRetirado lr = l.get(0);
		assertEquals(0,s.devolucao(lr));
	}
	
	@Test
	public void testErrorDevolucaoDeLivroAlreadyReturned() {
		Server s = new Server();
		initTestServer(s);
		s.retirada(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456792", "A arte da Guerra", "Xing crishong", "Saraiva", 1, 250, false), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false), s.findClientWithThisCardCode("4000979800448876"));
		Livro livro = new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false);
		List<LivroRetirado> l = s.findSimilarLivroRetirado(s.findSimilarBooks(livro).get(0), s.findClientWithThisCardCode("4000979800448876"));
		LivroRetirado lr = l.get(0);
		s.devolucao(lr);
		assertEquals(1, s.devolucao(lr));
	}
	
}
