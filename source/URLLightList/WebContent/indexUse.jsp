<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>URLLightList</title>
  <link rel="stylesheet" href="style.css" type="text/css" media="screen" />
  <script type="text/javascript" src="js/jquery.js"></script>
  <script type="text/javascript" src="js/minhasFuncoes.js"></script>
</head>
<body>

<%@include file="pag/menuUse.html"%>
<br><br>
<div class="cleared reset-box"></div><br><br>		
		 <div style="margin-left: 50px; margin-right: 50px; height: 250px;">
		 <h2 class="art-postheader">URLLightList</h2><br><br>
		    <p style="text-align: justify;">URLLightList � servi�o gratuito que disponibiliza uma
				lista categorizada de dom�nios, a qual � utilizada pelo
				administrador de redes para controlar o acesso � paginas da
				internet.</p><br>
			<p>A LightList tem como base os dom�nios da URLBlackList,
				entretanto com uma s�rie de benef�cios:</p><br>
			<p>. somente dom�nios ativos s�o disponibilizados, ou
				seja, todos os dom�nios inativos foram expurgados;</p>
			<p>. IPs, domin�os inv�lidos e com erros de resolu��o
				foram retirados da lista;</p>
			<p>. a lista possui um servi�o de reclassifica��o de
				categorias.</p>	<br>		
			<p>Abaixo podemos abservar a compara��o entre a URLBLackList e URLLightList:</p>	
		</div>
		
		<img style="margin-left: 200px;margin-bottom: 50px;" alt="Resultado" src="images/resultado.JPG">	
<%@include file="pag/rodape.html"%>
</body>
</html>