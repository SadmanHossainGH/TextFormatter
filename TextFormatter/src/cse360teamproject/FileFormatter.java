package cse360teamproject;
//-------------------------------------------------------------------------
// FILENAME:      FileFormatter.java 
// DESCRIPTION:   formats the text file based on the flag
// 			               
//------------------------------------------------------------------------- 
import javax.swing.*;  
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileFormatter extends menuPanel
{

   //number of characters in current scanned string
   int stringLength = 0;
   
   //number of characters leftover from scanned string if 
   //max character length not reached or char length
   //is > 80
   int stringLeft = 0;
   
   //If the 2 column flag is called, this ArrayList holds every 
   //35 character line in it until either the end of the file is
   //reached or a 1 column flag is reached.We use this to append
   //each a line from the first column with a line from the second 
   ArrayList<String> twoColumn = new ArrayList<String>();
	  
   public FileFormatter(File newFile , JTextArea previewT) throws FileNotFoundException
   {
	   //FileReader reads the file path of the selected file
	   FileReader fileInput = new FileReader(newFile);
	   //BufferedReader reads the FileReader
	   BufferedReader buffRead = new BufferedReader(fileInput);
	   //Scanner reads Buffered file
	   Scanner scanFile = new Scanner(buffRead);
	   
	   //string that contains the entire text from the file
	   //,but formatted
	   String wholeText ="";
	   //current string that is being read from the text file
	   String currentString = "";
	   //the leftovers of the string if it is not to its max
	   //or is over the max
	   String stringExtra = "";
	   
	   //Flag Variables
	   //current justification,indentation,column, and
	   //flag type
	   char flag = 'L';
	   char justification = 'L';
	   char space = 'S';
	   char indent = 'N';
	   int column = 1;
	   
	   //oldJustification is used if Title is called so the 
	   //justification
	   char oldJustification = 'L';
	   //originalJustification is for the specific case
	   //that two column is active and the justification
	   //type changes in the middle of it
	   char originalJustification = 'L';
	   //old column needed to see if the column just
	   //changed to 2, initializes use of ArrayList
	   int oldColumn = 1;
	   char oldIndent = 'N';
	   boolean emptyScanLine = false;
	   boolean emptySpace = false;
	   
	   //the  limit for a one character line is 79 (not 80 b/c
	   //we have to account for a space)
	   int limit = 79;
	   
	   //while a line can be read from the.txt file or
	   //there is not leftover text that has not been 
	   //formatted or a 2 column is active and the end
	   //of the txt file has been reached. For the 2 column 
	   //case,we still have to append lines from both columns
	   //to format them, so the loop has to keep running
	   while(scanFile.hasNext() || stringExtra != "" || (column == 2 && stringExtra == ""))
	   {
		   //if the file still has readable text and currentString
		   //hans't reached its max
		   if(scanFile.hasNext() && stringLeft < limit)
		   {
		      //currentString reads next line
		      currentString = scanFile.nextLine();
		      
		      if(currentString.length() == 0 )
		      {
		    	  emptyScanLine = true;
		      }
		      else
		      {
		    	  emptyScanLine = false;
		      }
		   }
		   //needed in the case that the entire file was read,but
		   //leftover characters exist from a previous line that
		   //needs formatting. No new text is scanned at this
		   //point
		   else
		   {
			   currentString = "";
		   }
		   
		   stringLength = currentString.length();
		   
		   //if the currentString has the potential to be an applicable flag
		   if((currentString.length() == 2) && (currentString.charAt(0) == '-'))
		   {
			   //makes flag upper case for simplicity
			   currentString = currentString.toUpperCase();
			   flag = currentString.charAt(1);
			   currentString = "";
               
			   //switches based on flag. We could use currentString,but I think
			   //using 'flag' makes if more clear
			   switch(flag)
			   {
			       //1)Left Justified
			       case 'L':
			    	   justification = 'L';
			    	   break; 
			       //2)Right Justified
			       case 'R':
			    	   justification = 'R';
			    	   indent ='N';
			    	   break;   
				  //3)Center Justified
				  case 'C':
				      justification = 'C';
				      indent = 'N';
				      break;     
				  //4)Title Justified
				  case 'T':
					  //NOTE: We need to keep the original justification
					  //to revert it back after we center the single line
					  //We could make a switch statement based on the flag,
					  // but we also need to account for the column and spacing
					  if(justification != 'T')
					  {
				        oldJustification = justification;
					  }
				      justification = 'T';
				      indent = 'N';
				      break;
				 //5)Double Space
				 case 'D':
				      space = 'D';
				      break; 
			     //6)Single Justified
				 case 'S':
				     space = 'S';
				     break;   
		         //7)Single Indent
			     case 'I':
			    	 //makes sure indent only works
			    	 //with left justification
				     if(justification == 'L')
				     {

				         oldIndent = indent;
				    	 indent = 'I';
				    	 
				     }
				     else
				     {
				    	 indent = 'N';
				     }
				     break;     
		         //8)Multiple Indent
			     case 'B':
			    	 //makes sure only left justification
			    	 //is accepted
				     if(justification == 'L')
				     {
				    	 oldIndent = indent;
				    	 indent = 'B';
				     }
				     else
				     {
				    	 indent = 'N';
				     }
				     break;
		         //9)2 Columns
				 case '2':
				      indent = 'N';
				      oldColumn = column;
				      column = 2;
				      
				      break; 
		         //10)1 Column
				 case '1': 
				     indent = 'N';
				     oldColumn = column;
				     column = 1;
				     break;   
		         //11)Extra Space
			     case 'E':
			    	 //keeps prior flag to revert after
			    	 //empty space is done
                     emptySpace = true;
				     break;     
		         //12)No Indentation
			     case 'N':
				     indent = 'N';
				     break;
		        //Default
			     default:
				     flag = 'L';
				     break;    	   
			   }  
		   }
		   else
		   {
			   
			       if(justification == 'T' && stringLength <= limit)
			       {
			    	   if(column ==1 )
			    	   {
			    	    stringExtra = indent(" " + stringExtra, indent, oldIndent, stringLeft);
			    	    stringExtra = justify(stringExtra,oldJustification,limit);
			    	    wholeText = spacing(wholeText,stringExtra,space);
			    	    stringExtra = "";
			    	   
			    	    currentString = justify(" " + currentString,justification,limit);
			    	    wholeText = spacing(wholeText,currentString,space);
			    	    justification = oldJustification;
			    	    currentString = "";
			    	   }
			       }
			       
			      //locks indenting and changes the
			      //max character limit to 34 to
				  if(column == 2 && oldColumn== 1)
				  {
					 limit = 34;
					 oldColumn = 2;
					 indent = 'N';
					 originalJustification = justification;
				  }
			   
			   //This IF statement formats all the strings in the current ArrayList.
			   //activates during four different conditions 
               //1)the column type just changed to 1 column from 2 column
			   //2)the column type is is still 2 column but the scanner has reached the end of 
			   //  available text
			   //3)the justification type has just changed and2 column is still active
			   //4)an empty space has just been called
			   //In all these cases,the ArrayList needs to be printed out and the flag
			   //variables need to be updated
			   
			   if((column == 1 && oldColumn==2) || (column == 2 && !scanFile.hasNextLine() && stringExtra == "" && !twoColumn.isEmpty())
			      || (column == 2 && justification != originalJustification) || (column == 2 && emptySpace==true))
			   {
				   if(justification == 'T')
				   {
					   twoColumn.add(stringExtra);
					   stringExtra = "";
				   }
				   
				   if(emptySpace == true && stringExtra.length() <= limit)
				   {
                       if(!stringExtra.startsWith(" "))
                       {
                    	   stringExtra = " " + stringExtra;
                       }
					   stringExtra = justify(stringExtra,originalJustification,limit);
					   twoColumn.add(stringExtra);
					   stringExtra = "";
					   
				   }
				      
				   int columnSize = twoColumn.size();
				   //if column size isn't even, add " " as
				   //the last string to create even appending
				   if(columnSize % 2 == 1)
				   {
					   twoColumn.add(" ");
					   columnSize = columnSize + 1;
				   }
				   int halfColumnSize = (columnSize/2) ;
				   
				   //if a string has been read from the file, it needs
				   //to be stored in a temporary variable so that
				   //it isn't overwritten
				   String oldString = currentString;
				   
				   for(int increment = 0; increment <= (columnSize/2)-1 ; increment++)
				   {
					   //finds 1st column line and associated 2nd column 
					   //line
					   String firstHalf = twoColumn.get(increment); 
					   String secondHalf = twoColumn.get(halfColumnSize);
					     
					   if(originalJustification == 'T')
					   {
						   firstHalf = justify(firstHalf,originalJustification,limit);
						   secondHalf = justify(secondHalf,originalJustification,limit);
					   }
					   					   					   					   
					   //appends the two lines and formats them to be 80 characters long
					   switch(originalJustification)
					   {
					     case 'L':
					       currentString = String.format("%-45s",firstHalf) +  String.format("%-35s",secondHalf);
					       break;
					     case 'R':
					       currentString = String.format("%35s",firstHalf) +  String.format("%45s",secondHalf);
					       break;
					     case 'C':
					       currentString = center(firstHalf,firstHalf.length(),35) + "          " + center(secondHalf,secondHalf.length(),35);
					       break;
					     case 'T':
						   currentString = center(firstHalf,firstHalf.length(),35) + "          " + center(secondHalf,secondHalf.length(),35);
						   justification = oldJustification;
						       break;
					     default:
					       currentString = String.format("%-45s",firstHalf) +  String.format("%-35s",secondHalf);
						       break;
					   } 
					   
					   //formats the combined String so it can be added to the the rest
					   //of the formatted text. The 35 character lines were already cut down when 
					   //they were separated
	                   wholeText = spacing(wholeText,currentString,space);
					   
					   halfColumnSize++;
				   }
				   //reverts the limit and column back to
				   //its previous state
				   twoColumn.clear();
				   oldColumn = column;
				   
				   //updates flag type variables based on the current situation
				   if(justification == originalJustification && emptySpace == false || column == 1)
				   {
				    column = 1;
				    limit = 79;
				   }
				   originalJustification = justification;
				
                  //also reverts Strings
				  currentString = oldString;
				  
				  //makes a new space if empty space was called
				  if(emptySpace == true && stringExtra == "")
				  {
					   wholeText = wholeText + "\n";
					   emptySpace = false;
				  }
				  
				  //finally,adds a space in between the columns and the next piece of
				  //text to differentiate where to read
				  //wholeText = wholeText + "\n";
			    }
			   
			    //indents for  a one column line that was not indented in a previous loop
                if((column == 1) && (!stringExtra.startsWith(indent("", indent, oldIndent,0))))
                {
     			   stringLeft = stringExtra.length();
    			   stringExtra = indent(stringExtra, indent, oldIndent, stringLeft);
    			   
    			   //reverts the current indentation to none if
    			   //indent was only meant for a single line 
    			   if(indent == 'I')
    			   {
    				   indent = 'N';
    			   } 
               }
                
			  //stringExtra will be added to currentString if the
			  //last line had leftovers.I fit ends with a space
              //a space was added in a prior loop
               if(stringExtra == "" || stringExtra.endsWith(" "))
               {
                  currentString = stringExtra + currentString;
               }
               else
               {
            	  currentString = stringExtra + " " + currentString;
               }
               
		      //empties stringExtra in case more leftovers appear in this
		      //line
		      stringExtra = "";
		      //newLine boolean so we know if we needs to make a new line
		      boolean newLine = false;
		  
		      //stringLength measured to see if it fits on the line
		      stringLength = currentString.length();
		      		      
	          //if the string size is less than or equal to 79, currentString becomes
		      //leftovers to be combined with the next scanned line
		      //(79 because we need to account for the space)
		      if(stringLength < limit)
		      {
		    	  //if part of the string hasn't been cut in the previous loop
		    	  if(!stringExtra.startsWith(indent( " ", indent, oldIndent,1)))
		    	  {
		    	    stringExtra = " " + currentString;
		    	  }
		    	  //else space is not needed
		    	  else
		    	  {
		    		stringExtra = currentString;  
		    	  }
		    	  
		    	  currentString = "";
		    	  stringLeft = stringExtra.length();
		    	  stringLength = currentString.length();
		    	  
		    	  if(emptyScanLine == true)
		    	  {
		    		 currentString = stringExtra;
		    		 stringExtra = "";
		    		 
		    		 stringLeft = stringExtra.length();
			    	 stringLength = currentString.length(); 
		    		 emptyScanLine = false;
		    		 newLine = true;
		    	  }
		    	  else
		    	  //makes sure stringExtra is clear
		    	  {
				    newLine = false;
		    	  }

		      }
		      //if length of the text line can fit exactly all 79
		      //stringLength characters
		      else if(stringLength == limit)
			  {
		    	   //if part of the string hasn't been cut in the previous loop
		    	  if(!currentString.startsWith(indent( " ", indent, oldIndent,1)))
		    	  {
		             //we need the space
				    currentString =  " " + currentString;
				    
				    //stringLength updated to account for space
				    stringLength = currentString.length();
		    	  }
				  					  
			     //stringExtra not needed since no leftovers
			     stringExtra = "";
			     stringLeft = stringExtra.length();
					  
			     //the current line is to its maximum now,so we
			     //need a new line
			     newLine= true;
			  }
		      //if currentString is greater than the max number of characters
		      //per line
			  else if(stringLength > limit)
			  {
			    //79 includes eventual space that needs to be appended with the final string
			    //lastSpaceIndex  finds the index of the last space (or the index prior to the start
			   //of the last word)
			   int lastSpaceIndex = (currentString.substring(0,limit).lastIndexOf(" "));
					  
		      //if space was not found, the string cannot fit in the line
			  //,so it is stored in stringExtra for the next line
			  if(lastSpaceIndex == -1)
			  {
				  stringExtra = currentString;
				  currentString =  "";
				  newLine = false;
			  }
			  else
			  {
				 //we want to ignore the last space index
				 stringExtra = currentString.substring(lastSpaceIndex + 1 ,stringLength); 
				 stringLeft = stringExtra.length();
				 
				 //if part of the string hasn't been cut in the previous loop
				 if(!currentString.startsWith(indent( " ", indent, oldIndent,1)))
				 {
				   //currentString gets all the text <=80 till the last word
			       currentString = " " + currentString.substring(0,lastSpaceIndex);
				 }
				 //else the space was added in a previously loop so it is not needed
				 else
				 {
				   currentString = currentString.substring(0,lastSpaceIndex);
				 }
				 
				 stringLength = currentString.length();
				 newLine = true; 
			  }
			  
		    }
		        
		    //if leftover characters still exist after all the text file's
		    //been read,clear stringExtra to get the exit condition and
		    // make one final line
		    if((!scanFile.hasNext()) && (stringLeft <= limit) && (stringLength == 0))
		    {	  
		    	  currentString = stringExtra;
		    	  stringExtra= "";
		    	  
		    	  stringLength = currentString.length();
		    	  stringLeft = stringExtra.length();
		    	  newLine = true;
		    }
            
		    //adds empty space 
			if(emptySpace == true)
		    {
			   wholeText = wholeText + "\n";
			   emptySpace = false;
		    }
			
			//formats line if 1 column is currently used. Doesn't 
			//format the 35 character lines because it would ruin the look
			//of the lines when we append two together
            if(column == 1)
            {
    		    //we can just format the whole line when its done or if it
    		    if( (newLine == true || justification == 'T'))
    		    {
    		        currentString = justify(currentString,justification,limit);
                    wholeText = spacing(wholeText,currentString,space);
    		    }
            }//end of if block for (column == 1) 
 		   //adds currentString to ArrayList if 2 column flag is active
           else
 		   {
        	    currentString = justify(currentString,originalJustification,limit);
 		        twoColumn.add(currentString);
 		  
 		   }
           
          //justification if statement put here to account
          //for both 1 column and 2 column cases
          if(justification == 'T')
  		  {
  		    	justification = oldJustification;
  		  }
            
		  //emptying currentString here is not necessary. I did it to
          //show that the currentString always resets after a loop
		  stringLength = 0;
		  currentString ="";
		  
	     }//end of else block that formats everything
     } //end of .hasNextLine() While Loop
	   
	 //adds all text to JTextArea and
	 previewT.setText(wholeText);
	 previewT.updateUI();
	 scanFile.close();
   }//end of FileFormatter constructor
   
   /************************************************************************
    * indent indents a line based on the indent parameter. A 'N' 
    * character causes no . An 'I' character indents a line 5 spaces.Finally, a
    * 'B' character indents a line 10 spaces.
    * 
    * @param  a string to indent (currentString), and indent type (indent), and
    *          the length of an indentation (indentSize)
    * @return indent() returns a String that has been indented
    *************************************************************************/
   String indent(String currentString, char indent, char oldIndent, int indentSize)
   {
	   //changes indent size based on current indent flag
	   if(indent  == 'I')
	   {
		   indentSize = indentSize + 5;
		   currentString = java.lang.String.format("%" + indentSize + "s", currentString);
		   indent = oldIndent;
	   }
	   else if(indent == 'B')
	   {
		   indentSize = indentSize + 10;
		   currentString = java.lang.String.format("%" + indentSize + "s", currentString);
	   }
	   return currentString;
   }
   
   /*********************************************************************
    * center left and right justifies a string towards a center position.
    * The center depends on the length of the String (stringLength) and
    * the maximum  of characters per line (limit).
    * 
    * @param  a string to center (currentString), the length of the string
    *         (stringLength), and the maximum amount of characters per
    *         line (limit).
    * @return center() returns a String that has been centered
    *********************************************************************/
   String center(String currentString, int stringLength,int limit)
   {
	   //adds 1 to the limit because the space has already been added
	   //at this point
	   limit = limit +1;
	   
	   //the left and right padding for the string
	   int startPad = (limit- stringLength)/2;
	   int endPad = limit - (stringLength + startPad);
	   
	   //attempts to equalize padding to generate
	   //more centered result
	   if(startPad != endPad)
	   {
		   startPad = startPad + 1;
	   }
	   
	   if(startPad > 0 && endPad > 0)
	   {
	      //translates amount of padding into number of spaces
		  String startPadding = String.format("%"+startPad+"s", "");
		  String endPadding = String.format("%"+endPad+"s", "");
		  
		  //left justifies String based on amount of left and right padding,
		  //mimicking left and right justification
		  currentString = String.format("%" + limit +"s", startPadding + currentString + endPadding);
	   }
	   else
	   {
             //no centering was needed
			 currentString = String.format("%" + limit + "s",currentString); 
	   } 
	   return currentString;
   }
   
    /***********************************************************************
    * Justifies line of text (currentString) given a justification character
    * (justification), and a maximum number of characters (limit). A 'L'
    * character left justifies. A 'R' character right justifies. A 'C'
    * character centers a line, and and 'T' character centers only a single
    * line regardless of current justification.
    * 
    * @param  a string to justify (currentString),the justification type
    *         (justification), and the maximum amount of characters per
    *         line (limit).
    * @return justify() returns a String that has been justified
    * ********************************************************************/
   String justify(String currentString, char justification, int limit)
   {  
	   switch(justification) 
       {
    	 //the plan is to justify (Left,Right,Center,or Title) based on justification
    	//NOTE: For Title, change justification back to 'L'
    	case 'L':
    		 currentString = String.format("%-" + limit + "s", currentString);
    		 break;
    	case 'R':
    		 //working on it
    		currentString = String.format("%" + limit + "s", currentString);
    		 break;
    	case 'C':
    	    currentString = center(currentString,stringLength,limit);
    		 break;
    	case 'T':
    		 currentString = center(currentString,stringLength,limit);
    		 break;
       default:
    	     currentString = String.format("%-" + limit + "s", currentString);
    		 break;
    	}
    	
	   return currentString;
   }
   
   /*****************************************************************
   * Changes the line spacing after a String (currentString) based on
   * on a space type character (space). A 'S' character creates a 
   * single spaced line. A 'D' character creates a double spaced line.
   * 
   * @param  a string to space (currentString),the spacing type
   *         (space), and a output string that is used as the
   *         return value (wholeText)
   *         
   * @return spacing() returns a String that has been spaced
   ******************************************************************/
   String spacing(String wholeText, String currentString, char space)
   {
	
	//this adds one empty line, flag kept the prior
	// space flag ('S' or 'D') 
   	switch(space)
   	{
   	
   	  //based on the space flag, we add an extra '/n' for double and the default for
   	  // single. I think I have an idea for 2 Column,be we might have to change
   	  //things around based on the result
   	case 'S':
   		 wholeText = wholeText + currentString + "\n";
   		 break;
   	case 'D':
   		 wholeText = wholeText + (currentString +"\n\n");
   		 break;
      default:
   	     wholeText = wholeText +  currentString + "\n";
   		 break;
    }
   	return wholeText; 	   
  }
   
}//end of FileFormatter Class
