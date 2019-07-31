<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/common/header.jsp"/>
<body>
    <form method="post" action="/controller">
        <input type="hidden" name="command" value="update_brand" />
        <input type="hidden" name="brandId" value="${brandId}">
        <input type="text" name="name" pattern="^[\w\-)( ]{2,45}$" placeholder="name" value="${name}"><br/>
        <input type="text" name="country" pattern="^[\w\-)( ]{2,45}$" placeholder="country" value="${country}"><br/>
        <input type="text" name="info" pattern=".{0,300}" placeholder="info" value="${info}"><br/>
        <c:choose>
            <c:when test="${isActive}"><input type="checkbox" name="active" value="is active" checked> is active?<br/></c:when>
            <c:otherwise><input type="checkbox" name="active" value="is active"> is active?<br/></c:otherwise>
        </c:choose>
        <button type="submit">update</button>
    </form>
</body>
<jsp:include page="/common/footer.jsp"/>
</html>