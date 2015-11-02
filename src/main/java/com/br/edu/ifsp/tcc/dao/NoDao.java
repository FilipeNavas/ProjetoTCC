/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.edu.ifsp.tcc.dao;

import com.br.edu.ifsp.tcc.interfaces.NoInterface;
import com.br.edu.ifsp.tcc.modelo.No;
import com.br.edu.ifsp.tcc.modelo.PercorreNo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;


/**
 *
 * @author Filipe
 */
public class NoDao implements NoInterface{
    
    GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
    GraphDatabaseService db;
    
    @Override
    public No bucarNoPorId(String busca){
        
        //Pega o Banco
        db = ConexaoBanco.getConnection();
       
        No no = new No();
        
        //cria um map chamado params para colocar chave/valor
        Map<String, Object> params = new HashMap<>();
       
        //coloca o valor conceito na chave conceito
        params.put( "busca", "(?i)" + busca);
        
	try ( Transaction transaction = db.beginTx();
                
        //"START n = node(*) MATCH (n)-[r]->(x) return n, type(r), x"
                
        Result result = db.execute( "START n = node(*)"
                                    + " MATCH (n)"
                                    + " WHERE"
                                    + " has(n.id) and n.id=~{busca}"
                                    + " return n", params ) ){
            
            while ( result.hasNext() ){
                
                Map<String,Object> row = result.next();
    
      
                Node n = (Node) row.get("n");


                for (String propriedade : n.getPropertyKeys()) {   

                    //Seta os atributos de NoInicial
                    switch (propriedade){

                        case "id":
                            no.setId(n.getProperty(propriedade).toString());
                        break;

                        case "nome":
                            no.setNome(n.getProperty(propriedade).toString());
                        break;

                        case "descricao":
                            no.setDescricao(n.getProperty(propriedade).toString());
                        break;

                    }

                }  
        
            }//fim do while
            
            transaction.success();
        }//fim do result execute
      
       //retorna o no
       return no;  
       
   }// fim do método
    
    
    @Override
    public void criarNo(No novoNo, List<PercorreNo> listaNoRelacionamentos){
        
        //Pega o Banco
        db = ConexaoBanco.getConnection();
        
        //Pega o ultimo no para incrementar o id
        No ultimoNo = buscarUltimoNo();
        
        String id;
        
        Node novoNoNeo4j;
        Node noFinalNeo4j;       
        
        //Se o ultimoNo for vazio quer dizer q nao tem nada no BD.
        if(ultimoNo.getId() == null){
            id = "0";
        }else{
            //Cria o id a ser passado (adiciona 1 ao ultimo valor)
            //Retorna a conta como uma String
            id = String.valueOf(Integer.parseInt(ultimoNo.getId()) + 1);
        }
        
        try ( Transaction tx = db.beginTx() ){
            
            //Cria no no
            novoNoNeo4j = db.createNode();
            
            //Atribui as propriedades
            novoNoNeo4j.setProperty( "id", id);
            novoNoNeo4j.setProperty( "nome", novoNo.getNome() );
            novoNoNeo4j.setProperty( "descricao", novoNo.getDescricao());
            
            //Cria os relacionamentos com os outros nos
            for (PercorreNo percorreNo : listaNoRelacionamentos) {
                
                //Busca o No (do Neo4j) pelo id
                noFinalNeo4j = buscarNoNeo4jPorId(percorreNo.getNoFinal().getId());
            
                //Cria os relacionamentos
                novoNoNeo4j.createRelationshipTo( noFinalNeo4j, percorreNo.getRelacionamento() );
            }
            
            //Finaliza a transacao
            tx.success();
            
        }//fim do try
       
    }
    
    public Node buscarNoNeo4jPorId(String id){
        
       //Pega o Banco
        db = ConexaoBanco.getConnection();
       
        Node noNeo4j = null;
        
        //cria um map chamado params para colocar chave/valor
        Map<String, Object> params = new HashMap<>();
       
        //coloca o valor conceito na chave conceito
        params.put( "busca", "(?i)" + id);
        
	try ( Transaction transaction = db.beginTx();
                
        //"START n = node(*) MATCH (n)-[r]->(x) return n, type(r), x"
                
        Result result = db.execute( "START n = node(*)"
                                    + " MATCH (n)"
                                    + " WHERE"
                                    + " has(n.id) and n.id=~{busca}"
                                    + " return n", params ) ){
            
            while ( result.hasNext() ){
                
                Map<String,Object> row = result.next();
    
      
                noNeo4j = (Node) row.get("n");
                
            }//fim do while
            
            transaction.success();
        }//fim do result execute
        
        return noNeo4j;
        
    }
    
    
    public No buscarUltimoNo(){
        
        //Pega o Banco
        db = ConexaoBanco.getConnection();        
        
        No no = new No();
        
	try ( Transaction transaction = db.beginTx();
                
        //"START n = node(*) MATCH (n)-[r]->(x) return n, type(r), x"
                
        Result result = db.execute( "Match (n)"
                                    + " Return n"
                                    + " Order by ID(n) desc"
                                    + " Limit 1") ){
            
            while ( result.hasNext() ){
                
                Map<String,Object> row = result.next();
    
      
                Node n = (Node) row.get("n");


                for (String propriedade : n.getPropertyKeys()) {   

                    //Seta os atributos de NoInicial
                    switch (propriedade){

                        case "id":
                            no.setId(n.getProperty(propriedade).toString());
                        break;

                        case "nome":
                            no.setNome(n.getProperty(propriedade).toString());
                        break;

                        case "descricao":
                            no.setDescricao(n.getProperty(propriedade).toString());
                        break;

                    }

                }  
        
            }//fim do while
            
            transaction.success();
        }//fim do result execute
      
       //retorna o no
       return no;  
        
    }
    
    @Override
    public List<No> buscarTodosNos(){
        
        //Pega o Banco
        db = ConexaoBanco.getConnection();       
        
        List listaDeNos = new ArrayList();
        
	try ( Transaction transaction = db.beginTx();
                
        //"START n = node(*) MATCH (n)-[r]->(x) return n, type(r), x"
                
        Result result = db.execute( " Match (n)"
                                    + " Return n") ){
            
            while ( result.hasNext() ){
                
                Map<String,Object> row = result.next();
        
                Node n = (Node) row.get("n");

                No no = new No();

                for (String propriedade : n.getPropertyKeys()) {   

                    //Seta os atributos de NoInicial
                    switch (propriedade){

                        case "id":
                            no.setId(n.getProperty(propriedade).toString());
                        break;

                        case "nome":
                            no.setNome(n.getProperty(propriedade).toString());
                        break;

                        case "descricao":
                            no.setDescricao(n.getProperty(propriedade).toString());
                        break;

                    }

                }  

                listaDeNos.add(no);

               }//fim do while
            
            //Finaliza a transacao
            transaction.success();
            
        }//Fim do result execute
        
        //retorna a lista
        return listaDeNos;  

    }

    @Override
    public void editarNo(No no, List<PercorreNo> listaNoRelacionamentos){
        
        //Pega o Banco
        db = ConexaoBanco.getConnection();
        
        try ( Transaction tx = db.beginTx() ){
            
            Node noNeo4j = buscarNoNeo4jPorId(no.getId());
            
            //Atribui as propriedades
            noNeo4j.setProperty( "nome", no.getNome() );
            noNeo4j.setProperty( "descricao", no.getDescricao());
            
            //Cria os relacionamentos com os outros nos
            for (PercorreNo percorreNo : listaNoRelacionamentos) {
                
                //Busca o No (do Neo4j) pelo id
                noNeo4j = buscarNoNeo4jPorId(percorreNo.getNoFinal().getId());
            
                //Cria os relacionamentos
                noNeo4j.createRelationshipTo( noNeo4j, percorreNo.getRelacionamento() );
            }
            
            //Finaliza a transacao
            tx.success();
            
        }//fim do try
        
    }
    
}
