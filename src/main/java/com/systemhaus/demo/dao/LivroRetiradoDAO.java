package com.systemhaus.demo.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<LivroRetirado> findSimilarLivroRetirado(Livro livro, Cliente cliente) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery(
				"from LivroRetirado lr join fetch lr.retirada join fetch lr.renovacoes join fetch lr.cliente cl join fetch lr.livro l join fetch l.prateleira p join fetch p.estante join fetch cl.cartao join fetch cl.endereco"
				+ " where lr.livro.retirado = true "
				+ " and cl.ativo = true and l.ativo = true"
				//+ " and lr.livro.id = l.id and lr.cliente.id = cl.id and cl.cartao.id = ca.id and cl.endereco.id = en.id "
				+ (livro.validate() ? " and lr.livro.ISBN = \'" + livro.getISBN() + "\'" : "")
				+ (cliente.validate() ? " and lr.cliente.id = " + cliente.getId() : ""));
	
		List<LivroRetirado> result = (List<LivroRetirado>)query.list();
		
		return result;
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
