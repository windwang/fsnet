<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link type="text/css" href="css/jquery.qtip.css" rel="stylesheet" />
<link type="text/css" href="css/fullcalendar.print.css" media="print"
	rel="stylesheet" />
<link type="text/css" href="css/fullcalendar.css" rel="stylesheet" />

<script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.qtip.js"></script>
<script type="text/javascript" src="js/fullcalendar.min.js"></script>
<script type="text/javascript" src="js/fullcalendar.config.js"></script>

<c:set var="localeCode" value="${pageContext.request.locale.language}" />


<c:choose>
	<c:when test="${localeCode eq 'fr'}">
		<script type="text/javascript">
			jQuery(document).ready(function($) {
				$('#calendar').fullCalendar($.fullCalendarLocale.fr);
			});
		</script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			jQuery(document).ready(function($) {
				$('#calendar').fullCalendar($.fullCalendarLocale.en);
			});
		</script>
	</c:otherwise>
</c:choose>

<div id="calendar"></div>