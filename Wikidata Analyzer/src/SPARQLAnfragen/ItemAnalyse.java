package SPARQLAnfragen;

import java.util.regex.Pattern;

import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;
import virtuoso.jena.driver.VirtGraph;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.query.*;

public class ItemAnalyse {
	
	public static void StatementsPerItem() {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no) LIMIT 30", graph);
		Writer.schreiben("Das Item " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("i");
				RDFNode no = result.get("no");
				String i2 = i.toString();
				String no2 = no.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
				String[] no3 = no2.split(Pattern.quote("^"),2);
				Writer.schreiben(no3[0].toString()+"\n", "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println("StatementsPerItem() Fertig.");
	}
	
	/*gibt die durchschnittliche Anzahl an Statements pro Item an*/
	public static void AverageStatementPerItem() {
		double items=0;
		double statements=0;
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT (count (distinct ?s) as ?no) WHERE {?s ?p ?o}", graph);	
		Writer.schreiben("Anzahl der unterschiedlichen Items: " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode no = result.get("no");
				String no2 = no.toString();
				String[] no3 = no2.split(Pattern.quote("^"),2);
				Writer.schreiben(no3[0], "Distribution_Item.txt");
				items = Integer.parseInt(no3[0]);
												}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		VirtuosoQueryExecution vqe2 = VirtuosoQueryExecutionFactory.create("SELECT count (?p) as ?no WHERE {?s ?p ?o}", graph);	
		Writer.schreiben("Anzahl der Statements: " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results2 = vqe2.execSelect();
			while (results2.hasNext()) {
				QuerySolution result2 = results2.nextSolution();
				RDFNode no = result2.get("no");
				String no2 = no.toString();
				String[] no3 = no2.split(Pattern.quote("^"),2);
				Writer.schreiben(no3[0], "Distribution_Item.txt");
				statements = Integer.parseInt(no3[0]);
												}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println("AverageStatementPerItem() Fertig.");
		double average = statements/items;
		System.out.println("Durchschnittliche Anzahl: "+average);
		Writer.schreiben("Durchschnittliche Anzahl an Statements/Item: ", "Distribution_Item");
		Writer.schreiben(average, "Distribution_Item.txt");
	}

	public static void ItemsWithAtLeast5Statement () {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		/*Anfrage gibt Items mit mind. 5 Statements wieder */
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no > 4)}", graph);
		Writer.schreiben("Anzahl Items die mind. 5 Statements haen in " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("number");
				String i2 = i.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println("ItemsWithAtLeast5Statement () Fertig.");
	}
	
	public static void ItemsWithAtLeast4Statement () {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		/*Anfrage gibt Items mit mind. 5 Statements wieder */
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no > 3)}", graph);
		Writer.schreiben("Anzahl Items die mind. 4 Statements haen in " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("number");
				String i2 = i.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println("ItemsWithAtLeast4Statement () Fertig.");
	}
	
	public static void ItemsWithAtLeast3Statement () {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		/*Anfrage gibt Items mit mind. 5 Statements wieder */
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no > 2)}", graph);
		Writer.schreiben("Anzahl Items die mind. 3 Statements haen in " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("number");
				String i2 = i.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println("ItemsWithAtLeast3Statement () Fertig.");
	}
	
	public static void ItemsWithAtLeast2Statement () {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		/*Anfrage gibt Items mit mind. 5 Statements wieder */
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no > 1)}", graph);
		Writer.schreiben("Anzahl Items die mind. 2 Statements haen in " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("number");
				String i2 = i.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println("ItemsWithAtLeast2Statement () Fertig.");
	}
	
	public static void ItemsWithAtLeast1Statement () {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		/*Anfrage gibt Items mit mind. 5 Statements wieder */
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no > 0)}", graph);
		Writer.schreiben("Anzahl Items die mind. 1 Statements haen in " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("number");
				String i2 = i.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println("ItemsWithAtLeast1Statement Fertig.");
	}
	
	public static void ItemsWith0Statement () {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		/*Anfrage gibt Items mit mind. 5 Statements wieder */
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no = 0)}", graph);
		Writer.schreiben("Anzahl Items die 0 Statements haen in " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("number");
				String i2 = i.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println(" ItemsWith0Statement () Fertig.");
	}
	
	public static void ItemsWithAtLeast6Statement () {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		/*Anfrage gibt Items mit mind. 5 Statements wieder */
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no > 5)}", graph);
		Writer.schreiben("Anzahl Items die mind. 6 Statements haben in " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("number");
				String i2 = i.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println(" ItemsWithAtLeast6Statement () Fertig.");
	}
	
	public static void ItemsWithAtLeast7Statement () {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		/*Anfrage gibt Items mit mind. 5 Statements wieder */
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no > 6)}", graph);
		Writer.schreiben("Anzahl Items die mind. 7 Statements haben in " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("number");
				String i2 = i.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println(" ItemsWithAtLeast7Statement () Fertig.");
	}
	
	public static void ItemsWithAtLeast8Statement () {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		/*Anfrage gibt Items mit mind. 5 Statements wieder */
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no > 7)}", graph);
		Writer.schreiben("Anzahl Items die mind. 8 Statements haben in " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("number");
				String i2 = i.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println(" ItemsWithAtLeast8Statement () Fertig.");
	}
	
	public static void ItemsWithAtLeast9Statement () {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		/*Anfrage gibt Items mit mind. 5 Statements wieder */
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no > 8)}", graph);
		Writer.schreiben("Anzahl Items die mind. 9 Statements haben in " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("number");
				String i2 = i.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println(" ItemsWithAtLeast9Statement () Fertig.");
	}
	
	public static void ItemsWithAtLeast10Statement () {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		/*Anfrage gibt Items mit mind. 5 Statements wieder */
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no > 9)}", graph);
		Writer.schreiben("Anzahl Items die mind. 10 Statements haben in " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("number");
				String i2 = i.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println(" ItemsWithAtLeast10Statement () Fertig.");
	}
	
	public static void ItemsWithAtLeast25Statement () {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		/*Anfrage gibt Items mit mind. 5 Statements wieder */
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no > 24)}", graph);
		Writer.schreiben("Anzahl Items die mind. 25 Statements haben in " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("number");
				String i2 = i.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println(" ItemsWithAtLeast25Statement () Fertig.");
	}
	
	public static void ItemsWithAtLeast50Statement () {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		/*Anfrage gibt Items mit mind. 5 Statements wieder */
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no > 49)}", graph);
		Writer.schreiben("Anzahl Items die mind. 50 Statements haben in " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("number");
				String i2 = i.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println(" ItemsWithAtLeast50Statement () Fertig.");
	}
	
	public static void ItemsWithAtLeast100Statement () {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		/*Anfrage gibt Items mit mind. 5 Statements wieder */
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no > 99)}", graph);
		Writer.schreiben("Anzahl Items die mind. 100 Statements haben in " + graph.getGraphName()+":", "Distribution_Item.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("number");
				String i2 = i.toString();
				Writer.schreiben(i2, "Distribution_Item.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println(" ItemsWithAtLeast100Statement () Fertig.");
	}
	
	
	/* gibt die Anzahl der Items im Graph an, die mindestens eine bestimmte Anzahl an Statements haben und speichert diese um den Graph zu zeichnen*/
	public static void ClassesCreateDrawDistributionOfItemsWithStatements() {
		
		
		int[] j = {1,2,3,4,5,6,7,8,10,11,12,13,14,15,16,17,18,19,20,25,50};
		for (int i=0; i<=j.length-1; i=i+1) {
			
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?i) As ?number Where {{SELECT ?i count(?p) As ?no WHERE {?i ?p ?o} Group By ?i ORDER BY desc(?no)} Filter(?no > "+j[i]+")}", graph);
		Writer.schreiben(j[i],"Distribution_Items_with_Statements_Draw_x.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode s = result.get("number");
				String s2 = s.toString();
				String[] s3 = s2.split(Pattern.quote("^"),2);
				Writer.schreiben(s3[0].toString(),"Distribution_Items_with_Statements_Draw_y.txt");
			}} 
			finally {
				vqe.close();			
		}}
		System.out.println("ClassesCreateDrawDistributionOfItemsWithStatements() Fertig.");
	}
	
	
	
}
