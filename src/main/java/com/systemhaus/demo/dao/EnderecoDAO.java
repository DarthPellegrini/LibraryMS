package com.systemhaus.demo.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import com.systemhaus.demo.SessionUtil;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.Endereco;
import com.systemhaus.demo.domain.EnderecoRepository;

public class EnderecoDAO implements EnderecoRepository{

	Biblioteca biblioteca;
	
	public EnderecoDAO() {
		this.biblioteca = new Biblioteca();
	}
	
	public EnderecoDAO(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}
	
	/*
	 * Método que verifica se existem muitas pessoas no mesmo endereço
	 */
	@Override
	public boolean thereAreTooManySimilarAddresses(Endereco exemplo) {
		Session session = SessionUtil.getInstance().getSession();
		Query query = session.createQuery("from Cliente c where c.endereco = " + exemplo.getId());
		List<Cliente> list= query.getResultList();
		session.close();
		return list.size() >= 6;
	}
	
}
