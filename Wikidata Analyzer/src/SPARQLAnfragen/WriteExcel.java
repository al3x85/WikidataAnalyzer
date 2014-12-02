package SPARQLAnfragen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class WriteExcel {
	
	 public WritableCellFormat timesBoldUnderline;
	  public WritableCellFormat times;
	  public String inputFile;
	  //public String root = "http://www.wikidata.org/entity/Q35120";
	  List listRoot = new ArrayList<String>();
	  List listChildsToAnalyse = new ArrayList<String>();
	  int lastRowNumber=0;
	  //int rowNumber =2;
      //int columnNumber =0;
      int row = 2;
      int column = 3;
      String currentNodeLabel="";
	  
	  
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
	    createEntry(new ArrayList<String>(), "http://www.wikidata.org/entity/Q35120", 2, excelSheet);
	    /*createContent(excelSheet,2,0, listChildsToAnalyse.get(listChildsToAnalyse.size()-1).toString());
	    for (int x=0;x<=10000;x++) {
	    createContent(excelSheet,lastRowNumber,0, listChildsToAnalyse.get(listChildsToAnalyse.size()-1).toString());
	    }*/
	    
	    //createContent(excelSheet, listRoot.get(1).toString());
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

	 /* public void createContent(WritableSheet sheet, int rowNumber, int columnNumber, String root) throws WriteException,
	      RowsExceededException {
		  listRoot.add(root);
	      listChildsToAnalyse.remove(listChildsToAnalyse.size()-1);
	      VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
	      VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT ?s WHERE {?s <http://www.wikidata.org/entity/P279c> <"+listRoot.get(listRoot.size()-1)+">}", graph);
			try {
				ResultSet results = vqe.execSelect();
				while (results.hasNext()) {
					QuerySolution result = results.nextSolution();
					RDFNode s = result.get("s");
					String s2 = s.toString();
					listRoot.add(s2);
					listChildsToAnalyse.add(s2);
					for (int n=0; n<=(listRoot.size()-1);n++) {
						addLabel(sheet, column+n, rowNumber, listRoot.get(n).toString() );
					}
					//addLabel(sheet, column, row, s2);
					//addLabel(sheet, 2,row,listRoot.get(0).toString());
					row++;
					VirtuosoQueryExecution vqe2 = VirtuosoQueryExecutionFactory.create("SELECT (COUNT(?s) AS ?Summe) FROM <http://simpleStatements> WHERE {?s <http://www.wikidata.org/entity/P279c> <"+s2+"> OPTION(TRANSITIVE, T_DISTINCT)}", graph);
					try {
						ResultSet results2 = vqe2.execSelect();
						while (results2.hasNext()) {
							QuerySolution result2 = results2.nextSolution();
							RDFNode summe = result2.get("Summe");
							String summe2 = summe.toString();
							String[] summe3 = summe2.split(Pattern.quote("^"),2);
							addNumber(sheet, columnNumber, rowNumber, Integer.parseInt(summe3[0].toString()));
							rowNumber++;
							lastRowNumber = rowNumber;
							listRoot.remove(listRoot.size()-1);	
							
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
				listChildsToAnalyse.remove(listChildsToAnalyse.size()-1);
			}	
			System.out.println("createSubclassesAndWeight() Fertig.");
	      
	      
	    
	  }*/

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

	  public static void main(String[] args) throws WriteException, IOException {
	    WriteExcel test = new WriteExcel();
	    test.setOutputFile("D:/workspace Test/Wikidata Analyzer/TreeMapSourceData.xls");
	    test.write();
	    System.out
	        .println("Please check the result file under D:/workspace Test/Wikidata Analyzer/TreeMapSourceData.xls ");
	  }
	  
	  public void createEntry(List<String> ancestors, String current, int row, WritableSheet sheet) throws WriteException,
      RowsExceededException {
		  //gibt die Hierarchietiefe an
		  if(ancestors.size() < 3)
		  {
		  VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
	      VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT ?s ?label WHERE {?s <http://www.wikidata.org/entity/P279c> <"+current+">}", graph);
	      List<String> subnodes = new ArrayList<String>();
	      try {
				ResultSet results = vqe.execSelect();
				while (results.hasNext()) {
					QuerySolution result = results.nextSolution();
					RDFNode s = result.get("s");
					//RDFNode label = result.get("label");
					String s2 = s.toString();
					//String label2 = label.toString();
					subnodes.add(s2);
				}
	      }
	      finally {
	    	  vqe.close();
	      }
	      ArrayList<String> clone = new ArrayList<String>(ancestors); 
	      clone.add(current);
	      if (!subnodes.isEmpty()) {
	    	  for (String node : subnodes) {
	    		  String summe4="";
	    		  VirtuosoQueryExecution vqe2 = VirtuosoQueryExecutionFactory.create("SELECT (COUNT(?s) AS ?Summe) FROM <http://simpleStatements> WHERE {?s <http://www.wikidata.org/entity/P279c> <"+node+"> OPTION(TRANSITIVE, T_DISTINCT)}", graph);
					try {
						ResultSet results2 = vqe2.execSelect();
						while (results2.hasNext()) {
							QuerySolution result2 = results2.nextSolution();
							RDFNode summe = result2.get("Summe");
							String summe2 = summe.toString();	
							String [] summe3 = summe2.split(Pattern.quote("^"),2);
							summe4 = summe3[0];
					}
					}
					finally {
						vqe2.close();
					}
					
					createContent(row, summe4, clone, node, sheet);
					row++;
					createEntry(clone, node, row, sheet);
			}
	      }
		  }
	  }
	  
	  public void createContent(int row, String weight, List<String> nodes, String currentNode, WritableSheet sheet) throws WriteException,
      RowsExceededException{
		  sheet.insertRow(row);
		  addLabel(sheet, 0, row, weight);
		  for (int i=0; i<=nodes.size()-1;i++) {
			  addLabel(sheet, i+2, row, nodes.get(i).toString());
		}
		  addLabel(sheet, nodes.size()+2, row , currentNode);
	  }

}
