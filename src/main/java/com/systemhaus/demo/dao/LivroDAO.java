package com.systemhaus.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.systemhaus.demo.SessionUtil;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Estante;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRepository;
import com.systemhaus.demo.domain.Prateleira;
import com.systemhaus.demo.domain.RegLivros;

public class LivroDAO implements LivroRepository {
	
	private Biblioteca biblioteca;

	public LivroDAO(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}

	@Override
	public void save(Livro livro) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		
		session.save(livro);
		
		this.addDisponivel(livro.getISBN());
		
		tx.commit();
		session.close();
	}
	
	@Override
	public List<Livro> findBySimilarExample(Livro example, boolean findAvailable, boolean isRetirado) {
		List<Livro> livros = new ArrayList<Livro>();
		if ((example.getISBN().isEmpty() && example.getTitulo().isEmpty()
			&& example.getAutor().isEmpty() && example.getEditora().isEmpty() 
			&& (example.getEdicao() == 0 || example.getNumeroPaginas() == 0)) )
			return livros;
		
		Session session = SessionUtil.getInstance().getSession();
		
		String hql = "from Livro l where ";
		String parameters = "";
		String data[] = {example.getISBN(), example.getTitulo(), example.getAutor(), example.getEditora()};
		String dataIndex[] = {"l.ISBN","l.titulo","l.autor","l.editora"};
		
		for (int i = 0; i < data.length; i++) 
			if(!data[i].isEmpty())
				parameters += (parameters.length() == 0 ? "" : " and ") + dataIndex[i] + " like \'%" + data[i] + "%\'";
		if (example.getEdicao() > 0)
			parameters += (parameters.length() == 0 ? "" : " and ") + "l.edicao = " + String.valueOf(example.getEdicao());
		if (example.getNumeroPaginas() > 0)
			parameters += (parameters.length() == 0 ? "" : " and ") + "l.numeroPaginas = " + String.valueOf(example.getNumeroPaginas());
		if(findAvailable)
			parameters += (parameters.length() == 0 ? "" : " and ") + "l.retirado = " + isRetirado;
		
		Query query = session.createQuery(hql+parameters);
		livros = query.getResultList();
		
		session.close();
		return livros;
	}

	@Override
	public void editByExample(String iSBNOriginal, Livro livro) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		
		TypedQuery<Object[]> query = session.createQuery("select l.id,l.ISBN,l.titulo,l.autor,l.editora,l.edicao,l.numeroPaginas,l.retirado,l.prateleira.id, l.prateleira.estante.id"
				+ " from Livro l where isbn = \'" + iSBNOriginal + "\'");
		
		List<Object[]> list= query.getResultList();
		
		List<Livro> livros = new ArrayList<>();
		for(int i=0;i<list.size()-1;i++){
			Object[] obj = list.get(i);
			
			Estante e = new Estante(biblioteca);
			e.setId((int)obj[9]);
			Prateleira p = new Prateleira(e);
			p.setId((int)obj[8]);
			e.addPrateleira(p);
	        Livro l = new Livro((String)obj[1],(String)obj[2],(String)obj[3],(String)obj[4],(int)obj[5],(int)obj[6],(boolean)obj[7]);
	        l.setId((int)obj[0]);
	        
	        livro.setPrateleira(p);
	        p.addLivro(livro);
	        
	        l.setAllDataFrom(livro);
	        session.update(l);
		}
		
		tx.commit();
		session.close();
	}
	
	@Override
	public void deleteAllTheseBooks(String iSBNOriginal) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		
		TypedQuery<Livro> query = session.createQuery("from Livro where ISBN = \'" + iSBNOriginal + "\'");
		
		for (Livro l : query.getResultList()) {
			session.remove(l);
		}
		
		tx.commit();
		session.close();
	}
	
	@Override
	public void deleteOnlyTheseBooks(String iSBNOriginal, int delete) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		
		TypedQuery<Livro> query = session.createQuery("from Livro where ISBN = \'" + iSBNOriginal + "\' and retirado = false");
		
		List<Livro> livros = query.getResultList();
		
		for (int i = 0; i < delete; i++) {
			session.remove(livros.get(i));
		}
		
		tx.commit();
		session.close();
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

	@Deprecated
	@Override
	public void deleteOnlyTheseBooks(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros,
			int delete) {
		// TODO Auto-generated method stub
		
	}

	@Deprecated
	@Override
	public void deleteAllTheseBooks(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros) {
		// TODO Auto-generated method stub
		
	}
	
	@Deprecated
	@Override
	public void markBooksForDeletion(String iSBNOriginal,List<Prateleira> prateleiras, List<Livro> livros) {
		for(ListIterator<Estante> eIt = biblioteca.getEstantes().listIterator(biblioteca.getEstantes().size());
				eIt.hasPrevious();) {
			Estante e = eIt.previous();
			for(ListIterator<Prateleira> pIt = e.getPrateleiras().listIterator(e.getPrateleiras().size());
					pIt.hasPrevious();) {
				Prateleira p = pIt.previous();
				for(ListIterator<Livro> lIt = p.getLivros().listIterator(p.getLivros().size());
						lIt.hasPrevious();) {
					Livro l = lIt.previous();
					if ( l.getISBN().equals(iSBNOriginal) && !l.isRetirado()) {
						if(!prateleiras.contains(p))
							prateleiras.add(p);
						if (!livros.contains(l))
							livros.add(l);
					}
				}
			}
		}
	}
	
}
