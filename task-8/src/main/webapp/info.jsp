<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <script src="script.js"></script>
    <title>Vneklasniki</title>
</head>
<body>
<%--<script lang="javaScript">--%>
    <%--import requst from 'script.js'--%>
    <%--if (request === null) {--%>
        <%--request = new XMLHttpRequest();--%>
        <%--request.open("GET", "http://localhost:8080/task_8_war/start");--%>
        <%--request.setRequestHeader('Content-type', 'application/json; charset=utf-8');--%>
        <%--request.onreadystatechange = function () {--%>
            <%--if (request.readyState === 4 && request.status === 200){--%>
                <%--document.getElementById("output").innerHTML = "";--%>
                <%--document.getElementById("output").innerHTML = request.responseText;--%>
            <%--}--%>
        <%--};--%>
        <%--request.send();--%>
    <%--}--%>
<%--</script>--%>
<div class="header">
    User:&nbsp;
    <c:out value="${service.login}" default="Guest"/>&nbsp;

    <c:if test="${request.responseText == null}">
        <a href="javascript:disp(document.getElementById('login'))">Login</a>&nbsp;&nbsp;
        <a href="javascript:disp(document.getElementById('reg'))">Reg</a>
    </c:if>
    <c:if test="${request.responseText != null}">
        <a href="javascript:">logout</a>
    </c:if>
    <hr>
    <br>
</div>
<form name="authForm" id="login" method="post" style="display: none;">
    <label for="name">login</label>&nbsp;&nbsp;
    <input type="text" name="username" id="name" class="text"/>
    <label for="password">password</label>&nbsp;&nbsp;
    <input type="password" name="password" id="password" class="text"/>
    <a href="javascript:login()">submit</a>
</form>
<form name="regForm" id="reg" method="post" style="display: none;">
    <br>
    <label> User
        <input type="checkbox" class="check-box" value="user" title="User">
    </label>&nbsp;&nbsp;
    <label> Group
        <input type="checkbox" class="check-box" value="group" title="Group">
    </label>&nbsp;&nbsp;
    <label> Friend
        <input type="checkbox" class="check-box" value="friend" title="Friend">
    </label>&nbsp;&nbsp;
    <label> Message
        <input type="checkbox" class="check-box" value="message" title="Message">
    </label>&nbsp;&nbsp;
    <label> Present
        <input type="checkbox" class="check-box" value="present" title="Present">
    </label><br><br><br>
    <label for="username">login</label>&nbsp;&nbsp;
    <input type="text" name="username" id="username" class="text"/>
    <a href="javascript:reg()">submit</a>
</form>
<div id="output"><div id="json"></div> </div>
</body>
</html>
