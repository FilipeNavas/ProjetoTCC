/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.edu.ifsp.tcc.interfaces;

import com.br.edu.ifsp.tcc.modelo.PercorreNo;
import java.util.List;
/**
 *
 * @author Filipe
 */
public interface PercorreNoInterface {
    
    /**
    * Este metodo busca tudo o que existe no BD.
    * @return List<PercorreNo>
    */
   public List<PercorreNo> bucarTodos();
   
   /**
    * Este metodo faz a busca de nos e relacionamentos pela busca do usuario.
    * @param busca
    * @return 
    */
   public List<PercorreNo> bucarNos(String busca);
    
}
