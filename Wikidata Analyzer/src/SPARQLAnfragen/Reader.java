package SPARQLAnfragen;

import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class Reader {
	
	
	
	public static List<String> readFile(String filename) { 
		File file = new File(filename);

		List<String> list1 = new ArrayList<String>();
		
	    FileReader fr = null;  
		try {
		
		  fr = new FileReader(file);
		  BufferedReader reader = new BufferedReader(fr);
	      
	      String zeile = reader.readLine();
	      while (zeile != null) {
	    	  list1.add(zeile);
	    	  System.out.println(zeile);
	    	  zeile = reader.readLine();  	  
	      }
	      reader.close();
		} catch (IOException e) {
			System.out.println("Fehler");
		}
		finally {
	      try {fr.close(); } catch (Exception e ) {}
		}
		return list1;
	}
	
	
	

}
