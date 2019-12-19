package com.systemhaus.demo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.Evento;
import com.systemhaus.demo.domain.LivroRetiradoRepository;
import com.systemhaus.demo.domain.RegLivros;
import com.systemhaus.demo.domain.TipoEvento;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRetirado;

public class LivroRetiradoDAO implements LivroRetiradoRepository{

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	@Override
	public boolean save(Livro livro, Cliente cliente) {
		Session session = sessionFactory.getCurrentSession();
		
		if (this.addRetirado(livro.getISBN())) {
			LivroRetirado livroRetirado = new LivroRetirado(livro, cliente);
			livroRetirado.addRetirada(new Evento(TipoEvento.RETIRADA, livroRetirado));

			session.save(livroRetirado.getRetirada());
			session.save(livroRetirado);
			
			return true;
		}else {
			return false;
		}
		
	}
	
	@Transactional
	@Override
	public void estenderRetirada(LivroRetirado livroRetirado) {
		Session session = sessionFactory.getCurrentSession();
		
		livroRetirado.estenderRetirada(new Evento(TipoEvento.RENOVACAO,livroRetirado));
		
		session.save(livroRetirado.getLastRenovacao());
		session.update(livroRetirado);
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<LivroRetirado> findSimilarLivroRetirado(Livro livro, Cliente cliente) {
		Session session = sessionFactory.getCurrentSession();
		
		/* alterar query para verificar quais parâmetros serão utilizados, 
		 * SE AMBOS o livro E o cliente foram informados
		 * SE SOMENTE o LIVRO foi informado
		 * SE SOMENTE o CLIENTE foi informado
		 **/ 
		
		Query query = session.createQuery(
				"select lr.id, lr.retirada.id, lr.dataDevolucao, lr.livro.id from LivroRetirado lr, Evento e"
				+ " where lr.livro.retirado = true and lr.retirada.id = e.id and e.tipoEvento != " + TipoEvento.DEVOLUCAO.ordinal()
				+ (livro.validate() ? " and lr.livro.ISBN = \'" + livro.getISBN() + "\'" : "")
				+ (cliente.validate() ? " and lr.cliente.id = " + cliente.getId() : ""));
	
		List<LivroRetirado> livrosRetirados = new ArrayList<LivroRetirado>();
		
		//TODO: fazer revisões sobre o IF para a inclusão
		List<Object[]> result = query.list();
		for (Object[] obj : result) {
			Evento retirada = (Evento) session.get(Evento.class, (int)obj[1]);
			livro.setId((int)obj[3]);
			LivroRetirado livroR = new LivroRetirado(livro, cliente, retirada);
			livroR.setDataDevolucao((Date)obj[2]);
			livroR.setId((int)obj[0]);
			
			Query renQuery = session.createQuery(
					"from Evento where livroRetirado = " + livroR.getId() + " and tipoEvento = " + TipoEvento.RENOVACAO.ordinal());
			
			if(renQuery.list().size() != 0) {
				livroR.setRenovacoes(renQuery.list());
			}
			
			livrosRetirados.add(livroR);
		}
		
		return livrosRetirados;
	}
	
	@Transactional
	@Override
	public int devolver(LivroRetirado livroRetirado) {
		Session session = sessionFactory.getCurrentSession();
		
		if(livroRetirado.getDevolucao() == null) {
			this.remRetirado(livroRetirado.getLivro().getISBN());
			livroRetirado.getLivro().setRetirado(false);
			livroRetirado.setDevolucao(new Evento(TipoEvento.DEVOLUCAO, livroRetirado));
			
			session.save(livroRetirado.getDevolucao());
			session.update(livroRetirado);
			
			return 0;
		}else {
			return 1;
		}
	}
	
	/*
	 * Remove um livro que estava retirado e foi devolvido do catálogo
	 */
	@Transactional
	public boolean remRetirado(String isbn) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from RegLivros where isbn = \'" + isbn+ "\'");
		
		List<RegLivros> list = query.list();
		
		if (list.size() > 0 && list.get(0).getQuantLivrosParaRetirar() + 1 <= list.get(0).getQuantLivrosNoCatalogo()){
			list.get(0).setQuantLivrosParaRetirar(list.get(0).getQuantLivrosParaRetirar()+1);
			session.update(list.get(0));
			
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Adiciona um exemplar que estava disponível e foi retirado
	 */
	@Transactional
	public boolean addRetirado(String isbn) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from RegLivros where isbn = \'" + isbn+ "\'");
		
		List<RegLivros> list = query.list();
		
		if (list.size() > 0 && list.get(0).getQuantLivrosParaRetirar() - 1 >= 0){
			list.get(0).setQuantLivrosParaRetirar(list.get(0).getQuantLivrosParaRetirar()-1);
			session.update(list.get(0));
			
			return true;
		} else {
			return false;
		}
	}
}
