/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.edu.ifsp.tcc.modelo;

import org.neo4j.graphdb.RelationshipType;



/**
 *
 * @author Filipe
 */
public enum Relacionamento implements RelationshipType{
    
    EH_UMA,
    ESTA_CONTIDO,
    EH;
    
}
