<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>URLLughtList</title>
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
<h2 class="art-postheader" style="margin-left: 20px;">Gerenciar Administrador</h2>
<hr>
<div style="height: 50px;"></div>
 
 <c:if test="${adm==null}">
 <form action="BuscarAdministradorController.do" method="get">
	<label>Login:</label> <input name="nome" type="text"> <input
		type="submit" value="Buscar">
</form>
</c:if>

  <c:if test="${adm!= null}">
			<label>Login:</label>
			<input type="hidden" name="id" id="id" value="${adm.id}" >
			<input type="text" name="login" id="login" value="${adm.login}"><br /><br /> <label>Senha:</label>
			<input type="password" name="senha" id="senha" value="${adm.senha}"><br /><br />
			<label>Confirma:</label>
			<input type="password" name="confirmacao" id="confirmacao"/><br>
			<br/><input type="button" value="Alterar" onclick="atualizarAdministrador()">
			<input type="button" value="Cancelar" onclick="exibirIndex()">
		
	</c:if>
	<hr style="margin-top: 25px;">
 </div>

<%@include file="pag/rodape.html"%>
	
	
</body>
</html>