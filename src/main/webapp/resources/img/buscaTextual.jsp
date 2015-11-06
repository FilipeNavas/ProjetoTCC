<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="Projeto de ferramenta de apoio à indexação de obras em uma acervo.">
        <meta name="author" content="IFSP SBV">
        <link rel="icon" href="../../favicon.ico">
        
        <!-- Bootstrap CSS -->
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        
        <!-- Custom CSS -->
        <link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">
        
        
        <!-- JAVASCRIPT -->
    
        <!-- JQUERY -->
        <!-- GOOGLE CDN  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>    -->
        <!-- LOCAL -->      <script src="${pageContext.request.contextPath}/resources/js/jquery/jquery-2.1.4.min.js"></script>
        
        <!-- BOOTSTRAP -->
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
        
        <!-- BUSCA TEXTUAL JS -->
        <script src="${pageContext.request.contextPath}/resources/js/busca_textual.js"></script>
        
        <!-- CUSTOM JS -->
        <script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
    
        <title>Busca Textual</title>
   
    </head>
   
    <body>       
        
       <!-- Static navbar -->
        <nav class="navbar navbar-default navbar-static-top">
          <div class="container">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="${pageContext.request.contextPath}/">Ontologia Indexador</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/">Ínicio</a></li>
                <li  class="active"><a href="${pageContext.request.contextPath}/buscaTextual.jsp">Busca Textual</a></li>
                <li><a href="${pageContext.request.contextPath}/buscaVisual.jsp">Busca Visual</a></li>
              </ul>
              <ul class="nav navbar-nav navbar-right">
                <li><a href="${pageContext.request.contextPath}/sobre.jsp">Sobre</a></li>
              </ul>
            </div><!--/.nav-collapse -->
          </div>
        </nav>
    
 
      
        <div class="container">
            
        
            <div class="jumbotron centralizado">
             <h1>Busca Textual</h1>
             <strong class="lead">
                 Busca dos conceitos e relacionamentos por texto
             </strong>
         </div>
        
        
        
        <div align="center">
            
            
            <div id="fomulario" class="row">
                <div class="col-md-12">
              
                    <div class="col-md-4 col-md-offset-1">
                        <label class="col-md-12">Conceito</label>
                    </div> 
                    
                    <div class="col-md-4">
                        <label class="col-md-12">Tipos de Busca</label>
                    </div>
                    
                </div>
                
                <div class="row col-md-12">
                    <div class="col-md-4 col-md-offset-1">
                        <input type="text" id ="conceito" name="conceito" class="col-md-12 input-lg" />
                    </div>
                    
                    <div class="col-md-4">
                        
                        <select name="select" id="selecao" class="col-md-12 input-lg">
                            <option name="click" onchange="l" value="l">Busca por Livro</option>
                            <option name="click" onchange="r"  value="r">Busca por Relacionamento</option>
                        </select>
                       
                    </div>
                    
                    <div class="col-md-2">
                        <a href="#" id="btnEnviar"  class="btn btn-success btn-large input-lg centralizado col-md-12" style="font-size: 22px;">
                            Buscar
                        </a>
                    </div>
                    
                    
                    
                </div>
                
            </div>  
            
            
           
        <!-- DIV MENSAGEM CAMPO REQUERIDO -->
        <div id="divMensagemCampoRequerido" class="alert alert-warning col-md-10 col-md-offset-1" hidden="true" style="margin-top: 10px;">
            Por favor, preencha todos os campos da busca.
        </div>
        
        <!-- DIV ATUALIZAR - Onde os resultados aparecem -->
        <div id="divAtualizar"  style="visibility: hidden; margin-top: 10px;">         
        <table id="tableAtualizar" class="table table-bordered table-hover col-md-12">
           
        <c:choose>
                <%-- QUANDO AS DUAS LISTAS FOREM VAZIAS MOSTRA A MSG DE NEHNHUM RESULTADO --%>
                <c:when test="${empty livros and empty percorre}">
                    
                    <!-- DIV MENSAGEM CAMPO REQUERIDO -->
                    <div id="divMensagemCampoRequerido" class="alert alert-info col-md-10 col-md-offset-1" style="margin-top: 10px;">
                        Nenhum resultado obtido.
                    </div>

                </c:when>
                
                <%-- SENAO, VAMOS CONFERIR QUAL LISTA EH E MONTAR A TABELA --%>
                <c:otherwise>

                    <c:choose>
                        <c:when test="${livros != null }">
                             <thead>
                                <th>
                                    Autor
                                </th>
                                <th>
                                    Título
                                </th>
                                <th>
                                    Edição
                                </th>
                                <th>
                                    Cidade
                                </th>
                                <th>
                                    Editora
                                </th>
                                <th>
                                    Ano
                                </th>
                                <th>
                                    Páginas
                                </th>
                             </thead>
                        </c:when>
                        <c:otherwise>
                            <thead>
                            <th style="width: 120px;">
                                    Nó inicial
                                </th>
                                <th>
                                    Relacionamento
                                </th>
                                <th>
                                    Nó final
                                </th>
                             </thead>
                        </c:otherwise>
                    </c:choose> <%-- FIM CHOOSE LIVROS != NULL --%>

                    <c:forEach  var="livro" items="${livros}">
                       <tr>                    
                            <td width="500">${livro.autor}</td>                   
                            <td width="500">${livro.titulo}</td>
                            <td>${livro.edicao}</td>              
                            <td>${livro.cidade}</td>
                            <td>${livro.editora}</td>
                            <td>${livro.ano}</td>
                            <td>${livro.paginas}</td>
                        </tr>
                    </c:forEach>

                    <c:forEach  var="percorre" items="${percorre}">          
                        <tr> 
                            <td>${percorre.noInicial}</td>
                            <td>${percorre.tipoRelacionamento}</td>
                            <td <td width="1728">>${percorre.noFinal}</td>
                        </tr>
                    </c:forEach>  

                </c:otherwise> <%-- FIM OTHERWISE WHEN (IF) TUDO VAZIO --%>
        </c:choose>
        </table>
        </div>
            
            
        </div>    
            
        </div>    
      
        <footer class="footer">
            <div class="container">
                <div class="col-md-12">
                    <div class="col-md-4">
                        <p class="text-muted">Desenvolvido por  <a href="https://github.com/willcehsar/">Willian César</a>  e <a href="https://github.com/FilipeNavas">Filipe Navas</a></p>
                    </div>
                    <div class="col-md-7">
                        <p class="text-muted">Orientado por Profª Dra. Rosana Ferrareto, Profº Ms. Gustavo Prieto e Ms. Maria Carolina</p>
                    </div>
                    <div class="col-md-1">
                        <p class="text-muted"><a href="https://sbv.ifsp.edu.br/">IFSP</a></p>
                    </div>
                </div>
                
                
            </div>
        </footer>
      
      
  


        
        
      
    
  </body>      
</html>
  
  
 
 