<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="icon" type="image/png" href="images/favicon.ico" />
	<meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
	<meta name="author" content="Luka Cvrk - www.solucija.com" />
	<meta name="description" content="Site Description" />
	<meta name="keywords" content="site, keywords" />
	<meta name="robots" content="index, follow" />
	<link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
	<title>FSNet</title>
  
    <script language="JavaScript" src="maquette.js">
</script>
</head>
<body onload="showMenu();">
	<div class="wrap background">
	<jsp:include page="haut.jsp"></jsp:include>
		
		<div id="left">
			<h2><a href="#">Création d'une communaut&eacute;  </a></h2>
			<p>Date<br />
		  JJ-MM-AA</p>
		  
			<form id="form1" method="post" action="" >
			  <table style="width:80%" border="0">
                <tr>
                  <td colspan="2" style="width:70%"><div style="align:center"></div>
                  	<div style="align:enter"></div></td>
                </tr>
                
                <tr>
                  <td style="width:90%">Centre d'inter&ecirc;t</td>
                  <td style="width:176"><select name="menu1" onchange="MM_jumpMenu('parent',this,0)">
                    <option>Theme</option>
                  </select></td>
                </tr>
                <tr>
                  <td style="width:90%">Nom communaut&eacute; </td>
                  <td><label>
                    <input name="textfield" type="text" size="30" />
                  </label></td>
                </tr>
                <tr>
                  <td style="width:90%">Descriptif</td>
                  <td><label>
                    <textarea name="textfield2" cols="30" rows="5"></textarea>
                  </label></td>
                </tr>
                <tr>
                  <td style="width:90%">&nbsp;</td>
                  <td style="text-align:right">
                      <div style="align:right">
                        <input type="submit" name="Submit" value="Ajouter" />
                      </div></td>
                </tr>
              </table>
          </form>
			<p>&nbsp;</p>
			<p>&nbsp;</p>

			<p class="subtitle">&nbsp;</p>
		</div>
		
		<div id="side">
				<div class="boxtop"></div>
				<div class="box">
					<h3>Mes communaut&eacute;s </h3>
				    <a href="#">
				  <span class="item">
						<span class="sidedate">JEE<br />
					&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <strong>Nouveaut&eacute; J2EE </strong><br />
						Detail</span>
				  </a>
					<a href="#">
					<span class="item">
						<span class="sidedate">JAVA&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<strong>Eclipse ... </strong><br />
						Detail					</span>
					</a>
					<a href="#">
					<span class="item last">
					<span class="sidedate">JSP<br />
						</span>
						<strong>Nouveaut&eacute; JSP </strong><br />
						Detail</span>
					</a>
				</div>
				<div class="boxbottom"></div>
		</div>
		
	</div>
	<jsp:include page="bas.jsp"></jsp:include>
</body>
</html>