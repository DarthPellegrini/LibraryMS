package com.systemhaus.demo.dao;

import java.io.File;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.Evento;
import com.systemhaus.demo.domain.LivroRetiradoRepository;
import com.systemhaus.demo.domain.RegLivros;
import com.systemhaus.demo.domain.TipoEvento;

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

import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRetirado;

public class LivroRetiradoDAO implements LivroRetiradoRepository{

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional(readOnly = true)
	@Override
	public void generateLivroRetiradoPendenteReport() {
		 Session session = sessionFactory.getCurrentSession();
		 Filter filter = session.enableFilter("tipoEventoFilter");
	     filter.setParameter("tipoEventoFilterParam", TipoEvento.RENOVACAO.ordinal());
	        
		 Query query = session.createQuery(
					"from LivroRetirado lr join fetch lr.cliente cl join fetch lr.livro l"
					+ " where lr.livro.retirado = true"
					+ " and cl.ativo = true and l.ativo = true");
		 
		 List<LivroRetirado> result = (List<LivroRetirado>)query.list();
		 	
		 try {
			JRBeanCollectionDataSource beanCollection =  new JRBeanCollectionDataSource(result);
		
			InputStream inputStream = new ClassPathResource("reports/LivroRetiradoRep.jrxml").getInputStream();
			
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
	                rootPath + "/reports/LivroRetiradoRep.pdf");
	        // Output
	        exporter.setExporterOutput(exporterOutput);
	 
	        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
	        exporter.setConfiguration(configuration);
	        exporter.exportReport();
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	}
	
	@Transactional(readOnly = true)
	@Override
	public void generateLivroRetiradoHistoricoReport() {
		 Session session = sessionFactory.getCurrentSession();
		 Filter filter = session.enableFilter("tipoEventoFilter");
	     filter.setParameter("tipoEventoFilterParam", TipoEvento.RENOVACAO.ordinal());
	     
		 Query query = session.createQuery(
					"from LivroRetirado lr join fetch lr.cliente cl join fetch lr.livro l"
							+ " where lr.livro.retirado = false"
							+ " and cl.ativo = true and l.ativo = true");
		
		 List<LivroRetirado> result = (List<LivroRetirado>)query.list();
		 	
		 try {
			JRBeanCollectionDataSource beanCollection =  new JRBeanCollectionDataSource(result);
			
			InputStream inputStream = new ClassPathResource("reports/LivroRetiradoHistoricoRep.jrxml").getInputStream();
			
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
	                rootPath + "/reports/LivroRetiradoHistoricoRep.pdf");
	        // Output
	        exporter.setExporterOutput(exporterOutput);
	 
	        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
	        exporter.setConfiguration(configuration);
	        exporter.exportReport();
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	}
	
	@Transactional(readOnly = true)
	@Override
	public void generateLivroRetiradoAtrasadoReport() {
		 Session session = sessionFactory.getCurrentSession();
		 Filter filter = session.enableFilter("tipoEventoFilter");
	     filter.setParameter("tipoEventoFilterParam", TipoEvento.RENOVACAO.ordinal());
	     
		 Query query = session.createQuery(
					"from LivroRetirado lr join fetch lr.cliente cl join fetch lr.livro l"
					+ " where lr.livro.retirado = true and lr.dataDevolucao < :dataDev"
					+ " and cl.ativo = true and l.ativo = true");
				 
		 query.setParameter("dataDev",  Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()), StandardBasicTypes.DATE);
		
		 List<LivroRetirado> result = (List<LivroRetirado>)query.list();
		 	
		 try {
			JRBeanCollectionDataSource beanCollection =  new JRBeanCollectionDataSource(result);
			
			InputStream inputStream = new ClassPathResource("reports/LivroRetiradoAtrasadoRep.jrxml").getInputStream();
			
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
	                rootPath + "/reports/LivroRetiradoAtrasadoRep.pdf");
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
	public boolean save(Livro livro, Cliente cliente) {
		Session session = sessionFactory.getCurrentSession();
		
		if (this.addRetirado(livro.getISBN())) {
			LivroRetirado livroRetirado = new LivroRetirado(livro, cliente);
			livroRetirado.addRetirada(new Evento(TipoEvento.RETIRADA, livroRetirado));

			session.save(livroRetirado.getRetirada());
			session.save(livroRetirado);
			
			return true;
		}else {
			return false;
		}
		
	}
	
	@Transactional
	@Override
	public void estenderRetirada(LivroRetirado livroRetirado) {
		Session session = sessionFactory.getCurrentSession();
		
		livroRetirado.estenderRetirada(new Evento(TipoEvento.RENOVACAO,livroRetirado));
		
		session.save(livroRetirado.getLastRenovacao());
		session.update(livroRetirado);
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<LivroRetirado> findSimilarLivroRetirado(Livro livro, Cliente cliente) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"select distinct lr from LivroRetirado lr join fetch lr.retirada join fetch lr.renovacoes join fetch lr.cliente cl join fetch lr.livro l join fetch l.prateleira p join fetch p.estante join fetch cl.cartao join fetch cl.endereco"
				+ " where l.retirado = true"
				+ " and cl.ativo = true and l.ativo = true"
				+ (livro.validate() ? " and l.ISBN = \'" + livro.getISBN() + "\'" : "")
				+ (cliente.validate() ? " and cl.id = " + cliente.getId() : ""));
	
		List<LivroRetirado> result = (List<LivroRetirado>)query.list();
		
		/*
		 * Má-prática: faltam exatas 3 horas pra entregar esse trabalho
		 * sou obrigado a fazer essa gambi
		 */
		for (LivroRetirado lr : result) {
			for(ListIterator<Evento> iter = lr.getRenovacoes().listIterator(); iter.hasNext();){
			    Evento e = iter.next();
			    if (e.getTipoEvento() != TipoEvento.RENOVACAO) {
			    	iter.remove();
			    }
			}
		}
		
		return result;
	}
	
	@Transactional
	@Override
	public int devolver(LivroRetirado livroRetirado) {
		Session session = sessionFactory.getCurrentSession();
		
		if(livroRetirado.getDevolucao() == null) {
			this.remRetirado(livroRetirado.getLivro().getISBN());
			livroRetirado.getLivro().setRetirado(false);
			livroRetirado.setDevolucao(new Evento(TipoEvento.DEVOLUCAO, livroRetirado));
			
			session.save(livroRetirado.getDevolucao());
			session.update(livroRetirado);
			
			return 0;
		}else {
			return 1;
		}
	}
	
	/*
	 * Remove um livro que estava retirado e foi devolvido do catálogo
	 */
	@Transactional
	public boolean remRetirado(String isbn) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from RegLivros where isbn = \'" + isbn+ "\'");
		
		List<RegLivros> list = query.list();
		
		if (list.size() > 0 && list.get(0).getQuantLivrosParaRetirar() + 1 <= list.get(0).getQuantLivrosNoCatalogo()){
			list.get(0).setQuantLivrosParaRetirar(list.get(0).getQuantLivrosParaRetirar()+1);
			session.update(list.get(0));
			
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Adiciona um exemplar que estava disponível e foi retirado
	 */
	@Transactional
	public boolean addRetirado(String isbn) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from RegLivros where isbn = \'" + isbn+ "\'");
		
		List<RegLivros> list = query.list();
		
		if (list.size() > 0 && list.get(0).getQuantLivrosParaRetirar() - 1 >= 0){
			list.get(0).setQuantLivrosParaRetirar(list.get(0).getQuantLivrosParaRetirar()-1);
			session.update(list.get(0));
			
			return true;
		} else {
			return false;
		}
	}
}
