<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<h3>Update Profile</h3>
<html:form action="/ModifyProfile">
    <table id="ModifyProfile">
        <tr>
            <td>
                <label for="name">Name :</label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="name" value="${currentUser.nom}"/>
            </td>
        </tr>
        <tr>
            <td>
                <label for="firstName">FirstName :</label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="firstName"  value="${currentUser.prenom}"/>
            </td>
        </tr>
        <tr>
            <td>
                <label for="adress">Address :</label>
            </td>
            <td>
                <html:textarea errorStyleClass="error" property="adress" value="${currentUser.adresse}"/>
            </td>
        </tr>
        <tr>
            <td>
                <label>Inscription date :</label>
            </td>
            <td>
                <html:textarea errorStyleClass="error" property="dateEntree" value="${currentUser.dateEntree}" disabled="true"/>
            </td>
        </tr>
        <tr>
            <td>
                <label for="dateOfBirth">Birth date :</label>
            </td>
            <td>
                <!-- TODO ajouter un calendrier avec ajax -->
                <html:text errorStyleClass="error" property="dateOfBirth" value="${currentUser.dateNaissance}"/>
            </td>
        </tr>
        <tr>
            <td>
                <label for="sexe">Sexe :</label>
            </td>
            <td>
                <!-- TODO refaire avec les constante de entité social -->
                <html:select property="sexe">
                    <html:option value="Man">Man</html:option>
                    <html:option value="Woman">Woman</html:option>
                </html:select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="pwd">Password :</label>
            </td>
            <td><html:password property="pwd" value="${currentUser.mdp}"/></td>
        </tr>
        <tr>
            <td>
                <label for="confirmPwd">Confirm password :</label>
            </td>
            <td><html:password property="confirmPwd" value="${currentUser.mdp}"/></td>
        </tr>
        <tr>
            <td>
                <label for="job">Job :</label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="job" value="${currentUser.profession}"/>
            </td>
        </tr>
        <tr>
            <td>
                <label for="mail">Email :</label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="mail" value="${currentUser.email}" readonly="true"/>
            </td>
        </tr>
        <tr>
            <td>
                <label for="phone">Phone number :</label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="phone" value="${currentUser.numTel}"/>
            </td>
        </tr>
        <tr> <td/>
            <td><html:submit>Validate</html:submit></td>
        </tr>
    </table>

</html:form>      