<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="members.title.choice" />
	</legend>

	<table class="inLineTable fieldsetTableAppli">
		<tr>
			<td><input type="radio" name="chooseSimpleOrMultiple"
				checked="checked" id="simpleMember" value="simpleChoose"
				onchange="changeSimpleOrMultiple()" /> <label for="simpleMember">
					<bean:message key="members.simpleMember" />
			</label></td>
		</tr>

		<tr>
			<td><input type="radio" name="chooseSimpleOrMultiple"
				id="multipleMember" value="multipleChoose"
				onchange="changeSimpleOrMultiple()" /> <label for="multipleMember">
					<bean:message key="members.multipleMember" />
			</label></td>
		</tr>
	</table>
</fieldset>

<!--<input type="radio" name="chooseSimpleOrMultiple" id="multipleMemberFile" value="multipleFileChoose" onchange="changeSimpleOrMultiple()"/>-->
<!--<label for="multipleMemberFile"> -->
<!--	<bean:message key="members.multipleWithFile" />  -->
<!--</label>-->

<div id="divChooseSimpleMember">
	<jsp:include page="/content/manageMembers/CreateMember.jsp" />
</div>
<div id="divChooseMultipleMember">
	<jsp:include page="/content/manageMembers/CreateMultipleMember.jsp" />
</div>
<div id="divChooseMultipleFileMember">
	<jsp:include
		page="/content/manageMembers/CreateMultipleMemberWithFile.jsp" />
</div>