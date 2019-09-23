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
	public void testErrorAddingBookNoEmptySpaceAvailable() {
		Server s = new Server();
		for(int i = 0; i <= 100; i++) {
			s.addBook(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false));
		}
		boolean result = s.addBook(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false));
		assertFalse(result);
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
		assertFalse(result);
	}
	
	@Test
	public void testAddingNewBookRoutineToCreateANewEstate() {
		Server s = new Server();
		assertEquals(1, s.getCountOfEstantes());
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 100);
		assertEquals(1, s.getCountOfEstantes());
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 1);
		assertEquals(2, s.getCountOfEstantes());
	}
	
	@Test
	public void testAddNewBookRoutineCheckAvailableQuantities() {
		Server s = new Server();
		s.addNewBookRoutine(new Livro("9780123456789", 1, "Livro", "Agatha Cristie", "LP&M", 250, false), 1);
		assertEquals(1, s.returnBookCount("9780123456789"));
	}
}
