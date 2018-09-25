package com.conor.HoL.Game;

import static org.junit.Assert.*;

import org.junit.Test;

public class LogicTest {

	Logic logic = new Logic();
	
	@Test
	public void testResult() {
		logic.setCurrCard(1);
		logic.setNextCard(2);
		
		assertEquals(true, logic.getResult(1));
		assertEquals(false, logic.getResult(0));
		
		logic.setCurrCard(1);
		logic.setNextCard(1);
		
		assertEquals(true, logic.getResult(1));
		assertEquals(true, logic.getResult(0));
		
		logic.setCurrCard(2);
		logic.setNextCard(1);
		
		assertEquals(false, logic.getResult(1));
		assertEquals(true, logic.getResult(0));
	}
	
	

}
