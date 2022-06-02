<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>URLLightList</title>
   <script src="js/jquery.js"></script>
  <script type="text/javascript" src="js/jquery.dropdownPlain.js"></script>
  <link rel="stylesheet" href="css/style_Menu.css" type="text/css" media="screen, projection" />
  <link rel="stylesheet" href="style.css" type="text/css" media="screen" />
  <script type="text/javascript" src="js/minhasFuncoes.js"></script>
</head>
<body>

<%@include file="pag/topo.html"%>
<%@include file="pag/menu.html"%>


<div style="display: block; text-align: center; margin-top: 100px; margin-left:100px;
margin-right: 100px;  height: 290px;" >

<hr>
<h2 class="art-postheader" style="text-align: left; margin-left: 20px;">Gerenciar Domínio</h2>
<hr>
<div style="height: 50px;"></div>

<c:if test="${dominio == null}">
  <form action="BuscarDominioController.do" method="get">
	<label>Domínio:</label>
	<input name="nomeDominio" type="text">
    <input type="submit" value="Buscar">
  </form>
</c:if>
<br>

<c:if test="${dominio != null}">
	<label>Domínio:</label>
     <label>${dominio.nome}</label><br><br>
	 <input type="hidden" name="id" id="id" value="${dominio.id}">
	 <c:if test="${listaCategoria != null}">
	   <label for="speed">Selecionar categorias:</label>
	    <select name="speed" id="speed">
	     <c:forEach var="categoria" items="${listaCategoria}">
			 <c:choose><c:when test="${categoria.nome==dominio.categoria.nome}">
			 <option value="${categoria.nome}" selected="selected">${categoria.nome}</option>
			 </c:when>
			  <c:otherwise><option value="${categoria.nome}">${categoria.nome}</option>
			 </c:otherwise></c:choose> 
		</c:forEach>
	    </select>
	 </c:if><br><br>	
	  <input type="button" value="Alterar" onclick="atualizarDominio()">
	  <input type="button" value="Excluir" onclick="excluirDominio()">
	  <input type="button" value="Cancelar" onclick="exibirIndex()">
</c:if>
	<hr style="margin-top: 25px;">
 </div>

<%@include file="pag/rodape.html"%>



</body>
</html>