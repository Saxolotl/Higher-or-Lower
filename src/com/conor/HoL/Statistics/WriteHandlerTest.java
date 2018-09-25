package com.conor.HoL.Statistics;

import static org.junit.Assert.*;

import org.junit.Test;

public class WriteHandlerTest {

	WriteHandler write = null;
	
	@Test
	public void testWrite() {
		//fail("Not yet implemented");
		write = new WriteHandler(new User(";", 0, 0, 0, 0, 0, "test"));
		assertTrue(write.createFile());
		
		write = new WriteHandler(new User("JUnit", 0, 0, 0, 0, 0, "test"));
		assertTrue(write.createFile());
		
		write = new WriteHandler(new User("567483[] 45  241    4kfd", 0, 0, 0, 0, 0, "test"));
		assertTrue(write.createFile());
	}
	
	@Test
	public void testWriteChanges(){
		write = new WriteHandler(new User("JUnit", 1, 2, 3, 4, 5, "test"));
		assertTrue(write.writeChanges());
		
	}

}
