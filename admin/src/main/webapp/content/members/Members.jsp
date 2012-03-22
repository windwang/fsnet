<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<style type="text/css">
#divChooseSimpleMember {
	display: block;
}

#divChooseMultipleMember {
	display: none;
}

#divChooseMultipleFileMember {
	display: none;
}
</style>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="members.title.choice" />
	</legend>

	<table id="chooseCreateMembers" class="inLineTable fieldsetTableAdmin">

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
	<%@ include file="CreateMember.jsp"%>
</div>

<div id="divChooseMultipleMember">
	<%@ include file="CreateMultipleMember.jsp"%>
</div>

<div id="divChooseMultipleFileMember">
	<%@ include file="CreateMultipleMemberWithFile.jsp"%>
</div>


