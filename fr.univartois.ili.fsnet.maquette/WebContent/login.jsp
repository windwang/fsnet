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
    <style type="text/css">
<!--
.Style4 {font-size: large}
-->
    </style>
</head>
<body>
<div class="wrap background">
		<div id="search">
			<form action="">
				<fieldset>
					<input type="text" class="field" value="Mot clÃ©" />
					<input type="submit" class="button" value="" />
				</fieldset>
			</form>
		</div>
		<ul id="menu">
			<li><a class="current">Login</a></li>						
		</ul>
			<p class="date">Date<br />
		  JJ-MM-AA</p>
		<div id="logo">
		  <h1><a href="http://www.google.com">FSNet<br />
			</a></h1>
			<h2 id="slogan">Réseau social </h2>
		</div>
		
		
			
		<table width="923" border="0">
          <tr>
            <td style="width:486px">&nbsp;</td>
            <td style="width:612px"><div style="align:center"><h1> <span class="Style4">Bienvenue sur FSNet</span><br /></h1> </div></td>
          </tr>
        </table>
	
    <div  style="text-align: center;" class="promo">    
	<form action="login" method="post" id="infoLogin" >
	 
      <table width="90%" border="0">
        <tr>
          <td style="width: 50%;text-align:right">Email</td>
          <td style="width:203"><label>
            <input type="text" name="email" />
          </label></td>
          <td style="width:119">&nbsp;</td>
        </tr>
        <tr>
          <td style="width: 50%;text-align:right">Mot de passe </td>
          <td><input type="password" name="textfield2" /></td>
          <td><label>
             
               <input type="submit" name="Submit" value="Connexion" />                        
            
          </label></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td><div style="align:center"><a href="www.google.fr">Mot de passe oubliÃ© ?</a></div></td>
        </tr>
      </table>
     
	  </form>
	  
      <p style="align:center">&nbsp;</p>
    </div>
	   
	
      <p>&nbsp;</p>
      <p>&nbsp;</p>
     
</div>
	
<jsp:include page="bas.jsp"></jsp:include>
</body>
</html>