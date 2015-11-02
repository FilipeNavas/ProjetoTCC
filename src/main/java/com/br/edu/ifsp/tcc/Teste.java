/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.edu.ifsp.tcc;

import com.br.edu.ifsp.tcc.modelo.Relacionamento;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 *
 * @author Filipe
 */
public class Teste {
    
    
public static void main(String[] args) {
	GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
	GraphDatabaseService db= dbFactory.newEmbeddedDatabase("C:/Neo4jDB");
	try (Transaction tx = db.beginTx()) {

		Node javaNode = db.createNode();
		javaNode.setProperty("TutorialID", "JAVA001");
		javaNode.setProperty("Title", "Learn Java");
		javaNode.setProperty("NoOfChapters", "25");
		javaNode.setProperty("Status", "Completed");				
		
		Node scalaNode = db.createNode();
		scalaNode.setProperty("TutorialID", "SCALA001");
		scalaNode.setProperty("Title", "Learn Scala");
		scalaNode.setProperty("NoOfChapters", "20");
		scalaNode.setProperty("Status", "Completed");
		
		Relationship relationship = javaNode.createRelationshipTo
		(scalaNode,Relacionamento.ESTA_CONTIDO);
		relationship.setProperty("Id","1234");
		relationship.setProperty("OOPS","YES");
		relationship.setProperty("FP","YES");
		
		tx.success();
	}
	   System.out.println("Done successfully");
}
    
}
