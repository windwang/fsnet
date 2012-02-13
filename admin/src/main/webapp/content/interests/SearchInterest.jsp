<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<link rel=stylesheet type="text/css" href="css/osx.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.simplemodal.js"></script>
<script type="text/javascript" src="js/osx.js"></script>   


<h3><bean:message key="interests.11"/></h3>
<html:javascript formName="/SearchInterest"/>
<html:form action="/SearchInterest">
 		<div class="errorMessage"><html:errors property="searchInterestName"/></div>
    	<p><html:text property="searchInterestName"/>
      <html:submit styleClass="button" >
      	<bean:message key="interest.validate"/>
      </html:submit></p>
</html:form>

<logic:present name="interestSearchPaginator" scope="request">
	<jsp:include page="/content/interests/ResultInterest.jsp"/>
</logic:present>

<c:if test="${!empty success}">
	<script type="text/javascript">
		jQuery(function () { popup(); });
		success = null;
	</script>
	<div id="osx-modal-content" class="simplemodal-data">
		<div id="osx-modal-title">Message</div>
		<div class="close">
			<a class="simplemodal-close" href="#">X</a>
		</div>
		<div id="osx-modal-data">
			<p><c:out value="${success}"/></p>
		</div>
	</div>
</c:if>