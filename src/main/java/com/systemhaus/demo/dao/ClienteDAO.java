package com.systemhaus.demo.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.systemhaus.demo.domain.Cartao;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.ClienteRepository;
import com.systemhaus.demo.domain.Endereco;

public class ClienteDAO implements ClienteRepository {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	@Override
	public void save(Cliente cliente) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(cliente);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Cliente> findSimilarClients(Cliente similar) {
		if(similar.getNome().isEmpty() && similar.getCpf().isEmpty() 
				&& similar.getTelefone().isEmpty() && similar.getCidade().isEmpty() 
				&& similar.getBairro().isEmpty() && similar.getRua().isEmpty()
				&& similar.getNumero() == 0)
			return new ArrayList<Cliente>();
		
		Session session = sessionFactory.getCurrentSession();
		
		String hql = "select c.id, c.nome, c.cpf, c.telefone, e, ca from Cliente c, Endereco e, Cartao ca where "
				+ "c.endereco = e.id and c.cartao = ca.id";
		String parameters = "";
		String data[] = {similar.getNome(),similar.getCpf(),similar.getTelefone(),similar.getCidade(),similar.getBairro(),similar.getRua()};
		String dataIndex[] = {"c.nome","c.cpf","c.telefone","e.cidade","e.bairro","e.rua"};
		
		for (int i = 0; i < data.length; i++) 
			if(!data[i].isEmpty())
				parameters += " and " + dataIndex[i] + " like \'%" + data[i] + "%\'";
		if (similar.getNumero() > 0)
			parameters += " and e.numero = " + String.valueOf(similar.getNumero());
		
		Query query = session.createQuery(hql+parameters);
		List<Object[]> list= query.list();
		
		List<Cliente> clientes = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			Object[] obj = list.get(i);
	        Cliente cli = new Cliente((String)obj[1],(String)obj[2],(String)obj[3],(Endereco)obj[4],(Cartao)obj[5]);
	        cli.setId((int)obj[0]);
			clientes.add(cli);
		}
		
        return clientes;
	}

	@Transactional(readOnly = true)
	@Override
	public void createValidCode(Cliente c) {
		String code = "";
		do {
			code = c.getCartao().createNewValidCodCartao();
		}while(findCardWithThisCode(code));
		c.setCodCartao(code);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean findCardWithThisCode(String code) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Cartao where codigo = ?");
		query.setParameter(0, code);
		boolean result = !query.list().isEmpty();
		return result;
	}

	@Transactional
	@Override
	public void delete(Cliente cliente) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(cliente);
	}

	@Transactional
	@Override
	public void update(Cliente cliente) {
		Session session = sessionFactory.getCurrentSession();
		session.update(cliente);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean thisCpfAlreadyExists(String CPF) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Cliente where cpf = \'" + CPF + "\'");
		List<Cliente> clientes= query.list();
		return clientes.size() != 0;
	}

	@Transactional(readOnly = true)
	@Override
	public Cliente findClientWithThisCardCode(String code) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select c.id, c.nome, c.cpf, c.telefone, e, ca "
									+ "from Cliente c, Cartao ca, Endereco e "
									+ "where c.cartao = ca.id and c.endereco = e.id and ca.codigo = \'" + code + "\'");
		
		List<Object[]> list= query.list();
		
		List<Cliente> clientes = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			Object[] obj = list.get(i);
	        Cliente cli = new Cliente((String)obj[1],(String)obj[2],(String)obj[3],(Endereco)obj[4],(Cartao)obj[5]);
	        cli.setId((int)obj[0]);
			clientes.add(cli);
		}
		
		return (clientes.size() != 0) ? clientes.get(0) : null;
	}

	@Transactional(readOnly = true)
	@Override
	public boolean thereAreTooManySimilarAddresses(Endereco exemplo) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from Cliente c where c.endereco = " + exemplo.getId());
		
		List<Cliente> list= query.list();
		
		return list.size() >= 6;
	}
	
	@Override
	@Deprecated
	public void edit(String CPF, Cliente cliente) {}

	@Override
	@Deprecated
	public void delete(String CPF) {}
}
