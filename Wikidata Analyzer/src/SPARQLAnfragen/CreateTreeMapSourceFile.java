package SPARQLAnfragen;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class CreateTreeMapSourceFile {
	
	 public WritableCellFormat timesBoldUnderline;
	  public WritableCellFormat times;
	  public String inputFile;

	
	
	
	public static void main(String[] args) throws WriteException, IOException {
		// TODO Auto-generated method stub
		//createSubclassesAndWeight();
		 WriteExcel test = new WriteExcel();
		    test.setOutputFile("D:/workspace Test/Wikidata Analyzer/TreeMapSourceData.xls");
		    test.write();
		    System.out
		        .println("D:/workspace Test/Wikidata Analyzer/TreeMapSourceData.xls ");
	}
	
	
	public static void createSubclassesAndWeight() {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		String root ="http://www.wikidata.org/entity/Q35120";
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT ?s WHERE {?s <http://www.wikidata.org/entity/P279c> <"+root+">}", graph);
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode s = result.get("s");
				String s2 = s.toString();
				VirtuosoQueryExecution vqe2 = VirtuosoQueryExecutionFactory.create("SELECT (COUNT(?s) AS ?Summe) FROM <http://simpleStatements> WHERE {?s <http://www.wikidata.org/entity/P279c> <"+s2+"> OPTION(TRANSITIVE, T_DISTINCT)}", graph);
				try {
					ResultSet results2 = vqe2.execSelect();
					while (results2.hasNext()) {
						QuerySolution result2 = results2.nextSolution();
						RDFNode summe = result2.get("Summe");
						String summe2 = summe.toString();
						
					}
				}
				finally {
					vqe2.close();
				}
			
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println("createSubclassesAndWeight() Fertig.");
	}
	

	  
	public void setOutputFile(String inputFile) {
	  this.inputFile = inputFile;
	  }

	  public void write() throws IOException, WriteException {
	    File file = new File(inputFile);
	    WorkbookSettings wbSettings = new WorkbookSettings();

	    wbSettings.setLocale(new Locale("en", "EN"));

	    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
	    workbook.createSheet("Report", 0);
	    WritableSheet excelSheet = workbook.getSheet(0);
	    createLabel(excelSheet);
	    createContent(excelSheet);

	    workbook.write();
	    workbook.close();
	  }

	  public void createLabel(WritableSheet sheet)
	      throws WriteException {
	    // Lets create a times font
	    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
	    // Define the cell format
	    times = new WritableCellFormat(times10pt);
	    // Lets automatically wrap the cells
	    times.setWrap(true);

	    // create create a bold font with unterlines
	    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
	        UnderlineStyle.SINGLE);
	    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
	    // Lets automatically wrap the cells
	    timesBoldUnderline.setWrap(true);

	    CellView cv = new CellView();
	    cv.setFormat(times);
	    cv.setFormat(timesBoldUnderline);
	    cv.setAutosize(true);

	    // Write a few headers
	    addCaption(sheet, 0, 0, "Size");
	    addCaption(sheet, 0, 1, "INTEGER");
	    

	  }

	  public void createContent(WritableSheet sheet) throws WriteException,
	      RowsExceededException {

	    // now a bit of text
	    CreateTreeMapSourceFile.createSubclassesAndWeight();
		  for (int i = 12; i < 20; i++) {
	      // First column
	      addLabel(sheet, 0, i, "Boring text " + i);
	      // Second column
	      addLabel(sheet, 1, i, "Alexander");
	    }
	  }

	  private void addCaption(WritableSheet sheet, int column, int row, String s)
	      throws RowsExceededException, WriteException {
	    Label label;
	    label = new Label(column, row, s, timesBoldUnderline);
	    sheet.addCell(label);
	  }

	  public void addNumber(WritableSheet sheet, int column, int row,
	      Integer integer) throws WriteException, RowsExceededException {
	    Number number;
	    number = new Number(column, row, integer, times);
	    sheet.addCell(number);
	  }

	  public void addLabel(WritableSheet sheet, int column, int row, String s)
	      throws WriteException, RowsExceededException {
	    Label label;
	    label = new Label(column, row, s, times);
	    sheet.addCell(label);
	  }

}
