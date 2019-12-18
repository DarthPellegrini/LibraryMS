package com.systemhaus.demo.dao.memory;

import java.util.ListIterator;

import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Estante;
import com.systemhaus.demo.domain.Prateleira;
import com.systemhaus.demo.domain.EstanteRepository;
import com.systemhaus.demo.domain.Livro;

public class EstanteDAO implements EstanteRepository {
	
	private Biblioteca biblioteca;

	public EstanteDAO(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
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
	public Prateleira getPrateleiraWithEmptySpace() {
		return biblioteca.getPrateleiraWithEmptySpace();
	}

	@Override
	public void addEstante() {
		biblioteca.getEstantes().add(new Estante(biblioteca));
	}
	
	@Override
	public long getCountOfEstantes() {
		return biblioteca.getEstantes().size();
	}
	
	@Override
	public boolean needsReorganization() {
		for (Estante e : biblioteca.getEstantes())
			if(!e.isFull() && e != biblioteca.getLastEstante())
				return true;
		return false;
	}

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
