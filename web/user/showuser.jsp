<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/common/header.jsp"/>
<body>
<table border="1">
    <tr>
        <form method="post" action="/controller">
            <input type="hidden" name="command" value="update_password"/>
            <input type="text" name="oldPassword" pattern="^[\w\-]{6,18}$" placeholder="old password"><br/>
            <input type="text" name="newPassword1" pattern="^[\w\-]{6,18}$" placeholder="new password"><br/>
            <input type="text" name="newPassword2" pattern="^[\w\-]{6,18}$" placeholder="repeat new password"><br/>
            <button type="submit">change password</button>
        </form>
    </tr>
    <tr>
        <td>
            <form method="post" action="/controller">
                <input type="hidden" name="command" value="update_user_data"/>
                <input type="text" name="phone" pattern="^(\+?\d{12})|(\d{11})|(\d{7})$" placeholder="phone" value="${phone}"><br/>
                <input type="text" name="name" pattern="^[\w\- ]{2,35}$" placeholder="name" value="${name}"><br/>
                <button type="submit">change</button>
            </form>
        </td>
    </tr>
    <tr>
        <td>${email}</td>
    </tr>
    <tr>
        <td>${discount}</td>
    </tr>
    <tr>
        <td>${bill}</td>
    </tr>
</table>
</body>
<jsp:include page="/common/footer.jsp"/>