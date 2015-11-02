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
        
        <!-- CUSTOM JS -->
        <script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
    
        <title>Inicio</title>
        
    
   </head>
   
       
        
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
                <li><a href="${pageContext.request.contextPath}/buscaTextual.jsp">Busca Textual</a></li>
                <li><a href="${pageContext.request.contextPath}/buscaVisual.jsp">Busca Visual</a></li>
              </ul>
              <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="${pageContext.request.contextPath}/sobre.jsp">Sobre</a></li>
              </ul>
            </div><!--/.nav-collapse -->
          </div>
        </nav>
    
 
      
        <div class="container">
            
        
            <div class="jumbotron centralizado">
                <div class="row">
            
                    <div class="span6">
                        <p>Desenvolvido por  Willian César  e Filipe Navas Rodrigues</P>

                        <p><b>Orientadores</b> <br>
                            Profª Dra. Rosana Ferrareto Lourenço Rodrigues <br>
                            Profº Ms.  Gustavo Aurélio Prieto <br>
                            Ms. Maria Carolina Gonçalves
                        </p>
                        
                        <hr>
                        <p>
                            Layout feito com <a href="http://getbootstrap.com">Bootstrap</a>
                            <br>
                            Grafo feito com <a href="http://visjs.org/index.html">Vis.js</a>
                        </p>
   
                    </div>

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
      
      
  


        
        
      
    
    
</html>
  
  
 
 