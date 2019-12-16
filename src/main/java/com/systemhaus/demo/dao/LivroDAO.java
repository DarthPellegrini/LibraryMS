package com.systemhaus.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.Query;

import org.hibernate.Session;

import com.systemhaus.demo.SessionUtil;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Cartao;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.Endereco;
import com.systemhaus.demo.domain.Estante;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRepository;
import com.systemhaus.demo.domain.Prateleira;

public class LivroDAO implements LivroRepository {
	
	private Biblioteca biblioteca;

	public LivroDAO(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}

	@Override
	public void save(Livro livro) {
		Session session = SessionUtil.getInstance().getSession();
		//session.save(livro.getPrateleira().getEstante().getBiblioteca());
		//session.save(livro.getPrateleira().getEstante());
		//session.save(livro.getPrateleira());
		session.save(livro);
		//TODO: criar um BibliotecaDAO para lidar com o controle do estoque dos livros
		biblioteca.addDisponivel(livro.getISBN());
		session.close();
	}
	
	@Override
	public List<Livro> findBySimilarExample(Livro example, boolean findAvailable) {
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
				parameters += (parameters.length() == 0 ? "" : " and ") + dataIndex[i] + " = \'" + data[i] + "\'";
		if (example.getEdicao() > 0)
			parameters += (parameters.length() == 0 ? "" : " and ") + "l.edicao = " + String.valueOf(example.getEdicao());
		if (example.getNumeroPaginas() > 0)
			parameters += (parameters.length() == 0 ? "" : " and ") + "l.numeroPaginas = " + String.valueOf(example.getNumeroPaginas());
		if(findAvailable)
			parameters += (parameters.length() == 0 ? "" : " and ") + "l.retirado = false";
		
		Query query = session.createQuery(hql+parameters);
		List<Livro> list= query.getResultList();
		
		session.close();
		return livros;
	}

	@Override
	public void editByExample(String iSBNOriginal, Livro livro) {
		for (Estante e : biblioteca.getEstantes()) 
			for (Prateleira p : e.getPrateleiras()) 
				for (Livro l : p.getLivros())
					if (l.getISBN().equals(iSBNOriginal)) l.setAllDataFrom(livro);
	}
	
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
	
	@Override
	public void deleteAllTheseBooks(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros) {
		biblioteca.getRegLivros().remove(biblioteca.findRegLivrosForThis(iSBNOriginal));
		for (Prateleira p : prateleiras) 
			p.getLivros().removeAll(livros);
	}
	
	@Override
	public void deleteOnlyTheseBooks(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros, int delete) {
		biblioteca.remDisponivel(iSBNOriginal, delete);
		for (Prateleira p : prateleiras)
			for(ListIterator<Livro> lIt = p.getLivros().listIterator(p.getLivros().size());
					lIt.hasPrevious();) {
				Livro l = lIt.previous();
			    if (livros.contains(l) && !l.isRetirado()) {
			        lIt.remove();
			        if (--delete <= 0)
				    	return;
			    }
			}
	}
}
