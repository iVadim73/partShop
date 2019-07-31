<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/common/header.jsp"/>
<body>
<form method="post" action="/controller">
    <input type="hidden" name="command" value="update_part"/>
    <input type="hidden" name="partId" value="${partId}">
    <input type="text" name="catalogNo" pattern="^[\w\-)( ]{2,45}$" placeholder="catalogNo" value="${catalogNo}"><br/>
    <input type="text" name="originalCatalogNo" pattern="^[\w\-)( ]{2,45}$" placeholder="originalCatalogNo" value="${originalCatalogNo}"><br/>
    <input type="text" name="info" pattern="^.{0,300}$" placeholder="info" value="${info}"><br/>
    <input type="text" name="price" pattern="^[\d]{1,19}([,.][\d]{1,4})?$" placeholder="price" value="${price}"><br/>
    <%--<input type="text" name="pictureUrl" pattern="" placeholder="pictureUrl" value="${}"><br/>--%>
    <input type="text" name="wait" pattern="^\d{1,3}$" placeholder="wait" value="${wait}"><br/>
    <select name="brandId">
        <c:forEach items="${brands}" var="foreachbrand">
            <c:choose>
                <c:when test="${foreachbrand.brandId == brand.brandId}"><option selected value="${foreachbrand.brandId}">${foreachbrand.name}</option></c:when>
                <c:otherwise><option value="${foreachbrand.brandId}">${foreachbrand.name}</option></c:otherwise>
            </c:choose>

        </c:forEach>
    </select><br/>
    <input type="text" name="stockCount" pattern="^\d{1,3}$" placeholder="stockCount" value="${stockCount}"><br/>
    <c:choose>
        <c:when test="${isActive}"><input type="checkbox" name="active" value="is active" checked> is active?<br/></c:when>
        <c:otherwise><input type="checkbox" name="active" value="is active"> is active?<br/></c:otherwise>
    </c:choose>
    <%--<input type="checkbox" name="active" value="is active"> is active?<br/>--%>
    <button type="submit">update</button>
</form>
</body>
<jsp:include page="/common/footer.jsp"/>
</html>
