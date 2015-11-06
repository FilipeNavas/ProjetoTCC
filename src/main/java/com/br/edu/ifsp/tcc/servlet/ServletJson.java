/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.edu.ifsp.tcc.servlet;

import com.br.edu.ifsp.tcc.dao.NoDao;
import com.br.edu.ifsp.tcc.dao.PercorreNoDao;
import com.br.edu.ifsp.tcc.interfaces.NoInterface;
import com.br.edu.ifsp.tcc.interfaces.PercorreNoInterface;
import com.br.edu.ifsp.tcc.modelo.No;
import com.br.edu.ifsp.tcc.modelo.PercorreNo;
import com.br.edu.ifsp.tcc.modelo.Relacionamento;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Filipe
 */

@WebServlet(name = "ServletJson", urlPatterns = {"/ServletJson"})
public class ServletJson extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        
        String conceito = request.getParameter("conceito");
        String acao = request.getParameter("selecao");
      
        try {  

            switch (acao) {
                case "todos":
                    {
                        //Cria um objeto de ConceitoDao
                        PercorreNoInterface percorreNoDao = new PercorreNoDao();
                        //Chama o metodo de busca
                        List lista = percorreNoDao.buscarTodos();
                        //Converte para JSON
                        // create a new Gson instance
                        Gson gson = new Gson();
                        // convert your list to json
                        String jsonTodos = gson.toJson(lista);

                        //Outro meio de mandar o Json
                        response.getWriter().write(jsonTodos);
                        break;
                    }
                
                case "busca":
                    {
                        //Cria um objeto de PercorreNoDao
                        PercorreNoInterface percorreNoDao = new PercorreNoDao();
                        //Chama o metodo de busca
                        List lista = percorreNoDao.buscarNos(conceito);
                        //Converte para JSON
                        // create a new Gson instance
                        Gson gson = new Gson();
                        // convert your list to json
                        String jsonLivros = gson.toJson(lista);

                        //Outro meio de mandar o Json
                        response.getWriter().write(jsonLivros);
                            break;
                    }
                
                case "buscaNo":
                    {
                        //Pega o Id do no que esta no request
                        String idNo = request.getParameter("idNo");
                        //Cria um objeto de NoInterface
                        
                        NoInterface noDao = new NoDao();
                        
                        //Chama o metodo de busca
                        No no = noDao.buscarNoPorId(idNo);
                        
                        //noDao.createNo();
                        
                        //Converte para JSON
                        // create a new Gson instance
                        Gson gson = new Gson();
                        // convert your list to json
                        String jsonLivros = gson.toJson(no);

                        //Outro meio de mandar o Json
                        response.getWriter().write(jsonLivros);
                        break;
                    }
                
                case "criarNo":
                    {
                        
                        //Pega a operacao (editar ou novo)
                        String operacao = request.getParameter("operacao");
                        
                        //Pega o nome do no a ser criado
                        String nomeNo = request.getParameter("nomeNo");
                        String descricaoNo = request.getParameter("descricaoNo");
                        
                        //Pega os nos a serem relacionados
                        String nos = request.getParameter("nos");
                        //Pega os relacionamentos dos nos com o novo no
                        String relacionamentos = request.getParameter("rels");
                        
                        String[] nosArray = nos.split(",");
                        String[] relsArray = relacionamentos.split(",");
                        
                        //Monta o novo no a ser criado
                        No novoNo = new No();
                        novoNo.setDescricao(descricaoNo);
                        novoNo.setNome(nomeNo);
                        
                        //Cria uma lista de PercorreNo, que vai ter os PercorreNos que indicam
                        //o relacionamento do novo no com outros e os tipos de relacionamento
                        List listaPercorreNo = new ArrayList();
                        
                        //Itera os arrays
                        for (int i = 0; i < nosArray.length; i++) {
                            
                            //Pega os valores na posicao
                            String noStr = nosArray[i];
                            String relStr = relsArray[i];
                            
                            No noFinal = new No();
                            noFinal.setId(noStr);
                            
                            PercorreNo pn = new PercorreNo();
                            
                            //Adiciona no noinicial e nofinal
                            pn.setNoInicial(novoNo);
                            pn.setNoFinal(noFinal);
                            
                            //Adiciona o tipo de relacionamento
                            switch(relStr){
                                case "E_UMA":
                                    pn.setRelacionamento(Relacionamento.E_UMA);
                                break;
                                case "EH":
                                    pn.setRelacionamento(Relacionamento.EH);
                                break;
                                case "ESTA_CONTIDO":
                                    pn.setRelacionamento(Relacionamento.ESTA_CONTIDO);
                                break;
                            }
                            
                            //Adiciona o objeto a lista
                            listaPercorreNo.add(pn);
                            
                        }
                        
                        
                        NoInterface noDao = new NoDao();
                        
                        //Pega o Id do no (se tiver - tem no editar e deletar)
                        String idNo = request.getParameter("idNo");
                        novoNo.setId(idNo);
                        
                        //Se a o relacionamento da lista for nula no primeiro elemento,
                        //quer dizer que nao foi adicionado nenhum no e relacionamento de vdd.
                        //O unico elemento que tem eh o noNovo (noInicial) que sempre passamos.
                        PercorreNo pn = (PercorreNo) listaPercorreNo.get(0);
                                
                        if(pn.getRelacionamento() == null){
                            listaPercorreNo = null;
                        }
                        
                        //Ve qual a operacao (se eh novo ou editar)
                        switch (operacao){
                            case "novo":
                                
                               
                                noDao.criarNo(novoNo, listaPercorreNo);
                                break;
                            case "editar":
                                
                                //Cria um objeto de PercorreNoDao
                                //PercorreNoInterface percorreNoDao = new PercorreNoDao();
                        
                                //List pnLista = percorreNoDao.bucarPercorreNoPorIdNo(idNo);
                                
                                noDao.editarNo(novoNo, listaPercorreNo);
                                break;
                                
                            //Excluir o No(e seus relacionamentos)
                            case "deletar":
                                noDao.deletarNo(novoNo, listaPercorreNo);
                                break;
                        }
                        
                        //Cria um objeto Gson
                        Gson gson = new Gson();
                        
                        // convert to json
                        //Retorna uma msg
                        String jsonString = gson.toJson("SUCESSO");

                        //Outro meio de mandar o Json
                        response.getWriter().write(jsonString);
                        
                    }
                case "buscaTodosNos":
                    {
                        NoInterface noDao = new NoDao();
                        
                        List lista = noDao.buscarTodosNos();
                        
                        //Converte para JSON
                        // create a new Gson instance
                        Gson gson = new Gson();
                        // convert your list to json
                        String jsonNos = gson.toJson(lista);
                        
                        //Envia o Json
                        response.getWriter().write(jsonNos);
                        
                        break;
                        
                    }
                case "buscaRelacionamentos":
                    {
                        List lista = new ArrayList();
                        lista.add(Relacionamento.EH);
                        lista.add(Relacionamento.ESTA_CONTIDO);
                        lista.add(Relacionamento.E_UMA);
                        
                        //Converte para JSON
                        // create a new Gson instance
                        Gson gson = new Gson();
                        // convert your list to json
                        String jsonRelacionamentos = gson.toJson(lista);
                        
                        //Envia o Json
                        response.getWriter().write(jsonRelacionamentos);
                        
                    }
                case "buscaRelacionamentosDoNo":
                    {
                        //Cria um objeto de PercorreNoDao
                        PercorreNoInterface percorreNoDao = new PercorreNoDao();

                        String idNo = request.getParameter("idNo");

                        List pnLista = percorreNoDao.buscarPercorreNoPorIdNo(idNo);
                        
                        //Converte para JSON
                        // create a new Gson instance
                        Gson gson = new Gson();
                        String retorno;
                            
                        //Se a lista estiver vazio retorna a msg "vazio"
                        if(pnLista.isEmpty()){
                            
                            retorno = "vazio";

                        }else{
                        
                            // convert your list to json
                            retorno = gson.toJson(pnLista);

                        }
                        
                        //Envia o Json
                        response.getWriter().write(retorno);
                    }
                    
            }

        }catch (IOException exc){
        }finally {            
            out.close();
        }//fim do finally
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
