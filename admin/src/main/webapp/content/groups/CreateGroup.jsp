<%-- 
		 Author : Morad LYAMEN
		
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h3><bean:message key="groups.create" /></h3>



<html:form action="/CreateGroup">
	<table id="CreateGroup">
		<tr>
			<td>
				<label for="name"> 
					<bean:message key="groups.name" />
				</label>
			</td>
			<td colspan="3">
				<html:text property="name" styleId="name" errorStyleClass="error" />
			</td>
		</tr>
		
		<tr class="errorMessage">
			<td colspan="2">
				<html:errors property="name" />
			</td>
		</tr>
		
		<tr>
			<td>
				<label for="owner"> 
					<bean:message key="groups.owner" />
				</label>
			</td>
			<td colspan="3">
				<html:select property="socialEntityId" styleClass="select" >
					<html:option value=""><bean:message key="groups.listMember"/></html:option>
					<c:forEach var="socialEntity" items="${allMembers}">
						<html:option value="${socialEntity.id}">${socialEntity.name} ${socialEntity.firstName}</html:option>
					</c:forEach>
				</html:select>
            </td>
			
		</tr>
		
		<tr class="errorMessage">
            <td colspan="2">
                <html:errors property="socialEntityId"/>
            </td>
        </tr>
        
        <tr>
			<td ROWSPAN="2">
				<label for="members"> 
					<bean:message key="groups.members" />
				</label>
			</td>
			
			<td ROWSPAN="2">
				<html:select property="memberListLeft" styleClass="select" size="5" multiple="multiple">
					<html:option value="" disabled="true"><bean:message key="groups.listMember"/></html:option >
					<html:option value="" ><bean:message key="groups.listMember"/>4</html:option>
					<html:option value=""><bean:message key="groups.listMember"/>5</html:option>
					<html:option value=""><bean:message key="groups.listMember"/>6</html:option>
					<c:forEach var="socialEntity" items="${allMembers}">
						<html:option value="${socialEntity.id}">${socialEntity.name} ${socialEntity.firstName}</html:option>
					</c:forEach>
				</html:select>
            </td>
            
            <td>
				
				<html:button property="" onclick="Deplacer(this.form.memberListLeft,this.form.memberListRight)">
				    <bean:message key="groups.addMembers" />
				</html:button>
					
				
			</td>	
			
			<td ROWSPAN="2">
				<html:select property="memberListRight" styleClass="select" size="5" multiple="multiple">
					<html:option value="" disabled="true"><bean:message key="groups.listMember"/></html:option>
					<html:option value=""><bean:message key="groups.listMember"/>1</html:option>
					<html:option value=""><bean:message key="groups.listMember"/>2</html:option>
					<html:option value=""><bean:message key="groups.listMember"/>3</html:option>
					<c:forEach var="socialEntity" items="${allMembers}">
						<html:option value="">  </html:option>
					</c:forEach>
				</html:select>
            </td>	
			
		</tr>		
		
		<tr>	
		    <td>
				<html:button property="" onclick="Deplacer(this.form.memberListRight,this.form.memberListLeft)">
				    <bean:message key="groups.removeMembers" />
				</html:button>
			</td>
		</tr>	
		
		
		<tr>
			<td ROWSPAN="2">
				<label for="groups"> 
					<bean:message key="groups.groups" />
				</label>
			</td>
			
			<td ROWSPAN="2">
				<html:select property="groupListLeft" styleClass="select" size="5" multiple="multiple">
					<html:option value="" disabled="true"><bean:message key="groups.listGroup"/></html:option>
					<html:option value=""><bean:message key="groups.listGroup"/>4</html:option>
					<html:option value=""><bean:message key="groups.listGroup"/>5</html:option>
					<html:option value=""><bean:message key="groups.listGroup"/>6</html:option>
					<c:forEach var="socialEntity" items="${allMembers}">
						<html:option value="${socialEntity.id}">${socialEntity.name} ${socialEntity.firstName}</html:option>
					</c:forEach>
				</html:select>
            </td>
            
            <td>
				
				<html:button property="" onclick="Deplacer(this.form.groupListLeft,this.form.groupListRight)">
				    <bean:message key="groups.addGroups" />
				</html:button>
					
				
			</td>	
			
			<td ROWSPAN="2">
				<html:select property="groupListRight" styleClass="select" size="5" multiple="multiple">
					<html:option value="" disabled="true"><bean:message key="groups.listGroup"/></html:option>
					<html:option value=""><bean:message key="groups.listGroup"/>1</html:option>
					<html:option value=""><bean:message key="groups.listGroup"/>2</html:option>
					<html:option value=""><bean:message key="groups.listGroup"/>3</html:option>
					<c:forEach var="socialEntity" items="${allMembers}">
						<html:option value="">  </html:option>
					</c:forEach>
				</html:select>
            </td>	
			
		</tr>		
		
		<tr>	
		    <td>
				<html:button property="" onclick="Deplacer(this.form.groupListRight,this.form.groupListLeft)">
				    <bean:message key="groups.removeGroups" />
				</html:button>
			</td>
		</tr>	
		
		
		<tr class="errorMessage">
            <td colspan="2">
                <html:errors property="socialEntityId"/>
            </td>
        </tr>
				
		<tr>
			<td colspan="2">
				<html:submit styleClass="button">
					<bean:message key="groups.validate" />
				</html:submit>
			</td>
		</tr>
		
	</table>
</html:form>

<script type="text/javascript">

function Deplacer(l1,l2) {
	
  if(l1.options.selectedIndex>=0)
	 for (var i = l1.options.length-1; i >=0 ; i--) {
	     if(l1.options[ i ].selected){
			o=new Option(l1.options[i].text,l1.options[i].value);
			l2.options[l2.options.length]=o;
			l1.options[i]=null;
		}
  }
  else{
	alert("Aucun membre sélectionnée");
  }
}
	
</script>