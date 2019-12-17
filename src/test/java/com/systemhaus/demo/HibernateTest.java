package com.systemhaus.demo;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;

import com.systemhaus.demo.dao.ClienteDAO;
import com.systemhaus.demo.dao.LivroDAO;
import com.systemhaus.demo.dao.LivroRetiradoDAO;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.ClienteRepository;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRepository;
import com.systemhaus.demo.domain.LivroRetirado;
import com.systemhaus.demo.domain.LivroRetiradoRepository;

public class HibernateTest {
	
	/*
	 * Inicialização dos dados para testes
	 */
	
	public void initTestServer(Server s) {
		s.addNewBookRoutine(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false), 1);
		s.addNewBookRoutine(new Livro("9780123456790", "Guerra de tronos: coroa espinhosa", "George Martinho", "Saraiva", 1, 250, false), 42);
		s.addNewBookRoutine(new Livro("9780123456791", "Príncipe dos Espinhos", "Agatha Marinho", "LP&M", 1, 250, false), 3);
		s.addNewBookRoutine(new Livro("9780123456792", "A arte da Guerra", "Xing crishong", "Saraiva", 1, 250, false), 12);
		s.addNewBookRoutine(new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false), 7);
		s.addClienteRoutine(new Cliente("Leonardo Pellegrini","02789345274","5551999999999","Rio Pardo","Higienópolis","Almirante Alexandrino",281,"4000979800448877",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
		s.addClienteRoutine(new Cliente("Pellegrini","02789345280","5551999999998","Porto Alegre","Higienópolis","Marechal Floriano",925,"4000979800448882",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
		s.addClienteRoutine(new Cliente("Eduarda","02789345281","5551999999997","Santa Cruz","Centro","Governador Pinheiro",856,"4000979800448876",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
	}
	
	public void initEnhancedTestServer(Server s) {
		this.initTestServer(s);
		s.retirada(s.findSimilarBooks(new Livro("9780123456789", "Mistério no trem", "Agatha Cristie", "LP&M", 1, 250, false)).get(0), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(s.findSimilarBooks(new Livro("9780123456792", "A arte da Guerra", "Xing crishong", "Saraiva", 1, 250, false)).get(0), s.findClientWithThisCardCode("4000979800448877"));
		s.retirada(s.findSimilarBooks(new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false)).get(0), s.findClientWithThisCardCode("4000979800448876"));
		List<LivroRetirado> l = s.findSimilarLivroRetirado(s.findSimilarBooks(new Livro("9780123456793", "Aranhas Espinhosas", "Anônimo", "Darkside", 1, 250, false)).get(0), s.findClientWithThisCardCode("4000979800448876"));
		LivroRetirado lr = l.get(0);
		lr.getRetirada().setData(Date.from(LocalDateTime.now().minusDays(30).atZone(ZoneId.systemDefault()).toInstant()));
		s.estenderRetirada(lr);
		lr.getLastRenovacao().setData(Date.from(LocalDateTime.now().minusDays(20).atZone(ZoneId.systemDefault()).toInstant()));
		s.estenderRetirada(lr);
		lr.getLastRenovacao().setData(Date.from(LocalDateTime.now().minusDays(10).atZone(ZoneId.systemDefault()).toInstant()));
		s.estenderRetirada(lr);
		s.devolucao(lr);
	}
	
	@Test
	public void testHibernate() {
		Session session = SessionUtil.getInstance().getSession();  
	    Transaction t = session.beginTransaction();  
	    
	    Biblioteca b = new Biblioteca();
	    Server s = new Server(b);
	    
	    t.commit();
	    session.close();
	}

//	@Test
//	public void testClienteDAO() {
//		ClienteRepository cr = new ClienteDAO();
//		cr.save(new Cliente("LeeL","02789345277","5551999999999","Rio Pardo","Higienópolis","Almirante Alexandrino",281,"4000979800448877",Date.from(LocalDate.now().plusYears(4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
//		Cliente c = new Cliente();
//		c.setCpf("02789345277");
//		Cliente cli = cr.findSimilarClients(c).get(0);
//		cli.setNome("Leozin");
//		cli.setCpf("02789345276");
//		cr.update(cli);
//		c.setCpf(cli.getCpf());
//		assertEquals(1,cr.findSimilarClients(c).size());
//	}
	
//	@Test
//	public void testDAO() {
//		Server s = new Server();
//		initTestServer(s);
//		s.addNewBookRoutine(new Livro("9780123456790", "Guerra de tronos: coroa espinhosa", "George Martinho", "Saraiva", 1, 250, false), 5);
//		Livro l = new Livro();
//		l.setISBN("9780123456790");
//		Livro ex = s.findSimilarBooks(l).get(0);
//	}
}
