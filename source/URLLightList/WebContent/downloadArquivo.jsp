<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>URLLightList</title>
<link rel="stylesheet" href="css/style_Menu.css" type="text/css"
	media="screen, projection" />
<link rel="stylesheet" href="style.css" type="text/css" media="screen" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.dropdownPlain.js"></script>
<script type="text/javascript" src="js/minhasFuncoes.js"></script>
</head>
<body>

	<%@include file="pag/topo.html"%>
	<%@include file="pag/menu.html"%>

	<div style="display: block;  margin-top: 100px; margin-left: 100px; margin-right: 100px; height: 400px;">
		<hr>
		<h2 class="art-postheader"
			style="text-align: left; margin-left: 20px;">Download URLBlackList</h2>
		<hr>
		<div style="height: 50px;"></div>
		
			<label>URL MD5:</label><input style="width: 600px;" type="text" name="urlMD5" id="urlMD5"
				value="http://urlblacklist.com/cgi-bin/commercialdownload.pl?type=information&file=bigblacklist"><div style="height: 15px;"></div>
			<input  type="button" value="Verificar Versão" onclick="downloadMD5()">
		
		<div style="height: 50px;"></div>
		
		    <label>URL BlackList:</label>
			<input style="width: 600px;" type="text" name="url"id="url"
				value="http://urlblacklist.com/cgi-bin/commercialdownload.pl?type=download&file=smalltestlist"><br><div style="height: 15px;"></div>
			<input type="button" value="Baixar URLBlackList" onclick="downloadBlackList()"><br>
		

	</div>

	<%@include file="pag/rodape.html"%>

</body>
</html>