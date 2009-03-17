<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://iliforum.ili.fsnet.com/" prefix="fsnet"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="icon" type="image/png" href="images/favicon.ico" />
<meta http-equiv="content-type"
	content="application/xhtml+xml; charset=UTF-8" />
<meta name="author" content="Luka Cvrk - www.solucija.com" />
<meta name="description" content="Site Description" />
<meta name="keywords" content="site, keywords" />
<meta name="robots" content="index, follow" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/style.css" />
<title>FSNet</title>
<script language="JavaScript" src="maquette.js">
</script>
</head>
<body onload="showMenu();">
<div class="wrap background">
<jsp:include page="haut.jsp"></jsp:include>


<table style="padding: 6; width: 100%; text-align: center; border: 0;">
	<tr>
		<td style="width: 100%"><strong>${monHub.nomCommunaute }</strong> <span>
		${monTopic.sujet } </span></td>
	</tr>
</table>

<div id="community_menu"
	style="margin-top: 3px; display: none; text-align: left">
<table style="padding: 4; border: 0;">
	<tr>
		<td>Liens de la communauté</td>
	</tr>
	<tr>
		<td><a href="http://www.developpez.net/forums/group.php">Groupes
		sociaux</a></td>
	</tr>
	<tr>
		<td><a href="http://www.developpez.net/forums/album.php">Images
		&amp; albums</a></td>
	</tr>

</table>
</div>

<div class="vbmenu_popup" id="pagenav_menu" style="display: none">
<table style="padding: 4; border: 0">

	<tr>
		<td class="thead">aller à la page...</td>
	</tr>
	<tr>
		<td class="vbmenu_option" title="nohilite">
		<form id="pagenav_form" method="get" action="">
		<p><input class="bginput" id="pagenav_itxt"
			style="FONT-SIZE: 11px; size: 4;" /></p>
		<p><input class="button" id="pagenav_ibtn" type="button"
			value="Go" /></p>
		</form>
		</td>
	</tr>
</table>
</div>

<table
	style="MaRGIN-BOTTOM: 3px; width: 100%; cellPadding: 0; border: 0">
	<tr valign="bottom">
		<td class="smallfont">&nbsp;</td>
	</tr>
</table>

<table class="tborder"
	style="BORDER-BOTTOM-WIDTH: 0px; padding: 6; width: 100%; text-align: center; border: 0;">
	<tr>
		<td class="tcat" style="width: 100%">
		<div class="smallfont"></div>
		</td>
		<td class="vbmenu_control" id="threadtools">&nbsp;</td>
	</tr>
</table>
</div>

<div id="posts">
<div style="align: center">
<div class="page" style="WIDTH: 100%; TEXT-aLIGN: left">
<div
	style="PaDDING-RIGHT: 25px; PaDDING-LEFT: 25px; PaDDING-BOTTOM: 0px; PaDDING-TOP: 0px; text-align: left;">
<div id="edit3994424"
	style="PaDDING-RIGHT: 0px; PaDDING-LEFT: 0px; PaDDING-BOTTOM: 6px; PaDDING-TOP: 0px">
<a href="hub.jsp">FSNet</a> - <a href="GotoTopic?idHub=${monHub.id}">${monHub.nomCommunaute}</a> - <a href="GotoMessage?idTopic=${monTopic.id}">${monTopic.sujet}</a>
<table class="tborder" id="post3994424"
	style="padding: 6; width: 100%; text-align: left; border: 0">


	<tr>
		<td colspan="2" class="thead"
			style="BORDER-RIGHT: #d1d1e1 1px solid;BORDER-TOP: #d1d1e1 1px solid; FONT-WEIGHT: normal; BORDER-LEFT: #d1d1e1 1px solid; BORDER-BOTTOM: #d1d1e1 1px solid">
		${monTopic.sujet}</td>
		
	</tr>

	<fsnet:message var="messageDTO" topic="${monTopic}">
		<tr valign="top">
			<td class="alt2"
				style="BORDER-RIGHT: #d1d1e1 1px solid; BORDER-TOP: #d1d1e1 1px solid; BORDER-LEFT: #d1d1e1 1px solid; BORDER-BOTTOM: #d1d1e1 1px solid; width:20%">
			<div id="postmenu_3994424">${messageDTO.message.propMsg.nom }</div>
			<div class="smallfont">
			<div style="align: center">Membre de la communauté</div>
			</div>
			<div class="smallfont" style="text-align: center">
			<c:if test="${messageDTO.message.propMsg.photo != null}">
				<img src="${messageDTO.message.propMsg.photo}"  width="72" height="96" />
			</c:if>
			<c:if test="${messageDTO.message.propMsg.photo == null}">
				<img src="images/DLBMII.JPG"  width="72" height="96" />
			</c:if>
			<div class="smallfont"><br />
			<div>Date d'inscription: ${messageDTO.message.propMsg.dateEntree}</div>
			<div>Messages: ${messageDTO.nbMessUser }</div>
			<div></div>
			</div>
			</td>
			<td class="" id="td_post_3994424"
				style="BORDER-RIGHT: #d1d1e1 1px solid;BORDER-TOP: #d1d1e1 1px solid; BORDER-LEFT: #d1d1e1 1px solid; BORDER-BOTTOM: #d1d1e1 1px solid;"><!-- icon and title -->
			<div class="alt1"> ${messageDTO.message.dateMessage } </div>
			<c:if test="${messageDTO.message.propMsg.id == entite.id}">
				<a href="SupprMess?idMess=${messageDTO.message.id}&idEntite=${entite.id}"><img src="images/croix.jpg" width="15"></a>
			</c:if>
			<div id="post_message_3994424">${messageDTO.message.contenu}</div>
			</td>
		</tr>
	</fsnet:message>
	<tfoot>
		<tr> <td colspan="2" style="BORDER-RIGHT: #d1d1e1 1px solid; BORDER-LEFT: #d1d1e1 1px solid;"><form action="CreateMessage">
		
			<fieldset><legend> Repondre Message </legend>
			<p align="center">
		<label>	Contenu : </label></p>
		<p><textarea name="contenuMessage"  cols="100" rows="5"></textarea> <input
			type="submit" name="repondre" value="repondre" /></p></fieldset>
		</form></td></tr>
	</tfoot>
</table>


</div>

<jsp:include page="bas.jsp"></jsp:include>
</div>
</div>
</div>
</div>
</body>
</html>