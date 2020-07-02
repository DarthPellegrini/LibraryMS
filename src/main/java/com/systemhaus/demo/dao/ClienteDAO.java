package com.systemhaus.demo.dao;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import com.systemhaus.demo.domain.Cartao;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.ClienteRepository;
import com.systemhaus.demo.domain.Endereco;
import com.systemhaus.demo.report.ClienteView;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;

public class ClienteDAO implements ClienteRepository {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional(readOnly = true)
	@Override
	public void generateClienteReport() {
		 Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery(
				"select c.nome as nome, c.cpf as CPF, c.telefone as telefone, " +  
				"e.cidade as cidade, e.bairro as bairro, e.rua as rua, e.numero as numero, " +
				"ca.codigo as codigo, ca.validade as validade " + 
				"from Cliente c, Endereco e, Cartao ca " + 
				"where c.ativo = true " + 
				"and e.id = c.endereco.id " + 
				"and ca.id  = c.cartao.id ");
		 
		 query.setResultTransformer(new AliasToBeanResultTransformer(ClienteView.class));
		 
		 List<ClienteView> result = query.list();
		 	
		 try {
			JRBeanCollectionDataSource beanCollection =  new JRBeanCollectionDataSource(result);
			
			InputStream inputStream = new ClassPathResource("reports/ClienteRep.jrxml").getInputStream();
			
		 	// First, compile jrxml file
	        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
	 
	        // Parameters for report
	        Map<String, Object> parameters = new HashMap<String, Object>();
	 
	        JasperPrint print = JasperFillManager.fillReport(jasperReport,
	                parameters, beanCollection);
	 
	        JasperViewer.viewReport(print,false);
	        
	        String rootPath = System.getProperty("user.dir");
	        
	        // Make sure the output directory exists.
	        File outDir = new File(rootPath +"/reports");
	        outDir.mkdirs();
	 
	        // PDF Exportor.
	        JRPdfExporter exporter = new JRPdfExporter();
	 
	        ExporterInput exporterInput = new SimpleExporterInput(print);
	        // ExporterInput
	        exporter.setExporterInput(exporterInput);
	 
	        // ExporterOutput
	        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
	                rootPath + "/reports/ClienteRep.pdf");
	        // Output
	        exporter.setExporterOutput(exporterOutput);
	 
	        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
	        exporter.setConfiguration(configuration);
	        exporter.exportReport();
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
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
		String parameters = " and c.ativo = true ";
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
		cliente.setAtivo(false);
		session.update(cliente);
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
		Query query = session.createQuery("from Cliente where ativo = true and cpf = \'" + CPF + "\'");
		List<Cliente> clientes= query.list();
		return clientes.size() != 0;
	}

	@Transactional(readOnly = true)
	@Override
	public Cliente findClientWithThisCardCode(String code) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select c.id, c.nome, c.cpf, c.telefone, e, ca "
									+ "from Cliente c, Cartao ca, Endereco e "
									+ "where c.ativo = ativo = true and c.cartao = ca.id and c.endereco = e.id and ca.codigo = \'" + code + "\'");
		
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
		
		Query query = session.createQuery("from Cliente c where c.ativo = true and c.endereco = " + exemplo.getId());
		
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
