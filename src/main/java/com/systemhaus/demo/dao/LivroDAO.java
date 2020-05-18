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

import com.systemhaus.demo.domain.Estante;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRepository;
import com.systemhaus.demo.domain.Prateleira;
import com.systemhaus.demo.domain.RegLivros;
import com.systemhaus.demo.report.LivroView;
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

public class LivroDAO implements LivroRepository {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional(readOnly = true)
	@Override
	public void generateLivroReport() {
		 Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery(
				"select distinct l.ISBN as ISBN, l.titulo as titulo, l.autor as autor, l.editora as editora, l.edicao as edicao, " +
		 		"l.numeroPaginas as numeroPaginas, rl.quantLivrosNoCatalogo as quantLivrosNoCatalogo, " +
		 		"rl.quantLivrosParaRetirar as quantLivrosParaRetirar " + 
		 		"from Livro l, RegLivros rl " +
		 		"where l.ISBN = rl.isbn " +
		 		"order by l.ISBN");
		 
		 query.setResultTransformer(new AliasToBeanResultTransformer(LivroView.class));
		 
		 List<LivroView> result = query.list();
		 	
		 try {
			JRBeanCollectionDataSource beanCollection =  new JRBeanCollectionDataSource(result);
			
			InputStream inputStream = new ClassPathResource("Livros.jrxml").getInputStream();
			
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
	                rootPath + "/reports/Livros.pdf");
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
	public void save(Livro livro) {
		Session session = sessionFactory.getCurrentSession();
		
		session.save(livro);
		
		this.addDisponivel(livro.getISBN());
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Livro> findBySimilarExample(Livro example, boolean findAvailable, boolean isRetirado) {
		List<Livro> livros = new ArrayList<Livro>();
		if ((example.getISBN().isEmpty() && example.getTitulo().isEmpty()
			&& example.getAutor().isEmpty() && example.getEditora().isEmpty() 
			&& (example.getEdicao() == 0 || example.getNumeroPaginas() == 0)) )
			return livros;
		
		Session session = sessionFactory.getCurrentSession();
		
		String hql = "from Livro l where ";
		String parameters = "";
		String data[] = {example.getISBN(), example.getTitulo(), example.getAutor(), example.getEditora()};
		String dataIndex[] = {"l.ISBN","l.titulo","l.autor","l.editora"};
		
		for (int i = 0; i < data.length; i++) 
			if(!data[i].isEmpty())
				parameters += (parameters.length() == 0 ? "" : " and ") + dataIndex[i] + " like \'%" + data[i] + "%\'";
		if (example.getEdicao() > 0)
			parameters += (parameters.length() == 0 ? "" : " and ") + "l.edicao = " + String.valueOf(example.getEdicao());
		if (example.getNumeroPaginas() > 0)
			parameters += (parameters.length() == 0 ? "" : " and ") + "l.numeroPaginas = " + String.valueOf(example.getNumeroPaginas());
		if(findAvailable)
			parameters += (parameters.length() == 0 ? "" : " and ") + "l.retirado = " + isRetirado;
		
		Query query = session.createQuery(hql+parameters);
		livros = query.list();
		
		return livros;
	}

	@Transactional
	@Override
	public void editByExample(String iSBNOriginal, Livro livro) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("select l.id,l.ISBN,l.titulo,l.autor,l.editora,l.edicao,l.numeroPaginas,l.retirado,l.prateleira.id, l.prateleira.estante.id"
				+ " from Livro l where isbn = \'" + iSBNOriginal + "\'");
		
		List<Object[]> list= query.list();
		
		List<Livro> livros = new ArrayList<>();
		for(int i=0;i<list.size()-1;i++){
			Object[] obj = list.get(i);
			
			Estante e = new Estante();
			e.setId((int)obj[9]);
			Prateleira p = new Prateleira(e);
			p.setId((int)obj[8]);
			e.addPrateleira(p);
	        Livro l = new Livro((String)obj[1],(String)obj[2],(String)obj[3],(String)obj[4],(int)obj[5],(int)obj[6],(boolean)obj[7]);
	        l.setId((int)obj[0]);
	        
	        livro.setPrateleira(p);
	        p.addLivro(livro);
	        
	        l.setAllDataFrom(livro);
	        session.update(l);
		}
	}
	
	@Transactional
	@Override
	public void deleteAllTheseBooks(String iSBNOriginal) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from Livro where ISBN = \'" + iSBNOriginal + "\'");
		
		for (Livro l : (List<Livro>)query.list()) {
			session.delete(l);
		}
	}
	
	@Transactional
	@Override
	public void deleteOnlyTheseBooks(String iSBNOriginal, int delete) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from Livro where ISBN = \'" + iSBNOriginal + "\' and retirado = false");
		
		List<Livro> livros = query.list();
		
		for (int i = 0; i < delete; i++) {
			session.delete(livros.get(i));
		}
	}

	@Transactional(readOnly = true)
	@Override
	public int returnBookCount(String iSBN) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from RegLivros where isbn = \'" + iSBN + "\'");
		
		List<RegLivros> list = query.list();
		
		return (list.size() > 0) ? list.get(0).getQuantLivrosNoCatalogo() : 0;
	}
	
	@Transactional(readOnly = true)
	@Override
	public int returnAvailableBookCount(String iSBN) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from RegLivros where isbn = \'" + iSBN + "\'");
		
		List<RegLivros> list = query.list();
		
		return (list.size() > 0) ? list.get(0).getQuantLivrosParaRetirar() : 0;
	}
	
	@Transactional(readOnly = true)
	@Override
	public boolean allTheBooksAreAvailable(String iSBN) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from RegLivros where isbn = \'" + iSBN + "\'");
		
		List<RegLivros> list = query.list();
		
		return (list.size() > 0) ? (list.get(0).getQuantLivrosNoCatalogo()==list.get(0).getQuantLivrosParaRetirar()) : false;
	}
	
	@Transactional(readOnly = true)
	@Override
	public boolean havingOnlyThisAmountOfCopiesWontCauseProblems(String iSBN, int quantCopias){
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from RegLivros where isbn = \'" + iSBN + "\'");
		
		List<RegLivros> list = query.list();
		
		return (list.size() > 0) ? (quantCopias >= list.get(0).getMaxDeletionNumber()) : false;
	}
	
	/*
	 * Adiciona um livro no catálogo na biblioteca
	 */
	@Transactional
	public void addDisponivel(String isbn) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from RegLivros where isbn = \'" + isbn + "\'");
		
		List<RegLivros> list = query.list();
		
		if (list.size() > 0){
			list.get(0).setQuantLivrosNoCatalogo(list.get(0).getQuantLivrosNoCatalogo()+1);
			list.get(0).setQuantLivrosParaRetirar(list.get(0).getQuantLivrosParaRetirar()+1);
			session.update(list.get(0));
		} else {
			RegLivros reg = new RegLivros(isbn,1);
			session.saveOrUpdate(reg);
		}
		
	}
	/*
	 * Remove livros do catálogo da biblioteca
	 */
	@Transactional
	public void remDisponivel(String isbn, int quant) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from RegLivros where isbn = \'" + isbn + "\'");
		
		List<RegLivros> list = query.list();
		
		if (list.size() > 0 && list.get(0).getQuantLivrosParaRetirar() - 1 >= 0){
			list.get(0).setQuantLivrosNoCatalogo(list.get(0).getQuantLivrosNoCatalogo()-quant);
			list.get(0).setQuantLivrosParaRetirar(list.get(0).getQuantLivrosParaRetirar()-quant);
			session.update(list.get(0));
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
	
	@Transactional
	@Override
	public void deleteThisRegLivros(RegLivros reg) {
		Session session = sessionFactory.getCurrentSession();
		
		session.delete(reg);
	}
	
	@Transactional(readOnly = true)
	@Override
	public RegLivros findRegLivrosForThis(String isbn) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from RegLivros where isbn = \'" + isbn + "\'");
		
		List<RegLivros> list = query.list();
		
		return (list.size() > 0) ? list.get(0) : null;
	}

	@Deprecated
	@Override
	public void deleteOnlyTheseBooks(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros,
			int delete) {
		// TODO Auto-generated method stub
		
	}

	@Deprecated
	@Override
	public void deleteAllTheseBooks(String iSBNOriginal, List<Prateleira> prateleiras, List<Livro> livros) {
		// TODO Auto-generated method stub
		
	}
	
	@Deprecated
	@Override
	public void markBooksForDeletion(String iSBNOriginal,List<Prateleira> prateleiras, List<Livro> livros) {
		//Deprecated
	}
	
}
