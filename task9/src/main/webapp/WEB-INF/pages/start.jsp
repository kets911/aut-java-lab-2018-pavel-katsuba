<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Library</title>
</head>
<body>
<div class="header">
    User:&nbsp;
    <c:out value="${reader.username}" default="Guest"/>&nbsp;
    <c:if test="${reader == null}">
        <a href="${pageContext.request.contextPath} + login">auth</a>&nbsp;&nbsp;
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
<form name="chooseBooks" id="books" method="post" action="takeBooks">
    <c:forEach var="book" items="${books}">
        <i>${book.id}</i>&nbsp;<i>${book.nameBook}</i>&nbsp;<i>${book.publishingDate}</i>&nbsp;
        <c:if test="${!book.taken}">
            <input type="checkbox" id="bookCheckbox" name="bookCheckbox" value="${book.id}">
        </c:if><br>
        <c:if test="${book.taken}">
            <i>book is taken</i>
        </c:if><br>
    </c:forEach>
    <input type="submit" name="button" value="Take">
</form>
</body>
</html>