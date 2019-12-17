package com.systemhaus.demo.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.systemhaus.demo.SessionUtil;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.RegLivros;
import com.systemhaus.demo.domain.RegLivrosRepository;

public class RegLivrosDAO implements RegLivrosRepository {
	
	Biblioteca biblioteca;
	
	public RegLivrosDAO(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}
	
	@Override
	public int returnBookCount(String iSBN) {
		Session session = SessionUtil.getInstance().getSession();
		
		TypedQuery<RegLivros> query = session.createQuery("from RegLivros where isbn = \'" + iSBN + "\'");
		
		List<RegLivros> list = query.getResultList();
		
		session.close();
		return (list.size() > 0) ? list.get(0).getQuantLivrosNoCatalogo() : 0;
	}
	
	@Override
	public int returnAvailableBookCount(String iSBN) {
		Session session = SessionUtil.getInstance().getSession();
		
		TypedQuery<RegLivros> query = session.createQuery("from RegLivros where isbn = \'" + iSBN + "\'");
		
		List<RegLivros> list = query.getResultList();
		
		session.close();
		return (list.size() > 0) ? list.get(0).getQuantLivrosParaRetirar() : 0;
	}
	
	@Override
	public boolean allTheBooksAreAvailable(String iSBN) {
		Session session = SessionUtil.getInstance().getSession();
		
		TypedQuery<RegLivros> query = session.createQuery("from RegLivros where isbn = \'" + iSBN + "\'");
		
		List<RegLivros> list = query.getResultList();
		
		session.close();
		return (list.size() > 0) ? (list.get(0).getQuantLivrosNoCatalogo()==list.get(0).getQuantLivrosParaRetirar()) : false;
	}
	
	@Override
	public boolean havingOnlyThisAmountOfCopiesWontCauseProblems(String iSBN, int quantCopias){
		Session session = SessionUtil.getInstance().getSession();
		
		TypedQuery<RegLivros> query = session.createQuery("from RegLivros where isbn = \'" + iSBN + "\'");
		
		List<RegLivros> list = query.getResultList();
		
		session.close();
		return (list.size() > 0) ? (quantCopias >= list.get(0).getMaxDeletionNumber()) : false;
	}
	
	/*
	 * Adiciona um livro no catálogo na biblioteca
	 */
	public void addDisponivel(String isbn) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		
		TypedQuery<RegLivros> query = session.createQuery("from RegLivros where isbn = \'" + isbn + "\'");
		
		List<RegLivros> list = query.getResultList();
		
		if (list.size() > 0){
			list.get(0).setQuantLivrosNoCatalogo(list.get(0).getQuantLivrosNoCatalogo()+1);
			list.get(0).setQuantLivrosParaRetirar(list.get(0).getQuantLivrosParaRetirar()+1);
			session.update(list.get(0));
		} else {
			TypedQuery<Biblioteca> newQuery = session.createQuery("from Biblioteca");
			
			RegLivros reg = new RegLivros(isbn,1, (Biblioteca)newQuery.getResultList().get(0));
			biblioteca.getRegLivros().add(reg);
			session.saveOrUpdate(reg);
		}
		
		tx.commit();
		session.close();
	}
	/*
	 * Remove livros do catálogo da biblioteca
	 */
	public void remDisponivel(String isbn, int quant) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		
		TypedQuery<RegLivros> query = session.createQuery("from RegLivros where isbn = \'" + isbn + "\'");
		
		List<RegLivros> list = query.getResultList();
		
		if (list.size() > 0 && list.get(0).getQuantLivrosParaRetirar() - 1 >= 0){
			list.get(0).setQuantLivrosNoCatalogo(list.get(0).getQuantLivrosNoCatalogo()-quant);
			list.get(0).setQuantLivrosParaRetirar(list.get(0).getQuantLivrosParaRetirar()-quant);
			session.update(list.get(0));
		}
		tx.commit();
		session.close();  
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
			tx.commit();
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
	
	@Override
	public void deleteThisRegLivros(RegLivros reg) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		
		biblioteca.getRegLivros().remove(reg);
		session.delete(reg);
			
		tx.commit();
		session.close();
	}
	
	@Override
	public RegLivros findRegLivrosForThis(String isbn) {
		Session session = SessionUtil.getInstance().getSession();
		
		TypedQuery<RegLivros> query = session.createQuery("from RegLivros where isbn = \'" + isbn + "\'");
		
		List<RegLivros> list = query.getResultList();
		
		session.close();
		return (list.size() > 0) ? list.get(0) : null;
	}
}
