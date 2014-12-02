package SPARQLAnfragen;

//import Virtuoso.Query;
//import Virtuoso.QuerySolution;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.rdf.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;
import virtuoso.jena.driver.VirtGraph;

import java.awt.Color;
import java.io.*;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;











import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.sparql.engine.http.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.*;
import org.jfree.*;
import org.jfree.data.category.*;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class SPAQRL {
	
	
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		
		Writer.deleteFile("Ergebnis.txt");
		/*Writer.deleteFile("Klassen.txt");
		Writer.deleteFile("Distribution_Item.txt");
		Writer.deleteFile("Distribution_Class.txt");
		Writer.deleteFile("Subklassen_temp.txt");
		Writer.deleteFile("Distribution_Classes_with_Instances.txt");
		Writer.deleteFile("Distribution_Classes_with_Instances_Draw_x.txt");
		Writer.deleteFile("Distribution_Classes_with_Instances_Draw_y.txt");
		Writer.deleteFile("Distribution_Items_with_Statements_Draw_x.txt");
		Writer.deleteFile("Distribution_Items_with_Statements_Draw_y.txt");*/
		
		//ClassAnalyse.NumberOfSubClassOfEntity();
		ClassAnalyse.NumberOfDirectAndTransitiveSubClassOfEntity();
		/*ClassAnalyse.DirectSubClassOfEntity();
		ClassAnalyse.DirectSubClassOfClass();
		ClassAnalyse.NumberOfInstancesOfClasses2();
		ClassAnalyse.InstancesOfClasses("Subklassen_temp.txt");
		ClassAnalyse.AnzahlDistKlassen();
		ClassAnalyse.AnzahlInstanzenGesamt();
		ClassAnalyse.ClassWithMostInstances();
		ClassAnalyse.ClassesWithMinimumNoOfInstances();
		ClassAnalyse.ClassesCreateDrawDistribution();
		ItemAnalyse.ClassesCreateDrawDistributionOfItemsWithStatements();*/
		//ClassAnalyse.InstancesOfClasses("<http://www.wikidata.org/entity/Q5>");
		//ClassAnalyse.InstancesOfClasses("<http://www.wikidata.org/entity/Q35120>");
		//ItemAnalyse.AverageStatementPerItem();
		//ItemAnalyse.StatementsPerItem();
		//ItemAnalyse.ItemsWith0Statement();
		//ItemAnalyse.ItemsWithAtLeast1Statement();
		//ItemAnalyse.ItemsWithAtLeast2Statement();
		//ItemAnalyse.ItemsWithAtLeast3Statement();
		//ItemAnalyse.ItemsWithAtLeast4Statement();
		//ItemAnalyse.ItemsWithAtLeast5Statement();
		//ItemAnalyse.ItemsWithAtLeast6Statement();
		//ItemAnalyse.ItemsWithAtLeast7Statement();
		//ItemAnalyse.ItemsWithAtLeast8Statement();
		//ItemAnalyse.ItemsWithAtLeast9Statement();
		//ItemAnalyse.ItemsWithAtLeast10Statement();
		//ItemAnalyse.ItemsWithAtLeast25Statement();
		//ItemAnalyse.ItemsWithAtLeast50Statement();
		//ItemAnalyse.ItemsWithAtLeast100Statement();
		//superClassOf();
		AnzahlTriple();
		AnzahlEntity();
		AnzahlDistPredicate();
		AnzahlDistSubject();
		AnzahlDistObject();
		NumberOfClassesUsed();
		ClassesUsed();
		//subClassOf();		
		
		System.out.println("Alles fertig.");
	}
		/* gibt die # der Triple im angegebenen Graphen zurück*/
		public static void AnzahlTriple() {
			VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT (COUNT(*) AS ?no) { ?s ?p ?o  }", graph);
			Writer.schreiben("Anzahl an Triples in Graph " + graph.getGraphName()+":", "Ergebnis.txt");
			try {
				ResultSet results = vqe.execSelect();
				while (results.hasNext()) {
					QuerySolution result = results.nextSolution();
					RDFNode s = result.get("no");
					String s2 = s.toString();
					String[] s3 = s2.split(Pattern.quote("^"),2);
					Writer.schreiben(s3[0].toString()+"\n", "Ergebnis.txt");			
									}
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				vqe.close();
			}	
			System.out.println("AnzahlTriple() Fertig.");
		}
		
		public static void AnzahlEntity() {
			VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT (count (distinct ?s) as ?no) WHERE {?s ?p ?o}", graph);
			Writer.schreiben("Anzahl an unterschiedlichen Entitaeten in Graph " + graph.getGraphName() + ":", "Ergebnis.txt");
			try {
				ResultSet results = vqe.execSelect();
				while (results.hasNext()) {
					QuerySolution result = results.nextSolution();
					RDFNode s = result.get("no");
					String s2 = s.toString();
					String[] s3 = s2.split(Pattern.quote("^"),2);
					Writer.schreiben(s3[0].toString()+"\n", "Ergebnis.txt");
				}} 
				finally {
					vqe.close();			
			}
			System.out.println("AnzahlEntity()");
		}
		
		
		
		/* gibt die Anzahl der untersch. Prädikate zurück*/
		public static void AnzahlDistPredicate() {
			VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(distinct ?p) as ?no { ?s ?p ?o }", graph);
			Writer.schreiben("Anzahl an unterschiedlichen Praedikaten in Graph " + graph.getGraphName() + ":","Ergebnis.txt");
			try {
				ResultSet results = vqe.execSelect();
				while (results.hasNext()) {
					QuerySolution result = results.nextSolution();
					RDFNode s = result.get("no");
					String s2 = s.toString();
					String[] s3 = s2.split(Pattern.quote("^"),2);
					Writer.schreiben(s3[0].toString()+"\n", "Ergebnis.txt");
				}} 
				finally {
					vqe.close();			
			}	
			System.out.println("AnzahlDistPredicate() Fertig.");
		}
		
		/*gibt die Anzahl unterschiedliche Subjekte zurück*/
		public static void AnzahlDistSubject() {
			VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT (COUNT(DISTINCT ?s ) AS ?no) {  ?s ?p ?o   } ", graph);
			Writer.schreiben("Anzahl an unterschiedlichen Subjekte in Graph " + graph.getGraphName() + ":", "Ergebnis.txt");
			try {
				ResultSet results = vqe.execSelect();
				while (results.hasNext()) {
					QuerySolution result = results.nextSolution();
					RDFNode s = result.get("no");
					String s2 = s.toString();
					String[] s3 = s2.split(Pattern.quote("^"),2);
					Writer.schreiben(s3[0].toString()+"\n", "Ergebnis.txt");
				}} 
				finally {
					vqe.close();			
			}
			System.out.println("AnzahlDistSubject()");
		}
		
		/*gibt die Anzahl unterschiedliche Objete zurück*/
		public static void AnzahlDistObject() {
			VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT (COUNT(DISTINCT ?o ) AS ?no) {  ?s ?p ?o  filter(!isLiteral(?o)) } ", graph);
			Writer.schreiben("Anzahl an unterschiedlichen Objekte in Graph " + graph.getGraphName() + ":", "Ergebnis.txt");
			try {
				ResultSet results = vqe.execSelect();
				while (results.hasNext()) {
					QuerySolution result = results.nextSolution();
					RDFNode s = result.get("no");
					String s2 = s.toString();
					String[] s3 = s2.split(Pattern.quote("^"),2);
					Writer.schreiben(s3[0].toString()+"\n", "Ergebnis.txt");
				}} 
				finally {
					vqe.close();			
			}
			System.out.println("AnzahlDistObject()");
		}
		
		/* gibt eine Liste aller genutzer Klassen in dem Graphen aus und schreibt dies in Datei Klassen.txt */
		 	public static void ClassesUsed() {
			VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT DISTINCT ?type { ?s <http://www.wikidata.org/entity/P31c> ?type }", graph);
			Writer.schreiben("Anzahl an genutzten Klassen in Graph " + graph.getGraphName() + ":", "Klassen.txt");
			try {
				ResultSet results = vqe.execSelect();
				while (results.hasNext()) {
					QuerySolution result = results.nextSolution();
					RDFNode s = result.get("type");
					String s2 = s.toString();
					Writer.schreiben(s2+"\n", "Klassen.txt");
				}} 
				finally {
					vqe.close();			
			}
			System.out.println("ClassesUsed() Fertig.");
		 	}
		 	
		 	/* gibt die Anzahl aller genutzer Klassen in dem Graphen aus und schreibt dies in Datei Klassen.txt */
		 	public static void NumberOfClassesUsed() {
			VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT count(?type) As ?no { ?s <http://www.wikidata.org/entity/P31c> ?type }", graph);
			Writer.schreiben("Anzahl an genutzten Klassen in Graph " + graph.getGraphName() + ":", "Ergebnis.txt");
			try {
				ResultSet results = vqe.execSelect();
				while (results.hasNext()) {
					QuerySolution result = results.nextSolution();
					RDFNode s = result.get("no");
					String s2 = s.toString();
					String[] s3 = s2.split(Pattern.quote("^"),2);
					Writer.schreiben(s3[0].toString()+"\n", "Ergebnis.txt");
				}} 
				finally {
					vqe.close();			
			}
			System.out.println("NumberOfClassesUsed()");
		 	}
		
		/* gibt die # und die Namen der direkten Unterklassen der Oberklasse, 
		 * sowie die Anzahl aller transiiven Unterklassen aus */
		public static void subClassOf() {
			VirtGraph graph = new VirtGraph ("http://taxonomy.org", "jdbc:virtuoso://localhost:1111", "dba", "dba");
			
			String anfrage2 ="SELECT ?s WHERE {?s <http://www.w3.org/2000/01/rdf-schema#subClassOf> <http://www.wikidata.org/entity/Q35120>}"; 
			Query sparql2 = QueryFactory.create(anfrage2);

			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql2, graph);
						
			Writer.schreiben("Unterklassen der obersten Klasse Entitaet Q35120:", "Ergebnis.txt");	
						try {
			ResultSet results = vqe.execSelect();
			
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode s = result.get("s");
				RDFNode p = result.get("p");
				RDFNode o = result.get("o");
				String s2 = s.toString();
				Writer.schreiben(s2,"Ergebnis.txt");
				System.out.println(s + " " + p + " " +o);
			}
		} finally {
			vqe.close();
			
		}			
			System.out.println("subClassOf() Fertig.");
		}
		
		public static void superClassOf() {
			Writer.deleteFile("Ergebnis.txt");
			String child = "<http://www.wikidata.org/entity/Q5>";
			String child2 = "http://www.wikidata.org/entity/Q5";	
			String o2="";
			while (child2.isEmpty()==false) {
				
				VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
				String anfrage ="SELECT ?o ?l WHERE {"+child+" <http://www.wikidata.org/entity/P279c> ?o}";
				Query sparql2 = QueryFactory.create(anfrage);
							
				VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql2, graph);
				Writer.schreiben("Die Oberklasse von "+child+ "in Graph" +graph.getGraphName() + "ist:", "Ergebnis.txt");			
				try {
					ResultSet results = vqe.execSelect();
					o2="";
					while (results.hasNext()) {
						QuerySolution result = results.nextSolution();
						RDFNode o = result.get("o");
						o2 = o.toString();
						String o3 = o.asResource().listProperties().toString();
						Writer.schreiben(o2, "Ergebnis.txt");
						Writer.schreiben(o3, "Ergebnis.txt");
						/*String anfrage2="SELECT ?l WHERE { <"+o2+"> <http://www.wikidata.org/entity/P373c> ?l}";
						Query sparql3 = QueryFactory.create(anfrage2);
						VirtuosoQueryExecution vqe2 = VirtuosoQueryExecutionFactory.create (sparql3, graph);
						try {
							ResultSet results2 = vqe2.execSelect();
							while (results2.hasNext()) {
								QuerySolution result2 = results2.nextSolution();
								RDFNode l = result2.get("l");
								String l2 = l.toString();
								Writer.schreiben(l2, "Ergebnis.txt");
							} }
						finally {
							vqe2.close();
						}*/
					}
				} finally {
			vqe.close();
			}
			child = "<"+o2+">";	
			child2 = o2;
			}
			System.out.println("superClassOf() fertig");
			}
		
		public static void AnzahlInstanzen() {
			VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
			System.out.println(graph.getGraphName());
			System.out.println(graph.size());
			
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create ("SELECT count(?s) as ?Summe WHERE { ?s <http://www.wikidata.org/entity/P31c> <http://www.wikidata.org/entity/Q5>}", graph);
			Writer.schreiben("Anzahl der Instanzen der Klasse Mensch in Graph" + graph.getGraphName() +":", "Ergebnis.txt");
			try {
			ResultSet results = vqe.execSelect();
			
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode s = result.get("Summe");
				System.out.println(s);
				String s2 = s.toString();
				String[] s3 = s2.split(Pattern.quote("^"),2);
				Writer.schreiben(s3[0].toString()+"\n", "Ergebnis.txt");
			}
		} finally {
			vqe.close();	
		}
			System.out.println("AnzahlInstanzen()");
			}
		
		}