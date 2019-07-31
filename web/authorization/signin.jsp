<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/common/header.jsp"/>
    <body>
        <form method="post" action="/controller">
            <input type="hidden" name="command" value="signin"/>
            <input type="text" name="login" pattern="^[\w\-]{3,20}$" placeholder="login" value="${login}"><br/>
            <input type="password" name="password" pattern="^[\w\-]{6,18}$" placeholder="password" value="${password}"><br/>
            <button type="submit">sign in</button>
        </form>
        Don't have an account? <a href="registration.jsp">registration</a>
    </body>
<jsp:include page="/common/footer.jsp"/>
</html>
