package com.systemhaus.demo.dao.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.systemhaus.demo.domain.Biblioteca;
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
		biblioteca.addDisponivel(livro.getISBN());
	}

	@Override
	public Livro findByExample(Livro example) {
		return (example.getISBN().isEmpty() && example.getTitulo().isEmpty()
			&& example.getAutor().isEmpty() && example.getEditora().isEmpty() 
			&& (example.getEdicao() == 0 || example.getNumeroPaginas() == 0)) 
			? null : biblioteca.getEstantes().stream()
					.flatMap(e -> e.getPrateleiras().stream())
					.flatMap(p -> p.getLivros().stream())
					.filter(l -> {
							return (l.getISBN().equals(example.getISBN()) || example.getISBN().isEmpty()) 
									&& (l.getEdicao() == example.getEdicao() || example.getEdicao() == 0) 
									&& (l.getTitulo().equals(example.getTitulo()) || example.getTitulo().isEmpty()) 
									&& (l.getAutor().equals(example.getAutor()) || example.getAutor().isEmpty()) 
									&& (l.getEditora().equals(example.getEditora()) || example.getEditora().isEmpty()) 
									&& (l.getNumeroPaginas() == example.getNumeroPaginas() || example.getNumeroPaginas() == 0);
					})
					.findFirst().orElse(null);
	}
	
	@Override
	public List<Livro> findBySimilarExample(Livro example) {
		List<String> ISBNs = new ArrayList<String>();
		List<Livro> livros = new ArrayList<Livro>();
		String[] dadosExemplo = {example.getTitulo().toLowerCase(), 
				example.getAutor().toLowerCase(),
				example.getEditora().toLowerCase()};
		if ((example.getISBN().isEmpty() && example.getTitulo().isEmpty()
			&& example.getAutor().isEmpty() && example.getEditora().isEmpty() 
			&& (example.getEdicao() == 0 || example.getNumeroPaginas() == 0)) )
			return livros;
		for(ListIterator<Estante> eIt = biblioteca.getEstantes().listIterator();
				eIt.hasNext();) {
			Estante e = eIt.next();
			for(ListIterator<Prateleira> pIt = e.getPrateleiras().listIterator();
					pIt.hasNext();) {
				Prateleira p = pIt.next();
				for(ListIterator<Livro> lIt = p.getLivros().listIterator();
						lIt.hasNext();) {
					Livro l = lIt.next();
					if ((l.getISBN().equals(example.getISBN()) || example.getISBN().isEmpty())
						&& (l.getEdicao() == example.getEdicao() || example.getEdicao() == 0) 
						&& (l.getTitulo().toLowerCase().contains(dadosExemplo[0]) 
								|| example.getTitulo().isEmpty()) 
						&& (l.getAutor().toLowerCase().contains(dadosExemplo[1]) 
								|| example.getAutor().isEmpty()) 
						&& (l.getEditora().toLowerCase().contains(dadosExemplo[2]) 
								|| example.getEditora().isEmpty()) 
						&& (l.getNumeroPaginas() == example.getNumeroPaginas() || example.getNumeroPaginas() == 0)) {
						if (!ISBNs.contains(l.getISBN())) {
							ISBNs.add(l.getISBN());
							livros.add(l);
						}
					}
				}
			}
		}
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
		biblioteca.getRegistroDeLivros().remove(iSBNOriginal);
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
