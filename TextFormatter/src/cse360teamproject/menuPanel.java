package cse360teamproject;
//-------------------------------------------------------------------------
// FILENAME:      menuPanel.java  
// DESCRIPTION:   creates the components and reads the actions of the
// components in the menu			               
//-------------------------------------------------------------------------
import java.awt.*;     
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;  

public class menuPanel extends JFrame
{
	//instance variables
	private JLabel titleL,fileNameL,infoL,previewL;
	private JPanel titleP,buttonP,errorP,upperP,lowerP;
	private JButton fileButton,saveButton,errorButton,prevButton;
	
	protected JTextArea errorT;
	protected JScrollPane scrollPane1;
	
	protected JTextArea previewT;
	protected JScrollPane scrollPane2;
	
	protected JFileChooser fileChooser;
	private boolean fileLoaded = false;
	private boolean fileSaved = false;
	
	String errorLog = "";
	public menuPanel()
	{
		super("Text Formatter");
		
		//creates title Label (Text Formatter)
		titleL = new JLabel("Text Formatter",SwingConstants.CENTER);
		titleL.setForeground(Color.black);
		titleL.setFont(new Font("Courier",Font.BOLD,20));
		
		//creates file name label (shows if file is loaded)
		fileNameL = new JLabel("NO FILE LOADED",SwingConstants.CENTER);
		fileNameL.setForeground(Color.red);
		
		//creates "Load File" button
		fileButton = new JButton("Load Text File");
		fileButton.setBackground(Color.GRAY);
		
		//creates "Save" button
		saveButton = new JButton("Save File As");
		saveButton.setBackground(Color.GRAY);
		
		//creates "Error Log" button
		errorButton = new JButton("Show  Error Log");
		errorButton.setBackground(Color.GRAY);
		
		//creates "Preview" button
		prevButton = new JButton("Preview Formatted Text");
		prevButton.setBackground(Color.GRAY);
		
		//creates "Error Log" label
		infoL = new JLabel("Error Log");
		infoL.setForeground(Color.black);
		
		//creates text area that prints errors
	    errorT=new JTextArea();
	    errorT.setFont(new Font("Courier New", Font.PLAIN, 12));
	    errorT.setEditable(false);
	    errorT.setVisible(false);
	    scrollPane1=new JScrollPane(errorT,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    
		//creates "Preview" label
		previewL = new JLabel("Preview");
		previewL.setForeground(Color.black);
	    
		//creates text area that prints errors and/or preview of 
		//format
	    previewT=new JTextArea();
        previewT.setFont(new Font("Courier New", Font.PLAIN, 12));
	    previewT.setEditable(false);
	    previewT.setVisible(false);
	    scrollPane2=new JScrollPane(previewT,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    
	    //creates the panels that each JComponent fits in
		titleP = new JPanel();
		buttonP = new JPanel();
		errorP = new JPanel();
		upperP = new JPanel();
		lowerP = new JPanel();
		
		//sets a Panel made of the title label and file label
		titleP.setLayout(new GridLayout(4,1));
		{
			titleP.add(titleL);
			titleP.add(fileNameL);
			titleP.setBorder(BorderFactory.createEtchedBorder(Color.BLACK,Color.BLACK));
		}
		
		//sets a panel made of all four buttons
		buttonP.setLayout(new GridLayout(2,2));
		{
		 buttonP.add(fileButton);
		 buttonP.add(saveButton);
		 buttonP.add(errorButton);
		 buttonP.add(prevButton);
		 buttonP.setBackground(Color.darkGray);
		 buttonP.setBorder(BorderFactory.createEtchedBorder(Color.BLACK,Color.BLACK));
		}
		
		//sets a panel made of the"Error" label and a JTextArea
		//with a scroll pane
		errorP.setLayout(new BorderLayout());
		{
			errorP.add(infoL,BorderLayout.NORTH);
			errorP.add(scrollPane1,BorderLayout.CENTER);
			errorP.setBorder(BorderFactory.createEtchedBorder(Color.BLACK,Color.BLACK));
		}
		
		//combines the tree panels into the single frame
		upperP.setLayout(new GridLayout(3,1));
		{
			upperP.add(titleP);
			upperP.add(buttonP);
			upperP.add(errorP);
		}
		
		//sets a panel made of the"Preview" label and a JTextArea
	    //with a scroll pane
		lowerP.setLayout(new BorderLayout());
		{
			lowerP.add(previewL,BorderLayout.NORTH);
			lowerP.add(scrollPane2,BorderLayout.CENTER);
			lowerP.setBorder(BorderFactory.createEtchedBorder(Color.BLACK,Color.BLACK));
		}
		
		//puts the final upper and lower panels into the frame
		setLayout(new GridLayout(2,1));
		{
			add(upperP);
			add(lowerP);
		}
		
		//actionListener for "Load File" button
		fileButton.addActionListener(new ButtonListener());
		//actionListener for "Save As" button
		saveButton.addActionListener(new ButtonListener());
		//actionListener for "Show Error Log" button
		errorButton.addActionListener(new ButtonListener());
		//actionListener for "Preview" button
		prevButton.addActionListener(new ButtonListener());
		 
		//creates a file dialog caller to select the file
		//(limits searches to test files)
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt", "text"));
	}
		 
	private class ButtonListener implements ActionListener
    {
		// date and time format
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
		LocalDateTime now = LocalDateTime.now();
		
		public void actionPerformed(ActionEvent event)
		{
		   //if "Load" button was called
		   if(event.getSource()==fileButton)
		   {
			   //opens "choose file" dialog
			  int validLoadFile = fileChooser.showOpenDialog(fileButton);
			  fileLoaded = false;
			  
			  //if a file was chosen
			  if(validLoadFile == JFileChooser.APPROVE_OPTION)
			  {
				     File selectedFile = fileChooser.getSelectedFile();
				     fileLoaded = true;
				     previewT.setVisible(false);
				     
				     String fileName = selectedFile.getName();
				     
		             //ADD TRY AND CATCH FOR TEXT FILE READING
				     //Maybe call a class instance that formats the text files
				     //with "selectedFile" as a parameter
				     if (selectedFile.length() == 0)
			    	 {
				    	 fileLoaded = false;
			    		 fileNameL.setText(fileName + " CAN NOT BE FORMATTED");
			    		 JOptionPane.showMessageDialog(fileButton,
				    		        "The text file has no content.",
				    		        "No Content Error",
				    		        JOptionPane.ERROR_MESSAGE);
			    		 now = LocalDateTime.now();
				    	 errorLog += (dateTimeFormat.format(now) + ": No Content Error occurred during formatting.\n");
				    	 errorT.setText(errorLog);
			    	 }
				     else if (!selectedFile.getName().endsWith(".txt"))
				     {
				    	 fileLoaded = false;
				    	 JOptionPane.showMessageDialog(fileButton,
				    		        "Invalid file extension. You can only upload txt files.",
				    		        "File Extension Error",
	        	    		        JOptionPane.ERROR_MESSAGE);
				    	  now = LocalDateTime.now();
				    	  errorLog += (dateTimeFormat.format(now) + ": File Extension Error occurred during loading.\n");
				    	  errorT.setText(errorLog);
				    	  fileNameL.setText(fileName + " CAN NOT BE LOADED");
				     }
				     else
				     {
				    	 previewT.setText("");
				    	 errorT.setText("");
						 try {
							FileFormatter newFile = new FileFormatter(selectedFile, previewT);
							fileNameL.setText(selectedFile.getName() + " LOADED");
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(fileButton,
				    		        "Text file unreadable. Please exit the program and try again",
				    		        "Fatal Error",
	        	    		        JOptionPane.ERROR_MESSAGE);
							now = LocalDateTime.now();
							errorLog += (dateTimeFormat.format(now) + ": Fatal Error occurred during loading.\n");
					    	errorT.setText(errorLog);
						}
				     }

			         
			  }  
		   }
		   else if(event.getSource()==saveButton)
		   {
			   //error for no file loaded ( or at least no text to save)
			   if(fileLoaded == false)
			   {
			    	 JOptionPane.showMessageDialog(saveButton,
			    		        "Please load a text file for formatting.",
			    		        "No File Loaded Error",
			    		        JOptionPane.ERROR_MESSAGE);
			    	 now = LocalDateTime.now();
					 errorLog += (dateTimeFormat.format(now) + ": No File Loaded Error occurred during the saving.\n");
					 errorT.setText(errorLog);
			   }
			   //if there is text to save (i.e. a file was previously loaded
		       else if ((previewT.getText().trim()).length() > 0) 
			   {
			       //opens "choose file" dialog
			       int validSaveFile = fileChooser.showSaveDialog(saveButton);
			       File selectedFile = fileChooser.getSelectedFile();
			  
			      //if a file was chosen
			      if(validSaveFile == JFileChooser.APPROVE_OPTION)
			      {
			    	  
			    	  
				      //Error for save file name with " ",".",or null start
			          if (selectedFile.getName().startsWith(" ") || selectedFile.getName().startsWith(".") 
				    	  ||  selectedFile.getName() == null)
				      {
					      JOptionPane.showMessageDialog(saveButton,
			    		        "Invalid file name. Please check your file name again.",
			    		        "File Name Error",
			    		        JOptionPane.ERROR_MESSAGE);
					      
					      now = LocalDateTime.now();
					      errorLog += (dateTimeFormat.format(now) + ": File Name Error occurred during saving.\n");
					      errorT.setText(errorLog);
					      fileNameL.setText(selectedFile.getName() + " CAN NOT BE SAVED");
				      }
					  else if (!selectedFile.getName().endsWith(".txt"))
					  {
					    	 fileSaved = false;
					    	 JOptionPane.showMessageDialog(fileButton,
					    		        "Invalid file extension. You can only save to txt files.",
					    		        "File Extension Error",
		        	    		        JOptionPane.ERROR_MESSAGE);
					    	  now = LocalDateTime.now();
					    	  errorLog += (dateTimeFormat.format(now) + ": File Extension Error occurred during saving.\n");
					    	  errorT.setText(errorLog);
					    	  fileNameL.setText("TEXT CAN NOT BE SAVED TO " + selectedFile.getName());
					  }
			          else
			          {
			        	  PrintWriter writeToFile = null;
			        	  // tries to link printWriter to save file
			        	  try
						     {
						       writeToFile = new PrintWriter(selectedFile);
						       
						     }
						     //if file could not be scanned using Scanner object in FileFormatter
					         catch (Exception e)
					         {
                               //we didn't have an "unable to save file" error in the doc,so
					           //I don't know what to add here
					        	 
					        	 JOptionPane.showMessageDialog(saveButton,
						    		        "Unable to save file, please try again later.",
						    		        "Unkown Error",
						    		        JOptionPane.ERROR_MESSAGE);
								      
					        	      now = LocalDateTime.now();
								      errorLog += (dateTimeFormat.format(now) + ": Unknown Error occurred during saving.\n");
								      errorLog += e;
								      errorT.setText(errorLog);
								      fileNameL.setText(selectedFile.getName() + " CAN NOT BE SAVED");
						     }
			        	//gets entirety of text from JTextArea
			            //and writes it to file
			        	String wholeText = previewT.getText();
			        	writeToFile.println(wholeText);
			        	
			        	//closes writer
			        	writeToFile.flush();
			        	writeToFile.close();
			        	
			        	//updates red text label to inform user of proper save
			        	fileNameL.setText("TEXT SAVED TO " + selectedFile.getName());
			        	
			          }
			     }
		      }

		   }
		  else if(event.getSource()==errorButton)
		  {
			  //changes visibility of the error log
			  errorT.setText(errorLog);
			  errorT.setVisible(true);
		  }
		  else if(event.getSource()==prevButton && fileLoaded == true)
		  {
			  //changes the visibility of the preview log
              previewT.setVisible(true);
		  }
			  
	   }//end of ActionPreformed Class		 
	}//end of ButtonListener Class
			
 }//end of menuPanel class
	 
	 
	
