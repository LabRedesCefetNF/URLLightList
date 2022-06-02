<%@page import="urllightlist.entidade.Administrador"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <title>URLLightList</title>
   <link rel="stylesheet" href="css/style_Menu.css" type="text/css" media="screen, projection" />
   <link rel="stylesheet" href="style.css" type="text/css" media="screen" />
   <script type="text/javascript" src="js/jquery.js"></script>
   <script type="text/javascript" src="js/jquery.dropdownPlain.js"></script>
   <script type="text/javascript" src="js/minhasFuncoes.js"></script>
</head>

<body>
	<%@include file="pag/topo.html"%>
	<%@include file="pag/menu.html"%>
	
	
	<input type="hidden" value="${login}" id="login">
	<p style="height: 400px;" ></p>
	
	

	<%@include file="pag/rodape.html"%>


</body>

</html>