package com.systemhaus.demo.dao.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.Evento;
import com.systemhaus.demo.domain.LivroRetiradoRepository;
import com.systemhaus.demo.domain.TipoEvento;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRetirado;

public class LivroRetiradoDAO extends LivroRetiradoRepository{

	Biblioteca biblioteca;
	
	public LivroRetiradoDAO(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}
	
	@Override
	public boolean save(Livro livro, Cliente cliente) {
		if (biblioteca.addRetirado(livro.getISBN())) {
			LivroRetirado livroRetirado = new LivroRetirado(livro, cliente);
			livroRetirado.addRetirada(new Evento(TipoEvento.RETIRADA, livroRetirado));
			biblioteca.addLivroRetirado(livroRetirado);
			return true;
		}else
			return false;
		
	}
	
	/*
	 * Método de renovação, possui a regra de que somente 3 renovações são possíveis
	 */
	@Override
	public void estenderRetirada(LivroRetirado livroRetirado) {
		livroRetirado.estenderRetirada(new Evento(TipoEvento.RENOVACAO,livroRetirado));
	}
	
	@Override
	public LivroRetirado findLivroRetirado(Livro livro, Cliente cliente) {
		for(ListIterator<LivroRetirado> lrIt = biblioteca.getLivrosRetirados().listIterator(biblioteca.getLivrosRetirados().size());
				lrIt.hasPrevious();) {
			LivroRetirado livroRetirado = lrIt.previous();
			if (livroRetirado.getLivro().isEqual(livro) && livroRetirado.getCliente().getCpf().contentEquals(cliente.getCpf()))
				return livroRetirado;
		}
		return null;
	}
	
	@Override
	public List<LivroRetirado> findSimilarLivroRetirado(Livro livro, Cliente cliente) {
		List<LivroRetirado> livrosRetirados = new ArrayList<>();
		for (LivroRetirado livroRetirado : biblioteca.getLivrosRetirados()) {
			if (livroRetirado.getDevolucao() == null &&
				((livroRetirado.getLivro().isEqual(livro) && livroRetirado.getCliente().getCpf().contentEquals(cliente.getCpf()))) ||
				(livroRetirado.getLivro().isEqual(livro) && !cliente.validate()) || 
				(livroRetirado.getCliente().getCpf().contentEquals(cliente.getCpf())) && !livro.validate())
				livrosRetirados.add(livroRetirado);
		}
		return livrosRetirados;
	}
	
	@Override
	public int devolver(LivroRetirado livroRetirado) {
		if(livroRetirado.getDevolucao() == null) {
			biblioteca.remRetirado(livroRetirado.getLivro().getISBN());
			livroRetirado.getLivro().setRetirado(false); //precisa ser modificado quando o banco for incluído
			livroRetirado.setDevolucao(new Evento(TipoEvento.DEVOLUCAO, livroRetirado));
			return 0;
		}else
			return 1;
	}
	
	
}
