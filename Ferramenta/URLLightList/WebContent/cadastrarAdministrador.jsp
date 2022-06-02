<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
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


	<div
		style="display: block; text-align: center; margin-top: 100px; margin-left: 100px; margin-right: 100px; height: 290px;">

		<hr>
		<h2 class="art-postheader"
			style="text-align: left; margin-left: 20px;">Cadastro Administrador</h2>
		<hr>
		<div style="height: 50px;"></div>
	
			<label>Login:</label> <input type="text" name="login" id="login"><br />
			<label>Senha:</label> <input type="password" name="senha" id="senha"><br />
			<br/> <label>Confirma:</label> <input type="password" name="confirmacao" id="confirmacao"/><br>
			<div style="height: 25px;"></div>
			<input type="button" value="Salvar" onclick="cadastrarAdministrador()"> 
			<input type="button" value="Cancelar" onclick="exibirIndex()">
		
		<hr style="margin-top: 25px;">
	</div>
	
<%@include file="pag/rodape.html"%>
	

</body>
</html>