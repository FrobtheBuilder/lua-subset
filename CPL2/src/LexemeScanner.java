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
		
		String d = ""; 
		String c = a.replaceAll("\\s+", "");
		char[] b = a.toCharArray();
		
		for(int i=0;i<b.length;i++)
		{
			d += b[i];
			{
			if(d.equals("function"))
			{
				ListString.add("function");
				d = "";
			}
			else if(d.equals("a"))
			{
				ListString.add("a");
				d = "";
			}
			else if(d.equals("("))
			{
				ListString.add("(");
				d = "";
			}
			else if(d.equals(")"))
			{
				ListString.add(")");
				d = "";
			}
			else if(d.equals("x"))
			{
				ListString.add("x");
				d = "";
			}
			else if(d.equals("="))
			{
				ListString.add("=");
				d = "";
			}
			else if(d.equals("4"))
			{
				ListString.add("4");
				d = "";
			}
			else if(d.equals("end"))
			{
				ListString.add("end");
				d = "";
			}
			}
			
			
		}
		e.close();
		System.out.println(ListString.toString());
	}

}
