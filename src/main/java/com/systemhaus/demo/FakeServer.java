package com.systemhaus.demo;

import java.util.ArrayList;

import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Estante;
import com.systemhaus.demo.domain.Prateleira;
import com.systemhaus.demo.domain.Livro;

public class FakeServer {

	private Biblioteca biblioteca;
	
	public FakeServer () {
		createLibrary();
	}
	
	private void createLibrary() {
		biblioteca = new Biblioteca();
		ArrayList<Estante> estantes = new ArrayList<Estante>();
		ArrayList<Prateleira> prateleiras = new ArrayList<Prateleira>();
		prateleiras.add(new Prateleira());
		estantes.add(new Estante(prateleiras));
		biblioteca.setEstantes(estantes);
	}	
	
	public void addBook(String iSBN, String edicao, String titulo, String autor, String editora, String numeroPaginas,
			String quantCopias) {
		if (!iSBN.isEmpty() && !edicao.isEmpty() && !titulo.isEmpty() && !autor.isEmpty() &&
				!editora.isEmpty() && !numeroPaginas.isEmpty() && !quantCopias.isEmpty()) {
			biblioteca.getLastPrateleiras().get(biblioteca.getLastPrateleiras().size()-1)
				.addLivro(
					new Livro(
						iSBN, 
						Integer.parseInt(edicao),
						titulo,
						autor,
						editora,
						Integer.parseInt(numeroPaginas),
						Integer.parseInt(quantCopias))
					);
			//teste
			System.out.println(biblioteca.getLastLivros().get(biblioteca.getLastLivros().size()-1).toString());
		}
	}
}
