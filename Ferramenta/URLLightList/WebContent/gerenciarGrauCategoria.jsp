<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            
         <link rel="stylesheet" type="text/css" href="css/jquery.dataTables.css">
         <link rel="stylesheet" href="css/style_Menu.css" type="text/css" media="screen, projection" />
         <link rel="stylesheet" href="style.css" type="text/css" media="screen" />
        <script type="text/javascript" src="js/jquery.dropdownPlain.js"></script>
        <script type="text/javascript" src="js/minhasFuncoes.js"></script>
        <script type="text/javascript" charset="utf8" src="js/jquery.js"></script>
        <script type="text/javascript" charset="utf8" src="js/jquery.dataTables.js"></script>
        
        
       <script type="text/javascript">
   			$(document).ready(function(){
   			$('#TabelaGrauPerversidade').dataTable({
   					"pagingType": "full_numbers",
   	   					
				"language":{ 
				 "decimal": ",",
				 "thousands": "."},

		 	    "language":{ 
   	            "lengthMenu": "Exibição:_MENU_ registros por página",
   	            "zeroRecords": "Nenhum registro foi encontrado.",
   	            "info": "Exibindo página _PAGE_ de _PAGES_",
   	            "infoEmpty": "Não há registros disponíveis.",
   	            "infoFiltered": "(Filtrado do total de registros:_MAX_)"} 	            

			});



   			
   		}); 
   		
   		
   		</script>
   <script type="text/javascript" src="js/minhasFuncoes.js"></script> 
  <title>URLLightList</title>
</head>
<body>

    <%@include file="pag/topo.html"%>
	<%@include file="pag/menu.html"%>
	
	<div style="margin-left: 40px; margin-top: 100px; margin-right: 40px;" >
	<hr>
	<h2 class="art-postheader" style="text-align: left; ">Atualizar Grau de Perversidade</h2>
	<hr>
	
	
	<div style="height: 50px;"></div>
	 <table id="TabelaGrauPerversidade" class="display" style="width: 400px;" >
	      <thead>      
	        <tr>
	          <th>Nome</th>
	          <th>Grau de Perversidade</th>
	        </tr>
	      </thead>
	            
	      <tbody >
	        <c:forEach var="listaCategoria" items="${listaCategoria}">
	          <tr>
			  	  <td>${listaCategoria.nome}</td>
			      <td>
			      	<input type="text" value="${listaCategoria.grauPerversidade}" name="grau"/>
			  	    <input type="hidden" value="${listaCategoria.id}" class="id" />
			      </td>  	 
		      </tr>
	        </c:forEach>
		</tbody>
</table>

  <div style="margin-top: 25px; margin-bottom:25px; text-align: center;">
  <input type="button" value="Alterar" onclick="atualizarGrauCategoria()" />
  <input type="button" value="Cancelar" onclick="exibirIndex()" />
  </div>
  </div>
  
  <%@include file="pag/rodape.html"%>
</body>
</html>