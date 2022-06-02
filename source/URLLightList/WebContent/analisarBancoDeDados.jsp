<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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


<div style="display: block; text-align: center; margin-top: 100px; margin-left:100px; margin-right: 100px;  height: 290px;" >

<hr>
<h2 class="art-postheader" style="text-align: left; margin-left: 20px;">Analisar Banco de Dados</h2>
<hr>
<div style="height: 50px;"></div>
	<div>
		<label>Analisar status:</label>
		 <label>Ativo:</label>
		 <input type="radio" name="status" value="1">
		<label>Inativo:</label>
		<input type="radio" name="status" value="0">
		<label>Ambos:</label>
		<input type="radio" name="status" value="2"><br><br>
		
		<label>Continuar analise:</label> 
		<input type="checkbox" name="continuar" value="continuar" checked="checked"><div style="height: 25px;"></div>	
	    
	    <input type="button" value="Analisar" onclick="analisarBancoDados()">
	    <input type="button" value="Cancelar" onclick="exibirIndex()">		
	<div style="height: 50px;"></div>
	</div>
	<hr>
 </div>

<%@include file="pag/rodape.html"%>


</body>
</html>