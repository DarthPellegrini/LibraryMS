package com.systemhaus.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.systemhaus.demo.SessionUtil;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Cartao;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.ClienteRepository;

public class ClienteDAO implements ClienteRepository {

	Biblioteca biblioteca;
	
	public ClienteDAO() {
		this.biblioteca = new Biblioteca();
	}
	
	@Override
	public void save(Cliente cliente) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction t = session.beginTransaction();  
		biblioteca.addCliente(cliente);
		session.save(cliente);
		t.commit();
		session.close();
	}

	@Override
	public List<Cliente> findSimilarClients(Cliente similar) {
		if(similar.getNome().isEmpty() && similar.getCpf().isEmpty() 
				&& similar.getTelefone().isEmpty() && similar.getCidade().isEmpty() 
				&& similar.getBairro().isEmpty() && similar.getRua().isEmpty()
				&& similar.getNumero() == 0)
			return new ArrayList<Cliente>();
		
		Session session = SessionUtil.getInstance().getSession();
		
		String hql = "from Cliente c, Endereco e, Cartao ca where "
				+ "c.endereco = e.id and c.cartao = ca.id";
		String parameters = "";
		String data[] = {similar.getNome(),similar.getCpf(),similar.getTelefone(),similar.getCidade(),similar.getBairro(),similar.getRua()};
		String dataIndex[] = {"c.nome","c.cpf","c.telefone","e.cidade","e.bairro","e.rua"};
		
		for (int i = 0; i < data.length; i++) 
			if(!data[i].isEmpty())
				parameters += " and " + dataIndex[i] + " = \'" + data[i] + "\'";
		if (similar.getNumero() > 0)
			parameters += " and e.numero = " + String.valueOf(similar.getNumero());
		
		TypedQuery<Cliente> query = session.createQuery(hql+parameters);
		List<Cliente> clientes = query.getResultList();
		session.close();
        return clientes;
	}

	@Override
	public void createValidCode(Cliente c) {
		String code = "";
		do {
			code = c.getCartao().createNewValidCodCartao();
		}while(findCardWithThisCode(code));
		c.setCodCartao(code);
	}

	@Override
	public boolean findCardWithThisCode(String code) {
		Session session = SessionUtil.getInstance().getSession();
		TypedQuery<Cartao> query = session.createQuery("from Cartao where codigo = ?");
		query.setParameter(0, code);
		boolean result = !query.getResultList().isEmpty();
		session.close();
		return result;
	}

	@Override
	public void delete(Cliente cliente) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction t = session.beginTransaction();
		session.delete(cliente);
		t.commit();
		session.close();
	}

	@Override
	public void update(Cliente cliente) {
		Session session = SessionUtil.getInstance().getSession();
		Transaction t = session.beginTransaction();
		session.update(cliente);
		t.commit();
		session.close();
	}

	@Override
	public boolean thisCpfAlreadyExists(String CPF) {
		Session session = SessionUtil.getInstance().getSession();
		TypedQuery<Cliente> query = session.createQuery("from Cliente where cpf = ?");
		query.setParameter(0, CPF);
		List<Cliente> clientes= query.getResultList();
		session.close();
		return clientes.size() != 0;
	}

	@Override
	public Cliente findClientWithThisCardCode(String code) {
		Session session = SessionUtil.getInstance().getSession();
		TypedQuery<Cliente> query = session.createQuery("from Cliente where codigo = ?");
		query.setParameter(0, code);
		List<Cliente> clientes= query.getResultList();
		session.close();
		return (clientes.size() != 0) ? clientes.get(0) : null;
	}

	@Override
	public void edit(String CPF, Cliente cliente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String CPF) {
		// TODO Auto-generated method stub
		
	}
}
