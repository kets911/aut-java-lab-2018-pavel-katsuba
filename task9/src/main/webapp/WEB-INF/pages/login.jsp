<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Auth</title>
    <script lang="javascript">
        function disp(form) {
            if (form.id === "authorization") {
                document.getElementById('registration').style.display = "none";
                document.getElementById('loginHref').style.display = "none";
                document.getElementById('regHref').style.display = "block";
            } else {
                document.getElementById('authorization').style.display = "none";
                document.getElementById('loginHref').style.display = "block";
                document.getElementById('regHref').style.display = "none";
            }
            form.style.display = "block";
        }
    </script>
</head>

<body>
<div class="header">
    User:&nbsp;
    <c:out value="${reader.username}" default="Guest"/>&nbsp;
    <c:if test="${reader == null}">
        <a id="loginHref" href="javascript:disp(document.getElementById('authorization'))" style="display: none">Login</a>&nbsp;&nbsp;
        <a id="regHref" href="javascript:disp(document.getElementById('registration'))">Registration</a>
    </c:if>
    <c:if test="${reader != null}">
    <form name="logout" method="POST" ACTION="<c:url value='/logout'/>">
        <input type="hidden" name="processType" value="">
        <a href="javascript:document.logout.submit()">Logout</a>
    </form>
    <form name="account" method="POST" ACTION="account">
        <input type="hidden" name="processType" value="">
        <a href="javascript:document.account.submit()">account</a>
    </form>
    </c:if>
</div><br>

<%--<div th:if="${param.error}">--%>
<%--Invalid username and password.--%>
<%--</div>--%>
<c:if test="${error}">
    <h2><c:out value="${message}"/></h2>
    <hr>
</c:if>
<form name="loginForm" id="authorization" method="POST" action="login" style="display: block;">
    <h2>Authorization form</h2>
    Login:<br>
    <input type="text" name="username" value=""><br>
    Password:<br>
    <input type="password" name="password" ><br>
    <%--<input type="checkbox" name="_spring_security_remember_me">--%>
    <input type="submit" name="button" value="Enter">
</form>
<form name="RegForm" id="registration" method="POST" action="registration" style="display: none;">
    <h2>Registration form</h2>
    Login:<br>
    <input type="text" name=login value=""><br>
    Password:<br>
    <input type="password" name=password value=""><br>
    <input type="submit" name="button" value="Reg">
</form>
</body>
</html>