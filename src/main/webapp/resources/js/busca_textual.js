/* 
 * Este arquivo contem o JavaScript para a Busca Textual.
 */

$(document).ready(function() { 
                                      
$("#btnEnviar").click(function() {

      
      conceito = $("#conceito").val();
      selecao = $("#selecao").val();

          //Se o conceito ou a selcao for vazio, mostra uma mensagem de erro.
          if(conceito === "" || selecao === null){

              //Mostra a msg
              $("#divMensagemCampoRequerido").fadeIn();

              //Esvazia a div atualizar
              $("#divAtualizar").html("");

          }else{

              //Esconde a msg (caso ela esteja visivel)
              $("#divMensagemCampoRequerido").fadeOut(500);

              //Adiciona a classe CSS de carregando na div
              $("#btnEnviar").addClass("carregando");

              $.ajax({
                  type : "POST",

                  url : "http://localhost:8080/ProjetoTCC/ServletPercorrerOntologia",
                  //Esse comando abaixo pega o servidor(IP local) e a porta dinamicamente
                  //url : "http://<%= request.getServerName() + ":" + request.getServerPort() %>/ProjetoTCC/ServletPercorrerOntologia",
                  data : "conceito=" + conceito + "&selecao=" + selecao,                    
                  success : function(html) {

                      //console.log("HTML");
                      //console.log(html);

                      //Se nulo, vamos mostrar uma msg de que nao tem nenhum resultado
                      if(flagVazio === 'vazio'){

                            //console.log("ENTRO");
                            $("#divMensagemNenhumResultado").fadeIn();

                      }else{

                            //console.log("RESULTS");
                            //Esconde a msg de nenhum resultado caso ela esteja presente
                            $("#divMensagemNenhumResultado").fadeOut();

                            $("#divAtualizar").replaceWith($('#divAtualizar', $(html)));  
                            $("#divAtualizar").css( "visibility", "visible" ); 
                            //se entrou aqui é true, então tem q mostrar a div, então usa o jquery pra mudar o css da div

                            //Remove a classe CSS de carregando na div
                            //Chama a funcao que tira a classe CSS, passando um delay de meio segundo.
                            setTimeout( delay, 500 );
                          
                      }

                  }
               }); 

           }
    
    
    });
});   


  function delay() 
  {
    $("#btnEnviar").removeClass("carregando");
  };
  
  