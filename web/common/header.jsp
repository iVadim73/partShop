<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <ftm:setLocale value="${language}" scope="session"/>
    <ftm:setBundle basename="text"/>
    <table border="1">
        <tr>
            <td rowspan="2"><a href="/index.jsp">logo</a></td>
            <td><fmt:message key="header.menu1"/></td>
            <td>меню2</td>
            <td>menu3</td>
            <td rowspan="2">
                <c:choose>
                    <c:when test="${userLogin != null}">
                        <jsp:include page="/authorization/signout.jsp"/>
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="/authorization/authorization.jsp"/>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <form action="/controller">
                    <input type="hidden" name="command" value="search_part"/>
                    <input type="text" name="partOfPartCatalogNo" pattern="^[\w\-)( ]{1,45}$" placeholder="catalog no">
                    <button type="submit">search part</button>
                </form>
            </td>
        </tr>
    </table>
</head>
${condition}
