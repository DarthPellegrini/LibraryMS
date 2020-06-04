package com.systemhaus.demo.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.systemhaus.demo.domain.Login;
import com.systemhaus.demo.domain.LoginRepository;
import com.systemhaus.demo.domain.TipoAcesso;

public class LoginDAO implements LoginRepository{
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	@Transactional(readOnly = true)
	public TipoAcesso logIn(Login login) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("select l.tipoAcesso from Login l where l.user = \'" + login.getUser() + "\' and l.pass = \'" + login.getPass() + "\'");
		
		List<TipoAcesso> list = query.list();
		
		return (list.size() > 0) ? list.get(0) : null;
	}
	
	
} 
