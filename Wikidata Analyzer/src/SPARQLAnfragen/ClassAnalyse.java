package SPARQLAnfragen;

import java.util.regex.Pattern;

import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;
import virtuoso.jena.driver.VirtGraph;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.query.*;
import java.util.ArrayList;
import java.util.*;

public class ClassAnalyse {
	
	
	public static void NumberOfSubClassOfEntity() {
		VirtGraph graph = new VirtGraph ("http://taxonomy.org", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT (COUNT(?s) AS ?Summe) WHERE {?s <http://www.wikidata.org/entity/P279c> <http://www.wikidata.org/entity/Q35120>}", graph);
		Writer.schreiben("Anzahl direkten Subklassen von der obersten Klasse  in " + graph.getGraphName()+":", "Distribution_Class.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("Summe");
				String i2 = i.toString();
				String[] i3 = i2.split(Pattern.quote("^"),2);
				Writer.schreiben(i3[0].toString()+"\n", "Distribution_Class.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println("InstancesOfClasses () Fertig.");
	}
	
	public static void NumberOfDirectAndTransitiveSubClassOfEntity() {
		VirtGraph graph = new VirtGraph ("http://taxonomy.org", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT (COUNT(?s) AS ?Summe) FROM <http://simpleStatements> WHERE {?s <http://www.wikidata.org/entity/P279c> <http://www.wikidata.org/entity/Q35120> OPTION(TRANSITIVE, T_DISTINCT)}", graph);
		Writer.schreiben("Anzahl direkten und indirekter Subklassen von der obersten Klasse  in " + graph.getGraphName()+":", "Distribution_Class.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("Summe");
				String i2 = i.toString();
				String[] i3 = i2.split(Pattern.quote("^"),2);
				Writer.schreiben(i3[0].toString()+"\n", "Distribution_Class.txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	
		System.out.println("InstancesOfClasses () Fertig.");
	}
	
	
	public static void DirectSubClassOfEntity() {
		VirtGraph graph = new VirtGraph ("http://taxonomy.org", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT ?s WHERE {?s <http://www.w3.org/2000/01/rdf-schema#subClassOf> <http://www.wikidata.org/entity/Q35120>}", graph);
		Writer.schreiben("Unterklassen der obersten Klasse Entitaet Q35120:", "Distribution_Class.txt");	
		try {
			ResultSet results = vqe.execSelect();

			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode s = result.get("s");
				String s2 = s.toString();
				Writer.schreiben(s2,"Distribution_Class.txt");
				Writer.schreiben(s2, "Subklassen_temp.txt");
			}
		} finally {
				vqe.close();
		}	
		System.out.println("InstancesOfClasses () Fertig.");
	}
	
	
	public static void DirectSubClassOfClass() {
		List<String> klasse2 = Reader.readFile("Subklassen_temp.txt");
		String[] klasse = klasse2.toArray(new String[klasse2.size()]);		
		for (int j= 0; j<=klasse.length-1;j++) {		
		VirtGraph graph = new VirtGraph ("http://taxonomy.org", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT ?s WHERE {?s <http://www.w3.org/2000/01/rdf-schema#subClassOf> <"+klasse[j]+">}", graph);
		Writer.schreiben("Unterklassen der Klasse "+klasse[j]+":", "SubClassNo"+j+".txt");	
		try {
			ResultSet results = vqe.execSelect();

			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode s = result.get("s");
				String s2 = s.toString();
				Writer.schreiben(s2,"SubClassNo"+j+".txt");
			}
		} finally {
				vqe.close();
		}	}
		System.out.println("DirectSubClassOfClass() Fertig.");
	}
	
	
	
	/* gibt die Anzahl der Instanzen Klassen aus, die zuvor in die Datei Subklassen_temp.txt geschrieben wurden*/
	public static void NumberOfInstancesOfClasses2 () {
		List<String> klasse2 = Reader.readFile("Subklassen_temp.txt");
		String[] klasse = klasse2.toArray(new String[klasse2.size()]);		
		for (int j= 0; j<=klasse.length-1;j++) {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?s) as ?Summe WHERE { ?s <http://www.wikidata.org/entity/P31c> <"+ klasse[j] +">}", graph);
		Writer.schreiben("Anzahl Instanzen der Klasse "+klasse[j]+" in " + graph.getGraphName()+":", "SubClassNo"+j+".txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("Summe");
				String i2 = i.toString();
				String[] i3 = i2.split(Pattern.quote("^"),2);
				Writer.schreiben(i3[0].toString()+"\n", "SubClassNo"+j+".txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	}
		System.out.println("InstancesOfClasses () Fertig.");
	}
	
	/* gibt die Instanzen der Klassen aus, die zuvor in die Datei Subklassen_temp.txt geschrieben wurden*/
	public static void InstancesOfClasses (String filename) {
		List<String> klasse2 = Reader.readFile(filename);
		String[] klasse = klasse2.toArray(new String[klasse2.size()]);		
		for (int j= 0; j<=klasse.length-1;j++) {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT ?s WHERE { ?s <http://www.wikidata.org/entity/P31c> <"+klasse[j]+">}", graph);
		Writer.schreiben("Instanzen der Klasse "+klasse[j]+" in " + graph.getGraphName()+":", "SubClassNo"+j+".txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode i = result.get("s");
				String i2 = i.toString();
				Writer.schreiben(i2+"\n", "SubClassNo"+j+".txt");
								}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			vqe.close();
		}	}
		System.out.println("InstancesOfClasses () Fertig.");
	}
	
	/* gibt die Anzahl der unterschiedlichen Klassen in Graph an, die tatsächlich instanziert werden*/
	public static void AnzahlDistKlassen() {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT COUNT(distinct ?o) AS ?no { ?s <http://www.wikidata.org/entity/P31c> ?o }", graph);
		Writer.schreiben("Anzahl an unterschiedlichen Klassen in Graph " + graph.getGraphName() + "die instanziert werden:","Distribution_Class.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode s = result.get("no");
				String s2 = s.toString();
				String[] s3 = s2.split(Pattern.quote("^"),2);
				Writer.schreiben(s3[0].toString()+"\n","Distribution_Class.txt");
			}} 
			finally {
				vqe.close();			
		}
		System.out.println("AnzahlDistKlassen() Fertig.");
	}
	
	/* gibt die Anzahl der Instanzen im Graph an*/
	public static void AnzahlInstanzenGesamt() {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT COUNT(?s) AS ?no { ?s <http://www.wikidata.org/entity/P31c> ?o }", graph);
		Writer.schreiben("Anzahl an Instanzen in " + graph.getGraphName() + ":","Distribution_Class.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode s = result.get("no");
				String s2 = s.toString();
				String[] s3 = s2.split(Pattern.quote("^"),2);
				Writer.schreiben(s3[0].toString()+"\n","Distribution_Class.txt");
			}} 
			finally {
				vqe.close();			
		}
		System.out.println("AnzahlDistKlassen() Fertig.");
	}
	
	/* gibt die Klassen mit den meisten Instanzen an*/
	public static void ClassWithMostInstances() {
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT ?o count(?o) As ?summe WHERE {?s <http://www.wikidata.org/entity/P31c> ?o} Order By desc(?summe) Limit 25", graph);
		Writer.schreiben("Die Top 25 Klassen mit den meisten Instanzen in " + graph.getGraphName() + ":","Distribution_Class.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode s = result.get("summe");
				RDFNode o = result.get("o");
				String s2 = s.toString();
				String o2 = o.toString();
				String[] s3 = s2.split(Pattern.quote("^"),2);
				Writer.schreiben(o2+" | "+s3[0].toString()+"\n","Distribution_Class.txt");
			}} 
			finally {
				vqe.close();			
		}
		System.out.println("AnzahlDistKlassen() Fertig.");
	}
	
	/* gibt die Anzahl der Klassen im Graph an, die mindestens eine bestimmte Anzahl an Instanzen haben*/
	public static void ClassesWithMinimumNoOfInstances() {
		int[] j = {1,100,250,500,1000,10000,100000,250000,500000,1000000,2000000};
		for (int i=0; i<=j.length-1; i=i+1) {
			
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count (?o) As ?no WHERE {{ SELECT ?o count(?o) As ?summe WHERE {?s <http://www.wikidata.org/entity/P31c> ?o} Order By desc(?summe)} Filter (?summe > "+j[i]+")}", graph);
		Writer.schreiben("Anzahl Klassen, die mindestens "+j[i]+" Instanzen haben in " + graph.getGraphName() + ":","Distribution_Classes_with_Instances.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode s = result.get("no");
				String s2 = s.toString();
				String[] s3 = s2.split(Pattern.quote("^"),2);
				Writer.schreiben(s3[0].toString()+"\n","Distribution_Classes_with_Instances.txt");
			}} 
			finally {
				vqe.close();			
		}}
		System.out.println("ClassesWithMinimumNoOfInstances() Fertig.");
	}
	
	/* gibt die Anzahl der Klassen im Graph an, die mindestens eine bestimmte Anzahl an Instanzen haben und speichert diese um den Graph zu zeichnen*/
	public static void ClassesCreateDrawDistribution() {
		
		
		int[] j = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,25,50,100,250,500,750,1000,2500,5000,10000,25000,50000,75000,100000};
		for (int i=0; i<=j.length-1; i=i+1) {
			
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count (?o) As ?no WHERE {{ SELECT ?o count(?o) As ?summe WHERE {?s <http://www.wikidata.org/entity/P31c> ?o} Order By desc(?summe)} Filter (?summe > "+j[i]+")}", graph);
		Writer.schreiben(j[i],"Distribution_Classes_with_Instances_Draw_x.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode s = result.get("no");
				String s2 = s.toString();
				String[] s3 = s2.split(Pattern.quote("^"),2);
				Writer.schreiben(s3[0].toString(),"Distribution_Classes_with_Instances_Draw_y.txt");
			}} 
			finally {
				vqe.close();			
		}}
		System.out.println("ClassesCreateDrawDistribution() Fertig.");
	}
	
	public static void InstancesBelongingToClasses() {
		int[] j = {2,3,4,5,6,7,8,9,10,11,12};
		for (int i=0; i<=j.length-1; i=i+1) {
		
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?summe) As ?summen WHERE {{ SELECT count(?s) As ?summe FROM <http://simpleStatements> WHERE {?s <http://www.wikidata.org/entity/P31c> ?o} GROUP By ?s ?l ORDER BY DESC(?summe)} FILTER (?summe > "+j[i]+")}", graph);
		Writer.schreiben(j[i],"Instances_belonging_to_Classes_x.txt");
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode s = result.get("summen");
				String s2 = s.toString();
				String[] s3 = s2.split(Pattern.quote("^"),2);
				Writer.schreiben(s3[0].toString(),"Instances_belonging_to_Classes_y.txt");
			}} 
			finally {
				vqe.close();			
		}
		}
		System.out.println("InstanceBelongingToClasses() Fertig.");
}

}
