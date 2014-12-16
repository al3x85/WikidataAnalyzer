package SPARQLAnfragen;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class CreateSubset {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub

		Writer.deleteFile("SubSet_Handballer.nt");
		createSubset();
	}
	
	public static void createSubset() {
		List<String> instances = new ArrayList<String>();
		List<String> properties = new ArrayList<String>();
		List<String> items = new ArrayList<String>();
		
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtGraph graph2 = new VirtGraph ("http://WikidataComplete", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT ?instance FROM <http://simpleStatements> WHERE {?instance <http://www.wikidata.org/entity/P106c> <http://www.wikidata.org/entity/Q13365117>.} ", graph);
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode instance = result.get("instance");
				String instance2 = instance.toString();
				instances.add(instance2);
			}
		}
		finally {
			vqe.close();			
	}
		
		
		for (String instances2 : instances) {
			
		VirtuosoQueryExecution vqe2 = VirtuosoQueryExecutionFactory.create("SELECT <"+instances2+"> ?p ?o FROM <http://simpleStatements> WHERE {<"+instances2 +"> ?p ?o FILTER regex(?p, \"http://www.wikidata.org\")}", graph);
		try {
			ResultSet results2 = vqe2.execSelect();
			while (results2.hasNext()) {
				QuerySolution result2 = results2.nextSolution();
				RDFNode p = result2.get("p");
				RDFNode o = result2.get("o");
				String p2 = p.toString();
				String[] a2 = p2.split(Pattern.quote("c"), 2);
				String p3=a2[0].toString();
				String o2 = o.toString();
				// Properties speichern, da diese noch ein Label und ein Type Property brauchen
				if (!properties.contains(p3))
				{
					properties.add(p3);
				}
				//Fall 1: Objekt ist ein Item, zu dem noch die Klasse und das Label hinzukommen müssen
				if (o2.startsWith("http")) {
					items.add(o2);
					
					Writer.schreiben("<"+instances2+"> <"+p3+"> <"+o2+"> .", "SubSet_Handballer.nt");
				}
				//Fall 2: Objekt ist ein Value mit einem Datentyp
				else if (o2.contains("^^")) {
					String[] a = o2.split(Pattern.quote("^^"),2);
					String o3="\""+a[0].toString()+"\"^^"+"<"+a[1].toString()+">";
					Writer.schreiben("<"+instances2+">"+" "+"<"+p3+">"+" "+o3+" .","SubSet_Handballer.nt");
				}
				else if (o2.contains("@en")||o2.contains("@de")) 
				{
					String[] x = o2.split(Pattern.quote("@"),2);
					String o3 = "\""+x[0].toString()+"\"@"+x[1].toString();
					Writer.schreiben("<"+instances2+">"+" "+"<"+p3+">"+" "+o3+" .","SubSet_Handballer.nt");
				} 
				
			}
		}
		finally {
			vqe2.close();			
	}
		
		
		}		
		//schreibe für alle Instanzen die zugehörigen Klasse ins File
		for (String instances2 : instances) {
			VirtuosoQueryExecution vqe3 = VirtuosoQueryExecutionFactory.create("SELECT ?o FROM <http://simpleStatements> WHERE {<"+instances2+"> <http://www.wikidata.org/entity/P31c> ?o} ", graph);
			try {
				ResultSet results3 = vqe3.execSelect();
				while (results3.hasNext()) {
					QuerySolution result3 = results3.nextSolution();
					RDFNode o = result3.get("o");
					String o2 = o.toString();
					Writer.schreiben("<"+instances2+"> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <"+o2+"> .", "SubSet_Handballer.nt");	
				}
			}
			finally {
				vqe3.close();			
		}	
		}
		
		//schreibe für alle Instanzen die Labels ins File
		for (String instances2 : instances) {
			VirtuosoQueryExecution vqe4 = VirtuosoQueryExecutionFactory.create("SELECT ?label FROM <http://simpleStatements> WHERE {{<"+instances2+"> <http://www.w3.org/2000/01/rdf-schema#label> ?label.} FILTER (lang(?label) = \"\" || lang(?label) = \"de\" || lang(?label) = \"en\")} ", graph);
			try {
				ResultSet results4 = vqe4.execSelect();
				while (results4.hasNext()) {
					QuerySolution result4 = results4.nextSolution();
					RDFNode label = result4.get("label");
					String label2 = label.toString();
					String[] b = label2.split(Pattern.quote("@"),2);
					String label3 = "\""+b[0].toString()+"\"@"+b[1].toString();
					Writer.schreiben("<"+instances2+"> <http://www.w3.org/2000/01/rdf-schema#label> "+label3+" .", "SubSet_Handballer.nt");	
				}
			}
			finally {
				vqe4.close();			
		}	
		}
		
		//schreibe für alle Properties die labels dazu
		for (String properties2 : properties) {
			VirtuosoQueryExecution vqe5 = VirtuosoQueryExecutionFactory.create("SELECT ?label FROM <http://WikidataComplete> WHERE {{<"+properties2+"> <http://www.w3.org/2000/01/rdf-schema#label> ?label.} FILTER (lang(?label) = \"\" || lang(?label) = \"de\" || lang(?label) = \"en\")} ", graph2);
			try {
				ResultSet results5 = vqe5.execSelect();
				while (results5.hasNext()) {
					QuerySolution result5 = results5.nextSolution();
					RDFNode label = result5.get("label");
					String label2 = label.toString();
					String[] b = label2.split(Pattern.quote("@"),2);
					String label3 = "\""+b[0].toString()+"\"@"+b[1].toString();
					Writer.schreiben("<"+properties2+"> <http://www.w3.org/2000/01/rdf-schema#label> "+label3+" .", "SubSet_Handballer.nt");	
					Writer.schreiben("<"+properties2+"> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .", "SubSet_Handballer.nt");
				}
			}
			finally {
				vqe5.close();			
		}	
		}
		
		//schreibe für alle Items die Klassen dazu
		for (String items2 : items) {
			VirtuosoQueryExecution vqe6 = VirtuosoQueryExecutionFactory.create("SELECT ?o FROM <http://simpleStatements> WHERE {<"+items2+"> <http://www.wikidata.org/entity/P31c> ?o} ", graph);
			try {
				ResultSet results6 = vqe6.execSelect();
				while (results6.hasNext()) {
					QuerySolution result3 = results6.nextSolution();
					RDFNode o = result3.get("o");
					String o2 = o.toString();
					Writer.schreiben("<"+items2+"> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <"+o2+"> .", "SubSet_Handballer.nt");	
				}
			}
			finally {
				vqe6.close();			
		}	
		}
		
		//schreibe für alle Items die Labels dazu
		for (String items2 : items) {
			VirtuosoQueryExecution vqe7 = VirtuosoQueryExecutionFactory.create("SELECT ?label FROM <http://simpleStatements> WHERE {{<"+items2+"> <http://www.w3.org/2000/01/rdf-schema#label> ?label.} FILTER (lang(?label) = \"\" || lang(?label) = \"de\" || lang(?label) = \"en\")} ", graph);
			try {
				ResultSet results7 = vqe7.execSelect();
				while (results7.hasNext()) {
					QuerySolution result7 = results7.nextSolution();
					RDFNode label = result7.get("label");
					String label2 = label.toString();
					String[] b = label2.split(Pattern.quote("@"),2);
					String label3 = "\""+b[0].toString()+"\"@"+b[1].toString();
					Writer.schreiben("<"+items2+"> <http://www.w3.org/2000/01/rdf-schema#label> "+label3+" .", "SubSet_Handballer.nt");	
				}
			}
			finally {
				vqe7.close();			
		}	
		}
		
		/*
		VirtGraph graph = new VirtGraph ("http://simpleStatements", "jdbc:virtuoso://localhost:1111", "dba", "dba");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create("SELECT ?instance ?p ?o FROM <http://simpleStatements> WHERE {{ ?instance <http://www.wikidata.org/entity/P106c> <http://www.wikidata.org/entity/Q13365117>. ?instance ?p ?o.} OPTIONAL {{?instance <http://www.w3.org/2000/01/rdf-schema#label> ?label} FILTER (lang(?label) = \"\" || lang(?label) = \"de\" || lang(?label) = \"en\")}} ", graph);
		List<String> checked = new ArrayList<String>();
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				RDFNode instances = result.get("instance");
				RDFNode p = result.get("p");
				RDFNode o = result.get("o");
				String instance2 = instances.toString();
				String p2 = p.toString(); 
				String o2 = o.toString();
				String o3 ="";
				//falls Objekt o ein Item ist
				if (o2.startsWith("http") && !checked.contains(o2)) {
					o3 = "<"+o2+">";
					if (o2.startsWith("http://www.wikidata.org/entity/Q")) {
					VirtuosoQueryExecution vqe2 = VirtuosoQueryExecutionFactory.create("SELECT ?x ?y FROM <http://simpleStatements> WHERE {{ <"+o2+"> ?x ?y.} OPTIONAL {{<"+o2+"> <http://www.w3.org/2000/01/rdf-schema#label> ?label2} FILTER (lang(?label) = \"\" || lang(?label) = \"de\" || lang(?label) = \"en\")}}",graph);
					try {
						ResultSet results2 = vqe2.execSelect();
						while (results2.hasNext()) {
							QuerySolution result2 = results2.nextSolution();
							RDFNode x = result2.get("x");
							RDFNode y = result2.get("y");
							String x2 = x.toString();
							String y2 = y.toString();
							String y3 ="";
							if (y2.startsWith("http")) {
								y3 ="<"+y2+">";
								Writer.schreiben("<"+o2+"> <"+x2+"> "+y3+" .", "SubSet_Handballer.nt");
								checked.add(o2);
							}
							else if (y2.contains("^^")) {
								String[] b = y2.split(Pattern.quote("^^"),2);
								y3="\""+b[0].toString()+"\"^^"+"<"+b[1].toString()+">";
								Writer.schreiben("<"+o2+"> <"+x2+"> "+y3+" .", "SubSet_Handballer.nt");
								checked.add(o2);
							}
							else if (y2.contains("@en")||y2.contains("@de")) 
							{
								String[] b = y2.split(Pattern.quote("@"),2);
								y3 = "\""+b[0].toString()+"\"@"+b[1].toString();
								Writer.schreiben("<"+o2+"> <"+x2+"> "+y3+" .", "SubSet_Handballer.nt");
								checked.add(o2);
							} 
							///sonst
							else {
								y3 ="\""+y2+"\"";
								checked.add(o2);
							}
						}
					}
					finally {
						vqe2.close();			
				}}
				}
					
					
				
				//falls String ein Value mit Datentyp ist
				else if (o2.contains("^^")) {
					String[] a = o2.split(Pattern.quote("^^"),2);
					o3="\""+a[0].toString()+"\"^^"+"<"+a[1].toString()+">";
					Writer.schreiben("<"+instance2+">"+" "+"<"+p2+">"+" "+o3+" .","SubSet_Handballer.nt");
				}
				// falls String ein Label mit LanguageTag ist
				else if (o2.contains("@en")||o2.contains("@de")) 
				{
					String[] x = o2.split(Pattern.quote("@"),2);
					o3 = "\""+x[0].toString()+"\"@"+x[1].toString();
					Writer.schreiben("<"+instance2+">"+" "+"<"+p2+">"+" "+o3+" .","SubSet_Handballer.nt");
				} 
				///sonst
				else {
					o3 ="\""+o2+"\"";
					Writer.schreiben("<"+instance2+">"+" "+"<"+p2+">"+" "+o3+" .","SubSet_Handballer.nt");
				}
				
			}} 
			finally {
				vqe.close();			
		}
*/		System.out.println("createSubSet() Fertig.");
	}

}
