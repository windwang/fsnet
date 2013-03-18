<%@ taglib prefix="s" uri="/struts-tags"%>
<h2>
	<s:text name="cv.leftMenu.my" />
</h2>

<ul>
	<li><s:a action="CreateCv1">
			<s:text name="profile.leftMenu.createCV" />
		</s:a></li>
	<li><s:a action="DisplayCV">
			<s:text name="profile.leftMenu.myCV" />
		</s:a></li>
</ul>