package com.systemhaus.demo.dao;

import java.util.ListIterator;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.systemhaus.demo.SessionUtil;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Estante;
import com.systemhaus.demo.domain.EstanteRepository;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.Prateleira;

public class EstanteDAO implements EstanteRepository {

	private Biblioteca biblioteca;

	public EstanteDAO(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}

	/*
	 * Move to PrateleiraDAO ? Maybe not, need to search every prateleira of every estante
	 */
	@Override
	public Prateleira getPrateleiraWithEmptySpace() {
		Session session = SessionUtil.getInstance().getSession();
		
		Prateleira p = null;
		
		// Not working
		TypedQuery<Object[]> query = session.createQuery("select p.id, count(l) from Prateleira p left join p.livros l group by p.id");
		
		for (Object[] obj : query.getResultList()) {
			if((long)obj[1] < 20) {
				int prateleiraID = (int) obj[0];
				p = session.get(Prateleira.class, prateleiraID);
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
		
		TypedQuery<Integer> query = session.createQuery("select id from Biblioteca");
		if (query.getResultList().size() == 0) {
			biblioteca.getEstantes().add(new Estante(biblioteca));
			session.save(biblioteca);
		} else {
			int x = query.getResultList().get(0);
			biblioteca.setId(query.getResultList().get(0));
		}
			
		tx.commit();
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
	public int getCountOfEstantes() {
		Session session = SessionUtil.getInstance().getSession();
		
		TypedQuery<Integer> query = session.createQuery("select count(*) from Estante");
		
		session.close();
		return query.getResultList().get(0);
	}
	
	/*
	 * Search in database for estantes with prateleiras that have a bookcount == 20
	 */
	@Override
	public boolean needsReorganization() {
		for (Estante e : biblioteca.getEstantes())
			if(!e.isFull() && e != biblioteca.getLastEstante())
				return true;
		return false;
	}

	/*
	 * get all bookdata from the database and organize it
	 */
	@Override
	public void organizeLibrary() {
		//removendo estantes vazias
		for(ListIterator<Estante> eIt = biblioteca.getEstantes().listIterator();
				eIt.hasNext();) {
			Estante e = eIt.next();
			if (e.isEmpty())
				eIt.remove();
		}
		
		//verificando se há necessidade de reorganizar a biblioteca
		if(this.needsReorganization())
			for(ListIterator<Estante> eIt = biblioteca.getEstantes().listIterator(biblioteca.getEstantes().size());
					eIt.hasPrevious();) {
				Estante e = eIt.previous();
				for(ListIterator<Prateleira> pIt = e.getPrateleiras().listIterator(e.getPrateleiras().size());
						pIt.hasPrevious();) {
					Prateleira p = pIt.previous();
					for(ListIterator<Livro> lIt = p.getLivros().listIterator(p.getLivros().size());
							lIt.hasPrevious();) {
						Livro l = lIt.previous();
						//adiciona o livro no primeiro espaço vazio
						this.getPrateleiraWithEmptySpace().addLivro(l.copy());
						lIt.remove();
						if(!this.needsReorganization())
							return;
					}
				}
				//caso a estante esteja vazia, será removida
				if(e.isEmpty())
					eIt.remove();
			}
	}
	
}
