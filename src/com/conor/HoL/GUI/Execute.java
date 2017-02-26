package com.conor.HoL.GUI;

import com.conor.HoL.Statistics.WriteHandler;

import java.io.File;
import java.io.IOException;

import com.conor.HoL.Statistics.ReadHandler;
import com.conor.HoL.Statistics.User;

public class Execute {
	public static void main(String[] args) throws IOException{
		//User me = new User("Alice", 3, 2, 1, 5);
		//WriteHandler storage = new WriteHandler(me);
		//ReadHandler read = new ReadHandler();
		
		//storage.createFile();
		//storage.getStats();
		//storage.writeChanges();
		
		//File test = new File("Users/Alice.txt");
		//System.out.println(test.delete());
		
		new Menu();
		
	}
}
