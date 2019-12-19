package com.systemhaus.demo.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.systemhaus.demo.domain.Estante;
import com.systemhaus.demo.domain.EstanteRepository;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.Prateleira;

public class EstanteDAO implements EstanteRepository {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Prateleira getPrateleiraWithEmptySpace() {
		Session session = sessionFactory.getCurrentSession();
		
		Prateleira p = null;
		
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
		
		return p;
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
	
	@Transactional(readOnly = true)
	@Override
	public void addEstante() {
		Session session = sessionFactory.getCurrentSession();
		
		Estante e = new Estante();
		e.initializeEstante();
		session.save(e);
	}
	
	/*
	 * read from database and count estantes
	 */
	@Transactional(readOnly = true)
	@Override
	public long getCountOfEstantes() {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("select count(e.id) from Estante e");
		
		long l = (long)query.list().get(0);
		
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
