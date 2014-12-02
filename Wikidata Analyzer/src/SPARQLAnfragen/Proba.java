package SPARQLAnfragen;

import java.io.*;
import java.util.List;


public class Proba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Proba p = new Proba();
//	      p.start();
	      p.start2();
	}
	
	/**
	    * Construct a DOT graph in memory, convert it
	    * to image and store the image in the file system.
	    */
	   private void start()
	   {
		  List<String> x2 = Reader.readFile("Subklassen_temp.txt");
		  String[] x = x2.toArray(new String[x2.size()]);	 
		   
	      GraphViz gv = new GraphViz();
	      
	      gv.addln(gv.start_graph());
	      for (int i=0; i<= x.length-1;i=i+1) {
	      String y = x[i].substring(31);
	      gv.addln("Q35120 -> "+y+";");
	    	      
	      }
	      gv.addln(gv.end_graph());
	      System.out.println(gv.getDotSource());
	      
//	      String type = "gif";
//	      String type = "dot";
//	      String type = "fig";    // open with xfig
//	      String type = "pdf";
//	      String type = "ps";
//	      String type = "svg";    // open with inkscape
	      String type = "png";
//	      String type = "plain";
//	      File out = new File("/tmp/out." + type);   // Linux
	      File out = new File("D:/workspace Test/Wikidata Analyzer/out." + type);    // Windows
	      gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	   }
	   
	   /**
	    * Read the DOT source from a file,
	    * convert to image and store the image in the file system.
	    */
	   private void start2()
	   {
	    //  String dir = "/home/jabba/eclipse2/laszlo.sajat/graphviz-java-api";     // Linux
	    //  String input = dir + "/sample/simple.dot";
		   String input = "D:/workspace Test/graphviz-java-api/sample/simple4.dot";    // Windows
		   
		   GraphViz gv = new GraphViz();
		   gv.readSource(input);
		   System.out.println(gv.getDotSource());
	   		
	      String type = "gif";
//	    String type = "dot";
//	    String type = "fig";    // open with xfig
//	    String type = "pdf";
//	    String type = "ps";
//	    String type = "svg";    // open with inkscape
//	    String type = "png";
//	      String type = "plain";
		//   File out = new File("/tmp/simple." + type);   // Linux
		   File out = new File("D:/workspace Test/Wikidata Analyzer/out2." + type);   // Windows
		   gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	   }

}
