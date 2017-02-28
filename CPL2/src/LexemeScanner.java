import java.util.*;
import java.io.*;

public class LexemeScanner {

final static File myFile = new File("test5.lua");
static String a = "";


	public static void main(String[] args) throws Exception {
		ArrayList<String> ListString = new ArrayList<String>();
		Scanner e = new Scanner(myFile);
		while(e.hasNextLine())
		{
			a += e.nextLine();
		}
		
		String f = ""; //Finding if the string equals to a token
		String d = ""; //Reading the char b array and adding it together
		char[] b = a.toCharArray();
		
		/*
		 * This part is where it will read the string and breaking up the string
		 * into substring after after whitespace it detects. 
		 */
		
		for(int i=0;i<b.length;i++)
		{
			d += b[i];
			if(Character.isWhitespace(b[i]))
			{
				f = d.substring(0, d.length()-1); //Setting newly created string
				d = ""; //Resetting string to nothing
			if(f.equals("function"))
			{
				ListString.add("function");
				f = "";
			}
			else if(Character.isAlphabetic(f.charAt(0)) && f.length() == 1) //Detecting any letters
			{															    //length of string is 1
				ListString.add(f);
				f = "";
			}
			else if(f.equals("("))
			{
				ListString.add("(");
				f = "";
			}
			else if(f.equals(")"))
			{
				ListString.add(")");
				f = "";
			}
			else if(f.equals("="))
			{
				ListString.add("=");
				f = "";
			}
			else if(Character.isDigit(f.charAt(0)) && f.length() == 1) //Detecting any digits and 
																	   //length of string is 1
			{
				ListString.add(f);
				f = "";
			}
			else if(f.equals("end"))
			{
				ListString.add("end");
				f = "";
			}
			else
				System.out.println("Invalid lexeme: " + f );
			
			}
		}
		
		e.close();
		System.out.println("Token found: " + ListString.toString());
}
}

