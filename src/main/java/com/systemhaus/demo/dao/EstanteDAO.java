package com.systemhaus.demo.dao;

import java.util.ListIterator;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

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
		
		TypedQuery<Object[]> query = session.createQuery("select l.prateleira,count(l.prateleira) from Livro l group by l.prateleira");
		
		
		
		session.close();
		return p;
	}

	/*
	 * Move to biblioteca DAO, MAYBE
	 * Get data and add new estante to database
	 */
	@Override
	public void addEstante() {
		Session session = SessionUtil.getInstance().getSession();
		
		biblioteca.addEstante();
		
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
