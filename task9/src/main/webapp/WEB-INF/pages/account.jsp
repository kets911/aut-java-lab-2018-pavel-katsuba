<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reader card</title>
</head>
<body>
<div class="header">
    <form name="logout" method="POST" ACTION="<c:url value='/logout'/>">
        User:&nbsp;${reader.username}
        <input type="hidden" name="processType" value="">
        <a href="javascript:document.logout.submit()">Logout</a>
    </form>
    <form name="toStart" method="POST" ACTION="<c:url value='/'/>">
        <input type="hidden" name="processType" value="">
        <a href="javascript:document.toStart.submit()">toStart</a>
    </form>
    <hr>
    <br>
    <form id="returnBook" action="returnBook" method="post">
        <c:forEach var="book" items="${books}">
            <i>${book.id}</i>&nbsp;<i>${book.nameBook}</i>&nbsp;<i>${book.publishingDate}</i>&nbsp;
            <input type="checkbox" name="returnCheckbox" value="${book.id}">
            <br>
        </c:forEach>
        <input type="submit" name="button" value="return">
    </form>
</div>
</body>
</html>