/* 
* Este arquivo contem o JavaScript para a Busca Visual.
*/
/* global vis, layoutMethod */

var network;
var layoutMethod = "hubsize";

$(document).ready(function() { 
    
    //Esconde o bloco de detalhes
    $('#blockDetails').hide();
    
    //Esconde a msg de salvar o no caso ela esteja visivel
    $("#msgSalvarNo").hide();
    
    //Quando o modal esconde (o user fecha ele), vamos esconder a msg do Salvar No
    $('#myModal').on('hidden.bs.modal', function (e) {
        //Esconde a msg de salvar o no caso ela esteja visivel
        $("#msgSalvarNo").hide();
    });
    
    //############## BOTAO NOVO NO - ABRE O MODAL ###############
    $("#btnModal").click(function() {
        //Passa pro input operacao o valor novo
        $("#inputOperacao").val("novo");
        
        //Limpa os edits
        $("#nomeNo").val("");
        $("#descricaoNo").val("");
        
        $('#myModal').modal('show');
    });
    
    
    //############## BUSCA ###############
    
    $("#btnEnviar").click(function() {

        conceito = $("#conceito").val();
        //selecao = $("#selecao").val();

          //Se o conceito ou a selcao for vazio, mostra uma mensagem de erro.
          if(conceito === ""){

              //Mostra a msg
              $("#divMensagemCampoRequerido").fadeIn();

              //Esvazia a div atualizar
              $("#mynetwork").html("");

          }else{

              //Esconde a msg (caso ela esteja visivel)
              $("#divMensagemCampoRequerido").fadeOut(500);

              //Adiciona a classe CSS de carregando na div
              $("#btnEnviar").addClass("carregando");
              
                
            $.ajax({
                type : "POST",

                url : "http://localhost:8080/ProjetoTCC/ServletJson",
                //Esse comando abaixo pega o servidor(IP local) e a porta dinamicamente
                //url : "http://<%= request.getServerName() + ":" + request.getServerPort() %>/ProjetoTCC/ServletPercorrerOntologia",
                data : "conceito=" + conceito + "&selecao=busca" ,                    
                success : function(data) {
                    
                    console.log(data);
                    
                    //noFinal: "tipo: Linguagem de programacao. "
                    //noInicial: "Java"
                    //tipoRelacionamento: "e_uma"

                    //######### GRAFO ##########
                    
                    //NOS - NODES
                    var options = {};
                    var nodes = new vis.DataSet(options);
                    
                    //console.log("FOR");
                    $.each( data, function( key, val ) {
                        console.log("Id: " + val.noFinal.id + " - Val:" + val.noFinal.nome);
                      
                        //Adiciona os nos no dataset
                        //Aqui ele faz um try/catch pq em alguns momentos o mesmo no pode estar em noFInal (e tbm noFinal), dando erro 
                        //do id. Sendo assim, qdo cai no catch, ele faz o inverso, pegando o noInicial primeiro e depois o final.
                        //Assim fica garantido que todos os nos dessa busca serao retornados.
                        try {
                            //Adiciona os nos no DataSet
                            nodes.add([
                                //{id: key, label: "'" + val.categoria.tipo + "'"}
                                {id: val.noFinal.id, label: "'" + val.noFinal.nome + "'"}
                            ]);
                            
                            //Adiciona os nos no DataSet
                            nodes.add([
                                //{id: key, label: "'" + val.categoria.tipo + "'"}
                                {id: val.noInicial.id, label: "'" + val.noInicial.nome + "'"}
                            ]);
                        }
                        catch(err) {
                            console.log(err);
                            
                            try {

                                //Adiciona os nos no DataSet
                                nodes.add([
                                    //{id: key, label: "'" + val.categoria.tipo + "'"}
                                    {id: val.noInicial.id, label: "'" + val.noInicial.nome + "'"}
                                ]);
                                
                                //Adiciona os nos no DataSet
                                nodes.add([
                                    //{id: key, label: "'" + val.categoria.tipo + "'"}
                                    {id: val.noFinal.id, label: "'" + val.noFinal.nome + "'"}
                                ]);

                            }
                            catch(err) {
                                console.log(err);
                            }
                            
                            
                        }
                        
                        
                        
                        
                    });
                    
                    //Adiciona o conceito, ou seja, o no principal procurado
                    //nodes.add([
                    //        {id: 4, label: 'Classe'}
                    //    ]);
                    //FIM NOS - NODES                    
                    
                    

                    // RELACIONAMENTOS - EDGES
                    // create an array with edges var options = {};
                    var edges = new vis.DataSet(options);
 
                    $.each( data, function( key, val ) {
                        console.log("Inicial Id: " + val.noInicial.id + " - Final:" + val.noFinal.id + + " - Rel:" + val.relacionamento);
                        
                        //Adiciona os edges no DataSet
                        edges.add([
                            //{id: key, label: "'" + val.noFinal + "'"}
                            {id: key , from: val.noInicial.id, to: val.noFinal.id, arrows: 'to', label: "'" + val.relacionamento + "'", font: {align: 'bottom', size: '10'}}
                        ]);
                        
                    });
                    //FIM RELACIONAMENTOS - EDGES

                    // create a network
                    createGraph(nodes, edges);
                }
                
                
             }); 
             
            //Remove a classe CSS de carregando na div
            //Chama a funcao que tira a classe CSS, passando um delay de meio segundo.
            setTimeout( removeCarregandoDelay("#btnEnviar"), 500 );

           }
    
    
    });
    
    

    
    //################ TODOS #######################3
    $("#btnTodos").click(function() {

            pegarTodosNos();
    
            //Adiciona a classe CSS de carregando na div
            $("#mynetwork").addClass("carregando");
            
            $.ajax({
                type : "POST",

                url : "http://localhost:8080/ProjetoTCC/ServletJson",
                //Esse comando abaixo pega o servidor(IP local) e a porta dinamicamente
                //url : "http://<%= request.getServerName() + ":" + request.getServerPort() %>/ProjetoTCC/ServletPercorrerOntologia",
                //data : "&selecao=todos",       
                data : "selecao=" + 'todos',     
                success : function(data) {
                    
                    //Esconde a msg (caso ela esteja visivel)
                    $("#divMensagemCampoRequerido").fadeOut(500);
                    
                    console.log("TODOS");
                    console.log(data);
                    
                    //noFinal: "tipo: Linguagem de programacao. "
                    //noInicial: "Java"
                    //tipoRelacionamento: "e_uma"

                    //######### GRAFO ##########
                    
                    //NOS - NODES
                    var options = {};
                    var nodes = new vis.DataSet(options);
                    
                    //console.log("FOR");
                    $.each( data, function( key, val ) {
                        console.log("Id: " + val.noFinal.id + " - Val:" + val.noFinal.nome);
                      
                        
                        //Adiciona os nos no DataSet
                        //Aqui ele faz um try/catch pq em alguns momentos o mesmo no pode estar em noFInal (e tbm noFinal), dando erro 
                        //do id. Sendo assim, qdo cai no catch, ele faz o inverso, pegando o noInicial primeiro e depois o final.
                        //Assim fica garantido que todos os nos dessa busca serao retornados.
                        try {
                            //Adiciona os nos no DataSet
                            nodes.add([
                                //{id: key, label: "'" + val.categoria.tipo + "'"}
                                {id: val.noFinal.id, label: "'" + val.noFinal.nome + "'"}
                            ]);
                            
                            //Adiciona os nos no DataSet
                            nodes.add([
                                //{id: key, label: "'" + val.categoria.tipo + "'"}
                                {id: val.noInicial.id, label: "'" + val.noInicial.nome + "'"}
                            ]);
                        }
                        catch(err) {
                            console.log(err);
                            
                            try {

                                //Adiciona os nos no DataSet
                                nodes.add([
                                    //{id: key, label: "'" + val.categoria.tipo + "'"}
                                    {id: val.noInicial.id, label: "'" + val.noInicial.nome + "'"}
                                ]);
                                
                                //Adiciona os nos no DataSet
                                nodes.add([
                                    //{id: key, label: "'" + val.categoria.tipo + "'"}
                                    {id: val.noFinal.id, label: "'" + val.noFinal.nome + "'"}
                                ]);

                            }
                            catch(err) {
                                console.log(err);
                            }
                            
                            
                        }
                        
                        
                    });
                    
                    //Adiciona o conceito, ou seja, o no principal procurado
                    //nodes.add([
                    //        {id: 4, label: 'Classe'}
                    //    ]);
                    //FIM NOS - NODES                    
                    
                    

                    // RELACIONAMENTOS - EDGES
                    // create an array with edges var options = {};
                    var edges = new vis.DataSet(options);
 
                    $.each( data, function( key, val ) {
                        console.log("Inicial Id: " + val.noInicial.id + " - Final:" + val.noFinal.id + + " - Rel:" + val.relacionamento);
                        
                        //Adiciona os edges no DataSet
                        edges.add([
                            //{id: key, label: "'" + val.noFinal + "'"}
                            {id: key , from: val.noInicial.id, to: val.noFinal.id, arrows: 'to', label: "'" + val.relacionamento + "'", font: {align: 'bottom', size: '10'}}
                        ]);
                        
                    });
                    //FIM RELACIONAMENTOS - EDGES
                    
                    createGraph(nodes, edges);
                
                }
                    
             });
             
            //Remove a classe CSS de carregando na div
            //Chama a funcao que tira a classe CSS, passando um delay de meio segundo.
            setTimeout( removeCarregandoDelay("#mynetwork"), 500 );
            
    
    });
    
    
    
    //################ CRIAR NO #######################3
    $("#btnSalvarNo").click(function() {
            
            var nomeNo = $("#nomeNo").val();
            var descricaoNo = $("#descricaoNo").val();
            var nos = '';
            var relacionamentos = '';
            var operacao = $("#inputOperacao").val();
            
            //Faz uma iteracao nos inputs dos relacionamentos, pegando cada um em ordem.
            $('input[name^="inputNosRelacionamentos"]').each(function() {
                //Se tiver vazio apenas adiciona o valor
                if(nos === ''){
                    nos = $(this).val();
                //Senao adiciona a virgula
                }else{
                    nos = nos + "," + $(this).val();
                }
            });
            
            //Faz uma iteracao nos inputs dos nos relacionados, pegando cada um em ordem.
            $('input[name^="inputNovosRelacionamentos"]').each(function() {
                //Se tiver vazio apenas adiciona o valor
                if(relacionamentos === ''){
                    relacionamentos = $(this).val();
                //Senao adiciona a virgula
                }else{
                    relacionamentos = relacionamentos + "," + $(this).val();
                }
            });
            
            
            //Validação dos campos
            if(nomeNo === '' || descricaoNo === ''){
                $("#msgSalvarNo").removeClass("alert-success"); //Remove a classe de sucesso (caso ela esteja lá)
                $("#msgSalvarNo").addClass("alert-warning"); //Adiciona a classe de alerta
                $("#msgSalvarNo").html("Todos os campos são requeridos!");
                $("#msgSalvarNo").fadeIn(); //Mostra a mensagem
                return false;
            }
                       
            $.ajax({
                type : "POST",

                url : "http://localhost:8080/ProjetoTCC/ServletJson",
                data : "selecao=" + 'criarNo' + "&nomeNo=" + nomeNo + "&descricaoNo=" + descricaoNo + "&nos=" + nos + "&rels=" + relacionamentos + "&operacao=" + operacao ,     
                success : function(data) {
                    //console.log("CRIAR NO - Sucesso");
                    $("#msgSalvarNo").removeClass("alert-warning"); //Remove a classe de alerta (caso ela esteja lá)
                    $("#msgSalvarNo").addClass("alert-success"); //Adiciona a classe de sucesso
                    $("#msgSalvarNo").html("Nó salvo com sucesso!");
                    $("#msgSalvarNo").fadeIn();
                    
                    //Apaga os campos
                    $("#nomeNo").val('');
                    $("#descricaoNo").val('');;
                    
                    //Chama a função de pegar todos os nós
                    pegarTodosNos();
                },
                fail: function() {
                    //console.log("CRIAR NO - Erro");
                    $("#msgSalvarNo").removeClass("alert-success"); //Remove a classe de sucesso (caso ela esteja lá)
                    $("#msgSalvarNo").addClass("alert-warning"); //Adiciona a classe de alerta
                    $("#msgSalvarNo").html("Problema ao criar Nó !");
                    $("#msgSalvarNo").fadeIn(); //Mostra a mensagem
                }
            });
    });
    
    
    //############## BOTAO ADICIONAR RELACIONAMENTO AO NO ######################
    $("#btnAdicionarRel").click(function() {
        
        var noNovo = $("#nomeNo").val(); //No novo 
        var relacionamento = $("#selectTodosRelacionamentos").val(); //Relacionamento selecionado
        var noRelacionadoText = $("#selectTodosNos option:selected").text(); //Texto do No final que esta sendo relacionado
        
        var noRelacionadoVal = $("#selectTodosNos").val(); //Valor do no final do relacionamento
        
        //Cria o HTML que mostra a adicoes e o link pra remover
        //Cria um link para remover: 
            // - Passa no metodo para remover a div (com os inputs)
        var paragrafoAdicionado = "<div id='div"+ relacionamento + noRelacionadoVal +"'><p class='col-md-10'>" + noNovo + ' - ' + relacionamento + ' - ' + noRelacionadoText + "</p>" 
                                + "<a class='col-md-2 no-padding' href='#!' onClick=removeNovoRel('div"+ relacionamento + noRelacionadoVal +"'); class='btn-link'>"
                                + "<span class='glyphicon glyphicon-minus-sign' aria-hidden='true'></span>"
                                + "Remover </a></div>";
                
        //Adiciona na div os novo relacionamento
        $("#divRelAdicionados").append(paragrafoAdicionado);
        
        //### Cria os inputs com os valores para usar no Servlet ###
        //Cria um contador para colocar no nome dos campos. Isso sera usado para pegar os valores de cada um, em sua ordem,
        //que sera usado pra criar os relacionamentos entre o novo no e os nos selecionados.
        var count = count + 1;
        //Nos
        var inputNo = "<input class='hidden' type='text' name='inputNosRelacionamentos["+count+"]' id='inputNosRelacionamentos' value='" + noRelacionadoVal + "' />";
        $("#div" + relacionamento + noRelacionadoVal).append(inputNo);    
        
        //Relacionamentos
        var inputRelacionamento = "<input class='hidden' type='text' name='inputNovosRelacionamentos["+count+"]' value='" + relacionamento + "' />";
        $("#div" + relacionamento + noRelacionadoVal).append(inputRelacionamento);    
        
        $("#divRelAdicionados").fadeIn();
        
        
    });
    //############# FIM BOTAO ADICIONAR RELACIONAMENTO AO NO ###################
    
    
    //################ RESIZE FOCUS - REFOCAR #######################3
    $("#btnFocar").click(function() {
        resizeFocus();
    });
    
    //############## ON SHOW MODAL - CARREGAR NOS #####################
    $('#myModal').on('show.bs.modal', function (e) {
        pegarTodosNos(); //Chama a função que carrega todos os nós no select
        pegarRelacionamentos(); //Chama a função que carrega os relacionamentos no select 
    });
    
    
}); 

//Função que remove um elemento do DOM apenas passando o id
function removeNovoRel(id){
    
    $("#"+id).remove();
    
    $('#divRelAdicionados:empty').fadeOut();    
    
    //Se a div estiver vazia vai esconde-la
    if( !$.trim( $('#divRelAdicionados').html() ).length ) {
        $('#divRelAdicionados').hide();    
    }
    
}

function buscarNo(idNo){
    
     
    $.ajax({
            type : "POST",

            url : "http://localhost:8080/ProjetoTCC/ServletJson",
            //Esse comando abaixo pega o servidor(IP local) e a porta dinamicamente
            //url : "http://<%= request.getServerName() + ":" + request.getServerPort() %>/ProjetoTCC/ServletPercorrerOntologia",
            //data : "&selecao=todos",       
            data : "idNo=" + idNo + "&selecao=buscaNo" ,                    
            success : function(data) {
                
                //Esconde a msg (caso ela esteja visivel)
                $("#divMensagemCampoRequerido").fadeOut(500);

                
                //Quando o valor for encontrado no BD mostra o box lateral.
                //Senao, esconde ele.
                if( data.id !== undefined ){
                    var html = 
                            "<table class='table table-striped table-bordered'>" 
                         +  "<tr><td><strong>Id do Elemento</strong></td><td><strong>Nome</strong></td></tr>"
                         +  "<tr><td>" + data.id + "</td><td>" + data.nome + "</td></tr>"
                         +  "<tr><td colspan='2'><strong>Descrição</strong></td></tr>"
                         +  "<tr><td colspan='2'>" + data.descricao + "</td></tr>"
                         + " </table>";
                 
                    
                    //Cria o box lateral com as informacoes do no
                    $("#blockDetails").html(html);
                    $('#blockDetails').fadeIn();
                }else{
                    //html = "<p>Selecione um elemento para ver os detalhes.</p>";
                    $('#blockDetails').fadeOut();
                }
                
                
            }
    });
    
    
}


//Funca que cria o Grafo na tela. Parametros nodes e edges.
function createGraph(nodes, edges){
        // create a network
        var container = document.getElementById('mynetwork');
        var dataGraph = {
            nodes: nodes,
            edges: edges
        };


        var options = {
            autoResize: true,
            height: '100%',
            width: '100%',
            locale: 'en',
            interaction:{
                hover: true
            },
            layout:{
                //randomSeed: 2,
                //improvedLayout: true

                
                hierarchical: {
                    //sortMethod: 'directed',
                    direction: 'UD', //UD, DU, LR, RL
                    levelSeparation: 150
                    
                } /**/
            }
        };

        network = new vis.Network(container, dataGraph, options);

        //Pega o evento de clique
        network.on("click", function (params) {
            params.event = "[original event]";
            //console.log("PARAMS: " + params);
            //Pega o JSON do elemento clicado
            var obj = jQuery.parseJSON( JSON.stringify(params, null, 3) );
            
            //alert( obj.nodes );
            //document.getElementById('blockDetails').innerHTML = '<h4>Id do Elemento:</h4>' + obj.nodes;
            buscarNo(obj.nodes);
        });
        
        //Metodo do double-click no no
        network.on("doubleClick", function (params) {
            params.event = "[original event]";
            var obj  = jQuery.parseJSON( JSON.stringify(params, null, 3));
            criarModalEditar(obj.nodes);
        });

}

//Funcao que busca o no pelo id e popula os campos no modalEditar e o abre
function criarModalEditar(idNo){
    
    $.ajax({
            type : "POST",

            url : "http://localhost:8080/ProjetoTCC/ServletJson",
            //Esse comando abaixo pega o servidor(IP local) e a porta dinamicamente
            data : "idNo=" + idNo + "&selecao=buscaNo" ,                    
            success : function(data) {
                
                //Quando o valor for encontrado no BD retorna
                //Senao, retorna null
                if( data.id !== undefined ){
                    
                    $("#nomeNo").val(data.nome);
                    $("#descricaoNo").val(data.descricao);
                    
                    //console.log("DATA:" + data);
                    
                    $("#inputOperacao").val("editar");
                    $('#myModal').modal('show');
                    
                }else{
                    return false;
                }
                
            }
    });    
    
}
//Fim funcao buscarNoPorId

//Funcao que foca o grafo no meio da tela (de sua div)
function resizeFocus(){
        
    //network.stabilize();
    
    //Esconde a msg (caso ela esteja visivel)
    $("#divMensagemCampoRequerido").fadeOut(500);
    
    //Esse eh mais suave do que o stabilize
    network.fit({
        nodes:'',
        animation: { // -------------------> can be a boolean too!
          duration: 200,
          easingFunction: 'easeInCubic'
        }
    });
        
}

//Funcao que tira o carregando do botao Buscar
function removeCarregandoDelay(id) 
  {
    $(id).removeClass("carregando");
  };
 
 //Função que pega todos os nós do sistema
function pegarTodosNos(){
    
     $.ajax({
            type : "POST",

            url : "http://localhost:8080/ProjetoTCC/ServletJson",
            data : "selecao=" + 'buscaTodosNos',
            success : function(data) {
                
                //console.log("TODOS");
                //console.log(data);
                
                var options = "";
                $("#selectTodosNos").html(''); //Limpa todo o select pra nao gerar options duplicados
                
                $.each( data, function( key, val ) {
                    
                    options = options + "<option value='" + val.id + "'>" + val.nome + "</option>";
                
                });
                
                
                $("#selectTodosNos").append(options);
                $("#editarSelectTodosNos").append(options);
               
            }
        });
    
}

//Função para pegar os relacionamentos do sistema (já pré-definidos)
function pegarRelacionamentos(){
    
     $.ajax({
            type : "POST",

            url : "http://localhost:8080/ProjetoTCC/ServletJson",
            data : "selecao=" + 'buscaRelacionamentos',
            success : function(data) {
                
                //console.log("RELACIONAMENTOS");
                //console.log(data);
                
                var options = "";
                $("#selectTodosRelacionamentos").html(''); //Limpa todo o select pra nao gerar options duplicados
                
                $.each( data, function( key, val ) {
                    
                    options = options + "<option value='" + val + "'>" + val + "</option>";
                
                });
                
                
                $("#selectTodosRelacionamentos").append(options);
                $("#editarSelectTodosRelacionamentos").append(options);
               
            }
        });
    
}
 
 
 