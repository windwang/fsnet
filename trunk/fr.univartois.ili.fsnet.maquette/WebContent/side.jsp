<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://iliforum.ili.fsnet.com/" prefix="fsnet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script language="JavaScript" src="maquette.js">
</script>
</head>
<body onload="showMenu();">
<div id="side">
	<div class="boxtop"></div>
	<div class="box">
		<h3>Derniers messages de mes hubs</h3>
		<fsnet:hub var="hubdto">
			<a href="GotoMessage?idTopic=${hubdto.lastMessage.topic.id }">
				<span class="item"> 
					<span class="sidedate">${hubdto.hub.nomCommunaute}<br />
						&nbsp;&nbsp;&nbsp;&nbsp;
					</span> 
					<strong>${hubdto.lastMessage.topic.sujet } </strong><br />
					${hubdto.lastMessage.dateMessage}
				</span>
			</a> 
		</fsnet:hub>
	</div>
	<div class="boxbottom"></div>
</div>

</body>
</html>