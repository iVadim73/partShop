<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/common/header.jsp"/>
<body>
<table border="0">
    <tr>
        <td>
            <form action="/controller">
                <input type="hidden" name="command" value="search_user"/>
                <input type="text" name="partOfUserLogin" pattern="^[\w\-]{3,20}$" placeholder="user login">
                <button type="submit">search</button>
            </form>
        </td>
    </tr>
</table>
<table border="1">
    <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${users}" var="user">
        <tr>
            <td>${user.login}</td>
            <td>${user.email}</td>
            <td>${user.name}</td>
            <td>${user.role.type}</td>
            <td>${user.discount}</td>
            <td>${user.star}</td>
            <td>${user.bill}</td>
            <td>${user.isActive}</td>
            <td>
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="show_user_for_seller_and_admin"/>
                    <input type="hidden" name="userId" value="${user.userId}"/>
                    <button type="submit">more</button>
                </form>
            </td>
            <td>
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="activate_deactivate_user"/>
                    <input type="hidden" name="userId" value="${user.userId}">
                    <button type="submit">activate/deactivate</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<lt:pageList pageCount="${pageCount}" elementCount="${users.size()}" command="show_all_user"/>
</body>
<jsp:include page="/common/footer.jsp"/>