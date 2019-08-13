<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lt" uri="customTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>
    <table border="0">
        <tr>
            <td>
                <form action="/controller">
                    <input type="hidden" name="command" value="search_user"/>
                    <input type="text" name="partOfUserLogin" pattern="^[\w\-]{1,20}$" placeholder="<fmt:message key="login"/>">
                    <button type="submit"><fmt:message key="search"/>search</button>
                </form>
            </td>
        </tr>
    </table>
    <c:choose>
        <c:when test="${users.isEmpty()}">
            <fmt:message key="noUser"/>
        </c:when>
        <c:otherwise>
            <table border="1">
                <tr>
                    <td><fmt:message key="login"/></td>
                    <td><fmt:message key="email"/></td>
                    <td><fmt:message key="name"/></td>
                    <td><fmt:message key="role"/></td>
                    <td><fmt:message key="discount"/></td>
                    <td><fmt:message key="star"/></td>
                    <td><fmt:message key="bill"/></td>
                    <td><fmt:message key="isActive"/></td>
                </tr>
                <c:forEach begin="${10 * (pageCount - 1)}" end="${10 * pageCount - 1}" items="${users}" var="user">
                    <tr>
                        <td>${user.login}</td>
                        <td>${user.email}</td>
                        <td>${user.name}</td>
                        <td>${user.role.type}</td>
                        <td>${user.discount}</td>
                        <td>${user.star}</td>
                        <td><lt:fractionalNumber number="${user.bill}"/></td>
                        <td>${user.isActive}</td>
                        <td>
                            <form method="post" action="/controller">
                                <input type="hidden" name="command" value="show_user_for_seller_and_admin"/>
                                <input type="hidden" name="userId" value="${user.userId}"/>
                                <button type="submit"><fmt:message key="more"/></button>
                            </form>
                        </td>
                        <td>
                            <form method="post" action="/controller">
                                <input type="hidden" name="command" value="activate_deactivate_user"/>
                                <input type="hidden" name="userId" value="${user.userId}">
                                <button type="submit"><fmt:message key="activate/deactivate"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <lt:pageList pageCount="${pageCount}" elementCount="${users.size()}" command="show_all_user"/>
        </c:otherwise>
    </c:choose>
</body>
<jsp:include page="/common/footer.jsp"/>