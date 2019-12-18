package com.systemhaus.demo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.systemhaus.demo.SessionUtil;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.Evento;
import com.systemhaus.demo.domain.LivroRetiradoRepository;
import com.systemhaus.demo.domain.RegLivros;
import com.systemhaus.demo.domain.TipoEvento;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRetirado;

public class LivroRetiradoDAO implements LivroRetiradoRepository{

	Biblioteca biblioteca;
	
	public LivroRetiradoDAO(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}
	
	@Override
	public boolean save(Livro livro, Cliente cliente) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		
		if (this.addRetirado(livro.getISBN())) {
			LivroRetirado livroRetirado = new LivroRetirado(livro, cliente);
			livroRetirado.addRetirada(new Evento(TipoEvento.RETIRADA, livroRetirado));
			biblioteca.addLivroRetirado(livroRetirado);

			session.save(livroRetirado.getRetirada());
			session.saveOrUpdate(livroRetirado);
			
			tx.commit();
			session.close();
			return true;
		}else {
			tx.commit();
			session.close();
			return false;
		}
		
	}
	
	@Override
	public void estenderRetirada(LivroRetirado livroRetirado) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		
		livroRetirado.estenderRetirada(new Evento(TipoEvento.RENOVACAO,livroRetirado));
		
		session.save(livroRetirado.getLastRenovacao());
		session.update(livroRetirado);
		
		tx.commit();
		session.close();
	}
	
	@Override
	public List<LivroRetirado> findSimilarLivroRetirado(Livro livro, Cliente cliente) {
		Session session = SessionUtil.getInstance().getSession();
		
		/* alterar query para verificar quais parâmetros serão utilizados, 
		 * SE AMBOS o livro E o cliente foram informados
		 * SE SOMENTE o LIVRO foi informado
		 * SE SOMENTE o CLIENTE foi informado
		 **/ 
		
		TypedQuery<Object[]> query = session.createQuery(
				"select lr.id, lr.retirada.id, lr.dataDevolucao, lr.livro.id from LivroRetirado lr, Evento e"
				+ " where lr.livro.retirado = true and lr.retirada.id = e.id and e.tipoEvento != " + TipoEvento.DEVOLUCAO.ordinal()
				+ (livro.validate() ? " and lr.livro.ISBN = \'" + livro.getISBN() + "\'" : "")
				+ (cliente.validate() ? " and lr.cliente.id = " + cliente.getId() : ""));
	
		List<LivroRetirado> livrosRetirados = new ArrayList<>();
		
		//TODO: fazer revisões sobre o IF para a inclusão
		
		for (Object[] obj : query.getResultList()) {
			Evento retirada = session.get(Evento.class, (int)obj[1]);
			livro.setId((int)obj[3]);
			LivroRetirado livroR = new LivroRetirado(livro, cliente, retirada);
			livroR.setDataDevolucao((Date)obj[2]);
			livroR.setId((int)obj[0]);
			
			TypedQuery<Evento> renQuery = session.createQuery(
					"from Evento where livroRetirado = " + livroR.getId() + " and tipoEvento = " + TipoEvento.RENOVACAO.ordinal());
			
			if(renQuery.getResultList().size() != 0) {
				livroR.setRenovacoes(renQuery.getResultList());
			}
			
			livrosRetirados.add(livroR);
		}
		
		session.close();
		return livrosRetirados;
	}
	
	@Override
	public int devolver(LivroRetirado livroRetirado) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		
		if(livroRetirado.getDevolucao() == null) {
			this.remRetirado(livroRetirado.getLivro().getISBN());
			livroRetirado.getLivro().setRetirado(false);
			livroRetirado.setDevolucao(new Evento(TipoEvento.DEVOLUCAO, livroRetirado));
			
			session.save(livroRetirado.getDevolucao());
			session.update(livroRetirado);
			
			tx.commit();
			session.close();
			return 0;
		}else {
			tx.rollback();
			session.close();
			return 1;
		}
	}
	
	/*
	 * Remove um livro que estava retirado e foi devolvido do catálogo
	 */
	public boolean remRetirado(String isbn) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		
		TypedQuery<RegLivros> query = session.createQuery("from RegLivros where isbn = \'" + isbn+ "\'");
		
		List<RegLivros> list = query.getResultList();
		
		if (list.size() > 0 && list.get(0).getQuantLivrosParaRetirar() + 1 <= list.get(0).getQuantLivrosNoCatalogo()){
			list.get(0).setQuantLivrosParaRetirar(list.get(0).getQuantLivrosParaRetirar()+1);
			session.update(list.get(0));
			
			tx.commit();
			session.close();
			return true;
		} else {
			tx.rollback();
			session.close();
			return false;
		}
	}
	
	/*
	 * Adiciona um exemplar que estava disponível e foi retirado
	 */
	public boolean addRetirado(String isbn) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		
		TypedQuery<RegLivros> query = session.createQuery("from RegLivros where isbn = \'" + isbn+ "\'");
		
		List<RegLivros> list = query.getResultList();
		
		if (list.size() > 0 && list.get(0).getQuantLivrosParaRetirar() - 1 >= 0){
			list.get(0).setQuantLivrosParaRetirar(list.get(0).getQuantLivrosParaRetirar()-1);
			session.update(list.get(0));
			
			tx.commit();
			session.close();  
			return true;
		} else {
			tx.commit();
			session.close();
			return false;
		}
	}
}
