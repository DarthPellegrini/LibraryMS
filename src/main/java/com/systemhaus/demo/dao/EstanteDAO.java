package com.systemhaus.demo.dao;

import java.util.List;
import java.util.ListIterator;

import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.systemhaus.demo.SessionUtil;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Estante;
import com.systemhaus.demo.domain.EstanteRepository;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.Prateleira;

public class EstanteDAO implements EstanteRepository {

	//TODO: remover bibliotecado banco
	private int bibliotecaID;
	
	@Override
	public Prateleira getPrateleiraWithEmptySpace() {
		Session session = SessionUtil.getInstance().getSession();
		
		Prateleira p = null;
		
		// Not working
		Query query = session.createQuery("select p.id, count(l) from Prateleira p left join p.livros l group by p.id");
		List<Object[]> result = query.list();
		for (Object[] obj : result) {
			if((long)obj[1] < Prateleira.getSize()) {
				int prateleiraID = (int) obj[0];
				p = (Prateleira) session.get(Prateleira.class, prateleiraID);
				session.close();
				return p;
			}
		}
		
		session.close();
		return p;
	}

	public void initializeLibrary() {
		Session session = SessionUtil.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		Biblioteca biblioteca = new Biblioteca();
		
		Query query = session.createQuery("select id from Biblioteca");
		if (query.list().size() == 0) {
			biblioteca.getEstantes().add(new Estante(biblioteca));
			session.save(biblioteca);
		} else {
			int x = (int)query.list().get(0);
			biblioteca.setId((int)query.list().get(0));
		}
			
		tx.commit();
		bibliotecaID = biblioteca.getId();
		session.close();
	}

	@Override
	public boolean addBook(Livro livro) {
		Prateleira p = this.getPrateleiraWithEmptySpace();
		if (p == null)
			return false;
		else {
			livro.setPrateleira(p);
			return p.addLivro(livro);
		}
	}
	
	@Override
	public void addEstante() {
		Session session = SessionUtil.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		Biblioteca biblioteca = new Biblioteca();
		
		Estante e = new Estante(biblioteca);
		biblioteca.getEstantes().add(e);
		session.update(biblioteca);
		
		tx.commit();
		session.close();
	}
	
	/*
	 * read from database and count estantes
	 */
	@Override
	public long getCountOfEstantes() {
		Session session = SessionUtil.getInstance().getSession();
		
		Query query = session.createQuery("select count(e.id) from Estante e");
		
		long l = (long)query.list().get(0);
		
		session.close();
		return l;
	}
	
	@Deprecated
	@Override
	public boolean needsReorganization() {
		//Deprecated
		return false;
	}

	@Deprecated
	@Override
	public void organizeLibrary() {
		//Deprecated
	}
	
}
