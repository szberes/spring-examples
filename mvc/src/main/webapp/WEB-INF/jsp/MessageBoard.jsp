<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Messages</title>
</head>
<body>
<h1>Messages</h1>

<c:forEach items="${messageList}" var="message">
    <li id="spittle_<c:out value="message.id"/>">
        <div class="messageText">
            <c:out value="${message.text}"/>
        </div>
        <div>
            <span class="time"><c:out value="${message.date}"/></span>
            <span class="userName"><c:out value="${message.userName}"/></span>
        </div>
    </li>
</c:forEach>

<form method="POST">
    Username: <input type="text" name="username"/><br/>
    Message: <input type="text" name="message"/><br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>