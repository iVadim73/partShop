<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>

    <form method="post" action="/controller">
        <input type="hidden" name="command" value="update_brand" />
        <input type="hidden" name="brandId" value="${brandId}">
        <fmt:message key="name"/>: <input type="text" name="name" pattern="^[\w\-)( ]{2,45}$" placeholder="<fmt:message key="name"/>" value="${name}" required><br/>
        <fmt:message key="country"/>: <input type="text" name="country" pattern="^[\w\-)( ]{2,45}$" placeholder="<fmt:message key="country"/>" value="${country}" required><br/>
        <fmt:message key="info"/>: <input type="text" name="info" pattern=".{0,300}" placeholder="<fmt:message key="info"/>" value="${info}"><br/>
        <c:choose>
            <c:when test="${isActive}"><input type="checkbox" name="active" value="is active" checked> <fmt:message key="isActive"/><br/></c:when>
            <c:otherwise><input type="checkbox" name="active" value="is active"> <fmt:message key="isActive"/><br/></c:otherwise>
        </c:choose>
        <button type="submit"><fmt:message key="update"/></button>
    </form>

</body>
<jsp:include page="/common/footer.jsp"/>