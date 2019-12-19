package com.systemhaus.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.systemhaus.demo.SessionUtil;
import com.systemhaus.demo.domain.Biblioteca;
import com.systemhaus.demo.domain.Cartao;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.ClienteRepository;
import com.systemhaus.demo.domain.Endereco;

public class ClienteDAO implements ClienteRepository {
	
	@Override
	public void save(Cliente cliente) {
		Session session = SessionUtil.getInstance().getSession();
		Biblioteca biblioteca = new Biblioteca();
		Transaction t = session.beginTransaction();  
		biblioteca.addCliente(cliente);
		session.saveOrUpdate(cliente);
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
		Query query = session.createQuery("from Cartao where codigo = ?");
		query.setParameter(0, code);
		boolean result = !query.list().isEmpty();
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
		Query query = session.createQuery("from Cliente where cpf = \'" + CPF + "\'");
		List<Cliente> clientes= query.list();
		session.close();
		return clientes.size() != 0;
	}

	@Override
	public Cliente findClientWithThisCardCode(String code) {
		Session session = SessionUtil.getInstance().getSession();
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
		
		session.close();
		return (clientes.size() != 0) ? clientes.get(0) : null;
	}

	@Override
	public boolean thereAreTooManySimilarAddresses(Endereco exemplo) {
		Session session = SessionUtil.getInstance().getSession();
		
		Query query = session.createQuery("from Cliente c where c.endereco = " + exemplo.getId());
		
		List<Cliente> list= query.list();
		
		session.close();
		return list.size() >= 6;
	}
	
	@Override
	public void edit(String CPF, Cliente cliente) {}

	@Override
	public void delete(String CPF) {}
}
