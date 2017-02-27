package com.conor.HoL.GUI;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class UITools {

	public static void centreFrame(JFrame frame){
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int)((screen.getWidth() - frame.getWidth()) / 2);
		int y = (int)((screen.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}
	
	public static void centreFrame(JDialog dialog){
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int)((screen.getWidth() - dialog.getWidth()) / 2);
		int y = (int)((screen.getHeight() - dialog.getHeight()) / 2);
		dialog.setLocation(x, y);
	}
	
	public static void setUIFont(javax.swing.plaf.FontUIResource f)
	{
	    java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements())
	    {
	        Object key = keys.nextElement();
	        Object value = UIManager.get(key);
	        
	        if (value instanceof javax.swing.plaf.FontUIResource)
	        {
	            UIManager.put(key, f);
	        }
	    }
	}
	
}
