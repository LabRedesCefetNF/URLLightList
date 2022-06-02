<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>URLLightList</title>

<script type="text/javascript" charset="utf8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.dropdownPlain.js"></script>
<link rel="stylesheet" href="css/style_Menu.css" type="text/css" media="screen, projection" />
<link rel="stylesheet" href="style.css" type="text/css" media="screen" />
<script type="text/javascript" src="js/minhasFuncoes.js"></script>

</head>
<body>
	<%@include file="pag/topo.html"%>
	<%@include file="pag/menu.html"%>

	<div style="display: block; margin-top: 100px; margin-left: 100px; margin-right: 100px; height: 400px; ">
	<hr>
	<h2 class="art-postheader" style="text-align: left; margin-left: 20px;">Analisar URLBlackList</h2>
     <hr>
	<div style="height: 50px;"></div>
	<div>
	
	<label>Escolha uma pasta para análise:</label><br><br>
		<c:forEach var="conteudo" items="${conteudoPasta}">
		<div style="margin-left: 50px;">		 			
		 <input type="radio" name="conteudo" value="${conteudo}">
		 <label>${conteudo}</label>		 
		</div>
		</c:forEach>
	<div style="height: 50px;"></div>
		
	<label>Continuar analise:</label> 
	<input type="checkbox" name="continuar" value="continuar" checked="checked"><br>
	</div>	
	<br><br>
		<input type="button" value="Analisar" onclick="analisarArquivo()">
		<input type="button" value="Cancelar" onclick="exibirIndex()"><br><br>
		<label>( * ) A pasta deverá estar descompactada.</label><br>
		<label>( * ) Exibindo pastas do diretório Download.</label><br>
	</div>
		
		
	<%@include file="pag/rodape.html"%>
</body>
</html>