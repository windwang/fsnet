<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${requestScope.autocompleteChoices ne null}">
	<ul>
		<c:forEach items="${requestScope.autocompleteChoices}" var="choix">
			<li class="autoCompleteListMember">${choix}</li>
		</c:forEach>
	</ul>
</c:if>