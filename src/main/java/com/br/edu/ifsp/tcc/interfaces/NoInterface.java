/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.edu.ifsp.tcc.interfaces;

import com.br.edu.ifsp.tcc.modelo.No;
import com.br.edu.ifsp.tcc.modelo.PercorreNo;
import java.util.List;


/**
 *
 * @author Filipe
 */
public interface NoInterface {
    
    /**
     * Busca um No dado um id
     * @param busca
     * @return 
     */
    public No bucarNoPorId(String busca);
    
    public void criarNo(No novoNo, List<PercorreNo> listaNoRelacionamentos);
    
    /**
     * Busca todos os nos que tem no BD
     * @return 
     */
    public List<No> buscarTodosNos();
    
    public void editarNo(No no, List<PercorreNo> listaNoRelacionamentos);

}
