<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<ftm:setBundle basename="text"/>
<jsp:include page="/common/header.jsp"/>
<body>
    <form method="post" action="/controller">
        <input type="hidden" name="command" value="add_part"/>
        <fmt:message key="catalogNo"/>: <input type="text" name="catalogNo" pattern="^([\w\-)( ]{2,45})$" placeholder="<fmt:message key="catalogNo"/>" required><br/>
        <fmt:message key="originalCatalogNo"/>: <input type="text" name="originalCatalogNo" pattern="^([\w\-)( ]{2,45}$)" placeholder="<fmt:message key="originalCatalogNo"/>"><br/>
        <fmt:message key="info"/>: <input type="text" name="info" pattern="^.{0,300}$" placeholder="<fmt:message key="info"/>"><br/>
        <fmt:message key="brand"/>: <select name="brandId">
            <c:forEach items="${brands}" var="brand">
                <option value="${brand.brandId}">${brand.name}</option>
            </c:forEach>
        </select><br/>
        <fmt:message key="wait"/>: <input type="text" name="wait" pattern="^(\d{1,3})$" placeholder="<fmt:message key="wait"/>" required><br/>
        <fmt:message key="stockCount"/>:<input type="text" name="stockCount" pattern="^(\d{1,3})$" placeholder="<fmt:message key="stockCount"/>" required><br/>
        <fmt:message key="price"/>: <input type="text" name="price" pattern="^([\d]{1,19}([.,]\d{1,4})?)$" placeholder="<fmt:message key="price"/>" required><br/>
        <input type="checkbox" name="active" value="is active"> <fmt:message key="isActive"/><br/>
        <button type="submit"><fmt:message key="add"/></button>
    </form>
</body>
<jsp:include page="/common/footer.jsp"/>
</html>
