<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/common/header.jsp"/>
    <body>
        <form method="post" action="/controller">
            <input type="hidden" name="command" value="registration" />
            <input type="text" name="login" pattern="^[\w\-]{3,20}$" placeholder="login" value="${login}"><br/>
            <input type="password" name="password" pattern="^[\w\-]{6,18}$" placeholder="password" value="${password}"><br/>
            <input type="email" name="email" pattern="^(?=.{5,254}$).{1,64}@.{3,255}$" placeholder="email" value="${email}"><br/>
            <input type="text" name="phone" pattern="^(\+?\d{12})|(\d{11})|(\d{7})$" placeholder="phone" value="${phone}"><br/>
            <input type="text" name="name" pattern="^[\w\- ]{2,35}$" placeholder="name" value="${name}"><br/>
            <button type="submit">register</button>
        </form>
    </body>
<jsp:include page="/common/footer.jsp"/>
</html>
