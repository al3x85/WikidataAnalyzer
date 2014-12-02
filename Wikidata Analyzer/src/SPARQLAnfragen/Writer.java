package SPARQLAnfragen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

public class Writer {

	static FileWriter writer;
	static File file;
	
	public static void schreiben(String s, String p, String o, String filename) {
		file = new File(filename);
		try {
			writer = new FileWriter(file, true);
			writer.write(s +" " + p + " " + o +"\n");
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
		      e.printStackTrace();
		    }
	}
	public static void schreiben(String s, String filename) {
		file = new File(filename);
		try {
			writer = new FileWriter(file, true);
			writer.write(s);
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
		      e.printStackTrace();
		    }
	}
	
	public static void schreiben(double s, String filename) {
		file = new File(filename);
		try {
			writer = new FileWriter(file, true);
			writer.write(s+"\n");
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
		      e.printStackTrace();
		    }
	}
	
	public static void deleteFile(String filename) {
		file = new File(filename);
		if (file.exists()) {
			file.delete();
		}
	}
	
	
}
