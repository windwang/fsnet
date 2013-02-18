<%@ taglib prefix="s" uri="/struts-tags"%>
<h2>
	<s:text name="cv.leftMenu.my" />
</h2>

<ul>
	<li><s:url action="/CreateCv1">
			<s:text name="profile.leftMenu.createCV" />
		</s:url></li>
	<li><s:url action="/DisplayCV">
			<s:text name="profile.leftMenu.myCV" />
		</s:url></li>
</ul>