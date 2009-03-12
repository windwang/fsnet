<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="menu">
<dl>
	<dt><a class="${param.accueil}" href="index.jsp?accueil=current" title="Retour à l'accueil">Accueil</a></dt>
</dl>

<dl>
	<dt onmouseover="showMenu('smenu1');" onmouseout="showMenu();"><a class="${param.user}" href="#">Membres</a></dt>
	<dd id="smenu1">
	<ul>
		<li onmouseover="showMenu('smenu1');" onmouseout="showMenu();"><a
			href="AddUser.jsp?user=current&showHide=hide&deploy=[%2B]&titleDeploy=D%E9ployer la liste">Ajouter un membre</a></li>
		<li onmouseover="showMenu('smenu1');" onmouseout="showMenu();"><a
			href="SearchMember.jsp?user=current">Rechercher un membre</a></li>
	</ul>
	</dd>
</dl>
<dl>
	<dt onmouseover="showMenu('smenu2');" onmouseout="showMenu();"><a
		class="${param.interet}" href="#">Intérêts</a></dt>
	<dd id="smenu2">
	<ul>
		<li onmouseover="showMenu('smenu2');" onmouseout="showMenu();"><a
			href="AddInterest.jsp?interet=current&showHide=hide&deploy=[%2B]&titleDeploy=D%E9ployer la liste">Ajouter des intérêts</a></li>
	</ul>
	</dd>
</dl>
<dl>
	<dt><a href="#">Rapport d'activités</a></dt>
</dl>
<div id="search">
<form action="">
<fieldset><input type="text" class="field" value="Mot clé" />
<input type="submit" class="button" value="" /></fieldset>
</form>
</div>
</div>

</body>
</html>