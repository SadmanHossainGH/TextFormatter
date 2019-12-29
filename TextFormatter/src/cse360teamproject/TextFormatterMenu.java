package cse360teamproject;
//-------------------------------------------------------------------------
// FILENAME:      TextFormatterMenu.java
// DESCRIPTION:   creates the frame for the GUI menu
//				               
//-------------------------------------------------------------------------
import javax.swing.*;

public class TextFormatterMenu
{
	
	public static void main(String[] args)
	{
		//An instance of the panel for the menus,adds the
		//menu panel to the frame
	    menuPanel menuP = new menuPanel();
	    menuP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //size of menu is Width x Height(in pixels)
        menuP.setSize (800, 500);
        menuP.setVisible(true);
	 }
}
