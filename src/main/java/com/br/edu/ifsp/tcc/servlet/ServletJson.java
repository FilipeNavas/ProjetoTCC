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
                        List lista = percorreNoDao.bucarTodos();
                        //Converte para JSON
                        // create a new Gson instance
                        Gson gson = new Gson();
                        // convert your list to json
                        String jsonLivros = gson.toJson(lista);

                        //Outro meio de mandar o Json
                        response.getWriter().write(jsonLivros);
                        break;
                    }
                
                case "busca":
                    {
                        //Cria um objeto de ConceitoDao
                        PercorreNoInterface percorreNoDao = new PercorreNoDao();
                        //Chama o metodo de busca
                        List lista = percorreNoDao.bucarNos(conceito);
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
                        No no = noDao.bucarNoPorId(idNo);
                        
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
                        
                        //Ve qual a operacao (se eh novo ou editar)
                        switch (operacao){
                            case "novo":
                                noDao.criarNo(novoNo, listaPercorreNo);
                                break;
                            case "editar":
                                noDao.editarNo(novoNo, listaPercorreNo);
                                break;
                        }
                        
                        //Cria um objeto Gson
                        Gson gson = new Gson();
                        
                        // convert to json
                        String jsonString = gson.toJson("suceso");

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
