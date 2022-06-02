<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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


<div style="display: block; text-align: center; margin-top: 100px; margin-left:100px;
margin-right: 50px;  height: 290px;" >

<hr>
<h2 class="art-postheader" style="text-align: left; margin-left: 20px;">Gerar URLLightList - Ativa</h2>
<hr>
<div style="height: 50px;"></div>
<div>    
    <label>Nome da Pasta:</label>&nbsp;&nbsp;<input type="text" name="nomePasta" id="nomePasta"><div style="height: 15px;"></div>
    <label>Tipo de Lista: </label>&nbsp;&nbsp;
    <input type="radio" value="formatoBlackList" name="tipo"><label>Formato URLBlackList</label>&nbsp;&nbsp;
    <input type="radio" value="reclassificada" name="tipo"><label>Reclassificada</label><br/><br/><br/>
    <input type="button" style="font-size: 13px;"  value="Gerar URLLghtList" onclick="gerarURLLightListAtiva()"/>
	<input type="button" value="Cancelar" onclick="exibirIndex()"><br><br>
	<label>( * ) Lista da URLLightList será gerada na pasta Exporta.</label><br>
</div>

<hr style="margin-top: 25px;">
 </div>
 
<%@include file="pag/rodape.html"%>

<br/>
<br/>
<br/>



</body>
</html>