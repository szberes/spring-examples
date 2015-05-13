<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
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

<sec:authorize var="loggedIn" access="isAuthenticated()">
    <%--<c:choose>--%>
    <%--<c:when test="${loggedIn}">--%>
    <%--<%= request.getUserPrincipal().getName() %>--%>
    <span class="userName">UserName: <c:out value="<%= request.getUserPrincipal().getName() %>"/></span>
    <sf:form method="POST" commandName="newMessage">
        Message: <sf:textarea type="text" name="message" path="message" rows="3" cols="50"/><br/>
        <input type="submit" value="Submit"/>
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
    </sf:form>
    <%--</c:when>--%>
    <%--<c:otherwise>--%>
    <%--</c:otherwise>--%>
    <%--</c:choose>--%>
</sec:authorize>

<sec:authorize access="isAnonymous()">
    Log in to post messages <a href="<s:url value="/login" />">Login</a>
</sec:authorize>

</body>
</html>