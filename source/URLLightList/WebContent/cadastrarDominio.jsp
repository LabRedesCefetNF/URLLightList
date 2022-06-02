<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>URLLightList</title>
  
  <script type="text/javascript" src="js/jquery.js"></script>
  <script type="text/javascript" src="js/jquery.dropdownPlain.js"></script>
  <link rel="stylesheet" href="css/style_Menu.css" type="text/css" media="screen, projection" />
  <link rel="stylesheet" href="style.css" type="text/css" media="screen" />
  <script type="text/javascript" src="js/minhasFuncoes.js"></script>
 
 </head>
<body >
<%@include file="pag/topo.html"%>
<%@include file="pag/menu.html"%>


<div style="display: block; text-align: center; margin-top: 100px; margin-left:100px;
margin-right: 100px;  height: 290px;" >

<hr>
<h2 class="art-postheader" style="text-align: left; margin-left: 20px;">Cadastrar Dominio</h2>
<hr>
<div style="height: 50px;"></div>

<label>Domínio:</label><input type="text" name="nome" id="nome"><br><br> 
 <c:if test="${listaCategoria != null}" >
   <label for="speed">Categoria:</label>
    <select name="speed" id="speed" >
    <option value="">Selecione uma categoria</option>
     <c:forEach var="categoria" items="${listaCategoria}" >
		<option value="${categoria.nome}">${categoria.nome}</option>					
 	    </c:forEach>
    </select>
</c:if>
<div style="height: 25px;"></div>
 <input type="button" value="Salvar" onclick="cadastrarDominio()">
 <input type="button" value="Cancelar" onclick="exibirIndex()" >
 
 <hr style="margin-top: 25px;">
 </div>

<%@include file="pag/rodape.html"%>


</body>
</html>