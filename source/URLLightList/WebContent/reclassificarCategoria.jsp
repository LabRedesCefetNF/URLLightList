<%@page import="urllightlist.entidade.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="utf-8">
<title>URLLightList</title>

  <link rel="stylesheet" type="text/css" href="css/reclassificarEstilo.css">
  <link rel="stylesheet" href="css/style_Menu.css" type="text/css" media="screen, projection" />
  <link rel="stylesheet" href="style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
  <script type="text/javascript" charset="utf8" src="js/jquery.js"></script>
  <script type="text/javascript" charset="utf8" src="js/jquery-ui.js"></script>
  <script type="text/javascript" src="js/jquery.dropdownPlain.js"></script>
  <script type="text/javascript" src="js/minhasFuncoes.js"></script>
  
</head>

<body>

    <%@include file="pag/topo.html"%>
	<%@include file="pag/menu.html"%>
   
     
	<div style="margin-left: 40px; margin-top: 100px; margin-right: 40px;" >
	<hr style="background-color: fuchsia;">
	<h2 class="art-postheader" style="text-align: left; ">Reclassificar categorias</h2>
	<hr style="background-color: fuchsia;">
	<c:if test="${listaCategoria != null}">
		<label for="speed">Selecionar categorias:</label>
		<select name="speed" id="speed">
			<c:forEach var="categoria" items="${listaCategoria}">
				<option value="${categoria.nome}">${categoria.nome}</option>
			</c:forEach>
		</select>
	</c:if>

	<input type="button" style="font-size: 13px;" value="Adicionar"
		id="btnAdicionar" onclick="adicionarCategoria()" />
     
	<br />

	<label for="speed">Nome da reclassificação das categorias :</label>
	<br />
	<input type="text" id="nome" name="nome" />
	
	<br/>
	<br/>
	<fieldset style="width: 400px;">
		<legend>Listas de Categorias</legend>
		<ol id="selectable"></ol>
	</fieldset>

    <br />
	<br />

	<input type="button" style="font-size: 13px;" value="Excluir"
		id="btnRemover" onclick="removerCategoria()" />
	<input type="button" style="font-size: 13px;" value="Salvar"
		onclick="reclassificar()" />
    <input type="button" value="Cancelar" onclick="exibirIndex()" />
	<br />
	<br />
	<br />


	<table>
		<thead>
			<tr>
				<th>Nome</th>
				<th>Categorias aglutinadas</th>
				<th>Ação</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${listaReclassificacao != null}">
				<c:forEach var="reclassificacao" items="${listaReclassificacao}">
					<tr>
						<td>${reclassificacao.categoria.nome}</td>
						<td><c:forEach var="categorias" items="${reclassificacao.listaCategoria}">
	      	             ${categorias.nome},		          			          	
	                       </c:forEach></td>
						<td><a href="javascript: excluirReclassificacao(${reclassificacao.id});">
								<img src="images/delete.png" width="16" height="16" />
						</a></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<br />
	<br />
	
</div>	
<%@include file="pag/rodape.html"%>
</body>
</html>