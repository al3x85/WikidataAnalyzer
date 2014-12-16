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
	
	//HAAAAAAALLLLLLLLLLLLLLLOOOOOOOOOOOOOOO
	
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
      String LabelLang = "en";
	  
	  
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
	    TreeMapEntry root = new TreeMapEntry();
	    root.Label = "entity";
	    root.ItemID = "http://www.wikidata.org/entity/Q35120";
	    createEntry(new ArrayList<TreeMapEntry>(), root , 2, excelSheet);
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
	    test.setOutputFile("C:/Users/alex/git/LocalWikidataAnalyzer/Wikidata Analyzer/TreeMapSourceData.xls");
	    test.write();
	    System.out
	        .println("Please check the result file under C:/Users/alex/git/LocalWikidataAnalyzer/Wikidata Analyzer ");
	  }
	  
	  public void createEntry(List<TreeMapEntry> ancestors, TreeMapEntry current, int row, WritableSheet sheet) throws WriteException,
      RowsExceededException {
		  //gibt die Hierarchietiefe an
		  if(ancestors.size() < 3)
		  {
		  VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
	      VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT distinct ?s ?o WHERE {{?s <http://www.wikidata.org/entity/P279c> <"+current.ItemID+"> .} OPTIONAL {{?s <http://www.w3.org/2000/01/rdf-schema#label> ?o} FILTER (lang(?o) = \"\" || lang(?o) = \""+LabelLang+ "\")}}", graph);
	      List<TreeMapEntry> subnodes = new ArrayList<TreeMapEntry>();
	      try {
				ResultSet results = vqe.execSelect();
				while (results.hasNext()) {
					QuerySolution result = results.nextSolution();
					RDFNode s = result.get("s");
					String s2 = s.toString();
				
					RDFNode o = result.get("o");
					String o2 = "";
					if (o != null) {
						o2 = o.toString();
					}
					
					TreeMapEntry e =new TreeMapEntry();
					e.ItemID=s2;
					e.Label=o2;
					//String label2 = label.toString();
					subnodes.add(e);
				 }
	      }
	      finally {
	    	  vqe.close();
	      }
	      ArrayList<TreeMapEntry> clone = new ArrayList<TreeMapEntry>(ancestors); 
	      clone.add(current);
	      if (!subnodes.isEmpty()) {
	    	  for (TreeMapEntry node : subnodes) {
	    		  String summe4="";
	    		  VirtuosoQueryExecution vqe2 = VirtuosoQueryExecutionFactory.create("SELECT (COUNT(?s) AS ?Summe) FROM <http://simpleStatements> WHERE {?s <http://www.wikidata.org/entity/P279c> <"+node.ItemID+"> OPTION(TRANSITIVE, T_DISTINCT)}", graph);
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
	  
	  public void createContent(int row, String weight, List<TreeMapEntry> nodes, TreeMapEntry currentNode, WritableSheet sheet) throws WriteException,
      RowsExceededException{
		  sheet.insertRow(row);
		  addLabel(sheet, 0, row, weight);
		  for (int i=0; i<=nodes.size()-1;i++) {
			  TreeMapEntry x = nodes.get(i);
			  if(x.Label != "")
			  {
				  addLabel(sheet, i+2, row , x.Label.replace("@"+LabelLang, ""));
			  }
			  else
			  {
				  addLabel(sheet, i+2, row , x.ItemID);
				 
			  }
		}
		  if(currentNode.Label != "")
		  {
			  addLabel(sheet, nodes.size()+2, row , currentNode.Label.replace("@"+LabelLang, ""));
		  }
		  else
		  {
			  addLabel(sheet, nodes.size()+2, row , currentNode.ItemID);
			 
		  }
	  }

	  
	  
}
