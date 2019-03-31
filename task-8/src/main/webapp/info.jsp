<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <script src="script.js"></script>
    <script>
        window.onload = function () {
            let a = document.getElementById('upgrade');
            a.onclick = function () {
                let jsonService = {
                    "name": document.getElementById('serviceLogin').innerText,
                    "password": document.getElementById('servicePassword').innerText
                };
                let json = JSON.stringify(jsonService);
                request = new XMLHttpRequest();
                request.open("POST", "http://localhost:8080/task_8_war/api/registration/upgrade");
                request.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                request.onreadystatechange = function () {
                    if (request.readyState === 4 && request.status === 200){
                        let answer = JSON.parse(request.responseText);
                        document.getElementById('status').innerText = answer.status;
                        document.getElementById('requestCount').innerText = answer.requestCount;
                        // let a = document.getElementById("upgrade");
                        a.parentNode.removeChild(a);
                    }
                };
                request.send(json);
            }
        }
    </script>
    <title>Vneklasniki</title>
</head>
<body>
<div class="header">
    User:&nbsp;
    <c:out value="${service.login}" default="Guest"/>&nbsp;

    <c:if test="${service == null}">
        <a href="javascript:disp(document.getElementById('login'))">Login</a>&nbsp;&nbsp;
    </c:if>
    <c:if test="${service != null}">
        <form name="logout" id="logout" method="POST" ACTION="<c:url value='/logout'/>">
            <input type="hidden" name="processType" value="logout">
            <a href="javascript:logOut()">Logout</a>
        </form>
    </c:if>
    <hr>
    <br>
</div>
<form name="authForm" id="login" action="<c:url value='/auth'/>" method="post" style="display: none;">
    <label for="log">login</label>&nbsp;&nbsp;
    <input type="text" name="login" id="log" class="text"/>
    <label for="password">password</label>&nbsp;&nbsp;
    <input type="password" name="password" id="password" class="text"/>
    <input type="submit" value="LogIn"/>
</form>
<c:if test="${service != null}">
    <i>login:</i>&nbsp;<i id="serviceLogin">${service.login}</i><br>
    <i>password:</i>&nbsp;<i id="servicePassword">${service.password}</i><br>
    <i>Status:</i>&nbsp;<i id="status">${service.status}</i><br>
    <i>RequestCount:</i>&nbsp;<i id="requestCount">${service.requestCount}</i><br>
    <i>Token:</i>&nbsp;<i>${token}</i><br>
    <c:if test="${service.status eq 'Standard'}">
        <a href="#" id="upgrade">upgrade</a>
    </c:if>
</c:if>
<c:out value="${errorMessage}"/>
</body>
</html>
