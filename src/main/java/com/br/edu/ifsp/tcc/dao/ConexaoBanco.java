/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.edu.ifsp.tcc.dao;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 *
 * @author Filipe
 */
public final class ConexaoBanco {
    
    public static GraphDatabaseFactory dbFactory;
    public static GraphDatabaseService db;
    private static boolean instanciado = false;
        
    public static GraphDatabaseService getConnection(){
        
        try {
            
            if(!instanciado){

                dbFactory = new GraphDatabaseFactory();
                db = dbFactory.newEmbeddedDatabase("C:/Neo4jDB");
                registerShutdownHook( db );
                instanciado = true;
            
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return(db);
        
    }
    
    public static void shutdown(){
        db.shutdown();
    }
    
    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }
 
}
