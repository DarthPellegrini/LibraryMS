package com.systemhaus.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.systemhaus.demo.domain.Livro;

public class ServerTest {

	@Test
	public void testAddBook() {
		Server s = new Server();
		boolean result = s.addBook(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false));
		assertTrue(result);
	}
	
	@Test
	public void testFindBook() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 10);
		s.addNewBookRoutine(new Livro("9780123456790", 1, "Livro Novo", "Agatha Cristie", "LP&M", 250, false), 7);
		Livro l = s.findBook(new Livro("9780123456790", 1, "Livro Novo", "Agatha Cristie", "LP&M", 250, false));
		Livro livro = l.copy();
		livro.clear();
		//assertFalse(l == null ? false : true);
		livro.setISBN("9780123456790");
		assertTrue(l == null ? false : true);
	}
	
	@Test
	public void testErrorFindBook() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 10);
		s.addNewBookRoutine(new Livro("9780123456790", 1, "Livro Novo", "Agatha Cristie", "LP&M", 250, false), 7);
		s.addNewBookRoutine(new Livro("9780123456791", 1, "Novo Livro Novo", "Agatha Cristie", "LP&M", 250, false), 3);
		Livro l = s.findBook(new Livro("9780123456790", 1, "Livro Novo", "Agatha Cristie", "LP&M", 250, false));
		Livro livro = l.copy();
		livro.clear();
		assertFalse(l == null ? false : true);
	}
	
	@Test
	public void testErrorAddingBookNoEmptySpaceAvailable() {
		Server s = new Server();
		for(int i = 0; i <= 100; i++) {
			s.addBook(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false));
		}
		boolean result = s.addBook(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false));
		assertTrue(result);
	}
	
	@Test
	public void testAddNewBookRoutine() {
		Server s = new Server();
		boolean result = s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 1);
		assertTrue(result);
	}

	@Test
	public void testErrorAddingNewBookRoutineWithWrongISBN() {
		Server s = new Server();
		boolean result = s.addNewBookRoutine(new Livro("8780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 1);
		assertTrue(result);
	}
	
	@Test
	public void testErrorAddingNewBookRoutineWithInvalidQuantity() {
		Server s = new Server();
		boolean result = s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 0);
		assertFalse(result);
	}
	
	@Test
	public void testAddingNewBookRoutineToCreateANewEstate() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 100);
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 1);
		assertEquals(2, s.getCountOfEstantes());
	}
	
	@Test
	public void testAddNewBookRoutineCheckAvailableQuantities() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 1);
		assertEquals(1, s.returnBookCount("9780123456789"));
	}
	
	@Test
	public void testEditBook() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 2);
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, true), 8);
		boolean result = s.editBook("9780123456789", new Livro("9780123456789", 1, "Book", "Agatha Cristie", "LP&M", 250, false), 8);
		assertTrue(result);
	}
	
	@Test
	public void testEditBookWithMoreCopiesThanExpected() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 2);
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, true), 8);
		s.editBook("9780123456789", new Livro("9780123456789", 1, "Book", "Agatha Cristie", "LP&M", 250, false), 20);
		assertEquals(20, s.returnBookCount("9780123456789"));
	}
	
	@Test
	public void testErrorEditBookWithLessCopiesThanExpected() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 2);
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, true), 8);
		boolean result = s.editBook("9780123456789", new Livro("9780123456789", 1, "Book", "Agatha Cristie", "LP&M", 250, false), 5);
		assertFalse(result);
	}
	
	@Test
	public void testDeleteBook() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 140);
		s.addNewBookRoutine(new Livro("9780123456790", 1, "Livro novo", "Agatha Cristie", "LP&M", 250, false), 20);
		s.addNewBookRoutine(new Livro("9780123456790", 1, "Livro novo", "Agatha Cristie", "LP&M", 250, true), 20);
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 100);
		boolean result = s.deleteBook("9780123456790", 20);
		assertTrue(result);
	}
	
	@Test
	public void testErrorDeleteBookFail() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 140);
		s.addNewBookRoutine(new Livro("9780123456790", 1, "Livro novo", "Agatha Cristie", "LP&M", 250, false), 20);
		s.addNewBookRoutine(new Livro("9780123456790", 1, "Livro novo", "Agatha Cristie", "LP&M", 250, true), 20);
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 100);
		boolean result = s.deleteBook("9780123456790", 0);
		assertFalse(result);
	}
	
	@Test
	public void testOrganizeLibrary() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 140);
		s.addNewBookRoutine(new Livro("9780123456790", 1, "Livro novo", "Agatha Cristie", "LP&M", 250, false), 20);
		s.addNewBookRoutine(new Livro("9780123456790", 1, "Livro novo", "Agatha Cristie", "LP&M", 250, true), 20);
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 100);
		boolean result1 = s.needsReorganization();
		s.deleteBook("9780123456790", 0);
		boolean result2 = s.needsReorganization();
		assertEquals(result1, result2);
	}
}
