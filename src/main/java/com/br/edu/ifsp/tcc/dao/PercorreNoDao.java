/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.edu.ifsp.tcc.dao;

import com.br.edu.ifsp.tcc.interfaces.PercorreNoInterface;
import com.br.edu.ifsp.tcc.modelo.No;
import com.br.edu.ifsp.tcc.modelo.PercorreNo;
import com.br.edu.ifsp.tcc.modelo.Relacionamento;
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
public class PercorreNoDao implements PercorreNoInterface{
    
    GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
    GraphDatabaseService db;
    
    @Override
    public List<PercorreNo> buscarTodos() {
        
        //Pega o Banco
        db = ConexaoBanco.getConnection();
        
        List lista = new ArrayList();
        
	try ( Transaction transaction = db.beginTx();
                
        //"START n = node(*) MATCH (n)-[r]->(x) return n, type(r), x"
                
        //MATCH (n)-[r]-(x) RETURN n, type(r), x
                
        Result result = db.execute( "MATCH (n)-[r]->(x) RETURN n, type(r), x" ) ){
            
            while ( result.hasNext() ){
                
                Map<String,Object> row = result.next();

                Node x = (Node) row.get("x");
                Node n = (Node) row.get("n");

                //Cria um objeto No para NoInicial
                No noInicial = new No();
                
                //Cria um objeto No para NoFinal
                No noFinal = new No();
                
                //Cria o percorreNo
                PercorreNo percorreNo = new PercorreNo();
        
                for (String propriedade : n.getPropertyKeys()) {   
            
                    //Seta os atributos de NoInicial
                    switch (propriedade){

                        case "id":
                            noInicial.setId(n.getProperty(propriedade).toString());
                        break;

                        case "nome":
                            noInicial.setNome(n.getProperty(propriedade).toString());
                        break;

                        case "descricao":
                            noInicial.setDescricao(n.getProperty(propriedade).toString());
                        break;

                    }
                

                } 
                
                //pega o tipo de relacionamento
                String strRelacionamento = (row.get("type(r)").toString());
                
                //Seta o relacionamento
                switch (strRelacionamento) {

                    case "E_UMA":
                            percorreNo.setRelacionamento(Relacionamento.E_UMA);
                        break;

                    case "ESTA_CONTIDO":
                            percorreNo.setRelacionamento(Relacionamento.ESTA_CONTIDO);
                        break;

                    case "EH":
                            percorreNo.setRelacionamento(Relacionamento.EH);
                        break;

                }
        
        
                //percorre os atributos do nÃ³ final
                for (String propriedade : x.getPropertyKeys()){                              

                    //Seta os atributos de NoFinal
                    switch (propriedade){

                        case "id":
                            noFinal.setId(x.getProperty(propriedade).toString());
                        break;

                        case "nome":
                            noFinal.setNome(x.getProperty(propriedade).toString());
                        break;

                        case "descricao":
                            noFinal.setDescricao(x.getProperty(propriedade).toString());
                        break;

                    }

                }        

                //Seta NoInicial e NoFinal no PercorreNo
                percorreNo.setNoInicial(noInicial);
                percorreNo.setNoFinal(noFinal);

                //adiciona na lista o percorreNo
                lista.add(percorreNo);     

                
            }//fim do while
            
            //Termina a transacao com sucesso
            transaction.success();    
            
        }//Fim do result execute
        
        
        
        //Se a lista estiver vazia eh pq provavelmente os nos nao tem relacionamentos
        //Entao vamos pegar soh eles para mostrar na tela
        //if(lista.isEmpty()){
            
            try ( Transaction transaction = db.beginTx();
                
            Result result = db.execute( "MATCH (n) RETURN n" ) ){
            
            while ( result.hasNext() ){
                
                Map<String,Object> row = result.next();

                Node n = (Node) row.get("n");

                //Cria um objeto No para NoInicial
                No noInicial = new No();
                
                //Cria um objeto No para NoFinal
                No noFinal = new No();
                
                //Cria o percorreNo
                PercorreNo percorreNo = new PercorreNo();
        
                for (String propriedade : n.getPropertyKeys()) {   
            
                    //Seta os atributos de NoInicial
                    switch (propriedade){

                        case "id":
                            noInicial.setId(n.getProperty(propriedade).toString());
                        break;

                        case "nome":
                            noInicial.setNome(n.getProperty(propriedade).toString());
                        break;

                        case "descricao":
                            noInicial.setDescricao(n.getProperty(propriedade).toString());
                        break;

                    }
                

                } 
                
                
                //Seta NoInicial e NoFinal no PercorreNo
                percorreNo.setNoInicial(noInicial);
                percorreNo.setNoFinal(noFinal);

                //adiciona na lista o percorreNo
                lista.add(percorreNo);     

                
            }//fim do while
            
            //Termina a transacao com sucesso
            transaction.success();    
            
        }//Fim do result execute
            
            
        //}
        

        
        //Retorna a lista de percorreNo
        return lista;
                
    }
    
    
    @Override
    public List<PercorreNo> buscarNos(String busca){
      
        
        //Pega o Banco
        db = ConexaoBanco.getConnection();
        
        List lista = new ArrayList();
        
        //cria um map chamado params para colocar chave/valor
       Map<String, Object> params = new HashMap<>();
       
       //coloca o valor conceito na chave conceito
       params.put( "busca", "(?i)" + busca);
        
	try ( Transaction transaction = db.beginTx();
                
        //"START n = node(*) MATCH (n)-[r]->(x) return n, type(r), x"
                
        Result result = db.execute( "START n = node(*)"
                                    + "MATCH (n)-[r]->(x)"
                                    + "WHERE"
                                    + " has(n.nome) and n.nome=~{busca} or"
                                    + " has(x.nome) and x.nome=~{busca}"
                                    + "return n, type(r), x", params) ){
            
            while ( result.hasNext() ){
                
                Map<String,Object> row = result.next();
                
                Node x = (Node) row.get("x");
                Node n = (Node) row.get("n");

                //Cria um objeto PercorreNo
                PercorreNo percorreNo = new PercorreNo();   

                //Cria um objeto No para NoInicial
                No noInicial = new No();

                //Cria um objeto No para NoFinal
                No noFinal = new No();


                for (String propriedade : n.getPropertyKeys()) {   

                    //Seta os atributos de NoInicial
                    switch (propriedade){

                        case "id":
                            noInicial.setId(n.getProperty(propriedade).toString());
                        break;

                        case "nome":
                            noInicial.setNome(n.getProperty(propriedade).toString());
                        break;

                        case "descricao":
                            noInicial.setDescricao(n.getProperty(propriedade).toString());
                        break;

                    }

                }  

                //pega o tipo de relacionamento
                String strRelacionamento = (row.get("type(r)").toString());

                //Seta o relacionamento
                switch (strRelacionamento) {

                    case "E_UMA":
                            percorreNo.setRelacionamento(Relacionamento.E_UMA);
                        break;

                    case "ESTA_CONTIDO":
                            percorreNo.setRelacionamento(Relacionamento.ESTA_CONTIDO);
                        break;

                    case "EH":
                            percorreNo.setRelacionamento(Relacionamento.EH);
                        break;

                }


                //percorre os atributos do nÃ³ final
                for (String propriedade : x.getPropertyKeys()){                              

                    //Seta os atributos de NoFinal
                    switch (propriedade){

                        case "id":
                            noFinal.setId(x.getProperty(propriedade).toString());
                        break;

                        case "nome":
                            noFinal.setNome(x.getProperty(propriedade).toString());
                        break;

                        case "descricao":
                            noFinal.setDescricao(x.getProperty(propriedade).toString());
                        break;

                    }

                }        

                //Seta NoInicial e NoFinal no PercorreNo
                percorreNo.setNoInicial(noInicial);
                percorreNo.setNoFinal(noFinal);

                //adiciona na lista o percorreNo
                lista.add(percorreNo);           
                
            }//fim do while
        
            transaction.success();
        
        }//Fim do result execute
        
        return lista;
          
    }
    
    @Override
    public List<PercorreNo> buscarPercorreNoPorIdNo(String id){
      
        
        //Pega o Banco
        db = ConexaoBanco.getConnection();
        
        List lista = new ArrayList();
        
        //cria um map chamado params para colocar chave/valor
       Map<String, Object> params = new HashMap<>();
       
       //coloca o valor conceito na chave conceito
       params.put( "busca", "(?i)" + id);
        
	try ( Transaction transaction = db.beginTx();
                
        //"START n = node(*) MATCH (n)-[r]->(x) return n, type(r), x"
                
        Result result = db.execute( "START n = node(*)"
                                    + "MATCH (n)-[r]->(x)"
                                    + "WHERE"
                                    + " has(n.id) and n.id=~{busca} "                                    
                                    + "return n, type(r), x", params) ){
            
            while ( result.hasNext() ){
                
                Map<String,Object> row = result.next();
                
                Node x = (Node) row.get("x");
                Node n = (Node) row.get("n");

                //Cria um objeto PercorreNo
                PercorreNo percorreNo = new PercorreNo();   

                //Cria um objeto No para NoInicial
                No noInicial = new No();

                //Cria um objeto No para NoFinal
                No noFinal = new No();


                for (String propriedade : n.getPropertyKeys()) {   

                    //Seta os atributos de NoInicial
                    switch (propriedade){

                        case "id":
                            noInicial.setId(n.getProperty(propriedade).toString());
                        break;

                        case "nome":
                            noInicial.setNome(n.getProperty(propriedade).toString());
                        break;

                        case "descricao":
                            noInicial.setDescricao(n.getProperty(propriedade).toString());
                        break;

                    }

                }  

                //pega o tipo de relacionamento
                String strRelacionamento = (row.get("type(r)").toString());

                //Seta o relacionamento
                switch (strRelacionamento) {

                    case "E_UMA":
                            percorreNo.setRelacionamento(Relacionamento.E_UMA);
                        break;

                    case "ESTA_CONTIDO":
                            percorreNo.setRelacionamento(Relacionamento.ESTA_CONTIDO);
                        break;

                    case "EH":
                            percorreNo.setRelacionamento(Relacionamento.EH);
                        break;

                }


                //percorre os atributos do nÃ³ final
                for (String propriedade : x.getPropertyKeys()){                              

                    //Seta os atributos de NoFinal
                    switch (propriedade){

                        case "id":
                            noFinal.setId(x.getProperty(propriedade).toString());
                        break;

                        case "nome":
                            noFinal.setNome(x.getProperty(propriedade).toString());
                        break;

                        case "descricao":
                            noFinal.setDescricao(x.getProperty(propriedade).toString());
                        break;

                    }

                }        

                //Seta NoInicial e NoFinal no PercorreNo
                percorreNo.setNoInicial(noInicial);
                percorreNo.setNoFinal(noFinal);

                //adiciona na lista o percorreNo
                lista.add(percorreNo);           
                
            }//fim do while
        
            transaction.success();
        
        }//Fim do result execute
        
        return lista;
          
    }

}
